package com.taotao.pojo;

import java.io.Serializable;

public class ItemParamKey implements Serializable {
    private int id;
    private String paramName;
    private int groupId;
    private String paramGroup;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getParamGroup() {
        return paramGroup;
    }

    public void setParamGroup(String paramGroup) {
        this.paramGroup = paramGroup;
    }

    @Override
    public String toString() {
        return "ItemParamKey{" +
                "id=" + id +
                ", paramName='" + paramName + '\'' +
                ", groupId=" + groupId +
                ", paramGroup='" + paramGroup + '\'' +
                '}';
    }
}
