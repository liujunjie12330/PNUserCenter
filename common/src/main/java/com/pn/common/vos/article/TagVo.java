package com.pn.common.vos.article;

import com.pn.common.base.BaseModelVo;
import lombok.*;

/**
 * tag vo
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagVo extends BaseModelVo {
    private static final long serialVersionUID = -7389535239366845165L;
    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 标签类型：1-系统标签，2-自定义标签
     */
    private Integer tagType;

    /**
     * 状态：0-未发布，1-已发布
     */
    private Integer status;
}
