package com.pn.service.impls.pay.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AlipayByQrCodeDto implements Serializable {
    private static final long serialVersionUID = 7968342473272268554L;
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
    /**
     * 产品码
     */
    public final static String PRODUCT_CODE = "FAST_INSTANT_TRADE_PAY";
}
