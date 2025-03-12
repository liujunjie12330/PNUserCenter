package com.pn.service.paytest;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradePayResponse;
import com.pn.dao.entity.PnAlipayUserInfo;
import com.pn.dao.mapper.PnAlipayUserInfoMapper;
import com.pn.service.PayService;
import com.pn.service.impls.pay.AliPayService;
import com.pn.service.impls.pay.dto.AlipayByQrCodeDto;
import com.pn.service.impls.pay.dto.PayBaseDto;
import com.pn.web.PNUserCenterApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@SpringBootTest(classes = PNUserCenterApp.class)
@RunWith(SpringRunner.class)
public class AlipayTradePay {

    @Resource
    private AlipayConfig alipayConfig;

    @Resource(type = AliPayService.class)
    private PayService aliPayService;

    @Value("${alipay.notifyUrl}")
    private String notifyUrl;
    @Test
    public void payTest(){
        PayBaseDto payBaseDto = new PayBaseDto();
        payBaseDto.setOutBizNo("2025031219321734750b1bf7879444f3e9dbb9bf63898afd2");
        payBaseDto.setTradeNo("2025031222001411540505426232");
        aliPayService.queryPay(payBaseDto.getOutBizNo(),"");
    }

    @Resource
    private PnAlipayUserInfoMapper infoMapper;
    @Test
    public void testMapper(){
        PnAlipayUserInfo byUserId = infoMapper.getByUserId(1L);
        System.out.println(byUserId);
    }

    /**
     * 付款码支付场景
     *
     * @throws AlipayApiException
     */
    @Test
    public void test01() throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
        AlipayTradePayRequest request = new AlipayTradePayRequest();
        AlipayTradePayModel model = new AlipayTradePayModel();
        model.setOutTradeNo("20150320010101001");
        model.setTotalAmount("88.88");
        model.setSubject("Iphone6 16G");
        //支付宝的付款码
        model.setAuthCode("281071090222874589");
        model.setScene("bar_code");
        request.setBizModel(model);
        AlipayTradePayResponse response = alipayClient.execute(request);
        System.out.println(response.getBody());
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }

    @Test
    public void test02() throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
        // 构造请求参数以调用接口
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();

        // 设置商户订单号
        model.setOutTradeNo(generateOrderNumber("",256));

        // 设置订单总金额
        model.setTotalAmount("88.88");

        // 设置订单标题
        model.setSubject("Iphone6 16G");

        // 设置产品码
        model.setProductCode("FAST_INSTANT_TRADE_PAY");

        // 设置PC扫码支付的方式
        model.setQrPayMode("1");

        // 设置商户自定义二维码宽度
        model.setQrcodeWidth(100L);

        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl);
        AlipayTradePagePayResponse response = alipayClient.pageExecute(request, "GET");
        // 如果需要返回GET请求，请使用
        // AlipayTradePagePayResponse response = alipayClient.pageExecute(request, "GET");
        String pageRedirectionData = response.getBody();

        System.out.println(pageRedirectionData);

        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
            // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
            // String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
            // System.out.println(diagnosisUrl);
        }
    }

    public static String generateOrderNumber(String merchantPrefix, int maxLength) {
        // 获取当前时间戳
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

        // 生成一个UUID
        String uuid = UUID.randomUUID().toString().replace("-", "");

        // 商家自定义部分
        String customPart = merchantPrefix + timestamp;

        // 拼接字符串
        String orderNumber = customPart + uuid;

        // 截取前maxLength个字符
        if (orderNumber.length() > maxLength) {
            orderNumber = orderNumber.substring(0, maxLength);
        }
        return orderNumber;
    }

}