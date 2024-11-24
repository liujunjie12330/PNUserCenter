package com.pn.service.pnservice.register.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.constant.RedisKeyConstant;
import com.pn.common.exception.BizException;
import com.pn.dao.dto.register.UserRegisterDTO;
import com.pn.dao.dto.register.UserRegisterRespDTO;
import com.pn.dao.entity.PnUser;
import com.pn.dao.mapper.PnUserMapper;
import com.pn.service.pnservice.register.UserRegisterService;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {

    private final RedissonClient redissonClient;
    private final PnUserMapper userMapper;
    private final RBloomFilter<String> userRegisterBloomFilter;
    private final StringRedisTemplate stringRedisTemplate;
    @Autowired
    public UserRegisterServiceImpl(RedissonClient redissonClient, PnUserMapper userMapper, RBloomFilter<String> userRegisterBloomFilter,StringRedisTemplate stringRedisTemplate) {
        this.redissonClient = redissonClient;
        this.userMapper = userMapper;
        this.userRegisterBloomFilter = userRegisterBloomFilter;
        this.stringRedisTemplate = stringRedisTemplate;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserRegisterRespDTO register(UserRegisterDTO userRegisterDTO) {
        Boolean hasUsername = hasUsername(userRegisterDTO.getUsername());
        if(!hasUsername){
            throw new BizException("用户名已存在！");
        }
        RLock lock = redissonClient.getLock(RedisKeyConstant.LOCK_USER_REGISTER + userRegisterDTO.getUsername());
        boolean tryLock = false;
        try {
            tryLock = lock.tryLock(5, 30, TimeUnit.SECONDS);
            if (!tryLock) {
                throw new BizException("用户名已存在，请稍后重试！");
            }
            PnUser pnUser = mapToPnUser(userRegisterDTO);
            int insert = userMapper.insert(pnUser);
            if (insert < 1) {
                throw new BizException("用户注册失败，请稍后重试！");
            }
            //用户注销之后,原本所持有的用户名则会被放到redis中可复用的集合中重新拿来使用。
            //使用则删除分片中的用户名
            stringRedisTemplate.opsForSet().remove(RedisKeyConstant.USER_REGISTER_REUSE,userRegisterDTO.getUsername());
            //布隆过滤器防止用户重复注册，缓存穿透
            userRegisterBloomFilter.add(userRegisterDTO.getUsername());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new BizException("系统繁忙，请稍后重试！");
        } finally {
            if (tryLock) {
                lock.unlock();
            }
        }
        return mapToUserRegisterRespDTO(userRegisterDTO);

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
    private PnUser mapToPnUser(UserRegisterDTO userRegisterDTO) {
        PnUser pnUser = new PnUser();
        pnUser.setUsername(userRegisterDTO.getUsername());
        String saltedPassword = DigestUtil.md5Hex((PNUserCenterConstant.USER_PASSWORD_SLOT + userRegisterDTO.getPassword()).getBytes());
        pnUser.setPassword(saltedPassword);
        pnUser.setPhone(userRegisterDTO.getPhone());
        pnUser.setEmail(userRegisterDTO.getEmail());
        pnUser.setFullName(userRegisterDTO.getFullName());
        pnUser.setIsDeleted(false); // 默认值
        pnUser.setIsAdmin("0"); // 默认非管理员
        pnUser.setLastLoginDate(null); // 注册时无登录记录
        pnUser.setUpdateLastTime(new Date()); // 初始化最后更新时间
        return pnUser;
    }

    /**
     * 构建 UserRegisterRespDTO 返回对象
     */
    private UserRegisterRespDTO mapToUserRegisterRespDTO(UserRegisterDTO userRegisterDTO) {
        UserRegisterRespDTO respDTO = new UserRegisterRespDTO();
        respDTO.setUsername(userRegisterDTO.getUsername());
        respDTO.setPhone(userRegisterDTO.getPhone());
        respDTO.setEmail(userRegisterDTO.getEmail());
        respDTO.setPassword(userRegisterDTO.getPassword());
        return respDTO;
    }

}
