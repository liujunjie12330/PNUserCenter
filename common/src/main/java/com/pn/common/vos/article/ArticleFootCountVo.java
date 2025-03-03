package com.pn.common.vos.article;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 文章足迹计数
 *
 * @author louzai
 * @date 2022-07-18
 */
@Data
@Builder
public class ArticleFootCountVo implements Serializable {

    private static final long serialVersionUID = -6723815084912652479L;
    /**
     * 文章点赞数
     */
    private Integer praiseCount;

    /**
     * 文章被阅读数
     */
    private Integer readCount;

    /**
     * 文章被收藏数
     */
    private Integer collectionCount;

    /**
     * 评论数
     */
    private Integer commentCount;
}
