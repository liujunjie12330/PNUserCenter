package com.pn.common.enums;

import lombok.Getter;

/**
 * 发布状态枚举
 *
 * @author louzai
 * @since 2022/7/19
 */
@Getter
public enum PushStatusEnum {

    OFFLINE(0, "offline", "未发布"),
    ONLINE(1, "online", "已发布"),
    REVIEW(2, "review", "审核");

    PushStatusEnum(int code, String action, String desc) {
        this.code = code;
        this.desc = desc;
        this.action = action;
    }

    private final int code;
    private final String desc;
    private final String action;

    public static PushStatusEnum formCode(int code) {
        for (PushStatusEnum yesOrNoEnum : PushStatusEnum.values()) {
            if (yesOrNoEnum.getCode() == code) {
                return yesOrNoEnum;
            }
        }
        return PushStatusEnum.OFFLINE;
    }
}
