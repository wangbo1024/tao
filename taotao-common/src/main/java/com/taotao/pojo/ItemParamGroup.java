package com.taotao.pojo;

import java.io.Serializable;
import java.util.List;

public class ItemParamGroup implements Serializable {
    private int id;
    private String groupName;
    private Long itemCatId;
    private List<ItemParamKey> paramKeys;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getItemCatId() {
        return itemCatId;
    }

    public void setItemCatId(Long itemCatId) {
        this.itemCatId = itemCatId;
    }

    public List<ItemParamKey> getParamKeys() {
        return paramKeys;
    }

    public void setParamKeys(List<ItemParamKey> paramKeys) {
        this.paramKeys = paramKeys;
    }

    @Override
    public String toString() {
        return "ItemParamGroup{" +
                "id=" + id +
                ", groupName='" + groupName + '\'' +
                ", itemCatId=" + itemCatId +
                ", paramKeys=" + paramKeys +
                '}';
    }
}
