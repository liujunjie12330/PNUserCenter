package com.pn.service.utils;

import com.github.houbb.sensitive.word.core.SensitiveWordHelper;

/**
 * 敏感词过滤工具类
 * @author javadadi
 */
public class SensitiveUtil {
    /**
     * 检查是否含有敏感字
     *
     * @param text
     * @return
     */
    public static boolean check(String text) {
        return SensitiveWordHelper.contains(text);
    }

    /**
     * 替换敏感字符
     * @param text
     * @param targetChar
     * @return
     */
    public static String replace(String text, Character targetChar) {
        return SensitiveWordHelper.replace(text, targetChar);
    }
}
