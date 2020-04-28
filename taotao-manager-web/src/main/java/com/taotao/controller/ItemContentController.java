package com.taotao.controller;

import com.taotao.content.service.ItemContentService;
import com.taotao.pojo.LayuiTbItem;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbItemCatResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/content")
public class ItemContentController {
    @Autowired
    private ItemContentService itemContentService;
    @RequestMapping("/showContentZtree")
    @ResponseBody
    public List<TbItemCatResult> showContentZtree(Long id){
        List<TbItemCatResult> results = itemContentService.showContentZtree(id);
        return results;
    }
    @RequestMapping("/showContentTable")
    @ResponseBody
    public LayuiTbItem showContentTable(Long categoryId,Integer page,Integer limit){
        LayuiTbItem result = itemContentService.showContentTable(categoryId,page,limit);
        return result;
    }
    @RequestMapping("/deleteContentByCategoryId")
    @ResponseBody
    public LayuiTbItem deleteContentByCategoryId(@RequestBody List<TbContent> tbContents, @RequestParam(value = "page",defaultValue = "1")Integer page,@RequestParam(value = "limit",defaultValue = "10")Integer limit){
        LayuiTbItem result = itemContentService.deleteContentByCategoryId(tbContents,page,limit);
        return result;
    }
    @RequestMapping("/addContent")
    @ResponseBody
    public LayuiTbItem addContent(TbContent tbContent,@RequestParam(value = "page",defaultValue = "1")Integer page,@RequestParam(value = "limit",defaultValue = "10")Integer limit){
        LayuiTbItem result = itemContentService.addContent(tbContent,page,limit);
        return result;
    }
}
