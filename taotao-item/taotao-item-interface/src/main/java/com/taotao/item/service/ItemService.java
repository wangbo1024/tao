package com.taotao.item.service;

import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

public interface ItemService {
    /**
     * 根据商品id查询商品信息
     * @param itemId
     * @return
     */
    TbItem getItemById(Long itemId);

    TbItemDesc getItemDescByItemId(Long itemId);
}
