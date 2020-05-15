package com.taotao.mapper;


import com.taotao.pojo.OrderInfo;
import com.taotao.pojo.TbOrderItem;

public interface TbOrderMapper {

    int insert(OrderInfo orderInfo);
}