package com.taotao.mapper;

import com.taotao.pojo.ItemCat;
import com.taotao.pojo.TbItemCat;

import java.util.List;

public interface TbItemCatMapper {

    List<TbItemCat> findTbItemCatByParentId(Long parentId);
}