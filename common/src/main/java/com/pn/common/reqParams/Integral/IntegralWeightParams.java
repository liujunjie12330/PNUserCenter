package com.pn.common.reqParams.Integral;

import lombok.Data;

@Data
public class IntegralWeightParams {

    /**
     * id
     */
    private Long id;

    /**
     * 积分类型
     */
    private String key;

    /**
     * 积分值
     */
    private Long value;

    /**
     * 加分名称
     */
    private String title;

    /**
     * 描述
     */
    private String desc;

    /**
     * 上限，-1为无上限
     */
    private Long upperLimit;

    /**
     * 是否启用
     */
    private Boolean isUse;

}
