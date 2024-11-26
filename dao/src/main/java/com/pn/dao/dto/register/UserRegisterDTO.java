package com.pn.dao.dto.register;

import lombok.Data;

/**
 * @version 1.0
 * @Author：alex
 * @Date：2024/11/26 22:41
 * @Descirption xxx
 */
@Data
public class UserRegisterDTO {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String fullName;
}

