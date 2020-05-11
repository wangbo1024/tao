package com.taotao.service;

import com.taotao.pojo.TaotaoResult;

public interface ItemGroupService {
    /**
     * 根据cId查询商品规格参数组
     * @param cId
     * @return
     */
    TaotaoResult findItemGroup(Long cId);

    /**
     * 添加商品规格参数模板
     * @param cId
     * @param params
     * @return
     */
    TaotaoResult addGroup(Long cId, String params);
}
