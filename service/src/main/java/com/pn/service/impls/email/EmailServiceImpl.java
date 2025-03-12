package com.pn.service.impls.email;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import javax.annotation.Resource;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.util.Date;
import java.util.List;


/**
 * 邮件服务
 */
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Resource
    private JavaMailSender javaMailSender;
    /**
     * 邮件发送
     *
     * @param name    发送人名称
     * @param form    发送人
     * @param to      发送对象
     * @param subject 主题
     * @param content 内容
     * @param isHtml  是否为html
     * @param cc      抄送，多人用逗号隔开
     * @param bcc     密送，多人用逗号隔开
     * @param files   文件
     */
    @Override
    public void send(String name, String form, String to, String subject, String content, Boolean isHtml, String cc, String bcc, List<File> files) {
        if (StringUtils.isAnyBlank(form, to, subject, content)) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        try {
            //true表示支持复杂类型
            MimeMessageHelper messageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(), true);
            //邮件发信人
            messageHelper.setFrom(new InternetAddress(name + "<" + form + ">"));
            //邮件收信人
            messageHelper.setTo(to.split(","));
            //邮件主题
            messageHelper.setSubject(subject);
            //邮件内容
            messageHelper.setText(content, isHtml);
            //抄送
            if (!StringUtils.isEmpty(cc)) {
                messageHelper.setCc(cc.split(","));
            }
            //密送
            if (!StringUtils.isEmpty(bcc)) {
                messageHelper.setCc(bcc.split(","));
            }
            //添加邮件附件
            if (CollectionUtil.isNotEmpty(files)) {
                for (File file : files) {
                    messageHelper.addAttachment(file.getName(), file);
                }
            }
            // 邮件发送时间
            messageHelper.setSentDate(new Date());
            //正式发送邮件
            javaMailSender.send(messageHelper.getMimeMessage());
        } catch (Exception e) {
            log.error("邮件发送失败:{}",e.getMessage());
            throw new BizException(StatusCode.SEND_FAILED);
        }
    }

}
