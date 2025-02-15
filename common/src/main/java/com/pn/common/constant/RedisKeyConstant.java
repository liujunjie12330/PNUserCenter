package com.pn.common.constant;

/**
 * Redis Key 定义常量类
 *
 */
public interface RedisKeyConstant {
    /**
     * 全局 redis key (业务无关的key)
     */
    String GLOBAL_REDIS_KEY = "global:";

    /**
     * 用户注册锁.
     */
    public static final String LOCK_USER_REGISTER="pn_user_service:lock_user_register:";
    /**
     * 可复用用户名(后续增加用户注销接口，注销之后将用户所持有的用户名存入此redis集合)
     */
    public static final String USER_REGISTER_REUSE="pn_user_service:user_register_reuse";
    /**
     * 用户注销锁
     */
    public static final String USER_DELETION="pn_user_service:user_deletion:";

    /**
     * 限流 redis key
     */
    String RATE_LIMIT_KEY = GLOBAL_REDIS_KEY + "rate_limit:";
}
