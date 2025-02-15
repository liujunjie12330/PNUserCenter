package com.pn.common.vos.Integral;


import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class IntegralWeightVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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
     * 创建时间
     */
    private Date createTime;

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
