package com.pn.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.pn.common.enums.PayTypeEnum;
import com.pn.service.impls.pay.dto.AlipayByQrCodeDto;
import com.pn.service.impls.pay.dto.AlipayToThirdUserDto;
import com.pn.service.impls.pay.dto.PayBaseDto;

/**
 * 统一支付接口
 */
public interface PayService {

    void payToThirdUser(AlipayToThirdUserDto alipay, PayTypeEnum typeEnum);

    AlipayTradeQueryResponse queryPay(String outBizNo, String tradeNo);

    String payByQrCode(AlipayByQrCodeDto alipay, PayTypeEnum typeEnum) throws AlipayApiException;
}
