package com.pn.common.vos.article;


import com.pn.common.enums.ArticleTypeEnum;
import com.pn.common.enums.SourceTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 文章信息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleVO implements Serializable {
    private static final long serialVersionUID = -793906904770296838L;
    /*作者信息*/
    /**
     * 信息
     */
    private SimpleUserInfoDTO authorInfo;
    /*文章信息*/
    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 短标题
     */
    private String shortTitle;

    /**
     * 文章头图链接
     */
    private String cover;

    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 文章类型
     *
     * @see ArticleTypeEnum#getDesc()
     */
    private Integer articleType;

    /**
     * 来源：1-转载，2-原创，3-翻译
     *
     * @see SourceTypeEnum#getDesc()
     */
    private Integer source;

    /**
     * 原文链接
     */
    private String sourceUrl;

    /**
     * 官方状态：0-非官方，1-官方
     */
    private Integer officalStat;

    /**
     * 打赏的收款码截图
     */
    private String payImageUrl;
    /**
     * 是否推荐1--推荐
     */
    private Integer recommend;
    /*article detail*/
    /**
     * 正文
     */
    private String context;
    /*文章统计信息*/
    /**
     * 文章统计信息
     */
    private ArticleFootCountVo countVo;
    /*标签信息*/
    /**
     * 标签
     */
    private List<TagVo> tags;
    /*专栏信息*/

    /*分类信息*/
    /**
     * 分类信息
     */
    private CatalogPaveVo catalog;
    /*下面的是支付相关的信息*/
    private volatile boolean isNeedTpPay = false;

    private volatile String url = "";

    public boolean isNeedTpPay() {
        return isNeedTpPay;
    }

    public void setIsNeedTpPay(boolean paid) {
        isNeedTpPay = paid;
    }


}
