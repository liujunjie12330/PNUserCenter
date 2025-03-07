package com.pn.service;

import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pn.common.enums.PayTypeEnum;
import com.pn.dao.entity.PnTransactions;
import com.pn.service.impls.pay.dto.AlipayByQrCodeDto;
import com.pn.service.impls.pay.dto.AlipayToThirdUserDto;
import org.springframework.transaction.annotation.Transactional;

/**
 * 统一支付接口
 */
public interface PayService {

    void payToThirdUser(AlipayToThirdUserDto alipay, PayTypeEnum typeEnum);

    String payByQrCode(AlipayByQrCodeDto alipay, PayTypeEnum typeEnum) throws AlipayApiException;
}
