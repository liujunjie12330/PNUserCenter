package com.pn.common.reqParams.login;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: javadadi
 * @Time: 20:30
 * @ClassName: UserLoginParams
 */
@Data
public class UserLoginParams implements Serializable {
    private static final long serialVersionUID = -2200285514712276680L;

    private String username;
    private String password;
    private String code;
}
