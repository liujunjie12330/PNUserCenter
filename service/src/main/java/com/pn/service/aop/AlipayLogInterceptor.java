package com.pn.service.aop;

import cn.hutool.core.date.StopWatch;
import com.alipay.api.AlipayApiException;
import com.pn.common.annotation.AlipayLog;
import com.pn.common.base.UserTokenThreadHolder;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.enums.PayTypeEnum;
import com.pn.common.exception.BizException;
import com.pn.common.vos.login.UserVo;
import com.pn.dao.entity.PnOrder;
import com.pn.dao.mapper.PnOrderMapper;
import com.pn.service.impls.pay.dto.PayBaseDto;
import com.pn.service.utils.RedisCache;
import com.pn.service.utils.cover.RecordCoverUtil;
import com.pn.service.utils.id.IdUtil;
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

    @Resource
    private PnOrderMapper orderMapper;

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
        Long id = IdUtil.parseIdFromPayCode(dto.getOutBizNo());
        UserVo currentUser = UserTokenThreadHolder.getCurrentUser();
        String msg = "success";
        try {
            // 执行原方法
            result = joinPoint.proceed();
            log.info("【alipay】返回结果: {}", result);
            //发起支付成功,把支付相关信息放到redis里面
            redisCache.setHashCache(dto.getOutBizNo(),"pay_type",typeEnum.getType());
            redisCache.set(String.format(PNUserCenterConstant.ORDER_PREFIX,id,currentUser.getId()),dto.getOutBizNo());
        } catch (AlipayApiException e) {
            //代表发起支付错误,需要把支付的状态更改成false
            log.error("【alipay】错误信息: {}", e.getMessage(), e);
            throw new BizException(e.getMessage());
        } catch (Throwable e) {
            //发起支付成功但是系统出现问题，需要额外处理
            log.info("【alipay】支付成功但是系统出现问题,{}",e.getMessage());
            msg = e.getMessage();
            throw new RuntimeException(e);
        } finally {
            stopWatch.stop();
            //保存发起的支付记录
            insertOrder(dto,typeEnum,msg);
            log.info("【alipay】方法 {} 执行耗时: {} ms", signature.getName(), stopWatch.getTotalTimeMillis());
        }
        return result;
    }

    private void insertOrder(PayBaseDto dto,PayTypeEnum typeEnum,String msg)
    {
        UserVo currentUser = UserTokenThreadHolder.getCurrentUser();
        PnOrder pnOrder = RecordCoverUtil.coverToPnOrder(dto, typeEnum, currentUser.getId());
        orderMapper.insert(pnOrder);
    }


}
