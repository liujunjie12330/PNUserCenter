package com.pn.service.listener;


import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.vos.count.CommentDO;
import com.pn.dao.entity.PnUserFoot;
import com.pn.service.entity.notify.NotifyMsgEvent;
import com.pn.service.utils.RedisCache;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

import static com.pn.common.constant.CountConstant.*;

@Component
public class UserStatisticEventListener {
    @Resource
    RedisCache redisCache;
    /**
     * 用户相关的tag
     */
    public static final String USER_TAG = USER_STATISTIC_INFO + "%d";

    /**
     * 文章的相关tag
     */
    public static final String ARTICLE_TAG = ARTICLE_STATISTIC_INFO + "%d";


//    @EventListener(classes = NotifyMsgEvent.class)
    public void listenEvent(NotifyMsgEvent event) {
        Object content = event.getContent();
        if (Objects.isNull(content)) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        CommentDO comment = null;
        PnUserFoot userFoot = null;
        if (content.getClass().equals(PnUserFoot.class)) {
            userFoot = (PnUserFoot) content;
        }
        if (content.getClass().equals(CommentDO.class)) {
            comment = (CommentDO) content;
        }

        switch (event.getNotify()) {

            case REPLY:
                redisCache.incrHash(String.format(ARTICLE_TAG, comment.getArticleId()), COMMENT_COUNT, 1);
                break;

            case PRAISE:
                //记录用户收到点赞数 todo 现在用户只能在文章上面收到点赞
                redisCache.incrHash(String.format(USER_TAG, comment.getUserId()), PRAISE_COUNT, 1);
                //记录文章点赞数
                redisCache.incrHash(String.format(ARTICLE_TAG, comment.getUserId()), PRAISE_COUNT, 1);
                break;

            case COLLECT:
                //用户所有文章被收藏数
                redisCache.incrHash(String.format(USER_TAG, userFoot.getDocumentUserId()), COLLECTION_COUNT, 1);
                //单篇文章被收藏数
                redisCache.incrHash(String.format(ARTICLE_TAG, userFoot.getDocumentId()), COLLECTION_COUNT, 1);
                break;

            case FOLLOW:
                //粉丝增加
                redisCache.incrHash(String.format(USER_TAG,userFoot.getDocumentUserId()), FANS_COUNT, 1);
                break;

            case DELETE_REPLY:
                redisCache.incrHash(String.format(ARTICLE_TAG, comment.getArticleId()), COMMENT_COUNT, -1);
                break;

            case CANCEL_PRAISE:
                redisCache.incrHash(String.format(USER_TAG, comment.getUserId()), PRAISE_COUNT, -1);
                //记录文章点赞数-1
                redisCache.incrHash(String.format(ARTICLE_TAG, comment.getUserId()), PRAISE_COUNT, -1);
                break;

            case CANCEL_COLLECT:
                //用户所有文章被收藏数
                redisCache.incrHash(String.format(USER_TAG, userFoot.getDocumentUserId()), COLLECTION_COUNT, -1);
                //单篇文章被收藏数
                redisCache.incrHash(String.format(ARTICLE_TAG, userFoot.getDocumentId()), COLLECTION_COUNT, -1);
                break;

            case CANCEL_FOLLOW:
                redisCache.incrHash(String.format(USER_TAG,userFoot.getDocumentUserId()), FANS_COUNT, -1);
                break;
        }
    }
}
