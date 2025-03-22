package com.pn.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * AI历史消息表
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName(value = "pn_user_ai_history")
public class PnUserAiHistory extends BaseModel {
    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 问题
     */
    @TableField(value = "question")
    private String question;

    /**
     * 答案
     */
    @TableField(value = "answer")
    private String answer;

    /**
     * AI类型
     */
    @TableField(value = "ai_type")
    private Integer aiType;

    /**
     * 会话ID
     */
    @TableField(value = "chat_id")
    private String chatId;
}