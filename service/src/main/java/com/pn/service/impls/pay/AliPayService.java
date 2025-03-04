package com.pn.service.impls.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayFundTransUniTransferModel;
import com.alipay.api.domain.Participant;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.dao.entity.PnAlipayUserInfo;
import com.pn.dao.entity.PnTransactions;
import com.pn.dao.mapper.PnAlipayUserInfoMapper;
import com.pn.dao.mapper.PnTransactionsMapper;
import com.pn.service.impls.PayService;
import com.pn.service.impls.pay.dto.AlipayToThirdUserDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class AliPayService extends ServiceImpl<PnTransactionsMapper, PnTransactions> implements PayService {

    @Resource
    private AlipayConfig alipayConfig;

    @Resource
    private PnAlipayUserInfoMapper infoMapper;

    /**
     * 向第三方用户转账===>一般用于文章支付观看
     */
    @Override
    public void payToThirdUser(AlipayToThirdUserDto alipay) {
        //首先查询转账目标账户的
        PnAlipayUserInfo alipayUserInfo = infoMapper.getByUserId(alipay.getAuthorId());
        if (Objects.isNull(alipayUserInfo)) {
            throw new BizException(StatusCode.UNBOUND_PAYMENT);
        }
        try {
            // 初始化SDK
            AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);

            //构造请求参数以调用接口
            AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();
            AlipayFundTransUniTransferModel model = new AlipayFundTransUniTransferModel();

            // 设置商家侧唯一订单号
            model.setOutBizNo(alipay.getOutBizNo());

            // 设置订单总金额
            model.setTransAmount(alipay.getTransAmount());

            // 设置描述特定的业务场景
            model.setBizScene(AlipayToThirdUserDto.bizScene.DIRECT_TRANSFER.getCode());

            // 设置业务产品码
            model.setProductCode(AlipayToThirdUserDto.PRODUCT_CODE);

            // 设置转账业务的标题
            model.setOrderTitle(alipay.getTitle());

            // 设置收款方信息
            Participant payeeInfo = new Participant();
            payeeInfo.setIdentityType(AlipayToThirdUserDto.IDENTITY_TYPE);
            payeeInfo.setIdentity(alipayUserInfo.getAlipayUuid());
            payeeInfo.setName(alipayUserInfo.getDisplayName());
            model.setPayeeInfo(payeeInfo);

            // 设置业务备注
            model.setRemark(alipay.getRemark());

            request.setBizModel(model);
            AlipayFundTransUniTransferResponse response = alipayClient.certificateExecute(request);
            System.out.println(response.getBody());

            if (response.isSuccess()) {
                System.out.println("调用成功");
            } else {
                System.out.println("调用失败");
            }
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }
}
