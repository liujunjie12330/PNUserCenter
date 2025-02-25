package com.pn.common.vos.article;


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
@Accessors(chain = true)
public class SimpleUserInfoDTO implements Serializable {
    private static final long serialVersionUID = 4802653694786272120L;


    private Long userId;


    private String name;


    private String avatar;

    /**
     * 作者简介
     */
    private String profile;
}
