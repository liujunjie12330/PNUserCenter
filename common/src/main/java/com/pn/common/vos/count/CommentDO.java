package com.pn.common.vos.count;

import lombok.Data;


import java.io.Serializable;



@Data
public class CommentDO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 0未删除 1 已删除
     */
    private Integer deleted;
}

