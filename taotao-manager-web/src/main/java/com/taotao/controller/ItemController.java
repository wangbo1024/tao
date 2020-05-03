package com.taotao.controller;

import com.taotao.pojo.*;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    /**
     * 根据商品id查询商品信息
     * @param itemId
     * @return
     */
    @RequestMapping("/{itemId}")
    @ResponseBody
    public TbItem findTbItem(@PathVariable Long itemId){
        TbItem result = itemService.findTbItemById(itemId);
        return result;
    }

    /**
     * 分页展示商品信息
     * @param pageno
     * @param pagesize
     * @return
     */
    @RequestMapping("/showItemPage")
    @ResponseBody
    public LayuiTbItem showItem(@RequestParam(value="page") String pageno, @RequestParam (value="limit") String pagesize){
        LayuiTbItem tbItems = itemService.findAllTbItem(pageno,pagesize);
        return tbItems;
    }

    /**
     * 删除商品
     * @param tbItem
     * @return
     */
    @RequestMapping("/itemDelete")
    @ResponseBody
    public TaotaoResult itemDelete(@RequestBody List<TbItem> tbItem){
        Date update = new Date();
        TaotaoResult result = itemService.updateTbItem(tbItem,2,update);
        return result;
    }

    /**
     * 上架商品
     * @param tbItem
     * @return
     */
    @RequestMapping("/commodityUpperShelves")
    @ResponseBody
    public TaotaoResult commodityUpperShelves(@RequestBody List<TbItem> tbItem){
        Date update = new Date();
        TaotaoResult result = itemService.updateTbItem(tbItem,1,update);
        return result;
    }

    /**
     * 下架商品
     * @param tbItem
     * @return
     */
    @RequestMapping("/commodityLowerShelves")
    @ResponseBody
    public TaotaoResult commodityLowerShelves(@RequestBody List<TbItem> tbItem){
        Date update = new Date();
        TaotaoResult result = itemService.updateTbItem(tbItem,0,update);
        return result;
    }

    //http://localhost:8081/item/searchItem

    /**
     * page: 1
     limit: 10
     title:
     priceMin:
     priceMax:
     cId
     * @return
     */
    @RequestMapping("/searchItem")
    @ResponseBody
    public LayuiTbItem searchItem(Integer page,Integer limit,String title,Integer priceMin,Integer priceMax,Long cId){
        LayuiTbItem result = itemService.searchItem(page,limit,title,priceMin,priceMax,cId);
        return result;
    }

    @RequestMapping("/fileUpload")
    @ResponseBody
    public ImageDataResult fileUpLoad(MultipartFile file){
        try {
            String filename = file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            ImageDataResult result = itemService.addImage(filename,bytes);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加商品信息
     * @param tbItem
     * @param itemDesc
     * @return
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public TaotaoResult addItem(TbItem tbItem, String itemDesc,@RequestParam("paramKeyIds[]") String[] paramKeyIds,@RequestParam("paramValue[]") String[] paramValue){
        TaotaoResult result = itemService.addItem(tbItem,itemDesc,paramKeyIds,paramValue);
        return result;
    }
}
