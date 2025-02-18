package com.pn.common.base;


import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.vos.login.UserVo;

import java.util.Objects;

public class UserTokenThreadHolder {

    /**
     * 保存用户对象的ThreadLocal
     */
    private static final ThreadLocal<UserVo> userThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<String> tokenThreadLocal = new ThreadLocal<>();

    /**
     * 添加当前登录用户方法
     */
    public static void addCurrentUser(UserVo user) {
        userThreadLocal.set(user);
    }

    public static void addToken(String token) {
        tokenThreadLocal.set(token);
    }

    public static String getToken() {
        return tokenThreadLocal.get();
    }

    public static UserVo getCurrentUser() {
        UserVo userVo = userThreadLocal.get();
        if (Objects.isNull(userVo)) {
            throw new BizException(StatusCode.USER_NO_LOGIN);
        }
        return userVo;
    }


    /**
     * 防止内存泄漏
     */
    public static void remove() {
        userThreadLocal.remove();
        tokenThreadLocal.remove();
    }

}
