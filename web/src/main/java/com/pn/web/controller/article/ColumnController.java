package com.pn.web.controller.article;

import com.pn.common.constant.PNUserCenterConstant;
import com.pn.service.ColumnSettingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 专栏前台接口
 */
@RestController
@RequestMapping(PNUserCenterConstant.BASE_URL+"/column/index")
public class ColumnController {
    @Resource
    private ColumnSettingService settingService;
}
