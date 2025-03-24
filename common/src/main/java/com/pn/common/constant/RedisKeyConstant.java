package com.pn.common.constant;

/**
 * Redis Key 定义常量类
 *
 */
public interface RedisKeyConstant {
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
    /*ai 聊天相关*/
    /**
     * ai 聊天历史key=chatName_chatId_userId
     */
    public static final String USER_AI_CHAT_HISTORY="user_ai_history:%s_%s_%d";
    /**
     * ai 会话历史记录key = chatName_userId
     */
    public static final String USER_AI_SESSION_HISTORY="user_ai_session_history:%s_%d";
    /**
     * ai user聊天次数 每天200次 aiName_localDate
     */
    public static final String USER_LIMIT_CHAT_PER_DAY="user_limit_chat_per_day::%s_%s";
}
