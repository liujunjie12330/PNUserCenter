package com.pn.service.impls.register;

import cn.hutool.crypto.digest.DigestUtil;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.constant.RedisKeyConstant;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.reqParams.register.UserRegisterParam;
import com.pn.dao.entity.PnUser;
import com.pn.dao.mapper.PnUserMapper;
import com.pn.service.UserRegisterService;
import com.pn.service.utils.RedisCache;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static com.pn.common.utils.RegularUtil.isAccount;
import static com.pn.common.utils.RegularUtil.isPassword;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {

    @Resource
    private RedissonClient redissonClient;
    @Resource
    private PnUserMapper userMapper;
    @Resource
    private RBloomFilter<String> userRegisterBloomFilter;
    @Resource
    private RedisCache redisCache;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(UserRegisterParam userRegisterParam) {
        Boolean hasUsername = hasUsername(userRegisterParam.getUsername());
        if (!hasUsername) {
            throw new BizException(StatusCode.USER_ALREADY_EXIST);
        }
        //用户名，用户密码规范校验
        boolean isUsername = isAccount(userRegisterParam.getUsername());
        boolean isPassword = isPassword(userRegisterParam.getPassword());
        if (BooleanUtils.isFalse(isUsername && isPassword)) {
            throw new BizException(StatusCode.FORMAT_ERROR);
        }
        RLock lock = redissonClient.getLock(RedisKeyConstant.LOCK_USER_REGISTER + userRegisterParam.getUsername());
        boolean tryLock = false;
        try {
            tryLock = lock.tryLock(5, 30, TimeUnit.SECONDS);
            if (!tryLock) {
                throw new BizException(StatusCode.USER_ALREADY_EXIST);
            }
            PnUser pnUser = mapToPnUser(userRegisterParam);
            int insert = userMapper.insert(pnUser);
            if (insert < 1) {
                throw new BizException(StatusCode.OPERATION_ERROR);
            }
            //用户注销之后,原本所持有的用户名则会被放到redis中可复用的集合中重新拿来使用。
            //使用则删除分片中的用户名
            redisCache.delSetCache(RedisKeyConstant.USER_REGISTER_REUSE, userRegisterParam.getUsername());
            //布隆过滤器防止用户重复注册，缓存穿透
            userRegisterBloomFilter.add(userRegisterParam.getUsername());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new BizException(StatusCode.SYSTEM_ERROR);
        } finally {
            if (tryLock) {
                lock.unlock();
            }
        }
    }

    @Override
    public Boolean hasUsername(String username) {
        boolean contains = userRegisterBloomFilter.contains(username);
        if (contains) {
            return redisCache.hasSetKey(RedisKeyConstant.LOCK_USER_REGISTER, username);
        }
        return true;
    }

    /**
     * 将 UserRegisterParam 映射为 PnUser 实体类
     */
    private PnUser mapToPnUser(UserRegisterParam userRegisterParam) {
        PnUser pnUser = new PnUser();
        pnUser.setUsername(userRegisterParam.getUsername());
        String saltedPassword = DigestUtil.md5Hex((PNUserCenterConstant.USER_PASSWORD_SLOT + userRegisterParam.getPassword()).getBytes());
        pnUser.setPassword(saltedPassword);
        pnUser.setPhone(userRegisterParam.getPhone());
        pnUser.setEmail(userRegisterParam.getEmail());
        pnUser.setFullName(userRegisterParam.getFullName());
        return pnUser;
    }

    @Override
    @Deprecated
    public void deletion(UserRegisterParam param) {
        if (StringUtils.isAnyBlank(param.getUsername())) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        //添加ThreadLocal之后比对注销用户是否一致
        RLock lock = redissonClient.getLock(RedisKeyConstant.USER_DELETION + param.getUsername());
        lock.lock();
        try {
            PnUser pnUser = new PnUser();
            pnUser.setUsername(param.getUsername());
            userMapper.deletionUser(pnUser);
            //todo 登陆状态的删除
        } finally {
            lock.unlock();
        }
    }
}
