package com.taotao.mapper;


import com.taotao.pojo.TbItemCatResult;

import java.util.List;

public interface TbContentCategoryMapper {

    List<TbItemCatResult> findTbContentCategoryByParentId(Long cId);
}