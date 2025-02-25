package com.pn.common.vos.article;

import com.pn.common.base.PageParam;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * 文章首页列表vo
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleIndexVo extends PageParam {
    private static final long serialVersionUID = 4634216001172442707L;
    /**
     * 文章id
     */
    private Long articleId;
    /**
     * 文章摘要
     */
    private String summary;
    /**
     * 作者uid
     */
    private Long authorId;
    /**
     * 作者名
     */
    private String authorName;

    /**
     * 作者头像
     */
    private String authorAvatar;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 短标题--教程名称
     */
    private String shortTitle;

    /**
     * 封面
     */
    private String cover;

    /**
     * 是否官方
     */
    private Integer officalStat;

    /**
     * 是否置顶
     */
    private Integer toppingStat;

    /**
     * 是否推荐
     */
    private Integer creamStat;

    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 文章统计信息
     */
    private ArticleFootCountDTO articleFootCountDTO;
    /**
     * 标签信息
     */
    private List<TagVo> tagVos;
    /**
     * 教程id
     */
    private Long columnId;
    /**
     * 教程名称
     */
    private Long columnName;
    /**
     * 分类名称
     */
    private String  catalogName;
    /**
     * 分类id
     */
    private Long catalogId;
}
