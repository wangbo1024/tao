package com.taotao.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamMapper;
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
    @Autowired
    private TbItemParamMapper itemParamMapper;

    /**
     * 根据商品id查询商品信息
     * @param itemId
     * @return
     */
    @Override
    public TbItem findTbItemById(Long itemId) {
        TbItem tbItem = itemMapper.findTbItemById(itemId);
        return tbItem;
    }

    /**
     * 查询所有商品信息并分页
     * @param pageno 当前页码
     * @param pagesize  每页显示的数据条数
     * @return
     */
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

    /**
     * 修改商品的状态
     * @param tbItem  需要修改的商品
     * @param type  商品的状态
     * @param update   更新时间
     * @return
     */
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

    /**
     * 多条件搜索商品
     * @param page 当前页码
     * @param limit 每页显示商品数量
     * @param title 商品标题信息
     * @param priceMin 价格区间最低
     * @param priceMax 价格区间最高
     * @param cId 商品的分类id
     * @return
     */
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

    /**
     * 添加商品图片
     * @param filename
     * @param bytes
     * @return
     */
    @Override
    public ImageDataResult addImage(String filename, byte[] bytes) {
        String endpoint = "http://oss-cn-chengdu.aliyuncs.com";
        String accessKeyId = "";
        String accessKeySecret = "";
        String bucketName = "clive123";
        String objectName = "images/";
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName,objectName+filename,bis);
        ImageDataResult result = new ImageDataResult();
        result.setCode(0);
        result.setMsg("");
        ImageData data = new ImageData();
        data.setSrc("https://"+bucketName+".oss-cn-chengdu.aliyuncs.com/images/"+filename);
        result.setData(data);
        return result;
    }

    /**
     * 添加商品信息、商品描述信息以及商品规格参数信息
     * @param tbItem
     * @param itemDesc
     * @return
     */
    @Override
    public TaotaoResult addItem(TbItem tbItem, String itemDesc, String[] paramKeyIds, String[] paramValues) {
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
        /*添加商品对应的规格参数信息*/
        int count = 0;
        for (int k = 0; k < paramKeyIds.length; k++) {
            String paramKeyId = paramKeyIds[k];
            int paramId = Integer.parseInt(paramKeyId);
            String paramValue = paramValues[k];
            count = itemParamMapper.addTbItemParamValue(itemId,paramId,paramValue);
        }
        if (count <= 0){
            return TaotaoResult.build(500,"添加商品规格参数信息失败");
        }
        return TaotaoResult.build(200,"添加商品信息成功");
    }


}
