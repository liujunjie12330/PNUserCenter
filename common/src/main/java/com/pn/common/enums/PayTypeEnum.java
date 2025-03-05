package com.pn.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 付款类型
 */
@AllArgsConstructor
@Getter
public enum PayTypeEnum {
    ARTICLE("article"),
    PAY_TO_THIRD("pay_to_third");
    private final String type;
}
