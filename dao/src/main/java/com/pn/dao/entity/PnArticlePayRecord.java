package com.pn.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文章支付记录
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName(value = "pn_article_pay_record")
public class PnArticlePayRecord extends BaseModel {
    private static final long serialVersionUID = -9182855168332817116L;
    /**
     * 支付用户
     */
    @TableField(value = "pay_user_id")
    private String payUserId;
    /**
     * 支付用户的平台id
     */
    @TableField(value = "pay_pn_user_id")
    private Long payPnUserId;
    /**
     * 收款用户
     */
    @TableField(value = "receive_user_id")
    private String receiveUserId;

    /**
     * 收款用户的平台id
     */
    @TableField(value = "receive_pn_user_id")
    private Long receivePnUserId;
    /**
     * 文章ID
     */
    @TableField(value = "article_id")
    private Long articleId;

    /**
     * 支付状态
     */
    @TableField(value = "pay_status")
    private String payStatus;

    /**
     * 邮件通知用户的时间
     */
    @TableField(value = "notify_time")
    private Date notifyTime;

    /**
     * 通知确认次数
     */
    @TableField(value = "notify_cnt")
    private Integer notifyCnt;

    /**
     * 备注信息
     */
    @TableField(value = "notes")
    private String notes;

    /**
     * 验证码
     */
    @TableField(value = "verify_code")
    private String verifyCode;

    /**
     * 支付金额
     */
    @TableField(value = "pay_amount")
    private String payAmount;

    /**
     * 预支付ID
     */
    @TableField(value = "pre_pay_id")
    private String prePayId;

    /**
     * 预支付过期时间
     */
    @TableField(value = "pre_pay_expire_time")
    private Date prePayExpireTime;

    /**
     * 支付方式
     */
    @TableField(value = "pay_way")
    private String payWay;

    /**
     * 三方交易单号
     */
    @TableField(value = "third_trans_code")
    private String thirdTransCode;

    /**
     * 支付回调时间
     */
    @TableField(value = "pay_callback_time")
    private Date payCallbackTime;

}