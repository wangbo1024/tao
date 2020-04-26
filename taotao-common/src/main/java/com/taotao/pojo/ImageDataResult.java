package com.taotao.pojo;

import java.io.Serializable;

public class ImageDataResult implements Serializable {
    private int code;
    private String msg;
    private ImageData data;

    @Override
    public String toString() {
        return "ImageDataResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
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

    public ImageData getData() {
        return data;
    }

    public void setData(ImageData data) {
        this.data = data;
    }
}
