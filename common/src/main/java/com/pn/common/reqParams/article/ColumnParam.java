package com.pn.common.reqParams.article;

import com.pn.common.base.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 专栏（教程）请求参数
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ColumnParam extends PageParam {
    private static final long serialVersionUID = 2486619738768693637L;
    /**
     * ID
     */
    private Long columnId;

    /**
     * 专栏名
     */
    private String column;

    /**
     * 作者
     */
    private Long authorId;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 封面
     */
    private String cover;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 排序
     */
    private Integer section;

    /**
     * 专栏预计的文章数
     */
    private Integer nums=10;

    /**
     * 专栏类型
     */
    private Integer type;

    /**
     * 限时免费开始时间
     */
    private Date freeStartTime;

    /**
     * 限时免费结束时间
     */
    private Date freeEndTime;
}
