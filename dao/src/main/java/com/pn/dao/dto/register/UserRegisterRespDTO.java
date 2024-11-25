package com.pn.dao.dto.register;

import lombok.Data;

@Data
public class UserRegisterRespDTO {
    private String username;
    private String password;
    private String phone;
    private String email;
}
