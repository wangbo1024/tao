package com.taotao.service.impl;

import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.ItemParamGroup;
import com.taotao.pojo.ItemParamKey;
import com.taotao.pojo.TaotaoResult;
import com.taotao.service.ItemGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemGroupServiceImpl implements ItemGroupService {
    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;
    @Override
    public TaotaoResult findItemGroup(Long cId) {
        List<ItemParamGroup> groupList = tbItemParamItemMapper.findParamGroupByCId(cId);
        if (groupList.size() <= 0){
            return TaotaoResult.build(500,"查询规格参数组失败");
        }
        for (ItemParamGroup itemParamGroup: groupList) {
            List<ItemParamKey> paramKeyList = tbItemParamItemMapper.findParamKeyByGroupId(itemParamGroup.getId());
            itemParamGroup.setParamKeys(paramKeyList);
            if (paramKeyList.size() <= 0){
                return TaotaoResult.build(500,"查询规格参数项失败");
            }
        }
        return TaotaoResult.build(200,"有规格参数模板",groupList);
    }
}
