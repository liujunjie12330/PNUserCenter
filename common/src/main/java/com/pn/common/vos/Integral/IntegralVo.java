package com.pn.common.vos.Integral;


import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class IntegralVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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

    /**
     * 创建时间
     */
    private Date createTime;


}

