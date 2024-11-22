package com.pn.common.base;


import com.pn.common.enums.StatusCode;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回类
 * @author: javadadi
 * @Time: 0:09
 * @ClassName: BaseResponse
 */
@Data
public class BaseResponse<T> implements Serializable {
    /**
     * 状态码
     */
    private int code;
    /**
     * 返回数据
     */
    private T data;
    /**
     * 返回提示信息
     */
    private String msg;
    private static final long serialVersionUID = 3545683664055413614L;

    /**
     * 自己构建返回模板
     * @param code
     * @param data
     * @param msg
     */
    public BaseResponse(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    /**
     * 快速构建
     * @param statusCode
     */
    public BaseResponse(StatusCode statusCode){
        this(statusCode.getCode(),null, statusCode.getMessage());
    }

    /**
     * 不需要返回提示
     * @param code
     * @param data
     */
    public BaseResponse(int code, T data) {
        this(code,data,"");
    }
}
