package com.pn.common.reqParams.article;

import com.pn.common.base.PageParam;
import lombok.Data;

import java.awt.image.LookupOp;

/**
 * 首页文章搜索参数
 */
@Data
public class ArticleIndexParam extends PageParam {
    private static final long serialVersionUID = 6952486358962766426L;
    private String search;

    private Long catalogId;

    private Long TagId;

    private Long columnId;

    private Long articleId;

    private Long userId;
}
