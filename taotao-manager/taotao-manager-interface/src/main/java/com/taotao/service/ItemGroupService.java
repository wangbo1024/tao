package com.taotao.service;

import com.taotao.pojo.TaotaoResult;

public interface ItemGroupService {

    TaotaoResult findItemGroup(Long cId);

    TaotaoResult addGroup(Long cId, String params);
}