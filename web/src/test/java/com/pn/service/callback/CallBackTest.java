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
        // 构造 parameterMap
        Map<String, String[]> parameterMap = new HashMap<>();

        // 填充数据
        parameterMap.put("gmt_create", new String[]{"2025-03-16 16:00:16"});
        parameterMap.put("charset", new String[]{"UTF-8"});
        parameterMap.put("gmt_payment", new String[]{"2025-03-16 16:00:23"});
        parameterMap.put("notify_time", new String[]{"2025-03-16 16:00:25"});
        parameterMap.put("subject", new String[]{"尊敬的用户,您正在支付文章:wwwwwwwwwwwwwwwwwwwwwwwwwww"});
        parameterMap.put("sign", new String[]{"FrZC3b+zYly5n/Uzz9TrdJGzmJjyYragdscvF1BTA2bic462PH5lUDuXIRUoB1J5HS5HQOSwi84gyy9tXikvqTWc+aRi90eyTxkeqfZ5P2gkVVcHHYuRVklCS+WFhuR1lNYdvEy7Eeoz/dIfZ2lpR6Oesq1ZufaGMDXlZvR6Nx3pshS4JJxLH1NS/l4NStGyS7BBpg1GTnimuuGkWA4SvxJ2vp7IakWrmljVVfduOjUx/+C6XsvV5zmSgEjOve0aiFOh9TNWLB55DIiLqrRjyD8eVmliwiiMt4Cw5IWVZrctv+1dPocTi5hXG2/XXgUoaddB1wRacFhtlVDYdw4m+g=="});
        parameterMap.put("buyer_id", new String[]{"2088722057811544"});
        parameterMap.put("invoice_amount", new String[]{"0.99"});
        parameterMap.put("version", new String[]{"1.0"});
        parameterMap.put("notify_id", new String[]{"2025031601222160024011540505586305"});
        parameterMap.put("fund_bill_list", new String[]{"[{\"amount\":\"0.99\",\"fundChannel\":\"ALIPAYACCOUNT\"}]"});
        parameterMap.put("notify_type", new String[]{"trade_status_sync"});
        parameterMap.put("out_trade_no", new String[]{"1001000087_2507526892828673"});
        parameterMap.put("total_amount", new String[]{"0.99"});
        parameterMap.put("trade_status", new String[]{"TRADE_SUCCESS"});
        parameterMap.put("trade_no", new String[]{"2025031622001411540505459467"});
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
