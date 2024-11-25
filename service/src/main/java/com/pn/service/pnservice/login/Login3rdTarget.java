package com.pn.service.pnservice.login;

/**
 * @version 1.0
 * @Author：alex
 * @Date：2024/11/25 15:59
 * @Descirption xxx
 */
public interface Login3rdTarget {

    public String loginByGitee(String code, String state);
    public String loginByWechat(String ... params);
    public String loginByQQ(String ... params);
    public String loginByGitHub(String ... params);

}
