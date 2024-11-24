package com.pn.common.vos.login;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author: javadadi
 * @Time: 19:57
 * @ClassName: UserVo
 */
@Data
@Builder
public class UserVo {

    private Long id;


    /**
     * 用户名
     */
    private String username;


    /**
     * 用户全名
     */
    private String fullName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否管理员
     */
    private String isAdmin;

    /**
     * 最后一次登录时间
     */
    private Date lastLoginDate;

    /**
     * 最后一次修改时间
     */
    private Date updateLastTime;

}
