package com.pn.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则校验工具
 *
 * @author: zhuyu
 * @Time: 10:04
 * @ClassName: UserValidate
 */
public class RegularUtil {
    /**
     * 账户由数字和字母组成，且长度为4-16位。
     */
    private static final Pattern USERACCOUNT_PATTERN = Pattern.compile("^[a-zA-Z0-9][a-zA-Z0-9_]{3,15}$");
    /**
     * 邮箱正则
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    /**
     * 密码正则,以字母开头，长度在8~16之间，只能包含字母、数字和下划线
     */
    public static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z]\\w{7,15}$");
    /**
     * 电话号码正则
     */
    public static final Pattern PHONE_PATTERN = Pattern.compile("^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$");

    /**
     * 校验用户账户是否合格:数字字母开头，4-16位
     *
     * @param userAccount
     * @return
     */
    public static boolean isAccount(String userAccount) {
        if (StringUtils.isAnyBlank(userAccount)) {
            return false;
        }
        Matcher matcher = USERACCOUNT_PATTERN.matcher(userAccount);
        return matcher.matches();
    }

    /**
     * 校验邮箱是否合格
     */
    public static boolean isEmail(String email) {
        if (StringUtils.isAnyBlank(email)) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    /**
     * 密码校验：以字母开头，长度在8~16之间，只能包含字母、数字和下划线
     *
     * @param userPassword
     * @return
     */
    public static boolean isPassword(String userPassword) {
        if (StringUtils.isAnyBlank(userPassword)) {
            return false;
        }
        Matcher matcher = PASSWORD_PATTERN.matcher(userPassword);
        return matcher.matches();
    }

    /**
     * 电话号码校验：电话号码正则
     *
     * @param phone
     * @return boolen
     */
    public static boolean isPhone(String phone) {
        if (StringUtils.isAnyBlank(phone)) {
            return false;
        }
        Matcher matcher = PHONE_PATTERN.matcher(phone);
        return matcher.matches();
    }
}
