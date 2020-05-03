package com.taotao.service;

import com.taotao.pojo.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ItemService {
    TbItem findTbItemById(Long itemId);

    /**
     * 查询tbItem表，进行分页展示
     * @param pageno 当前页码
     * @param pagesize  每页显示的数据条数
     * @return layui格式的item数据
     */
    LayuiTbItem findAllTbItem(String pageno, String pagesize);

    /**
     * 修改tbItem表的数据，根据商品id修改商品的状态和更新时间
     * @param tbItem  需要修改的商品
     * @param type  商品的状态
     * @param update   更新时间
     * @return
     */
    TaotaoResult updateTbItem(List<TbItem> tbItem, int type, Date update);

    LayuiTbItem searchItem(Integer page, Integer limit, String title, Integer priceMin, Integer priceMax, Long cId);

    ImageDataResult addImage(String filename, byte[] bytes);

    TaotaoResult addItem(TbItem tbItem, String itemDesc, String[] paramKeyIds, String[] paramValue);
}
