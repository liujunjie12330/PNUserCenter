package com.pn.common.reqParams.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0
 * @Author：alex
 * @Date：2025/2/25 17:15
 * @Descirption xxx
 */
@Data
public class PermissionSaveParam implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    //权限码
    String code;

    // 权限名
    String name;

    //展示名称
    String  display;

    //权限状态
    int status;
}
