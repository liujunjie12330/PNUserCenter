package com.pn.service.utils.cover;

import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.pn.common.reqParams.user.UserRolePermissionSettingParam;
import com.pn.common.vos.login.UserVo;
import com.pn.dao.entity.PnAlipayUserInfo;
import com.pn.dao.entity.PnRole;
import com.pn.dao.entity.PnRolePermission;
import com.pn.dao.entity.PnUser;
import org.apache.catalina.User;

import java.util.Objects;

/**
 * 用户相关转换
 */
public class UserCoverUtil {

    public static UserVo pnUserCoverToVo(PnUser pnUser){
        return UserVo.builder()
                .id(pnUser.getId())
                .username(pnUser.getUsername())
                .fullName(pnUser.getFullName())
                .email(pnUser.getEmail())
                .phone(pnUser.getPhone())
                .isAdmin(pnUser.getIsAdmin())
                .avatar(pnUser.getAvatar())
                .lastLoginDate(pnUser.getLastLoginDate())
                .build();
    }

    public static PnRole paramCoverToPnRole(PnRole pnRole, UserRolePermissionSettingParam param, Long userId) {
        if(Objects.isNull(pnRole)){
            pnRole = new PnRole();
        }
        pnRole.setRoleName(param.getRoleName());
        pnRole.setRoleType(param.getRoleType());
        pnRole.setRoleDesc(param.getRoleDesc());
        pnRole.setCreateBy(userId);
        return pnRole;
    }

    public static PnRolePermission paramCoverToPnRolePermission(PnRolePermission rolePermission, UserRolePermissionSettingParam param, Long userId) {
        if(Objects.isNull(rolePermission)){
           rolePermission = new PnRolePermission();
        }
        rolePermission.setPermissionId(param.getPermissionId());
        rolePermission.setRoleId(param.getRoleId());
        rolePermission.setCreateBy(userId);
        return rolePermission;
    }
    public static PnAlipayUserInfo responseCoverToPnAliPay(AlipayUserInfoShareResponse response, Long pnUserId){
        PnAlipayUserInfo userInfo = new PnAlipayUserInfo();
        userInfo.setAlipayUuid(response.getUserId());
        userInfo.setPnUserId(pnUserId);
        userInfo.setUserName(response.getUserName());
        userInfo.setNickName(response.getNickName());
        userInfo.setDisplayName(response.getDisplayName());
        userInfo.setEmail(response.getEmail());
        userInfo.setMobile(response.getMobile());
        userInfo.setGender(response.getGender());
        userInfo.setPersonBirthday(response.getPersonBirthday());
        userInfo.setProvince(response.getProvince());
        userInfo.setCity(response.getCity());
        userInfo.setCountryCode(response.getCountryCode());
        userInfo.setAvatar(response.getAvatar());
        userInfo.setCertNo(response.getCertNo());
        userInfo.setCertType(response.getCertType());
        userInfo.setIsCertified(response.getIsCertified());
        userInfo.setIsStudentCertified(response.getIsStudentCertified());
        userInfo.setIsBlocked(response.getIsBlocked());
        userInfo.setInstOrCorp(response.getInstOrCorp());
        userInfo.setUserStatus(response.getUserStatus());
        userInfo.setUserType(Integer.valueOf(response.getUserType()));
        userInfo.setMemberGrade(response.getMemberGrade());
        return userInfo;
    }

}
