package com.pn.common.event;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 登录事件
 *
 */

@Data
public class IntegralEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 分值
     */
    private Long value;

    /**
     * 加分类型
     */
    private String type;

    /**
     * 加分描述
     */
    private String desc;

}
