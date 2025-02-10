package com.pn.service.impls.captcha;


import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.service.CaptchaService;
import com.pn.service.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

/**
 * @author: javadadi
 * @Time: 12:36
 * @ClassName: CaptchaServiceImpl
 */
@Service
@Slf4j
public class CaptchaServiceImpl implements CaptchaService {
    @Resource
    private RedisCache redisCache;

    @Override
    public String getCode(String username) {
        if (StringUtils.isEmpty(username)){
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        // 自定义纯数字的验证码（随机4位数字，可重复）
        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 4);
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        lineCaptcha.setGenerator(randomGenerator);
        // 重新生成code
        ByteArrayOutputStream outputStream = null;
        lineCaptcha.createCode();
        String base64Image ="";
        try {
            String code = lineCaptcha.getCode();
            redisCache.set(PNUserCenterConstant.CODE_TAG+username, code, PNUserCenterConstant.CAPTCHA_EXPIRE_TIME);
            BufferedImage image = lineCaptcha.getImage();
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", outputStream);
            base64Image = Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (Exception e) {
            log.error("captcha generator error {} date=>{}", e.getMessage(), new Date());
            throw new BizException(StatusCode.SYSTEM_ERROR);
        } finally {
            try {
                if (Objects.nonNull(outputStream)) {
                    outputStream.close();
                }
            } catch (IOException e) {
                log.error("outputStream close error ");
            }
        }
        return base64Image ;
    }
}
