package com.pn.service.impls.article.dao;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author javadadi
 */
@Data
@Builder
public class ArticlePayDto implements Serializable {
    private static final long serialVersionUID = -6647084420276210871L;
    /**
     * 支付的用户id
     */
    private Long payUserId;
    /**
     * 文章id
     */
    private Long articleId;
    /**
     * 作者id
     */
    private Long authorId;
}
