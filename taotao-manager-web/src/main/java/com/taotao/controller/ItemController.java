package com.taotao.controller;

import com.taotao.pojo.LayuiTbItem;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;
    @RequestMapping("/{itemId}")
    @ResponseBody
    public TbItem findTbItem(@PathVariable Long itemId){
        TbItem result = itemService.findTbItemById(itemId);
        return result;
    }
    @RequestMapping("/showItemPage")
    @ResponseBody
    public LayuiTbItem showItem(@RequestParam(value="page") String pageno, @RequestParam (value="limit") String pagesize){
        LayuiTbItem tbItems = itemService.findAllTbItem(pageno,pagesize);
//        System.out.println("pageno:"+pageno+"||pagesize:"+pagesize);
        return tbItems;
    }

}
