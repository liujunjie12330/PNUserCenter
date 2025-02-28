package com.pn.service.impls.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pn.common.base.UserTokenThreadHolder;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.reqParams.user.UserRolePermissionSettingParam;
import com.pn.dao.entity.PnPermission;
import com.pn.dao.mapper.PnPermissionMapper;
import com.pn.service.PermissionSettingService;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class PermissionSettingServiceImpl extends ServiceImpl<PnPermissionMapper, PnPermission> implements PermissionSettingService {

    @Override
    public Long save(UserRolePermissionSettingParam param) {
        //保存
        if(Objects.isNull(param.getPermissionId())) {
            insert(param);
        }
        return update(param);
    }


    private Long insert(UserRolePermissionSettingParam param) {
        PnPermission pnPermission = new PnPermission();
        pnPermission.setCode(param.getPermissionCode());
        pnPermission.setName(param.getPermissionName());
        pnPermission.setDisplay(param.getPermissionDisplay());
        pnPermission.setStatus(Integer.valueOf(param.getPermissionStatus()));
        Long userId = UserTokenThreadHolder.getCurrentUser().getId();
        pnPermission.setCreateBy(userId);
        this.baseMapper.insert(pnPermission);
        return pnPermission.getId();
    }


    private Long update(UserRolePermissionSettingParam param) {
        PnPermission pnPermission = getById(param.getPermissionId());
        if(Objects.isNull(pnPermission)) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        pnPermission.setCode(param.getPermissionCode());
        pnPermission.setName(param.getPermissionName());
        pnPermission.setDisplay(param.getPermissionDisplay());
        pnPermission.setStatus(Integer.valueOf(param.getPermissionStatus()));
        Long userId = UserTokenThreadHolder.getCurrentUser().getId();
        pnPermission.setCreateBy(userId);
        this.baseMapper.updateById(pnPermission);
        return pnPermission.getId();
    }

    void checkParam(UserRolePermissionSettingParam param) {
        String code = param.getPermissionCode();
        String name = param.getPermissionName();
        String display = param.getPermissionDisplay();
        String status = param.getPermissionStatus();
        if(Objects.isNull(code) || Objects.isNull(name) || Objects.isNull(display)) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
    }
}
