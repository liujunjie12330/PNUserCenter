package com.pn.common.base;


import lombok.Data;

import java.io.Serializable;

@Data
public class PageParam implements Serializable {
    private static final long serialVersionUID = 4893902304079155036L;
    /**
     * 每页大小
     */
    private Long size = 10L;
    /**
     * 当前页
     */
    private Long current = 1L;
}
