package com.xiaobu.constant;


import lombok.Data;

@Data
public class ApiResult {

    private Integer code;

    private String message;

    private Object data;

    public ApiResult(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


}
