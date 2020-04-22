package com.taotao.service.impl;

import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.LayuiTbItem;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


}
