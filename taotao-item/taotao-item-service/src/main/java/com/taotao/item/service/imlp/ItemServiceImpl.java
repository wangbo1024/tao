package com.taotao.item.service.imlp;

import com.taotao.item.service.ItemService;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService{
    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Override
    public TbItem getItemById(Long itemId) {
        TbItem tbItemById = tbItemMapper.findTbItemById(itemId);
        return tbItemById;
    }

    @Override
    public TbItemDesc getItemDescByItemId(Long itemId) {
        TbItemDesc tbItemDesc = itemDescMapper.getItemDescByItemId(itemId);
        return tbItemDesc;
    }
}
