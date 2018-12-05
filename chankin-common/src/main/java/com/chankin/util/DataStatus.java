package com.chankin.util;

public enum DataStatus {
    normal(1, "正常"),
    delete(2, "已删除"),
    forbidden(3, "已禁用");
    private int code;
    private String msg;

    DataStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
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
}
