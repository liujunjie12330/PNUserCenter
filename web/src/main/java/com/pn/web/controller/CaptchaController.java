package com.pn.web.controller;

import com.pn.common.base.BaseResponse;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.utils.ResultUtils;
import com.pn.service.pnservice.captcha.CaptchaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: javadadi
 * @Time: 22:29
 * @ClassName: CaptchaController
 */
@RestController
@RequestMapping(PNUserCenterConstant.BASE_URL + "/captcha")
public class CaptchaController {
    @Resource
    private CaptchaService captchaService;

    /**
     * 验证码获取
     * @param username
     * @return
     */
    @GetMapping("/getCode/{username}")
    public BaseResponse<String> getCode(@PathVariable("username") String username) {
        if (StringUtils.isEmpty(username)) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        return ResultUtils.success(captchaService.getCode(username));
    }
}
