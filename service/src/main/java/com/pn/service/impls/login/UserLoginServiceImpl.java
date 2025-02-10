package com.pn.service.impls.login;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.constant.RedisKeyConstant;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.vos.login.UserVo;
import com.pn.dao.bo.login.UsernamePasswordBO;
import com.pn.dao.entity.PnUser;
import com.pn.dao.entity.PnUserOauth;
import com.pn.dao.mapper.PnUserMapper;
import com.pn.dao.mapper.PnUserOauthMapper;
import com.pn.service.UserLoginService;
import com.pn.service.UserRegisterService;
import com.pn.service.utils.RedisCache;
import me.zhyd.oauth.model.AuthUser;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBloomFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.pn.common.utils.RegularUtil.isAccount;
import static com.pn.common.utils.RegularUtil.isPassword;
import static com.pn.service.utils.JWTUtil.sign;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author: javadadi
 * @Time: 19:27
 * @ClassName: UserLoginServiceImpl
 */
@Service
public class UserLoginServiceImpl extends ServiceImpl<PnUserMapper, PnUser> implements UserLoginService {
    @Resource
    private PnUserMapper userMapper;

    @Resource
    private RedisCache redisCache;

    @Resource
    private PnUserOauthMapper userOauthMapper;

    @Resource
    private PnUserMapper pnUserMapper;

    @Resource
    private UserRegisterService registerService;

    @Resource
    private RBloomFilter<String> userRegisterBloomFilter;;

    /**
     * 登陆--常规实现
     */
    @Override
    public String doLogin(String username, String password, String code) {
        PnUser pnUser = checkPreLogin(username, password, code);
        checkPassword(username, password);
        //校验通过，设置登陆标志
        UserVo userVo = getUserVo(pnUser);
        String jsonStr = JSONUtil.toJsonStr(userVo);
        redisCache.set(PNUserCenterConstant.USER_LOGIN + pnUser.getId() + pnUser.getUsername(), jsonStr);
        Map<String, String> map = new HashMap<>();
        map.put("username", userVo.getUsername());
        map.put("userId", String.valueOf(userVo.getId()));
        String token = sign(map);
        return token;
    }

    /**
     * 第三方登陆
     *
     * @param authUser
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String doLogin(AuthUser authUser) {
        UserVo userVo = null;
        String uuid = authUser.getUuid();
        String source = authUser.getSource();
        PnUserOauth oAuthUser = userOauthMapper.getByUuidAndSource(uuid, source);
        //不是第一次登陆
        if (Objects.nonNull(oAuthUser)) {
            Long userId = oAuthUser.getUserId();
            //上次登陆可能出现异常，初始化用户账号
            if (Objects.equals(userId, 0L)) {
                PnUser pnUser = initUser(authUser);
                pnUserMapper.addUser(pnUser);
                oAuthUser.setUserId(pnUser.getId());
                userOauthMapper.updateById(oAuthUser);
                userVo = getUserVo(pnUser);
            } else{
                //已经绑定有本平台账号
                PnUser pnUser = pnUserMapper.selectById(oAuthUser.getUserId());
                userVo =  getUserVo(pnUser);
            }
        } else {
            //第一次登陆本平台账号
            PnUserOauth pnUserOauth = initOauthUser(authUser);
            PnUser pnUser = initUser(authUser);
            pnUserMapper.addUser(pnUser);
            pnUserOauth.setUserId(pnUser.getId());
            userOauthMapper.insert(pnUserOauth);
            userVo = getUserVo(pnUser);
        }
        String jsonStr = JSONUtil.toJsonStr(userVo);
        redisCache.set(PNUserCenterConstant.USER_LOGIN + userVo.getId() + userVo.getUsername(), jsonStr);
        Map<String, String> map = new HashMap<>();
        map.put("username", userVo.getUsername());
        map.put("userId", String.valueOf(userVo.getId()));
        String token = sign(map);
        return token;
    }

    private PnUserOauth initOauthUser(AuthUser authUser) {
        PnUserOauth oAuthUser = new PnUserOauth();
        oAuthUser.setUuid(authUser.getUuid());
        oAuthUser.setSource(authUser.getSource());
        oAuthUser.setPlatformUsername(authUser.getNickname());
        oAuthUser.setAvatarUrl(authUser.getAvatar());
        return  oAuthUser;
    }

    private UserVo getUserVo(PnUser pnUser) {
        return UserVo.builder()
                .id(pnUser.getId())
                .username(pnUser.getUsername())
                .fullName(pnUser.getFullName())
                .email(pnUser.getEmail())
                .phone(pnUser.getPhone())
                .isAdmin(pnUser.getIsAdmin())
                .lastLoginDate(pnUser.getLastLoginDate())
                .build();
    }

    private PnUser initUser(AuthUser authUser) {
        PnUser pnUser = new PnUser();
        String username = "";
        boolean hasUsername = true;
        while(hasUsername){
           username  = UUID.randomUUID().toString().substring(0, 15).replace("-","");
           hasUsername = registerService.hasUsername(username);
        }
        redisCache.delSetCache(RedisKeyConstant.USER_REGISTER_REUSE, username);
        //布隆过滤器防止用户重复注册，缓存穿透
        userRegisterBloomFilter.add(username);
        //密码默认就是用户名
        String password = DigestUtil.md5Hex((PNUserCenterConstant.USER_PASSWORD_SLOT + username).getBytes());
        pnUser.setUsername(username);
        pnUser.setPassword(password);
        pnUser.setFullName(authUser.getNickname());
        pnUser.setEmail(authUser.getEmail());
        pnUser.setAvatar(authUser.getAvatar());
        return pnUser;
    }

    private PnUser checkPreLogin(String username, String password, String code) {
        boolean isAccount = isAccount(username);
        boolean isPassword = isPassword(password);
        if (BooleanUtils.isFalse(isAccount && isPassword)) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        if (StringUtils.isEmpty(code) || !redisCache.hasKey(PNUserCenterConstant.CODE_TAG + username)) {
            throw new BizException(StatusCode.CAPTCHA_ERROR);
        }
        String redisCode = (String) redisCache.get(PNUserCenterConstant.CODE_TAG + username);
        if (!code.equalsIgnoreCase(redisCode)) {
            throw new BizException(StatusCode.CAPTCHA_ERROR);
        }
        PnUser user = userMapper.getUserByUsername(username);
        if (Objects.isNull(user)) {
            throw new BizException(StatusCode.USER_NO_REGISTERING);
        }
        return user;
    }

    private void checkPassword(String username, String password) {
        UsernamePasswordBO user = userMapper.getByUsername(username);
        String realPassword = user.getPassword();
        String currentPassword = DigestUtil.md5Hex((PNUserCenterConstant.USER_PASSWORD_SLOT + password).getBytes());
        if (!realPassword.equals(currentPassword)) {
            throw new BizException(StatusCode.PASSWORD_ERROR);
        }
    }

}
