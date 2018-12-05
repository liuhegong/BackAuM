package com.chankin.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/*
 *
 * 响应报文结构基类
 *
 * */
@ApiModel
public class Result {
    /**
     * 响应码
     */
    @ApiModelProperty(value = "响应码", required = true, position = 0)
    private int code;

    /**
     * 响应消息
     */
    @ApiModelProperty(value = "响应消息", required = true, position = 1)
    private String msg;

    /*
     *  实体类
     * */
    @ApiModelProperty(value = "数据对象", required = false)
    private Object data = "";

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static Result instance(int code, String msg) {
        return new Result(code, msg);
    }

    public static Result success() {
        return new Result(ResponseCode.success.getCode(), ResponseCode.success.getMsg());
    }

    public static Result success(Object data) {
        return new Result(ResponseCode.success.getCode(), ResponseCode.success.getMsg(), data);
    }

    public static Result error() {
        return new Result(ResponseCode.error.getCode(), ResponseCode.error.getMsg());
    }

    public static Result error(String msg) {
        return new Result(ResponseCode.error.getCode(), msg);
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
