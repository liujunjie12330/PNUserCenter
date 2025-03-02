package com.pn.common.enums;

import lombok.Getter;

/**
 * @version 1.0
 * @Author：alex
 * @Date：2025/2/28 16:52
 * @Descirption xxx
 */
@Getter
public enum NotifyEnum {

    COMMENT(1,"评论"),
    PRAISE(2,"点赞"),
    COLLECT(3,"收藏"),
    FOLLOW(4,"关注"),
    REPLY(5,"回复"),
    DELETE_COMMENT(7,"删除评论"),
    CANCEL_PRAISE(8,"取消点赞"),
    CANCEL_COLLECT(9,"取消收藏"),
    CANCEL_FOLLOW(10,"取消关注"),
    DELETE_REPLY(11,"取消回复");
    ;

    int code;

    String message;

    NotifyEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }


}
