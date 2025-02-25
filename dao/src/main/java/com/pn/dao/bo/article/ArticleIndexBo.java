package com.pn.dao.bo.article;

import com.pn.common.vos.article.ArticleFootCountDTO;
import com.pn.common.vos.article.TagVo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 首页文章列表查询bo
 */
@Data
public class ArticleIndexBo  implements Serializable {
    private static final long serialVersionUID = -540659848780393955L;
    /**
     * 文章id
     */
    private Long Id;

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
    private Integer recommend;

    /**
     * 更新时间
     */
    private Date updateTime;
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
