package com.taotao.order.controller;

import com.taotao.order.service.OrderService;
import com.taotao.pojo.*;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("/order-cart")
    public String showOrder(Model model, HttpServletRequest request, HttpServletResponse response){
        List<TbItem> orderList = getOrderList(request);
        if (orderList == null){
            return "";
        }
        model.addAttribute("cartList",orderList);
        return "order-cart";
    }

    @RequestMapping("/create")
    public String createOrder(OrderInfo orderInfo,HttpServletRequest request){
        TbUser user = (TbUser) request.getAttribute("user");
        Long userId = user.getId();
        orderInfo.setUserId(userId);
        TaotaoResult result = orderService.addTbOrderItemAndOrderShipping(orderInfo);
        String orderId = result.getData().toString();
        request.setAttribute("orderId",orderId);
        request.setAttribute("payment",orderInfo.getPayment());
        DateTime dateTime = new DateTime();
        dateTime = dateTime.plusDays(3);
        request.setAttribute("date", dateTime.toString("yyyy-MM-dd"));
        return "success";
    }

    /**
     * 获取cookie中的商品列表
     * @param request
     * @return
     */
    private List<TbItem> getOrderList(HttpServletRequest request){
        String json = CookieUtils.getCookieValue(request, "TT_CART", true);
        if (StringUtils.isNotBlank(json)){
            List<TbItem> tbItems = JsonUtils.jsonToList(json, TbItem.class);
            return tbItems;
        }
        return null;
    }
}
