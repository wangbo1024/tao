package com.taotao.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.*;
import com.taotao.service.ItemService;
import com.taotao.utils.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.*;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Override
    public TbItem findTbItemById(Long itemId) {
        TbItem tbItem = itemMapper.findTbItemById(itemId);
        return tbItem;
    }

    @Override
    public LayuiTbItem findAllTbItem(String pageno, String pagesize) {
        /*当前页码*/
        Integer page = Integer.parseInt(pageno);
        /*每页显示数据条数*/
        Integer size = Integer.parseInt(pagesize);
        /*每页开始数据的索引*/
        Integer start = (page - 1) * size;
        List<TbItem> tbItems = itemMapper.findAllTbItem(start,size);
        Integer count = itemMapper.findTbItemsCount();
        LayuiTbItem layui = new LayuiTbItem();
        layui.setCode(0);
        layui.setCount(count);
        layui.setMsg("");
        layui.setData(tbItems);
        return layui;
    }

    @Override
    public TaotaoResult updateTbItem(List<TbItem> tbItem, int type, Date update) {
        if (tbItem.size() <= 0){
            return TaotaoResult.build(500,"请先选择商品，在操作",null);
        }
        List<Long> ids = new ArrayList<Long>();
        for (TbItem tbItems : tbItem) {
            ids.add(tbItems.getId());
        }
        int count = itemMapper.updateTbItem(ids,type,update);
        if (count > 0 && type == 0){
            return TaotaoResult.build(200,"商品下架成功",null);
        } else if (count > 0 && type == 1){
            return TaotaoResult.build(200,"商品上架成功",null);
        } else if (count > 0 && type == 2){
            return TaotaoResult.build(200,"商品删除成功",null);
        }
        return TaotaoResult.build(500,"商品修改失败",null);
    }

    @Override
    public LayuiTbItem searchItem(Integer page, Integer limit, String title, Integer priceMin, Integer priceMax, Long cId) {
        if (priceMin == null){
            priceMin = 0;
        }
        if (priceMax == null){
            priceMax = 1000000000;
        }
        Integer start = (page - 1) * limit;
        int count = itemMapper.searchItemLikeCount(title,priceMin,priceMax,cId);
        List<TbItem> tbItems = itemMapper.searchItemLike(start,limit,title,priceMin,priceMax,cId);
        LayuiTbItem layui = new LayuiTbItem();
        layui.setCode(0);
        layui.setCount(count);
        layui.setMsg("");
        layui.setData(tbItems);
        return layui;
    }

    @Override
    public ImageDataResult addImage(String filename, byte[] bytes) {
        String endpoint = "http://oss-cn-chengdu.aliyuncs.com";
        String accessKeyId = "LTAI4G9iBuw5JpNUX7R3WiL4";
        String accessKeySecret = "8DvJXb9KFUiHwax09rKPZaxZYqg0va";
        String bucketName = "2016122131";
        String objectName = "images/";
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName,objectName+filename,bis);
        ImageDataResult result = new ImageDataResult();
        result.setCode(0);
        result.setMsg("");
        ImageData data = new ImageData();
        data.setSrc(bucketName+objectName+filename);
        result.setData(data);
        return result;
    }

    @Override
    public TaotaoResult addItem(TbItem tbItem, String itemDesc) {
        /*添加商品基本信息*/
        long itemId = IDUtils.genItemId();
        tbItem.setId(itemId);
        tbItem.setStatus((byte)1);
        Date date = new Date();
        tbItem.setCreated(date);
        tbItem.setUpdated(date);
        int i = itemMapper.addTbItem(tbItem);
        if (i <= 0){
            return TaotaoResult.build(500,"添加商品基本信息失败");
        }
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setItemDesc(itemDesc);
        tbItemDesc.setCreated(date);
        tbItemDesc.setUpdated(date);
        int j = tbItemDescMapper.addTbItemDesc(tbItemDesc);
        if (j <= 0){
            return TaotaoResult.build(500,"添加商品描述信息失败");
        }
        return TaotaoResult.build(200,"添加商品信息成功");
    }


}
