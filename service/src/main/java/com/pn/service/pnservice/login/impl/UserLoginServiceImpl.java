package com.pn.service.pnservice.login.impl;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.utils.RegularUtil;
import com.pn.common.vos.login.UserVo;
import com.pn.dao.dto.login.UsernamePasswordDTO;
import com.pn.dao.entity.PnUser;
import com.pn.dao.mapper.PnUserMapper;
import com.pn.service.pnservice.login.UserLoginService;
import com.pn.service.utils.JWTUtil;
import com.pn.service.utils.RedisCache;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author: javadadi
 * @Time: 19:27
 * @ClassName: UserLoginServiceImpl
 */
@Service
public class UserLoginServiceImpl extends ServiceImpl<PnUserMapper, PnUser> implements UserLoginService  {
    @Resource
    private PnUserMapper userMapper;

    @Resource
    private RedisCache redisCache;


    /**
     * 登陆--常规实现
     */
    @Override
    public String doLogin(String username, String password, String code) {
        PnUser pnUser = checkPreLogin(username, password,code);
        checkPassword(username,password);
        //校验通过，设置登陆标志
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

    private PnUser checkPreLogin(String username, String password,String code){
        boolean isAccount = RegularUtil.isAccount(username);
        boolean isPassword = RegularUtil.isPassword(password);
        if (BooleanUtils.isFalse(isAccount && isPassword)){
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        if (StringUtils.isEmpty(code) || !redisCache.hasKey(PNUserCenterConstant.CODE_TAG+username)){
            throw new BizException(StatusCode.CAPTCHA_ERROR);
        }
        String redisCode = (String) redisCache.get(PNUserCenterConstant.CODE_TAG + username);
        if (!code.equalsIgnoreCase(redisCode)){
            throw new BizException(StatusCode.CAPTCHA_ERROR);
        }
        PnUser user = userMapper.getUserByUsername(username);
        if (Objects.isNull(user)){
            throw new BizException(StatusCode.USER_NO_REGISTERING);
        }
        return user;
    }

    private void checkPassword(String username,String password){
        UsernamePasswordDTO user = userMapper.getByUsername(username);
        String realPassword = user.getPassword();
        String currentPassword  = DigestUtil.md5Hex(PNUserCenterConstant.USER_PASSWORD_SLOT+password);
        if (!realPassword.equals(currentPassword)){
            throw new BizException(StatusCode.PASSWORD_ERROR);
        }
    }

}
