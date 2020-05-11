package com.taotao.pojo;

import java.io.Serializable;
import java.util.List;

public class TbItemParamGroup implements Serializable {
    private int id;
    private String groupName;
    private Long itemCatId;
    private List<TbItemParamKey> paramKeys;

    @Override
    public String toString() {
        return "TbItemParamGroup{" +
                "id=" + id +
                ", groupName='" + groupName + '\'' +
                ", itemCatId=" + itemCatId +
                '}';
    }

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

    public List<TbItemParamKey> getParamKeys() {
        return paramKeys;
    }

    public void setParamKeys(List<TbItemParamKey> paramKeys) {
        this.paramKeys = paramKeys;
    }
}
