package com.taotao.cart.controller;

import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

//http://localhost:8089/cart/add/158891840575418.html?num=1
@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("/add/{itemId}")
    public String addCart(@PathVariable Long itemId, Integer num, HttpServletResponse response, HttpServletRequest request){
        List<TbItem> cartList = getCartList(request);
        boolean b = false;
        if (cartList.size() > 0){
            for (TbItem tbItem:cartList) {
                if (tbItem.getId() == itemId){
                    tbItem.setNum(num);
                    b = true;
                    break;
                }
            }
        }
        if (!b){
            /*在cookie中没有该商品信息*/
            TbItem tbItem = itemService.findTbItemById(itemId);
            String image = tbItem.getImage();
        }
        return null;
    }

    /**
     * 根据cookie的key取value得到购物车中的商品列表
     * @param request
     * @return
     */
    private List<TbItem> getCartList(HttpServletRequest request){
        String json = CookieUtils.getCookieValue(request, "TT_CART", true);
        if (StringUtils.isNotBlank(json)){
            List <TbItem> lists = JsonUtils.jsonToList(json, TbItem.class);
            return lists;
        }
        return new ArrayList<TbItem>();
    }
}
