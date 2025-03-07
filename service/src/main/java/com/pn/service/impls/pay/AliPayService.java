package com.pn.service.impls.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayFundTransToaccountTransferModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.pn.common.annotation.AlipayLog;
import com.pn.common.enums.PayTypeEnum;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.dao.entity.PnAlipayUserInfo;
import com.pn.dao.mapper.PnAlipayUserInfoMapper;
import com.pn.service.PayService;
import com.pn.service.impls.pay.dto.AlipayByQrCodeDto;
import com.pn.service.impls.pay.dto.AlipayToThirdUserDto;
import com.pn.service.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

@Service
@Slf4j
public class AliPayService  implements PayService {

    @Resource
    private AlipayConfig alipayConfig;

    @Resource
    private PnAlipayUserInfoMapper infoMapper;

    @Resource
    private RedisCache cache;

    @Value("${alipay.notifyUrl}")
    private String notifyUrl;
    /**
     * 向第三方用户转账===>一般用于文章支付观看
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    @AlipayLog
    public void payToThirdUser(AlipayToThirdUserDto alipay, PayTypeEnum typeEnum) {
        //首先查询转账目标账户的
        PnAlipayUserInfo alipayUserInfo = infoMapper.getByUserId(alipay.getAuthorId());
        if (Objects.isNull(alipayUserInfo)) {
            throw new BizException(StatusCode.UNBOUND_PAYMENT);
        }
        try {
            // 初始化SDK
            AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
            //构造请求参数以调用接口
            AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
            AlipayFundTransToaccountTransferModel model = new AlipayFundTransToaccountTransferModel();
            //商户转账唯一订单号
            model.setOutBizNo(alipay.getOutBizNo());
            //收款方账户类型。
            //1、PayeeType=ALIPAY_USERID：PayeeAccount传值pid ,以2088开头的16位纯数字组成。
            //2、PayeeType=ALIPAY_LOGONID：PayeeAccount传值支付宝登录号(邮箱或手机号)
            model.setPayeeType("ALIPAY_USERID");
            //收款方账户。与payee_type配合使用。付款方和收款方不能是同一个账户。
            model.setPayeeAccount(alipayUserInfo.getAlipayUuid());
            //测试金额必须大于等于0.1，只支持2位小数，小数点前最大支持13位
            model.setAmount(alipay.getTransAmount());
            //当付款方为企业账户且转账金额达到（大于等于）50000元，remark不能为空。
            model.setRemark(alipay.getRemark());
            request.setBizModel(model);
            AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                //调用成功要记录本次的交易信息
                System.out.println("调用成功");
            } else {
                //调用失败,打印一下失败的信息
                System.out.println("调用失败");
            }
        } catch (Exception e) {
            //着重处理,可能存在数据库插入异常或者是调用支付异常
            throw new BizException(e.getMessage());
        }
    }

    @Override
    @AlipayLog
    public String payByQrCode(AlipayByQrCodeDto alipay, PayTypeEnum typeEnum) throws AlipayApiException
    {
            AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
            // 构造请求参数以调用接口
            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
            AlipayTradePagePayModel model = new AlipayTradePagePayModel();

            // 设置商户订单号
            model.setOutTradeNo(alipay.getOutBizNo());

            // 设置订单总金额
            model.setTotalAmount(alipay.getTransAmount());

            // 设置订单标题
            model.setSubject(alipay.getTitle());

            // 设置产品码
            model.setProductCode(AlipayByQrCodeDto.PRODUCT_CODE);

            // 设置PC扫码支付的方式
            model.setQrPayMode("1");

            // 设置商户自定义二维码宽度
            model.setQrcodeWidth(100L);
            request.setBizModel(model);
            request.setNotifyUrl(notifyUrl);
            AlipayTradePagePayResponse response = alipayClient.pageExecute(request, "GET");
            //拿到返回的url
            String pageRedirectionData = response.getBody();
            log.info("pay url:{}",pageRedirectionData);
            if (response.isSuccess()) {
                return  pageRedirectionData;
            } else {
                throw new BizException(StatusCode.PAYMENT_FAILED);
            }
    }
}
