package com.pn.service.impls.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pn.common.base.UserTokenThreadHolder;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.reqParams.user.PermissionSaveParam;
import com.pn.dao.entity.PnPermission;
import com.pn.dao.mapper.PnPermissionMapper;
import com.pn.service.PermissionSettingService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @version 1.0
 * @Author：alex
 * @Date：2025/2/25 17:06
 * @Descirption xxx
 */
@Service
public class PermissionSettingServiceIml extends ServiceImpl<PnPermissionMapper, PnPermission> implements PermissionSettingService {

    @Override
    public Long save(PermissionSaveParam param) {
        //保存
        if(Objects.isNull(param.getId())) {
            insert(param);
        }
        return update(param);
    }


    private Long insert(PermissionSaveParam param) {
        PnPermission pnPermission = new PnPermission();
        pnPermission.setCode(param.getCode());
        pnPermission.setName(param.getName());
        pnPermission.setDisplay(param.getDisplay());
        pnPermission.setStatus(param.getStatus());
        Long userId = UserTokenThreadHolder.getCurrentUser().getId();
        pnPermission.setCreateBy(userId);
        this.baseMapper.insert(pnPermission);
        return param.getId();
    }


    private Long update(PermissionSaveParam param) {
        PnPermission pnPermission = getById(param.getId());
        if(Objects.isNull(pnPermission)) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        pnPermission.setCode(param.getCode());
        pnPermission.setName(param.getName());
        pnPermission.setDisplay(param.getDisplay());
        pnPermission.setStatus(param.getStatus());
        Long userId = UserTokenThreadHolder.getCurrentUser().getId();
        pnPermission.setCreateBy(userId);
        this.baseMapper.updateById(pnPermission);
        return param.getId();
    }

    void checkParam(PermissionSaveParam param) {
        String code = param.getCode();
        String name = param.getName();
        String display = param.getDisplay();
        int status = param.getStatus();
        if(Objects.isNull(code) || Objects.isNull(name) || Objects.isNull(display)) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
    }
}
