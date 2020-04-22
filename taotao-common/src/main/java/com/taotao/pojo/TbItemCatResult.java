package com.taotao.pojo;

import java.io.Serializable;

public class TbItemCatResult implements Serializable {
    private Long id;
    private String name;
    private Byte isParent;

    @Override
    public String toString() {
        return "TbItemCatResult{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isParent=" + isParent +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getIsParent() {
        return isParent;
    }

    public void setIsParent(Byte isParent) {
        this.isParent = isParent;
    }
}
