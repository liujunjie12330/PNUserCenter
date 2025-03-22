package com.pn.service.impls.ai;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.constant.RedisKeyConstant;
import com.pn.common.enums.AISourceEnum;
import com.pn.common.vos.ai.ChatItemVo;
import com.pn.common.vos.ai.ChatSessionItemVo;
import com.pn.dao.entity.PnUserAiHistory;
import com.pn.dao.mapper.PnUserAiHistoryMapper;
import com.pn.service.ChatHistoryService;
import com.pn.service.utils.RedisCache;
import com.pn.service.utils.cover.AIHistoryCoverUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author javadadi
 */
@Slf4j
@Service
public class ChatHistoryServiceImpl extends ServiceImpl<PnUserAiHistoryMapper, PnUserAiHistory> implements ChatHistoryService {

    @Resource
    private RedisCache redisCache;

    @Override
    public List<ChatSessionItemVo> listChatSessions(AISourceEnum source, Long userId) {
        String sessionCacheKey = getUserAiSessionCacheKey(source.getName(), userId);
        Map<String, ChatSessionItemVo> map  = redisCache.getHashCaches(sessionCacheKey,ChatSessionItemVo.class);
        if (Objects.isNull(map)) {
            return Collections.emptyList();
        }
        // 将Map中的值转换为List
        List<ChatSessionItemVo> list = new ArrayList<>(map.values());
        // 对列表按更新时间降序排序
        list.sort((o1, o2) -> o2.getUpdateTime().compareTo(o1.getUpdateTime()));
        // 返回排序后的列表
        return list;
    }

    @Override
    public List<ChatItemVo> listHistory(AISourceEnum source, Long userId, String chatId, Integer size) {
        return Collections.emptyList();
    }

    @Override
    public void saveRecord(AISourceEnum source, Long userId, String chatId, ChatItemVo item) {
        PnUserAiHistory pnUserAiHistory = AIHistoryCoverUtil.coverToHistory(null, source, userId, chatId, item);
        //写入mysql
        save(pnUserAiHistory);
        //写入redis
        String itemKey = getUserAiHistoryCacheKey(source.getName(), userId);
        redisCache.setListCacheLPush(itemKey, pnUserAiHistory);

        //维护一份对话记录
        String sessionCacheKey = getUserAiSessionCacheKey(source.getName(), userId);
        ChatSessionItemVo session = (ChatSessionItemVo) redisCache.getHashCache(sessionCacheKey, chatId);
        if (Objects.isNull(session)) {
            // 如果当前会话不存在，则创建新会话记录
            session = new ChatSessionItemVo();
            session.setChatId(chatId);
            session.setTitle(!item.getQuestion().startsWith(PNUserCenterConstant.PROMPT_TAG) ? item.getQuestion() : item.getQuestion().substring(PNUserCenterConstant.PROMPT_TAG.length()));
            session.setCreatTime(System.currentTimeMillis());
            session.setUpdateTime(session.getCreatTime());
            session.setQasCnt(1);
        } else {
            // 如果会话已存在，则更新会话记录
            session.setUpdateTime(System.currentTimeMillis());
            session.setQasCnt(session.getQasCnt() + 1);
        }
        //更新一下redis
        redisCache.setHashCache(sessionCacheKey, chatId, session);

        //限制对话次数，允许存在200条
        if (session.getQasCnt() > PNUserCenterConstant.MAX_HISTORY_RECORD_ITEMS) {
            redisCache.doListLTrim(itemKey, 0, PNUserCenterConstant.MAX_HISTORY_RECORD_ITEMS);
        }
    }

    @Override
    public Boolean updateChatSessionName(AISourceEnum source, String chatId, String title, Long userId) {
        return null;
    }

    @Override
    public Boolean removeChatSession(AISourceEnum source, String chatId, Long userId) {
        return null;
    }

    private String getUserAiHistoryCacheKey(String chatName, Long userId) {
        return String.format(RedisKeyConstant.USER_AI_CHAT_HISTORY, chatName.toUpperCase(), userId);
    }

    private String getUserAiSessionCacheKey(String chatName, Long userId) {
        return String.format(RedisKeyConstant.USER_AI_SESSION_HISTORY, chatName.toUpperCase(), userId);
    }
}
