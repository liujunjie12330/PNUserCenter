package com.pn.dao.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;

@Data
@NoArgsConstructor
public class IntegralDTO {

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
