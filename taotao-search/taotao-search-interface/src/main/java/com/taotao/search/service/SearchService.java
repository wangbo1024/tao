package com.taotao.search.service;

import com.taotao.pojo.SearchItemResult;
import com.taotao.pojo.TaotaoResult;

public interface SearchService {
    TaotaoResult importSolr();

    SearchItemResult findSearchItemByQuery(String query, Integer page);
}
