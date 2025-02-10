package com.pn.web.filters;

import cn.hutool.json.JSONUtil;
import com.auth0.jwt.interfaces.Claim;

import com.pn.common.base.UserTokenThreadHolder;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.vos.login.UserVo;
import com.pn.service.utils.JWTUtil;
import com.pn.service.utils.RedisCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private RedisCache redisCache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            throw new BizException(StatusCode.USER_NO_LOGIN);
        }
        //检查token是否过期
        boolean expired = JWTUtil.isExpired(token);
        if (expired) {
            throw new BizException(StatusCode.USER_NO_LOGIN);
        }
        // todo 没有过期就检查ip是否变动
        Claim claim = JWTUtil.getClaims(token).get("map");
        Map map = claim.as(Map.class);
        String id = (String) map.get("userId");
        String username = (String) map.get("username");
        //没有过期就检查过期时间是否小于30min
        Date expiresAt = JWTUtil.getExpiresAt(token);
        Date now = new Date();
        long thirtyMinutesInMillis = 30 * 60 * 1000;
        if (expiresAt != null && expiresAt.getTime() - now.getTime() < thirtyMinutesInMillis) {
            //token 续期
            String sign = JWTUtil.sign(map);
            response.setHeader("token", sign);
        }
        //存入threadlocal
        if (!redisCache.hasKey(PNUserCenterConstant.USER_LOGIN + id + username)) {
            throw new BizException(StatusCode.USER_NO_LOGIN);
        }
        String userJson = (String) redisCache.get(PNUserCenterConstant.USER_LOGIN + id + username);
        UserVo userVo = JSONUtil.toBean(userJson, UserVo.class);
        UserTokenThreadHolder.addCurrentUser(userVo);
        return true;
    }

    /**
     * 避免内存泄露
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserTokenThreadHolder.remove();
    }
}
