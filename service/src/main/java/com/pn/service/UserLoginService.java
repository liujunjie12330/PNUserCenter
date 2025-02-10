package com.pn.service;

import me.zhyd.oauth.model.AuthUser;

/**
 * @author: javadadi
 * @Time: 19:08
 * @ClassName: UserLoginService
 */
public interface UserLoginService {

    String doLogin(String username, String password, String code);

    String doLogin(AuthUser authUser);

}
