package com.pn.service.pnservice.login.adapter;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.vos.login.UserVo;
import com.pn.dao.dto.register.UserRegisterDTO;
import com.pn.dao.entity.PnUser;
import com.pn.dao.mapper.PnUserMapper;
import com.pn.service.pnservice.login.Login3rdTarget;
import com.pn.service.pnservice.login.UserLoginService;
import com.pn.service.pnservice.login.impl.UserLoginServiceImpl;
import com.pn.service.pnservice.register.UserRegisterService;
import com.pn.service.pnservice.register.impl.UserRegisterServiceImpl;
import com.pn.service.utils.JWTUtil;
import com.pn.service.utils.RedisCache;
import org.redisson.api.RedissonClient;
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
        String userUrl = giteeUserUrl.concat(token);
        HttpResponse userInfoResponse = HttpRequest.get(userUrl).execute();
        String username = (String) JSONUtil.parseObj(userInfoResponse.body()).get("name");

        return autoRegister3rdAndLogin(username, "gitee");
    }

   private String autoRegister3rdAndLogin(String username, String thirdName) {
        //第三方第一次登录？自动注册
       PnUser pnUser = pnUserMapper.getUserByUsername(username);
       if(ObjectUtil.isEmpty(pnUser)) {
           UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
           userRegisterDTO.setUsername(username);
           //自动注册密码设定如：gitee_username
           userRegisterDTO.setPassword(thirdName+"_"+username);
           userRegisterService.register(userRegisterDTO);
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


    @Override
    public String loginByWechat(String... params) {
        return "";
    }

    @Override
    public String loginByQQ(String... params) {
        return "";
    }

    @Override
    public String loginByGitHub(String... params) {
        return "";
    }
}
