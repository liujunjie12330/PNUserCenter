package com.pn.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author javadadi
 */
@Data
public class BaseModel implements Serializable {
    private static final long serialVersionUID = 6135623164210596164L;
    /**
     * 创建时间
     */
    @TableField("create_at")
    private Date createAt;

    /**
     * 更新时间
     */
    @TableField("update_at")
    private Date updateAt;

    /**
     * 创建人
     */
    @TableField("create_by")
    private Long createBy;

    /**
     * 更新人
     */
    @TableField("update_by")
    private Long updateBy;

    /**
     * 是否删除1--删除
     */
    @TableField(value = "is_deleted")
    private Integer isDeleted;
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
}
