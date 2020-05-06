package com.taotao.mapper;

import org.apache.ibatis.annotations.Param;

public interface TbItemParamMapper {

    int addTbItemParamValue(@Param("itemId") long itemId,@Param("paramId") int paramId,@Param("paramValue") String paramValue);
    /*添加规格参数模板组*/
    void addTbItemParamGroup(@Param("itemCatId") Long itemCatId,@Param("groupName") String groupName);

    int findTbItemParamGroupId(@Param("itemCatId") Long itemCatId,@Param("groupName") String groupName);

    void addTbItemParamKey(@Param("paramName") String paramName,@Param("groupId") int groupId);
}