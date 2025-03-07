package com.pn.web.controller.callback;

import com.pn.common.base.BaseResponse;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.service.PayRecordService;
import com.pn.service.UserSettingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * 支付宝相关回调
 */
@RestController
@RequestMapping(PNUserCenterConstant.BASE_URL+"/callback/alipay")
public class AliPayCallBack {

    @Resource
    private UserSettingService userSettingService;

    @Resource
    private PayRecordService payRecordService;


    @RequestMapping("/payOrOauth")
    public BaseResponse<String> callback(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        /*支付回调 */
        if (parameterMap.containsKey("out_trade_no")||parameterMap.containsKey("trade_no")){
           Long articleId =  payRecordService.saveRecord(parameterMap);
            response.sendRedirect("http://localtost:8000/article/detial/"+articleId);
        } else {
            /*授权回调*/
            for (Map.Entry<String, String[]> stringEntry : parameterMap.entrySet()) {
                System.out.println(stringEntry.getKey()+"--------"+ Arrays.toString(stringEntry.getValue()));
            }
            String authCode = request.getParameter("auth_code");
            Long userId = Long.valueOf(request.getParameter("userId"));
            userSettingService.bindUserAlipay(authCode,userId);
        }
        return null;
    }
}
