package com.taotao.controller;

import com.taotao.pojo.TbItemCatResult;
import com.taotao.service.ItemCatService;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/itemCat")
public class ItemCatController {
    @Autowired
    private ItemCatService itemService;
    /**
     * http://localhost:8081/itemCat/showZtree
     */
    @RequestMapping("/showZtree")
    @ResponseBody
    public List<TbItemCatResult> showZtree(Long id){
        List<TbItemCatResult> result = itemService.showZtree(id);
        return result;
    }
}
