package com.taotao.pojo;

import java.io.Serializable;

public class TbItemParamValue implements Serializable {
    private Long itemId;
    private int paramId;
    private String paramValue;

    @Override
    public String toString() {
        return "TbItemParamValue{" +
                "itemId=" + itemId +
                ", paramId=" + paramId +
                ", paramValue='" + paramValue + '\'' +
                '}';
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public int getParamId() {
        return paramId;
    }

    public void setParamId(int paramId) {
        this.paramId = paramId;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }
}
