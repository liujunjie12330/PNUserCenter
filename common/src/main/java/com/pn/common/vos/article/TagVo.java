package com.pn.common.vos.article;

import com.pn.common.base.BaseModelVo;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 标签实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagVo extends BaseModelVo {

    private static final long serialVersionUID = 8194796552253358186L;
    /**
     * 标签 ID
     */
    @Field(type = FieldType.Long)
    private Long tagId;

    /**
     * 标签名称
     * 使用 IK 分词器进行全文索引
     */
    @Field(type = FieldType.Text, analyzer = "ik_analyzer")
    private String tagName;

    /**
     * 标签类型
     * 1 - 系统标签，2 - 自定义标签
     */
    @Field(type = FieldType.Integer)
    private Integer tagType;

    /**
     * 标签状态
     * 0 - 未发布，1 - 已发布
     */
    @Field(type = FieldType.Integer)
    private Integer status;
}