package com.pn.service.impls;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pn.dao.entity.PnTransactions;
import com.pn.service.impls.pay.dto.AlipayToThirdUserDto;

/**
 * 统一支付接口
 */
public interface PayService extends IService<PnTransactions> {

    void payToThirdUser(AlipayToThirdUserDto alipay);
}
