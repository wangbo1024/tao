package com.taotao.controller;

import com.taotao.pojo.LayuiTbItem;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemCatResult;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


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
        return tbItems;
    }
    @RequestMapping("/itemDelete")
    @ResponseBody
    public TaotaoResult itemDelete(@RequestBody List<TbItem> tbItem){
        Date update = new Date();
        TaotaoResult result = itemService.updateTbItem(tbItem,2,update);
        return result;
    }
    @RequestMapping("/commodityUpperShelves")
    @ResponseBody
    public TaotaoResult commodityUpperShelves(@RequestBody List<TbItem> tbItem){
        Date update = new Date();
        TaotaoResult result = itemService.updateTbItem(tbItem,1,update);
        return result;
    }
    @RequestMapping("/commodityLowerShelves")
    @ResponseBody
    public TaotaoResult commodityLowerShelves(@RequestBody List<TbItem> tbItem){
        Date update = new Date();
        TaotaoResult result = itemService.updateTbItem(tbItem,0,update);
        return result;
    }
}
