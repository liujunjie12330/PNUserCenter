package com.pn.service.bean.pay;

import com.alipay.api.AlipayConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: javadadi
 * @Time: 18:38
 * @ClassName: AlipayConfig
 */
@Configuration
public class AlipayBean {
    @Value("${alipay.appid}")
    private String appId;

    @Value("${alipay.appPrivateKey}")
    private String appPrivateKey;

    @Value("${alipay.alipayPublicKey}")
    private String alipayPublicKey;

    @Value("${alipay.notifyUrl}")
    private String notifyUrl;

    @Value("${alipay.serverUrl}")
    private String gateway;

    @Bean
    public AlipayConfig alipayConfig() {
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl(gateway);
        alipayConfig.setAppId(appId);
        alipayConfig.setPrivateKey(appPrivateKey);
        alipayConfig.setFormat("json");
        alipayConfig.setAlipayPublicKey(alipayPublicKey);
        alipayConfig.setCharset("UTF-8");
        alipayConfig.setSignType("RSA2");
        return alipayConfig;
    }

}
