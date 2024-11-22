package com.pn.common.utils;


import com.pn.common.base.BaseResponse;
import com.pn.common.enums.StatusCode;

/**
 * 返回工具类
 * @author liujunjie
 */
public class ResultUtils {

    /**
     * 成功,只设置返回数据
     * @param data  自定义返回数据
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(StatusCode.SUCCESS.getCode(), data, "ok");
    }


    /**
     * 成功带提示信息
     * @param data  返回数据
     * @param message  自定义返回信息
     */
    public static <T> BaseResponse<T> success(T data,String message) {
        return new BaseResponse<>(StatusCode.SUCCESS.getCode(), data, message);
    }
    /**
     * 失败 自己设置返回响应码
     * @param statusCode  系统响应状态码
     */
    public static BaseResponse error(StatusCode statusCode) {
        return new BaseResponse<>(statusCode);
    }

    /**
     * 失败带数据
     * @param data 数据
     * @param message  自定义返回信息
     * @param code 状态码
     */
    public static<T> BaseResponse<T> error(T data ,int code, String message){
        return  new BaseResponse<T>(code,data,message);
    }

    /**
     * 失败 自定义状态码，异常信息
     * @param code 状态码
     * @param message 异常信息
     */
    public static BaseResponse error(int code, String message) {
        return new BaseResponse(code, null, message);
    }

    /**
     * 失败
     * @param message 异常信息
     * @param statusCode 系统状态码
     *
     */
    public static BaseResponse error(StatusCode statusCode, String message) {
        return new BaseResponse(statusCode.getCode(), null, message);
    }
}
