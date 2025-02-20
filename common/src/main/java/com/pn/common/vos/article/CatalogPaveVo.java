package com.pn.common.vos.article;

import com.pn.common.base.BaseModelVo;
import lombok.*;

/**
 * catalog vo
 *
 * @author liujunjie
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class CatalogPaveVo extends BaseModelVo {
    private static final long serialVersionUID = -4474337712177518824L;
    /**
     * id
     */
    private Long categoryId;
    /**
     * 类目名称
     */
    private String categoryName;
    /**
     * 排序
     */
    private Integer rank;
    /**
     * 状态
     */
    private Integer status;
}
