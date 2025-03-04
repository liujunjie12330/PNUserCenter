package com.pn.web.controller.user;


import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.reqParams.id.IdParams;
import com.xkcoding.http.support.Http;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author javadadi
 */
@RestController
@RequestMapping(PNUserCenterConstant.BASE_URL+"/userSetting")
public class UserSettingController {

    @Value("${alipay.appid}")
    private String appid;

    @Value("${alipay.notifyUrl}")
    private String callBack;

    @GetMapping("/bindUserAlipay")
    public void bindAlipayAccount(HttpServletResponse response){
        try {
            response.sendRedirect("https://openauth-sandbox.dl.alipaydev.com/oauth2/publicAppAuthorize.htm?"
                    +"app_id="+appid
                    +"&scope=auth_user,auth_base"
                    +"&userId=1"
                    +"&redirect_uri="+callBack);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
