package com.pn.service.user;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.reqParams.user.UserRolePermissionSettingParam;
import com.pn.dao.bo.user.SearchUserPermissionBo;
import com.pn.service.RoleUserSettingService;
import com.pn.service.UserLoginService;
import com.pn.service.UserSettingService;
import com.pn.web.PNUserCenterApp;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version 1.0
 * @Author：alex
 * @Date：2025/2/26 17:28
 * @Descirption xxx
 */
@SpringBootTest(classes = PNUserCenterApp.class)
//@RunWith(SpringRunner.class)
public class UserRoleTest {

    @Resource
    private RoleUserSettingService roleUserSettingService;


    @Resource
    private UserLoginService loginService;
    @Test
    void testSearchUserRole(){
        UserRolePermissionSettingParam roleParam = new UserRolePermissionSettingParam();
//        roleParam.setRoleId(1L);
        roleParam.setRoleName("管理员");
        Page<SearchUserPermissionBo> page =
                roleUserSettingService.searchRoleOfUser(roleParam);
        List<SearchUserPermissionBo> records = page.getRecords();
        for (SearchUserPermissionBo record : records) {
            System.out.println(record);
        }

    }

    public static void main(String[] args) {
        System.out.println(DigestUtil.md5Hex((PNUserCenterConstant.USER_PASSWORD_SLOT + "20021018").getBytes()));
    }
}
