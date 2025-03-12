package com.pn.common.vos.article;

import com.pn.common.base.BaseModelVo;
import lombok.*;

import java.util.Date;

/**
 * 精要文章vo
 *
 * @author javadadi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleAdminVo extends BaseModelVo {
    private static final long serialVersionUID = 1492677654651773859L;
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
     * 作者头像
     */
    private String authorAvatar;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 短标题
     */
    private String shortTitle;

    /**
     * 封面
     */
    private String cover;

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
