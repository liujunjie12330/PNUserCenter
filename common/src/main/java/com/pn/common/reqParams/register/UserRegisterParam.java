package com.pn.common.reqParams.register;

import lombok.Data;

@Data
public class UserRegisterParam {
    private String username;
    private String password;
    private String checkPassword;
    private String email;
    private String phone;
    private String fullName;
}
