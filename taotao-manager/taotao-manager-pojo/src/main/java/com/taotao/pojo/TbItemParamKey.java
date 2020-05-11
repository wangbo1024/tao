package com.taotao.pojo;

import java.io.Serializable;

public class TbItemParamKey implements Serializable {
    private int id;
    private String paramName;
    private int groupId;
    private TbItemParamGroup tbItemParamGroup;
    private TbItemParamValue tbItemParamValue;

    @Override
    public String toString() {
        return "TbItemParamKey{" +
                "id=" + id +
                ", paramName='" + paramName + '\'' +
                ", groupId=" + groupId +
                '}';
    }

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

    public TbItemParamGroup getTbItemParamGroup() {
        return tbItemParamGroup;
    }

    public void setTbItemParamGroup(TbItemParamGroup tbItemParamGroup) {
        this.tbItemParamGroup = tbItemParamGroup;
    }

    public TbItemParamValue getTbItemParamValue() {
        return tbItemParamValue;
    }

    public void setTbItemParamValue(TbItemParamValue tbItemParamValue) {
        this.tbItemParamValue = tbItemParamValue;
    }
}
