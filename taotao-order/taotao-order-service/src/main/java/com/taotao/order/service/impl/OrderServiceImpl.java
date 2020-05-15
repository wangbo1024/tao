package com.taotao.order.service.impl;

import com.taotao.constant.Constant;
import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.OrderInfo;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;
import com.taotao.order.service.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private TbOrderMapper orderMapper;
    @Autowired
    private TbOrderShippingMapper orderShippingMapper;
    @Autowired
    private TbOrderItemMapper orderItemMapper;
    @Autowired
    private JedisClient jedisClient;
    /**
     * itemId: 158918563970770
     orderItems[0].num:
     orderItems[0].price:
     orderItems[0].totalFee:
     orderItems[0].title:
     orderItems[0].picPath
     */
    @Override
    public TaotaoResult addTbOrderItemAndOrderShipping(OrderInfo orderInfo) {
        if (!jedisClient.exists(Constant.ORDER_ID_KEY)){
            jedisClient.set(Constant.ORDER_ID_KEY,Constant.ORDER_ID_INIT.toString());
        }
        String orderId = jedisClient.incr(Constant.ORDER_ID_KEY).toString();
        Date date = new Date();
        orderInfo.setOrderId(orderId);
        orderInfo.setPostFee("0");
        //状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
        orderInfo.setStatus(1);
        orderInfo.setCreateTime(date);
        orderInfo.setUpdateTime(date);
        //创建订单
        int i = orderMapper.insert(orderInfo);
        if (i <= 0){
            return TaotaoResult.build(500,"提交订单失败");
        }
        List<TbOrderItem> orderItems = orderInfo.getOrderItems();
        for (TbOrderItem orderItem:orderItems) {
            Long orderItemId = jedisClient.incr(Constant.ORDER_ITEM_ID);
            orderItem.setOrderId(orderId);
            orderItem.setId(orderItemId.toString());
            int j = orderItemMapper.insert(orderItem);
            if (j <= 0){
                return TaotaoResult.build(500,"提交订单失败");
            }
        }
        TbOrderShipping orderShipping = orderInfo.getOrderShipping();
        orderShipping.setCreated(date);
        orderShipping.setUpdated(date);
        orderShipping.setOrderId(orderId);
        int k = orderShippingMapper.insert(orderShipping);
        if (k <= 0){
            return TaotaoResult.build(500,"提交订单失败");
        }
        return TaotaoResult.ok(orderId);
    }
}
