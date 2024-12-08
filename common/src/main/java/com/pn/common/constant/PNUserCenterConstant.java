package com.pn.common.constant;

/**
 * 系统相关常量
 * @author: javadadi
 * @Time: 18:28
 * @ClassName: PNUserCenterConstant
 */
public interface PNUserCenterConstant {
    /**
     * 基本请求路径
     */
    static final String BASE_URL = "/v1/usercenter/server/";
    /**
     * 用户登陆标识
     */
    static final String USER_LOGIN = "pn_user_login::";
    /**
     * 验证码标识
     */
    static final String CODE_TAG = "user_code::";
    /**
     * 用户密码盐
     */
    static final String USER_PASSWORD_SLOT = "user_pass_slot_pn_usercenter";
    /**
     * 验证码过期时间
     */
    static final   long CAPTCHA_EXPIRE_TIME = 5*60L;
    /**
     * token 过期时间
     */
    static final long JWT_EXPIRE_TIME = 3  * 60 * 60 * 1000L;
    /**
     * jwt secret
     */
    public static final String JWT_SECRET_KEY = "pn_user_center_jwt";
}
