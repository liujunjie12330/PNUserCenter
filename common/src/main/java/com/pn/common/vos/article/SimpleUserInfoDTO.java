package com.pn.common.vos.article;


import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 基本用户信息
 *
 * @author YiHui
 * @date 2022/9/26
 */
@Data
@Builder
public class SimpleUserInfoDTO implements Serializable {
    private static final long serialVersionUID = 4802653694786272120L;

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 作者简介
     */
    private String profile;
}
