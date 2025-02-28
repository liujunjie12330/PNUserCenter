package com.pn.service.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pn.common.reqParams.user.UserRolePermissionSettingParam;
import com.pn.dao.bo.user.SearchUserPermissionBo;
import com.pn.service.RoleUserSettingService;
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


}
