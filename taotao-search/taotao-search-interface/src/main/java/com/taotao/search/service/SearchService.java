package com.taotao.search.service;

import com.taotao.pojo.SearchItem;
import com.taotao.pojo.SearchItemResult;
import com.taotao.pojo.TaotaoResult;

public interface SearchService {
    /**
     * 将商品数据存入solr文档索引库
     * @return
     */
    TaotaoResult importSolr();

    /**
     * 通过用户输入条件查询相关商品信息
     * @param query
     * @param page
     * @return
     */
    SearchItemResult findSearchItemByQuery(String query, Integer page);

    /**
     *
     * @param item
     */
    void addSearchItem(SearchItem item);
}
