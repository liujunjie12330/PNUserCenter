package com.pn.web.controller.article;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pn.common.base.BaseResponse;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.reqParams.article.TagParam;
import com.pn.common.utils.ResultUtils;
import com.pn.common.vos.article.TagVo;
import com.pn.service.TagSettingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * tag控制器
 */
@RestController
@RequestMapping(PNUserCenterConstant.BASE_URL+"/tag")
public class TagController {
    @Resource
    private TagSettingService tagSettingService;


    @PostMapping("/page")
    public BaseResponse<Page<TagVo>> pageTag(@RequestBody TagParam param){
        return ResultUtils.success(tagSettingService.page(param));
    }
}
