//package com.pn.service.impls.user;
//
//
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.pn.common.base.UserTokenThreadHolder;
//import com.pn.common.enums.StatusCode;
//import com.pn.common.exception.BizException;
//import com.pn.common.reqParams.user.RoleParam;
//import com.pn.common.vos.login.UserVo;
//import com.pn.dao.entity.PnRole;
//import com.pn.dao.mapper.PnRoleMapper;
//import com.pn.service.RoleSettingService;
//import com.pn.service.utils.cover.UserRoleCoverUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.Resource;
//import java.util.Objects;
//
//@Slf4j
//@Service
//public class RoleSettingServiceImpl extends ServiceImpl<PnRoleMapper, PnRole> implements RoleSettingService {
//
//    @Resource
//    private PnRoleMapper roleMapper;
//
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public Long save(RoleParam param) {
//        if (Objects.isNull(param.getRoleId())) {
//            return insert(param);
//        }
//        return update(param);
//    }
//
//
//    private Long insert(RoleParam param) {
//        UserVo currentUser = UserTokenThreadHolder.getCurrentUser();
//        PnRole pnRole = UserRoleCoverUtil.paramConvertoPnRole(null, param, currentUser.getId());
//        save(pnRole);
//        return pnRole.getId();
//    }
//
//    private Long update(RoleParam param) {
//        PnRole pnRole = getById(param.getRoleId());
//        if (Objects.isNull(pnRole)) {
//            throw new BizException(StatusCode.PARAMS_ERROR);
//        }
//        UserVo currentUser = UserTokenThreadHolder.getCurrentUser();
//        pnRole = UserRoleCoverUtil.paramConvertoPnRole(pnRole, param, currentUser.getId());
//        this.updateById(pnRole);
//        return pnRole.getId();
//    }
//
//    private void paramCheck(RoleParam param) {
//        String roleName = param.getRoleName();
//        String roleDesc = param.getRoleDesc();
//        Boolean roleType = param.getRoleType();
//        if (Objects.isNull(roleName) || Objects.isNull(roleDesc)) {
//            throw new BizException(StatusCode.PARAMS_ERROR);
//        }
//    }
//}
