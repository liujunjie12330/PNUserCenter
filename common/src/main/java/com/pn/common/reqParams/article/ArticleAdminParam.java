package com.pn.common.reqParams.article;

import com.pn.common.base.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author javadadi
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ArticleAdminParam extends PageParam {
    private static final long serialVersionUID = -4706763445901884084L;
    /**
     * 文章id
     */
    private Long articleId;
    /**
     * 作者uid
     */
    private Long authorId;

    /**
     * 作者名
     */
    private String authorName;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 0 未发布 1 已发布
     */
    private Integer status;

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
}
