package com.taotao.mapper;

import com.taotao.pojo.TbItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TbItemMapper {
    TbItem findTbItemById(Long itemId);

    Integer findTbItemsCount();

    List<TbItem> findAllTbItem(@Param("start") Integer  start, @Param("size") Integer size);

}