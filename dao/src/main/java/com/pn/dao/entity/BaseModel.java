package com.pn.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
}
