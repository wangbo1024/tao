package com.taotao.order.service;

import com.taotao.pojo.OrderInfo;
import com.taotao.pojo.TaotaoResult;

public interface OrderService {
    TaotaoResult addTbOrderItemAndOrderShipping(OrderInfo orderInfo);
}
