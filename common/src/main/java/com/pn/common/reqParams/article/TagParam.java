package com.pn.common.reqParams.article;

import com.pn.common.base.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 标签参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TagParam extends PageParam {
    private static final long serialVersionUID = -1673065283400553595L;
    /**
     * ID
     */
    private Long tagId;

    /**
     * 标签名称
     */
    private String tag;
    /**
     * 上下线的操作
     */
    private String action;
}
