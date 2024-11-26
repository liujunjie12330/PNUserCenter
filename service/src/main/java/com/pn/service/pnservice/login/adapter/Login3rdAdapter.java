package com.pn.service.pnservice.login.adapter;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.exception.BizException;
import com.pn.common.vos.login.UserVo;
import com.pn.dao.dto.register.UserRegisterDTO;
import com.pn.dao.entity.PnUser;
import com.pn.dao.mapper.PnUserMapper;
import com.pn.service.pnservice.login.Login3rdTarget;
import com.pn.service.pnservice.login.UserLoginService;
import com.pn.service.pnservice.register.UserRegisterService;
import com.pn.service.utils.JWTUtil;
import com.pn.service.utils.RedisCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @Author：alex
 * @Date：2024/11/25 16:02
 * @Descirption xxx
 */
@Component
public class Login3rdAdapter  implements Login3rdTarget {

    @Resource
    private RedisCache redisCache;

    @Resource
    private UserLoginService userLoginService;
    @Resource
    private UserRegisterService userRegisterService;
    @Resource
    private PnUserMapper pnUserMapper;


    @Value("${gitee.state}")
    private String giteeState;

    @Value("${gitee.token.url}")
    private String giteeTokenUrl;

    @Value("${gitee.user.url}")
    private String giteeUserUrl;


    @Value("${gitee.client_id}")
    private String giteeClient_id;
    @Value("${gitee.client_secret}")
    private String giteeClient_secret;
    @Value("${gitee.callback}")
    private String giteeCallBack;


    //github
    @Value("${github.state}")
    private String githubState;

    @Value("${github.token.url}")
    private String githubTokenUrl;

    @Value("${github.user.url}")
    private String githubUserUrl;


    @Value("${github.client_id}")
    private String githubClient_id;
    @Value("${github.client_secret}")
    private String githubClient_secret;
    @Value("${github.callback}")
    private String githubCallBack;


    @Override
    public String loginByGitee(String code, String state) {
        //1 用户点授权之后，响应一个code，由前端传入后端
        String replace = giteeTokenUrl
                .replace("client_id=?", "client_id=" + giteeClient_id)
                .replace("client_secret=?", "client_secret=" + giteeClient_secret)
                .replace("redirect_uri=?", "redirect_uri=" + giteeCallBack);
        String tokenUrl = replace.concat(code);
        HttpResponse tokenResponse = HttpRequest.post(tokenUrl).execute();

        // 2 获取令牌
        String token = (String) JSONUtil.parseObj(tokenResponse.body()).get("access_token");

        //3 后端带着token向gitee发请求，就能获取到用户的详细信息
        String userUrl = "https://gitee.com/api/v5/user?access_token=" + token;
        HttpResponse userInfoResponse = HttpRequest.get(userUrl).execute();
        //用户名、账号、头像
        String userFullName = (String) JSONUtil.parseObj(userInfoResponse.body()).get("name");
        String userAccount = (String) JSONUtil.parseObj(userInfoResponse.body()).get("login");
//        String avatar_url = (String) JSONUtil.parseObj(userInfoResponse.body()).get("avatar_url");
        return autoRegister3rdAndLogin(userAccount,userFullName, "gitee");
    }





    @Override
    public String loginByGitHub(String code, String state) {
        Map<String, Object > paramMap = new HashMap<>();
        paramMap.put("client_id",githubClient_id);
        paramMap.put("client_secret",githubClient_secret);
        paramMap.put("redirect_uri",githubCallBack);
        paramMap.put("code",code);
        paramMap.put("accept","json");
        //获取token
        String result = null;
        //github在外网，容易访问超时
        try {
            result = HttpUtil.post(githubTokenUrl, paramMap);
        } catch (Exception e) {
            throw new BizException("github访问超时");
        }

        String token = result.split("&")[0].split("=")[1];
        System.out.println(token);

        HttpResponse userInfoResponse = HttpRequest.get(githubUserUrl).header("Authorization","Bearer "+token).execute();
        String bodied = userInfoResponse.body();
        //用户名、账号、头像
        String userAccount = (String) JSONUtil.parseObj(userInfoResponse.body()).get("login");

        String userFullName = null;
        //有的github用户没有填username，这里可能会出异常
        try {
            userFullName = (String) JSONUtil.parseObj(userInfoResponse.body()).get("name");
        } catch (Exception e) {
            userFullName=userAccount;

        }
//        String avatar_url = (String) JSONUtil.parseObj(userInfoResponse.body()).get("avatar_url");
        return autoRegister3rdAndLogin(userAccount,userFullName, "github");
    }


    @Override
    public String loginByWechat(String... params) {
        return "";
    }

    @Override
    public String loginByQQ(String... params) {
        return "";
    }

    //第三方登录注册
    private String autoRegister3rdAndLogin(String userAccount,String userFullName, String thirdName) {
        //第三方第一次登录？自动注册
        PnUser pnUser = pnUserMapper.getUserByUsername(userAccount);
        if(ObjectUtil.isEmpty(pnUser)) {
            UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
            userRegisterDTO.setUsername(userAccount);
            userRegisterDTO.setFullName(userFullName);
            //自动注册密码设定如：gitee_@Ming608
            userRegisterDTO.setPassword(thirdName+"_"+userAccount);
            userRegisterDTO.setPhone("");
            userRegisterDTO.setEmail("");
            userRegisterService.register(userRegisterDTO);
            pnUser = pnUserMapper.getUserByUsername(userAccount);
        }

        //使用过第三方登录？直接登录
        UserVo userVo = UserVo.builder()
                .id(pnUser.getId())
                .username(pnUser.getUsername())
                .fullName(pnUser.getFullName())
                .email(pnUser.getEmail())
                .phone(pnUser.getPhone())
                .isAdmin(pnUser.getIsAdmin())
                .lastLoginDate(pnUser.getLastLoginDate())
                .build();
        String jsonStr = JSONUtil.toJsonStr(userVo);
        redisCache.set(PNUserCenterConstant.USER_LOGIN+ pnUser.getId()+pnUser.getUsername(),jsonStr);
        Map<String, String> map = new HashMap<>();
        map.put("userAccount", userVo.getUsername());
        String token = JWTUtil.sign(map);
        return token;
    }
}
