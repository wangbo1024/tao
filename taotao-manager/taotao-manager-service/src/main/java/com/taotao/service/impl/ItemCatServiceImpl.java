package com.taotao.service.impl;

import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItemCatResult;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemMapper tbItemMapper;
    @Override
    public List<TbItemCatResult> showZtree(Long id) {
        if (id == null){
            id = 0l;
        }
        List<TbItemCatResult> result = tbItemMapper.showZtree(id);
        return result;
    }
}
