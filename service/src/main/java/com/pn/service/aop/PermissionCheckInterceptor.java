package com.pn.service.aop;


import com.pn.common.annotation.AuthCheck;
import com.pn.common.base.UserTokenThreadHolder;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.vos.login.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * 鉴权
 */
@Component
@Aspect
@Slf4j
public class PermissionCheckInterceptor {
    @Around("@annotation(authCheck)")
    public Object permissionCheck(ProceedingJoinPoint joinPoint, AuthCheck authCheck) {
        try {
            //任何权限
            List<String> anyPermissionList = Arrays.asList(authCheck.anyPermission());
            //必须权限
            List<String> mustPermissionList = Arrays.asList(authCheck.mustPermission());
            //记录请求信息
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            String method = request.getMethod();
            String pathInfo = request.getPathInfo();
            String ip = request.getRemoteAddr();
            String requestURI = request.getRequestURI();
            log.info("request info method ==>{},pathInfo==>{},ip==>{} requestURI==>{}", method, pathInfo, ip, requestURI);
            UserVo currentUser = UserTokenThreadHolder.getCurrentUser();
            List<String> userPermission = currentUser.getPermissions();
            boolean mustHas = mustHas(mustPermissionList, userPermission);
            boolean anyHas = anyHas(anyPermissionList, userPermission);
            if (BooleanUtils.isFalse(mustHas && anyHas)) {
                log.error("can not arrive {} because don't have permission==>{} ", requestURI, mustPermissionList.toString() + anyPermissionList.toString());
                throw new BizException(StatusCode.NO_AUTH_ERROR);
            }
            //校验通过
            log.info("auth permission success of uri===>{}",requestURI);
            return joinPoint.proceed();
        } catch (Throwable e) {
            log.error("system error ==>{}",e.getMessage());
        }
        return null;
    }

    /**
     * all permission check
     *
     * @param mustPermission
     * @param userPermission
     * @return
     */
    private boolean mustHas(List<String> mustPermission, List<String> userPermission) {
        if (CollectionUtils.isEmpty(mustPermission)) {
            return true;
        }
        if (CollectionUtils.isEmpty(userPermission)) {
            return false;
        }
        boolean check = true;
        for (String permission : mustPermission) {
            check = check && userPermission.contains(permission);
        }
        return check;
    }

    /**
     * any permission check
     *
     * @param anyPermission
     * @param userPermission
     * @return
     */
    private boolean anyHas(List<String> anyPermission, List<String> userPermission) {
        if (CollectionUtils.isEmpty(anyPermission)) {
            return true;
        }
        if (CollectionUtils.isEmpty(userPermission)) {
            return false;
        }
        boolean check = userPermission.contains(anyPermission.get(0));
        for (String permission : anyPermission) {
            check = check || userPermission.contains(permission);
        }
        return check;
    }

}
