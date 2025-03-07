package com.pn.service.callback;

import com.pn.service.PayRecordService;
import com.pn.web.PNUserCenterApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@SpringBootTest(classes = PNUserCenterApp.class)
@RunWith(SpringRunner.class)
public class CallBackTest {

    @Resource
    PayRecordService payRecordService;

    @Test
    public void test() {
        // 伪造 parameterMap
        Map<String, String[]> parameterMap = new HashMap<>();

        // 模拟支付宝返回的支付通知参数
        parameterMap.put("trade_no", new String[]{"202403070001"}); // 交易 ID
        parameterMap.put("out_trade_no", new String[]{"1001000062_2506425347227649"}); // 订单号
        parameterMap.put("buyer_id", new String[]{"2088722057811544"}); // 买家 ID
        parameterMap.put("total_amount", new String[]{new BigDecimal("99.99").toString()}); // 支付金额
        parameterMap.put("trade_status", new String[]{"1"}); // 交易状态
        // 时间格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        parameterMap.put("gmt_payment", new String[]{sdf.format(new Date())}); // 支付时间
        parameterMap.put("notify_time", new String[]{sdf.format(new Date())}); // 通知时间


        payRecordService.saveRecord(parameterMap);
    }
}
