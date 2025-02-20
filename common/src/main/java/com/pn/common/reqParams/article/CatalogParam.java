package com.pn.common.reqParams.article;

import com.pn.common.base.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class CatalogParam extends PageParam implements Serializable {
    private static final long serialVersionUID = -3388733324533008996L;
    /**
     * CatalogId
     */
    private Long catalogId;
    /**
     * articleId
     */
    private Long articleId;
    /**
     * 类目名称
     */
    private String categoryName;

    /**
     * 状态：0-未发布，1-已发布
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer rank;
}
