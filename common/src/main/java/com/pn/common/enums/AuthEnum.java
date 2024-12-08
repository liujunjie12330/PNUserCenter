package com.pn.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: javadadi
 * @Time: 19:53
 * @ClassName: AuthEnum
 */
@Getter
@AllArgsConstructor
public enum AuthEnum {
    GITHUB("github","github"),
    GITEE("gitee","gitee");
    private final String name;
    private final String description;
}
