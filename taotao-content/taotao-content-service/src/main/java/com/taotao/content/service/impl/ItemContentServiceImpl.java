package com.taotao.content.service.impl;

import com.taotao.content.service.ItemContentService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.Ad1Node;
import com.taotao.pojo.LayuiTbItem;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbItemCatResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class ItemContentServiceImpl implements ItemContentService {
    @Autowired
    private TbContentCategoryMapper categoryMapper;
    @Autowired
    private TbContentMapper contentMapper;

    /**
     * 展示TbContentCategory树形分类
     * @param cId
     * @return
     */
    @Override
    public List<TbItemCatResult> showContentZtree(Long cId) {
        if (cId == null){
            cId = 0l;
        }
        List<TbItemCatResult> result = categoryMapper.findTbContentCategoryByParentId(cId);
        return result;
    }

    /**
     * 展示相应分类的TbContent数据
     * @param id
     * @param page
     * @param limit
     * @return
     */
    @Override
    public LayuiTbItem showContentTable(Long id, Integer page, Integer limit) {
        LayuiTbItem result = new LayuiTbItem();
        Integer start = (page - 1) * limit;
        List<TbContent> tbContents = contentMapper.findTbContentByPage(id,start,limit);
        int count = contentMapper.findTbContentByParentIdAndCount(id);
        result.setCode(0);
        result.setMsg("");
        result.setCount(count);
        result.setData(tbContents);
        return result;
    }

    /**
     * 删除选中内容
     * @param tbContents
     * @param page
     * @param limit
     * @return
     */
    @Override
    public LayuiTbItem deleteContentByCategoryId(List<TbContent> tbContents, Integer page, Integer limit) {
        LayuiTbItem result = new LayuiTbItem();
        result.setCode(0);
        result.setMsg("");
        result.setCount(0);
        result.setData(null);
        if (tbContents.size() <= 0){
            /*证明用户没有选择数据*/
            return result;
        }
        int i = contentMapper.deleteContentByCategoryId(tbContents);
        if (i <= 0){
            /*没有执行到删除语句，即删除失败*/
            return result;
        }
        Long categoryId = tbContents.get(0).getCategoryId();
        int count = contentMapper.findTbContentByParentIdAndCount(categoryId);
        if (count <= 0){
            /*证明用户已经删除了全部的内容*/
            return result;
        }
        result.setCount(count);
        List<TbContent> data = contentMapper.findTbContentByPage(categoryId, (page - 1) * limit, limit);
        result.setData(data);
        return result;
    }

    @Override
    public LayuiTbItem addContent(TbContent tbContent, Integer page, Integer limit) {
        LayuiTbItem result = new LayuiTbItem();
        result.setCode(0);
        result.setMsg("");
        result.setCount(0);
        result.setData(null);
        Date date = new Date();
        tbContent.setCreated(date);
        tbContent.setUpdated(date);
        contentMapper.addContent(tbContent);
        Long categoryId = tbContent.getCategoryId();
        int count = contentMapper.findTbContentByParentIdAndCount(categoryId);
        result.setCount(count);
        List<TbContent> data = contentMapper.findTbContentByPage(categoryId, (page - 1) * limit, limit);
        result.setData(data);
        return result;
    }

    @Override
    public List<Ad1Node> showAd1Node() {
        List<Ad1Node> result = new ArrayList<Ad1Node>();
        List<TbContent> tbContents = contentMapper.findTbContentByPage(89L, 0, 10);
        for (TbContent tbContent : tbContents) {
            Ad1Node ad1Node = new Ad1Node();
            ad1Node.setSrcB(tbContent.getPic2());
            ad1Node.setHeight(240);
            ad1Node.setAlt("");
            ad1Node.setWidth(670);
            ad1Node.setSrc(tbContent.getPic());
            ad1Node.setWidthB(550);
            ad1Node.setHref(tbContent.getUrl());
            ad1Node.setHeightB(240);
            result.add(ad1Node);
        }
        return result;
    }


}
