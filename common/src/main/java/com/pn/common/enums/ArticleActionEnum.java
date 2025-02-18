package com.pn.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: javadadi
 * @Time: 20:03
 * @ClassName: ArticleActionEnum
 */
@AllArgsConstructor
@Getter
public enum ArticleActionEnum {
    POST("POST", "发布"),
    SAVE("SAVE", "暂存"),
    DELETE("DELETE", "删除");
    private final String action;
    private final String desc;
}
