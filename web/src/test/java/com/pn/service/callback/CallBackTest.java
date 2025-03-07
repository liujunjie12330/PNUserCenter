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
        // 创建一个 Map 对象来保存 JSON 数据
        Map<String, String[]> parameterMap = new HashMap<>();

        parameterMap.put("gmt_create", new String[]{"2025-03-07 23:17:20"});
        parameterMap.put("charset", new String[]{"UTF-8"});
        parameterMap.put("gmt_payment", new String[]{"2025-03-07 23:17:27"});
        parameterMap.put("notify_time", new String[]{"2025-03-07 23:17:29"});
        parameterMap.put("subject", new String[]{"尊敬的用户,您正在支付文章:是是是是是是是是是是是是是是是是是是是是"});
        parameterMap.put("sign", new String[]{"CSP0NB2PHEvSPDZMO2jixrc2t/AJz15Cxll8Yq+fiqcYLme7GzkA7d8NZCoiJms2GI7R0d1taiBUW3onAryTX48U/Yk/tnZqdlAHZlNBCMdBrJS8Tw7WgiGhiRalzsG5dCWXR7E7EENkq66CwVIiKaX61Jv60F0Q70Oa2tCbujJuvU2hUOe4VWt+HfiDqi4KJnknCC464RZhkiemNj6Ys9MuPmZ1N3BuZ2UWgcd6CgemZuP85tM59Fxg+NbnGG5ugRqUk6b4J/uz1QrbKE9bPv2X+rukdmbiEbze328kYcTnJ8QK1gmH8dlZh1cNv8D90klRm0mSjpxTFE+mxGXCLg=="});
        parameterMap.put("buyer_id", new String[]{"2088722057811544"});
        parameterMap.put("invoice_amount", new String[]{"0.99"});
        parameterMap.put("version", new String[]{"1.0"});
        parameterMap.put("notify_id", new String[]{"2025030701222231728011540505486248"});
        parameterMap.put("fund_bill_list", new String[]{"[{\"amount\":\"0.99\",\"fundChannel\":\"ALIPAYACCOUNT\"}]"});
        parameterMap.put("notify_type", new String[]{"trade_status_sync"});
        parameterMap.put("out_trade_no", new String[]{"1001000401_2506425347227649"});
        parameterMap.put("total_amount", new String[]{"0.99"});
        parameterMap.put("trade_status", new String[]{"TRADE_SUCCESS"});
        parameterMap.put("trade_no", new String[]{"2025030722001411540505379116"});
        parameterMap.put("auth_app_id", new String[]{"9021000143693037"});
        parameterMap.put("receipt_amount", new String[]{"0.99"});
        parameterMap.put("point_amount", new String[]{"0.00"});
        parameterMap.put("buyer_pay_amount", new String[]{"0.99"});
        parameterMap.put("app_id", new String[]{"9021000143693037"});
        parameterMap.put("sign_type", new String[]{"RSA2"});
        parameterMap.put("seller_id", new String[]{"2088721057811530"});

        // 打印输出 Map
        System.out.println(parameterMap);

        payRecordService.saveRecord(parameterMap);
    }
}
