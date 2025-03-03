package com.pn.web.controller.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.pn.common.base.BaseResponse;
import com.pn.common.constant.PNUserCenterConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * ali支付接口
 */
@RestController
@RequestMapping(PNUserCenterConstant.BASE_URL+"/alipay")
public class AliPayController {

    @Resource
    private AlipayConfig alipayConfig;

    @Value("${alipay.notifyUrl}")
    private String notifyUrl;

    @GetMapping("pay")
    public void pay(HttpServletResponse httpServletResponse) throws AlipayApiException, IOException {
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
        // 构造请求参数以调用接口
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        request.setNotifyUrl(notifyUrl);
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
        AlipayTradePagePayResponse response = alipayClient.pageExecute(request, "GET");
        // 如果需要返回GET请求，请使用
        // AlipayTradePagePayResponse response = alipayClient.pageExecute(request, "GET");
        String pageRedirectionData = response.getBody();

        System.out.println(pageRedirectionData);

        httpServletResponse.sendRedirect(pageRedirectionData);
    }

    @PostMapping("/callback")
    public BaseResponse<String> callback(HttpServletRequest request){
        System.out.println(request);
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> stringEntry : parameterMap.entrySet()) {
            System.out.println(stringEntry.getKey()+"--------"+ Arrays.toString(stringEntry.getValue()));
        }
        return null;
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
