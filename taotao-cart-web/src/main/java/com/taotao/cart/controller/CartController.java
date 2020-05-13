package com.taotao.cart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//http://localhost:8089/cart/add/158891840575418.html?num=1
@Controller
@RequestMapping("/cart")
public class CartController {
    @RequestMapping("/add/{itemId}")
    public String addCart(Integer num){

        return null;
    }
}
