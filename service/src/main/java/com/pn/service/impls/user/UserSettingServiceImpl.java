package com.pn.service.impls.user;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pn.common.base.UserTokenThreadHolder;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.vos.login.UserVo;
import com.pn.dao.entity.PnAlipayUserInfo;
import com.pn.dao.mapper.PnAlipayUserInfoMapper;
import com.pn.dao.mapper.PnRoleMapper;
import com.pn.service.UserSettingService;
import com.pn.service.utils.cover.UserCoverUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Slf4j
@Service
public class UserSettingServiceImpl implements UserSettingService {

    @Resource
    private AlipayConfig alipayConfig;

    @Resource
    private PnAlipayUserInfoMapper alipayUserInfoMapper;

    @Value("${alipay.appid}")
    private String appid;

    @Value("${alipay.notifyUrl}")
    private String callBack;

    @Override
    public void bindUserAlipay(String authCode, Long userId) {
        try {
            /*换取授权令牌*/
            AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
            AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
            // 设置授权方式
            request.setGrantType("authorization_code");
            // 设置授权码
            request.setCode(authCode);
            AlipaySystemOauthTokenResponse response = alipayClient.execute(request);
            //解析出授权令牌
            JsonObject authJson = JsonParser.parseString(response.getBody()).getAsJsonObject();
            JsonObject oauthTokenResponse = authJson
                    .getAsJsonObject("alipay_system_oauth_token_response");
            String accessToken = oauthTokenResponse.get("access_token").getAsString();
            /*调用会员信息查询接口，拿出用户的userId*/
            AlipayUserInfoShareRequest infoShareRequest = new AlipayUserInfoShareRequest();
            AlipayUserInfoShareResponse infoShareResponse = alipayClient.execute(infoShareRequest, accessToken);
            /*先判断一下用户是不是已经在系统中绑定过*/
            Boolean exist = alipayUserInfoMapper.exist(infoShareResponse.getUserId());
            if (exist) {
                log.warn("alipay_username=={},Duplicate binding", infoShareResponse.getDisplayName());
                return;
            }
            /*在拿到userId之后，讲绑定信息加密存储在服务里面===》先不加密*/
            PnAlipayUserInfo userInfo = UserCoverUtil.responseCoverToPnAliPay(infoShareResponse, userId);
            alipayUserInfoMapper.insert(userInfo);
        } catch (AlipayApiException e) {
            log.error("an error occur by{}", e.getMessage());
            throw new BizException(e.getMessage());
        }
    }

    @Override
    public String bindAlipayAccount() {
        //先查询一下用户有没有绑定过或者正在绑定
        if (existAlipayUserInfo()) {
            throw new BizException(StatusCode.REPEAT_BOUND);
        }
        UserVo currentUser = UserTokenThreadHolder.getCurrentUser();
        return "https://openauth-sandbox.dl.alipaydev.com/oauth2/publicAppAuthorize.htm?"
                + "app_id=" + appid
                + "&scope=auth_user,auth_base"
                + "&userId=" + currentUser.getId()
                + "&redirect_uri=" + callBack;
    }

    @Override
    public Boolean existAlipayUserInfo() {
        UserVo currentUser = UserTokenThreadHolder.getCurrentUser();
        return alipayUserInfoMapper.existUser(currentUser.getId());
    }

}
