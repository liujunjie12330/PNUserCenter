package com.pn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pn.dao.entity.PnTransactions;
import com.pn.service.impls.pay.dto.AlipayByQrCodeDto;
import com.pn.service.impls.pay.dto.AlipayToThirdUserDto;

/**
 * 统一支付接口
 */
public interface PayService {

    void payToThirdUser(AlipayToThirdUserDto alipay);

    String payByQrCode(AlipayByQrCodeDto alipay);

}
