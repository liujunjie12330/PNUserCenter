package com.pn.dao.dto.login;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户密码查询工具类
 * @author: javadadi
 * @Time: 19:01
 * @ClassName: UsernamePasswordDTO
 */
@Data
public class UsernamePasswordDTO implements Serializable {
    private static final long serialVersionUID = 5870480485130488016L;
    private String username;
    private String password;
    private Long id;
    private String fullName;
}
