package com.taotao.service.impl;

import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.LayuiTbItem;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemCatResult;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper itemMapper;
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
    public List<TbItemCatResult> showZtree(Long id) {
        if (id == null){
            id = 0l;
        }
        List<TbItemCatResult> result = itemMapper.showZtree(id);
        return result;
    }

    @Override
    public LayuiTbItem searchItem(Integer page, Integer limit, String title, Integer priceMin, Integer priceMax, Long cId) {
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


}
