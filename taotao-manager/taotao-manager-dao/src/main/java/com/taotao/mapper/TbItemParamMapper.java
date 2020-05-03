package com.taotao.mapper;

import org.apache.ibatis.annotations.Param;

public interface TbItemParamMapper {

    int addTbItemParamValue(@Param("itemId") long itemId,@Param("paramId") int paramId,@Param("paramValue") String paramValue);
}