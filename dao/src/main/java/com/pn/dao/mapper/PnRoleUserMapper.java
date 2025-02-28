package com.pn.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pn.common.reqParams.user.UserRolePermissionSettingParam;
import com.pn.dao.bo.user.SearchUserPermissionBo;
import com.pn.dao.entity.PnRoleUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PnRoleUserMapper extends BaseMapper<PnRoleUser> {

    // 查询用户对应角色包含的权限
    Page<SearchUserPermissionBo> searchUserPermission(Page<SearchUserPermissionBo> page, @Param("param") UserRolePermissionSettingParam param);
}