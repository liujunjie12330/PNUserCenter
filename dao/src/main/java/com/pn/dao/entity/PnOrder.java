package com.pn.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 订单表
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName(value = "pn_order")
public class PnOrder extends BaseModel {
    /**
     * 支付用户平台id
     */
    @TableField(value = "pay_user_id")
    private Long payUserId;
    /**
     * 文章id
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
    @TableField(value = "trans_amount")
    private String transAmount;

    /**
     * 交易标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 交易类型
     */
    @TableField(value = "`type`")
    private String type;

    /**
     * 状态,0-已支付,1-支付取消,2-支付异常
     */
    @TableField(value = "`status`")
    private Integer status;

    /**
     * 支付异常信息
     */
    @TableField(value = "msg")
    private String msg;

    /**
     * 返回json
     */
    @TableField(value = "body")
    private String body;
}