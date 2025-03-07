package com.pn.service.impls.pay.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class AlipayByQrCodeDto extends PayBaseDto implements Serializable {
    private static final long serialVersionUID = 7968342473272268554L;
    /**
     * 交易类型
     */
    private String payType;
    /**
     * 产品码
     */
    public final static String PRODUCT_CODE = "FAST_INSTANT_TRADE_PAY";
}
