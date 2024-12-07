package com.pn.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "pn_user")
public class PnUser extends BaseModel implements Serializable {
    private static final long serialVersionUID = -8305370453007829239L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 是否删除（0,1）
     */
    @TableField(value = "is_deleted")
    private Boolean isDeleted;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 用户密码
     */
    @TableField(value = "`password`")
    private String password;

    /**
     * 用户全名
     */
    @TableField(value = "full_name")
    private String fullName;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 是否管理员
     */
    @TableField(value = "is_admin")
    private Integer isAdmin;

    /**
     * 最后一次登录时间
     */
    @TableField(value = "last_login_date")
    private Date lastLoginDate;

    /**
     * 最后一次修改时间
     */
    @TableField(value = "update_last_time")
    private Date updateLastTime;
}