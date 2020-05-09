package com.taotao.service.impl;

import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.mapper.TbItemParamMapper;
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
    @Autowired
    private TbItemParamMapper tbItemParamMapper;

    /**
     * 根据cId查询商品规格参数组
     * @param cId
     * @return
     */
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

    @Override
    public TaotaoResult addGroup(Long cId, String params) {
        if (params == null || params.contains("")){
            return TaotaoResult.build(500,"请输入规格参数信息");
        }
        String[] clives = params.split("clive");
        int count = 0;
        for (int i = 0; i < clives.length; i++) {
            String[] group = clives[i].split(",");
            tbItemParamMapper.addTbItemParamGroup(cId,group[0]);
            int groupId = tbItemParamMapper.findTbItemParamGroupId(cId,group[0]);
            for (int j = 1; j < group.length; j++) {
                tbItemParamMapper.addTbItemParamKey(group[j],groupId);
                count ++;
            }
        }
        if (count <= 0){
            return TaotaoResult.build(500,"规格参数模板添加失败");
        }
        return TaotaoResult.build(200,"规格参数模板添加成功");
    }
}
