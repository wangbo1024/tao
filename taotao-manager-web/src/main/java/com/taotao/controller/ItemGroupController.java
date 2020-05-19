package com.taotao.controller;


import com.taotao.pojo.TaotaoResult;
import com.taotao.service.ItemGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/itemGroup")
@Api(value = "商品分类",tags = "ItemGroupController",description = "商品分类")
public class ItemGroupController {
    @Autowired
    private ItemGroupService itemGroupService;
    @RequestMapping("/showItemGroup")
    @ResponseBody
    @ApiOperation(value = "根据商品分类id查询所属分类",notes = "根据商品分类id查询所属分类")
    public TaotaoResult showItemGroup(Long cId){
        TaotaoResult result = itemGroupService.findItemGroup(cId);
        return result;
    }

    @RequestMapping("/addGroup")
    @ResponseBody
    @ApiOperation(value = "根据商品分类id查询所属分类",notes = "根据商品分类id查询所属分类")
    public TaotaoResult addGroup(Long cId,String params){
        TaotaoResult result = itemGroupService.addGroup(cId,params);
        return result;
    }
}
