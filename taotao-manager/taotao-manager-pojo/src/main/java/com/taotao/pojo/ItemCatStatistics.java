package com.taotao.pojo;

import java.io.Serializable;

public class ItemCatStatistics implements Serializable {
    private int value;
    private String name;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ItemCatStatistics{" +
                "value=" + value +
                ", name='" + name + '\'' +
                '}';
    }
}
