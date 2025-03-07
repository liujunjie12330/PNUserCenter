package com.pn.service.aop;

import cn.hutool.core.date.StopWatch;
import com.alipay.api.AlipayApiException;
import com.pn.common.annotation.AlipayLog;
import com.pn.common.enums.PayTypeEnum;
import com.pn.common.exception.BizException;
import com.pn.service.impls.pay.dto.PayBaseDto;
import com.pn.service.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * alipay方法监控
 */
@Component
@Aspect
@Slf4j
public class AlipayLogInterceptor {

    @Resource
    private RedisCache redisCache;

    @Around("@annotation(alipayLog)")
    public Object payLog(ProceedingJoinPoint joinPoint, AlipayLog alipayLog){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 获取方法信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String extraInfo = alipayLog.value();
        Object[] args = joinPoint.getArgs();
        log.info("【支付请求】方法: {}，参数: {}，附加信息: {}",
                signature.getName(), args, extraInfo);
        Object result;
        PayBaseDto dto = (PayBaseDto) args[0];
        PayTypeEnum typeEnum = (PayTypeEnum) args[1];
        try {
            // 执行原方法
            result = joinPoint.proceed();
            log.info("【支付成功】返回结果: {}", result);
            //支付成功,把支付相关信息放到redis里面
            redisCache.setHashCache(dto.getOutBizNo(),"paytype",typeEnum.getType());
            redisCache.setHashCache(dto.getOutBizNo(),"payInfo",args[0]);
            redisCache.setHashCache(dto.getOutBizNo(),"paystatus",true);
        } catch (AlipayApiException e) {
            //代表执行扣款错误,需要把支付的状态更改成false
            log.error("【支付异常】错误信息: {}", e.getMessage(), e);
            redisCache.delHashCache(dto.getOutBizNo(),"payinfo","paytype","paystatus");
            throw new BizException(e.getMessage());
        } catch (Throwable e) {
            //支付成功但是系统出现问题，需要额外处理
            log.info("支付成功但是系统出现问题,{}",e.getMessage());
            throw new RuntimeException(e);
        } finally {
            stopWatch.stop();
            log.info("【支付耗时】方法 {} 执行耗时: {} ms", signature.getName(), stopWatch.getTotalTimeMillis());
        }
        return result;
    }


}
