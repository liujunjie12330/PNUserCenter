package com.pn.service;



import com.pn.common.enums.AISourceEnum;
import com.pn.common.vos.ai.ChatRecordsVo;

import java.util.function.Consumer;

/**
 * ai 聊天service
 */
public interface ChatService {

    /**
     *ai 源
     */
    AISourceEnum source();

    /**
     * 是否时异步优先
     */
    default boolean asyncFirst() {
        return true;
    }

    /**
     * 开始进入聊天
     *
     * @param user     提问人
     * @param question 聊天的问题
     * @return 返回的结果
     */
    ChatRecordsVo chat(Long user, String question);

    /**
     * 开始进入聊天
     *
     * @param user     提问人
     * @param question 聊天的问题
     * @param consumer 接收到AI返回之后可执行的回调
     * @return 同步直接返回的结果
     */
    ChatRecordsVo chat(Long user, String question, Consumer<ChatRecordsVo> consumer);

    /**
     * 异步聊天
     *
     * @param user 用户id
     * @param question ai源
     * @param consumer 执行成功之后，直接异步回调的通知
     * @return 同步直接返回的结果
     */
    ChatRecordsVo asyncChat(Long user, String question, Consumer<ChatRecordsVo> consumer);


    /**
     * 查询聊天历史
     *
     * @param user 用户id
     * @param aiSource ai源
     */
    ChatRecordsVo getChatHistory(Long user, AISourceEnum aiSource);

}
