package com.taotao.content.service;

import com.taotao.pojo.Ad1Node;
import com.taotao.pojo.LayuiTbItem;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbItemCatResult;

import java.util.List;

public interface ItemContentService {
    /**
     * 展示TbContentCategory树形分类
     * @param cId
     * @return
     */
    List<TbItemCatResult> showContentZtree(Long cId);

    /**
     * 展示相应分类的TbContent数据
     * @param id
     * @param page
     * @param limit
     * @return
     */
    LayuiTbItem showContentTable(Long id, Integer page, Integer limit);

    /**
     * 删除选中的内容
     * @param tbContents
     * @param page
     * @param limit
     * @return
     */
    LayuiTbItem deleteContentByCategoryId(List<TbContent> tbContents, Integer page, Integer limit);

    /**
     * 添加内容信息
     * @param tbContent
     * @param page
     * @param limit
     * @return
     */
    LayuiTbItem addContent(TbContent tbContent, Integer page, Integer limit);

    List<Ad1Node> showAd1Node();
}
