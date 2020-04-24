package com.taotao.pojo;

import java.io.Serializable;
import java.util.List;

public class LayuiTbItem implements Serializable {
    /**
     * layui.put("code", 0);
     layui.put("msg", "");
     layui.put("count", count);
     layui.put("data", tbItems);
     */
    private int code;
    private String msg;
    private Integer count;
    private List<?> data;

    public LayuiTbItem() {
    }

    public LayuiTbItem(int code, String msg, Integer count, List<?> data) {

        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    @Override
    public String toString() {
        return "LayuiTbItem{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", data=" + data +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
