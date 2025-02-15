package com.pn.service.aop;

import com.pn.common.annotation.Integral;
import com.pn.common.base.UserTokenThreadHolder;
import com.pn.common.constant.RedisKeyConstant;
import com.pn.common.event.IntegralEvent;
import com.pn.dao.dto.IntegralDTO;
import com.pn.service.pnservice.integral.IntegralService;
import com.pn.service.utils.DateUtils;
import com.pn.service.utils.RedisCache;
import com.pn.service.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import javax.annotation.Resource;
import java.util.Objects;

@Slf4j
@Configuration
@Aspect
public class IntegralAspect {

    @Resource
    private IntegralService integralService;
    @Autowired
    private RedisCache redisCache;


    /**
     * 处理请求前执行
     */
    @Before(value = "@annotation(controllerLog)")
    public void doBefore(JoinPoint joinPoint, Integral controllerLog) {
        log.info("{}", controllerLog.actionName());
    }

    /**
     * 处理完请求后执行
     *
     * @param controllerIntegral 切点
     */
    @AfterReturning(pointcut = "@annotation(controllerIntegral)", returning = "jsonResult")
    public void doAfterReturning(Integral controllerIntegral, Object jsonResult) {
        String actionName = controllerIntegral.actionName().getActionName();
        long userId = Objects.equals(controllerIntegral.userId(), "") ? UserTokenThreadHolder.getCurrentUser().getId() : Long.parseLong(controllerIntegral.userId());
        String rateKey = RedisKeyConstant.RATE_LIMIT_KEY + userId + ":" + actionName;
        IntegralDTO integral = integralService.getIntegral(actionName);
        if (integral == null) {
            return;
        } else if (!integral.getIsUse()) {
            return;
        }
        Object cacheObject = redisCache.get(rateKey);
        if (cacheObject == null) {
            redisCache.set(rateKey, 0,DateUtils.getSecondsToTodayEnd());
        } else if ((int) cacheObject >= integral.getUpperLimit()) {
            return;
        }
        redisCache.set(rateKey, (int) redisCache.get(rateKey) + 1, DateUtils.getSecondsToTodayEnd());
        IntegralEvent integralEvent = new IntegralEvent();
        integralEvent.setUserId(userId);
        integralEvent.setValue(integral.getValue());
        integralEvent.setType(integral.getKey());
        integralEvent.setDesc(integral.getTitle());
        SpringUtils.context().publishEvent(integralEvent);
    }

}
