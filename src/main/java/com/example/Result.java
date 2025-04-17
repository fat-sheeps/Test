package com.example;

import lombok.Data;

@Data
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    // 用于成功返回
    public static <T> Result<T> success(T data) {
        return new Result<>(0, "success", data);
    }

    // 用于失败返回
    public static <T> Result<T> fail(int code, String msg) {
        return new Result<>(code, msg, null);
    }
    public static <T> Result<T> fail(String msg) {
        return new Result<>(-1, msg, null);
    }
}
