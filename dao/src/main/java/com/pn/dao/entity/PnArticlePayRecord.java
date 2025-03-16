package com.pn.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
     * 作者alipayid
     */
    @TableField(value = "receive_user_id")
    private String receiveUserId;

    /**
     * 作者平台id
     */
    @TableField(value = "receive_pn_user_id")
    private Long receivePnUserId;

    /**
     * 文章ID
     */
    @TableField(value = "article_id")
    private Long articleId;

    /**
     * 订单号
     */
    @TableField(value = "out_biz_no")
    private String outBizNo;

    /**
     * 支付金额
     */
    @TableField(value = "pay_amount")
    private String payAmount;

    /**
     * 0-完成,1-用户已支付,3-用户支付失败,4-打款异常
     */
    @TableField(value = "pay_status")
    private Integer payStatus;

    /**
     * 备注信息
     */
    @TableField(value = "extra")
    private String extra;

    /**
     * 支付方式
     */
    @TableField(value = "pay_way")
    private String payWay;

    /**
     * 交易单号
     */
    @TableField(value = "third_trans_code")
    private String thirdTransCode;

    /**
     * 支付回调时间
     */
    @TableField(value = "pay_callback_time")
    private Date payCallbackTime;
}