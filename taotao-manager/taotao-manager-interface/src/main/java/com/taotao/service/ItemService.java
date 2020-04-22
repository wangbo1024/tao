package com.taotao.service;

import com.taotao.pojo.LayuiTbItem;
import com.taotao.pojo.TbItem;

import java.util.List;
import java.util.Map;

public interface ItemService {
    TbItem findTbItemById(Long itemId);

    LayuiTbItem findAllTbItem(String pageno, String pagesize);

}
