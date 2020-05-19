package com.taotao.cart.controller;

import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
                if (tbItem.getId() == itemId.longValue()){
                    tbItem.setNum(tbItem.getNum()+num);
                    b = true;
                    break;
                }
            }
        }
        if (!b){
            /*在cookie中没有该商品信息*/
            TbItem tbItem = itemService.findTbItemById(itemId);
            String images = tbItem.getImage();
            String[] split = images.split("http");
            tbItem.setImage("http"+split[1]);
            //判断用户收藏的数量是否大于已有库存
            if (tbItem.getNum() >= num){
                tbItem.setNum(num);
            }
            cartList.add(tbItem);
        }
        CookieUtils.setCookie(request,response,"TT_CART",JsonUtils.objectToJson(cartList),60*60*24*7,true);
        return "cartSuccess";
    }

    @RequestMapping("/cart")
    public String showCart(Model model,HttpServletRequest request){
        List<TbItem> cartList = getCartList(request);
        model.addAttribute("cartList",cartList);
        int sum = 0;
        for (TbItem item:cartList) {
            sum+=item.getNum();
        }
        model.addAttribute("sum",sum);
        return "cart";
    }

    @RequestMapping("/delete/{itemId}")
    public String deleteCart(@PathVariable Long itemId,HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException {
        List<TbItem> cartList = getCartList(request);
        for (int i = 0; i < cartList.size(); i++) {
            if (cartList.get(i).getId() == itemId.longValue()){
                cartList.remove(i);
                break;
            }
        }
        CookieUtils.setCookie(request,response,"TT_CART",JsonUtils.objectToJson(cartList),60*60*24*7,true);
        return "redirect:/cart/cart.html";
    }

    @RequestMapping("/update/num/{itemId}/{num}")
    public String updateCart(ModelAndView view, @PathVariable Long itemId, @PathVariable int num, HttpServletRequest request, HttpServletResponse response){
        List<TbItem> cartList = getCartList(request);
        for (int i = 0; i < cartList.size(); i++) {
            if (cartList.get(i).getId() == itemId.longValue()){
                TbItem tbItem = cartList.get(i);
                tbItem.setNum(num);
                cartList.set(i,tbItem);
                break;
            }
        }
        CookieUtils.setCookie(request,response,"TT_CART",JsonUtils.objectToJson(cartList),60*60*24*7,true);
        return "cart";
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
