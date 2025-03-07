package com.pn.service.impls.pay.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * pay base dto
 */
@Data
public class PayBaseDto implements Serializable {
    private static final long serialVersionUID = 5207136670755365533L;
    /**
     * 系统生成的订单号
     */
    private String outBizNo;
    /**
     * 金额
     */
    private String transAmount;
    /**
     * 交易标题
     */
    private String title;
    /**
     * 备注
     */
    private String remark;
}
