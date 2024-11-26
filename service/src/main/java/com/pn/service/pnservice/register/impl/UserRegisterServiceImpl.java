package com.pn.service.pnservice.register.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.constant.RedisKeyConstant;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.params.register.UserDeletionReqParam;
import com.pn.common.params.register.UserRegisterParam;
import com.pn.common.vos.register.UserRegisterRespVo;
import com.pn.dao.entity.PnUser;
import com.pn.dao.mapper.PnUserMapper;
import com.pn.service.pnservice.register.UserRegisterService;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {

    @Resource
    private  RedissonClient redissonClient;
    @Resource
    private  PnUserMapper userMapper;
    @Resource
    private  RBloomFilter<String> userRegisterBloomFilter;
    @Resource
    private  StringRedisTemplate stringRedisTemplate;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserRegisterRespVo register(UserRegisterParam userRegisterParam) {
        Boolean hasUsername = hasUsername(userRegisterParam.getUsername());
        if(!hasUsername){
            throw new BizException(StatusCode.USER_ALREADY_EXIST);
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
            stringRedisTemplate.opsForSet().remove(RedisKeyConstant.USER_REGISTER_REUSE, userRegisterParam.getUsername());
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
        return mapToUserRegisterRespDTO(userRegisterParam);

    }

    @Override
    public Boolean hasUsername(String username) {
        boolean contains = userRegisterBloomFilter.contains(username);
        if (contains){
          return stringRedisTemplate.opsForSet().isMember(RedisKeyConstant.LOCK_USER_REGISTER,username);
        }
        return true;
    }

    /**
     * 将 UserRegisterDTO 映射为 PnUser 实体类
     */
    private PnUser mapToPnUser(UserRegisterParam userRegisterParam) {
        PnUser pnUser = new PnUser();
        pnUser.setUsername(userRegisterParam.getUsername());
        String saltedPassword = DigestUtil.md5Hex((PNUserCenterConstant.USER_PASSWORD_SLOT + userRegisterParam.getPassword()).getBytes());
        pnUser.setPassword(saltedPassword);
        pnUser.setPhone(userRegisterParam.getPhone());
        pnUser.setEmail(userRegisterParam.getEmail());
        pnUser.setFullName(userRegisterParam.getFullName());
        pnUser.setIsDeleted(false); // 默认值
        pnUser.setIsAdmin(0); // 默认非管理员
        pnUser.setLastLoginDate(null); // 注册时无登录记录
        return pnUser;
    }

    /**
     * 构建 UserRegisterRespDTO 返回对象
     */
    private UserRegisterRespVo mapToUserRegisterRespDTO(UserRegisterParam userRegisterParam) {
        UserRegisterRespVo respDTO = new UserRegisterRespVo();
        respDTO.setUsername(userRegisterParam.getUsername());
        respDTO.setPhone(userRegisterParam.getPhone());
        respDTO.setEmail(userRegisterParam.getEmail());
        return respDTO;
    }

    @Override
    public void deletion(UserRegisterParam param) {
        if (StringUtils.isAnyBlank(param.getUsername())){
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        //添加ThreadLocal之后比对注销用户是否一致
        RLock lock=redissonClient.getLock(RedisKeyConstant.USER_DELETION+ param.getUsername());
        lock.lock();
        try{
            PnUser pnUser=new PnUser();
            pnUser.setUsername(param.getUsername());
            userMapper.deletionUser(pnUser);
            //todo 登陆状态的删除
        }finally {
            lock.unlock();
        }
    }
}
