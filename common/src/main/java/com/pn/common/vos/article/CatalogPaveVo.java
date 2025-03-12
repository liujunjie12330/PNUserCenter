package com.pn.common.vos.article;

import com.pn.common.base.BaseModelVo;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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
    @Field(type = FieldType.Long)
    private Long categoryId;
    /**
     * 类目名称
     */
    @Field(type = FieldType.Text, analyzer = "ik_analyzer")
    private String categoryName;
    /**
     * 排序
     */
    @Field(type = FieldType.Integer)
    private Integer rank;
    /**
     * 状态
     */
    @Field(type = FieldType.Integer)
    private Integer status;
}
