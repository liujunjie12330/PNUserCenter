package com.pn.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
* @author: zhuyu
* @Time: 13:00
* @ClassName: StatusCode
*/
@AllArgsConstructor
@Getter
public enum StatusCode {
    /**
     * 用户中心相关
     */
    USER_NO_REGISTERING(1001, "用户未注册"),
    USER_ALREADY_EXIST(1002, "用户已经存在"),
    USER_NO_LOGIN(1003, "用户未登录"),
    NO_AUTH_ERROR(1004, "无权限"),
    FORBIDDEN_ACCESS(1005, "禁止访问"),
    PARAMS_ERROR(1006, "参数错误"),
    FORMAT_ERROR(1007, "格式错误"),
    CAPTCHA_ERROR(1008, "验证码错误"),
    PASSWORD_ERROR(1009, "密码错误"),
    NO_SUCH_ROLE(1010, "没有该角色"),
    NO_SUCH_PERMISSION(1011, "没有该权限"),
    NO_SUCH_PERMISSION_MESSAGE(1012, "没有该授权信息"),
    PASSWORD_NOT_EQUALS(1013,"两次密码不相同"),
    CONNECT_TIME_OUE(1014,"连接超时"),
    LOGIN_FAILED(1015,"登陆失败"),
    /**
     * 系统相关
     */
    OPERATION_ERROR(5001, "操作失败"),
    SYSTEM_ERROR(5002, "系统错误"),
    /**
     * 成果
     */
    SUCCESS(200, "success");
    /**
     * 状态码
     */
    private final int code;
    /**
     * 错误信息
     */
    private final String message;
}
