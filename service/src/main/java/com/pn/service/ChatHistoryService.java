package com.pn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pn.common.enums.AISourceEnum;
import com.pn.common.vos.ai.ChatItemVo;
import com.pn.common.vos.ai.ChatSessionItemVo;
import com.pn.dao.entity.PnUserAiHistory;

import java.util.List;

/**
 *ai 对话历史记录
 */
public interface ChatHistoryService extends IService<PnUserAiHistory>{
    /**
     * 获取对话列表
     *
     * @param source AI模型
     */
    List<ChatSessionItemVo> listChatSessions(AISourceEnum source, Long userId);

    /**
     * 获取对话记录
     *
     * @param source AI模型
     * @param chatId 对话id
     * @param size   记录条数
     * @return 对话记录
     */
    List<ChatItemVo> listHistory(AISourceEnum source, Long userId, String chatId, Integer size);

    /**
     * 保存最新的一条对话内容
     *
     * @param source AI模型
     * @param chatId 对话id
     * @param item   对话内容
     */
    void saveRecord(AISourceEnum source, Long userId, String chatId, ChatItemVo item);


    Boolean updateChatSessionName(AISourceEnum source, String chatId, String title, Long userId);

    Boolean removeChatSession(AISourceEnum source, String chatId, Long userId);

}
