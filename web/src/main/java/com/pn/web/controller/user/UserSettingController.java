package com.pn.web.controller.user;


import com.pn.common.base.BaseResponse;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.utils.ResultUtils;
import com.pn.service.UserSettingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @author javadadi
 */
@RestController
@RequestMapping(PNUserCenterConstant.BASE_URL+"/userSetting")
public class UserSettingController {

    @Resource
    private UserSettingService userSettingService;

    @GetMapping("/bindUserAlipay")
    public BaseResponse<String> bindAlipayAccount(){
        return ResultUtils.success(userSettingService.bindAlipayAccount());
    }

    @GetMapping("/existAlipayUser")
    public BaseResponse<Boolean> existAlipayUserInfo(){
        return ResultUtils.success(userSettingService.existAlipayUserInfo());
    }
}
