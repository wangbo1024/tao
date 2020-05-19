package com.taotao.controller;

import com.taotao.pojo.TaotaoResult;
import com.taotao.search.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/search")
@Api(value = "搜索功能接口", tags = "SearchController", description = "搜索功能接口")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @RequestMapping("/importSolr")
    @ResponseBody
    @ApiOperation(value = "一键导入solr索引库", notes = "一键导入solr索引库")
    public TaotaoResult importSolr(){
        TaotaoResult result = searchService.importSolr();
        return result;
    }

}
