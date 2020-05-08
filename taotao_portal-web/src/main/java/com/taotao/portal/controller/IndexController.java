package com.taotao.portal.controller;

import com.taotao.content.service.ItemContentService;
import com.taotao.pojo.Ad1Node;
import com.taotao.pojo.ItemCatDataResult;
import com.taotao.service.ItemCatService;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class IndexController {
    @Autowired
    private ItemCatService itemCatService;
    @Autowired
    private ItemContentService itemContentService;

    /**
     * 前端首页广告展示
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String showIndex(Model model){
        List<Ad1Node> ad1Nodes = itemContentService.showAd1Node();
        String ad1 = JsonUtils.objectToJson(ad1Nodes);
        model.addAttribute("ad1",ad1);
        return "index";
    }

    /**
     * 前端页面树形分类展示
     * @return
     */
    @RequestMapping("/itemcat/all")
    @ResponseBody
    public String showItemCat(){
        ItemCatDataResult result = itemCatService.showItemCat();
        String json = JsonUtils.objectToJson(result);
        return json;
    }
}
