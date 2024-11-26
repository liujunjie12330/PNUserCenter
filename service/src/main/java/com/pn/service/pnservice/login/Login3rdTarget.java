package com.pn.service.pnservice.login;

/**
 * @version 1.0
 * @Author：alex
 * @Date：2024/11/26 22:27
 * @Descirption xxx
 */
public interface Login3rdTarget {

    String loginByGitee(String code, String state);

    String loginByWechat(String... params);

    String loginByQQ(String... params);

    String loginByGitHub(String code, String state);
}
