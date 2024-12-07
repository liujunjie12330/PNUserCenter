package com.pn.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 第三方登陆信息表
 */
@Data
@EqualsAndHashCode(callSuper=true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "pn_user_oauth")
public class PnUserOauth extends BaseModel {
    private static final long serialVersionUID = -4606924606676392787L;
    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 关联用户表(pn_user)
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 第三方登录方式(github,gitee..)
     */
    @TableField(value = "platform")
    private String platform;

    /**
     * 三方平台用户id
     */
    @TableField(value = "platform_user_id")
    private String platformUserId;

    /**
     * 三方平台用户名
     */
    @TableField(value = "platform_username")
    private String platformUsername;

    /**
     * 用户头像url
     */
    @TableField(value = "avatar_url")
    private String avatarUrl;

    /**
     * 授权token
     */
    @TableField(value = "token")
    private String token;

    /**
     * 用户授权时间
     */
    @TableField(value = "authorized_at")
    private Date authorizedAt;

    /**
     * 是否删除
     */
    @TableField(value = "is_deleted")
    private Boolean isDeleted;
}