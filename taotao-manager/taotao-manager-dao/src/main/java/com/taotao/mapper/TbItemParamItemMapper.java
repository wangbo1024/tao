package com.taotao.mapper;


import com.taotao.pojo.ItemParamGroup;
import com.taotao.pojo.ItemParamKey;

import java.util.List;

public interface TbItemParamItemMapper {

    List<ItemParamGroup> findParamGroupByCId(Long cId);

    List<ItemParamKey> findParamKeyByGroupId(int id);
}