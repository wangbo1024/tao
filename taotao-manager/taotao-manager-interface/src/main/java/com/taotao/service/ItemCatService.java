package com.taotao.service;

import com.taotao.pojo.ItemCatDataResult;
import com.taotao.pojo.ItemCatStatistics;
import com.taotao.pojo.TbItemCatResult;

import java.util.List;

public interface ItemCatService {
    List<TbItemCatResult> showZtree(Long id);

    ItemCatDataResult showItemCat();

    List<ItemCatStatistics> statisticsItem();

}
