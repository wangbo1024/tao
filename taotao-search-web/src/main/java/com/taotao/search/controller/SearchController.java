package com.taotao.search.controller;

import com.taotao.pojo.SearchItem;
import com.taotao.pojo.SearchItemResult;
import com.taotao.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    @RequestMapping("/search")
    public String showSearch(@RequestParam("q")String q, @RequestParam(value = "page",defaultValue = "1") Integer page, Model model) throws UnsupportedEncodingException {
        String query = new String(q.getBytes("iso-8859-1"),"utf-8");
        SearchItemResult result = searchService.findSearchItemByQuery(query,page);
        model.addAttribute("query", query);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("itemList", result.getItemList());
        model.addAttribute("totalCount",result.getTotalCount());
        model.addAttribute("page", page);
        return "search";
    }
}
