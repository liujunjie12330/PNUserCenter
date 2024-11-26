package com.pn.common.vos.register;

import lombok.Data;

@Data
public class UserRegisterRespVo {
    private String username;
    private String password;
    private String phone;
    private String email;
}
