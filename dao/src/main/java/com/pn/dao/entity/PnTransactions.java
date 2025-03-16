package com.pn.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 交易信息表
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName(value = "pn_transactions")
public class PnTransactions extends BaseModel {
    private static final long serialVersionUID = 364018495934528243L;
    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 交易ID，对应支付宝交易号
     */
    @TableField(value = "transaction_id")
    private String transactionId;

    /**
     * 订单号，对应商户订单号
     */
    @TableField(value = "order_id")
    private String orderId;

    /**
     * 买家ID，对应买家支付宝用户ID
     */
    @TableField(value = "buyer_id")
    private String buyerId;

    /**
     * 支付金额，对应订单总金额
     */
    @TableField(value = "payment_amount")
    private BigDecimal paymentAmount;

    /**
     * 交易状态，对应交易状态
     */
    @TableField(value = "trade_status")
    private String tradeStatus;

    /**
     * 支付时间，对应支付宝支付时间
     */
    @TableField(value = "payment_time")
    private Date paymentTime;

    /**
     * 通知时间，对应支付宝通知时间
     */
    @TableField(value = "notify_time")
    private Date notifyTime;

    /**
     * 记录创建时间，默认为当前时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 记录更新时间，默认为当前时间，每次更新记录时自动更新
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 支付类型
     */
    @TableField(value = "pay_type")
    private String payType;
}