package com.taotao.item.controller;

import com.taotao.item.service.ItemService;
import com.taotao.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;
    @RequestMapping("/{itemId}")
    public String showItem(@PathVariable Long itemId, Model model){
        TbItem tbItem = itemService.getItemById(itemId);
        Item item = new Item(tbItem);
        model.addAttribute("item",item);
        return "item";
    }

    @RequestMapping("/desc/{itemId}")
    @ResponseBody
    public String showItemDesc(@PathVariable Long itemId,Model model) throws UnsupportedEncodingException {
        TbItemDesc tbItemDesc = itemService.getItemDescByItemId(itemId);
        System.out.println("-------------------->");
        System.out.println(tbItemDesc.getItemDesc());
        return tbItemDesc.getItemDesc();
    }
}
