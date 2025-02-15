package com.pn.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 类型
 */
@Getter
@AllArgsConstructor
public enum IntegralType {
    LOGIN("login");

    /**
     * -- GETTER --
     * 获取动作的字符串表示。
     *
     * @return 动作名称
     */
    private final String actionName;

    /**
     * 根据字符串值查找对应的枚举常量。
     *
     * @param actionName 动作名称
     * @return 对应的UserAction枚举常量
     * @throws IllegalArgumentException 如果没有匹配的枚举常量
     */
    public static IntegralType fromString(String actionName) {
        for (IntegralType action : IntegralType.values()) {
            if (action.actionName.equalsIgnoreCase(actionName)) {
                return action;
            }
        }
        throw new IllegalArgumentException("No enum constant with actionName " + actionName);
    }
}
