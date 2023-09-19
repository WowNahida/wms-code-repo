package com.syh.common;

import lombok.Data;

/**
 * @author 孙远航
 * @version 1.0
 * 对后端返回的json数据格式封装
 */
@Data
public class Result {
    //编码 200、400
    private int code;

    //成功/失败
    private String msg;

    //查询的总记录数
    private Long total;

    //数据
    private Object data;

    //失败
    public static Result fail() {
        return res(400, "失败", 0L, null);
    }

    //成功且不返回数据
    public static Result succeed() {
        return res(200, "成功", 0L, null);
    }

    //成功且返回数据
    public static Result succeed(Object data) {
        return res(200, "成功", 0L, data);
    }

    //成功且返回数据和条数
    public static Result succeed(Long total,Object data) {
        return res(200, "成功", total, data);
    }

    public static Result res(int code,String msg,Long total,Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setTotal(total);
        result.setData(data);
        return result;
    }



}
