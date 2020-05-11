package com.taotao.service.impl;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.*;
import com.taotao.service.ItemCatService;
import com.taotao.service.JedisClient;
import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemCatMapper tbItemCatMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("ITEMCAT")
    private String ITEMCAT;

    /**
     * 展示商品分类树形结构
     * @param id
     * @return
     */
    @Override
    public List<TbItemCatResult> showZtree(Long id) {
        if (id == null){
            id = 0l;
        }
        List<TbItemCatResult> result = tbItemMapper.showZtree(id);
        return result;
    }

    /**
     *
     * @return
     */
    @Override
    public ItemCatDataResult showItemCat() {
        ItemCatDataResult result = new ItemCatDataResult();
        String json = jedisClient.get(ITEMCAT);
        if(StringUtils.isNotBlank(json)){
            List list = JsonUtils.jsonToPojo(json,List.class);
            result.setData(list);
            return result;
        }
        //商品类目展示 加入缓存
        List list = getItemCatList(0L);
        result.setData(list);
        jedisClient.set(ITEMCAT,JsonUtils.objectToJson(list));
        return result;
    }

    private List getItemCatList(Long parentId){
        List list = new ArrayList();
        int count = 0;
        List<TbItemCat> itemCats =  tbItemCatMapper.findTbItemCatByParentId(parentId);
        for (TbItemCat tbItemCat : itemCats) {
            ItemCat itemCat = new ItemCat();
            /*判断该类目是否是父级类目*/
            if (tbItemCat.getIsParent()){
                /*判断该类目是几级类目*/
                itemCat.setUrl("/products/"+tbItemCat.getId()+".html");
                if (tbItemCat.getParentId() == 0){
                    /*一级类目*/
                    itemCat.setName("<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
                } else {
                    /*二级类目*/
                    itemCat.setName(tbItemCat.getName());
                }
                count ++;
                /*递归*/
                itemCat.setItem(getItemCatList(tbItemCat.getId()));
                list.add(itemCat);
                if (parentId == 0 && count >= 14){
                    break;
                }
            } else {
                list.add("/products/"+tbItemCat.getId()+".html|"+tbItemCat.getName());
            }
        }
        return list;
    }
}
