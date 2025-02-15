package com.pn.common.reqParams.Integral;

import lombok.Data;

@Data
public class IntegralParams{

    /**
     * id
     */
    private Long id;

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