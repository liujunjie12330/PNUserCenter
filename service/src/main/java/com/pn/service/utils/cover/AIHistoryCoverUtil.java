package com.pn.service.utils.cover;

import com.pn.common.enums.AISourceEnum;
import com.pn.common.vos.ai.ChatItemVo;
import com.pn.dao.entity.PnUserAiHistory;

import java.util.Objects;

/**
 * ai 聊天历史转换
 */
public class AIHistoryCoverUtil {

    public static PnUserAiHistory coverToHistory(PnUserAiHistory aiHistory,
                                                 AISourceEnum aiSourceEnum,
                                                 Long userId,
                                                 String chatId,
                                                 ChatItemVo chatItemVo) {
        if (Objects.isNull(aiHistory)) {
            aiHistory = new PnUserAiHistory();
        }
        aiHistory.setUserId(userId);
        aiHistory.setQuestion(chatItemVo.getQuestion());
        aiHistory.setAnswer(chatItemVo.getAnswer());
        aiHistory.setAiType(aiSourceEnum.getCode());
        aiHistory.setChatId(chatId);
        return aiHistory;
    }
}
