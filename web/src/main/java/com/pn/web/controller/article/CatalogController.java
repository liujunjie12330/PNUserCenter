package com.pn.web.controller.article;

import com.pn.common.base.BaseResponse;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.utils.ResultUtils;
import com.pn.common.vos.article.CatalogPaveVo;
import com.pn.service.CatalogService;
import com.pn.service.CatalogSettingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 分类接口
 */
@RestController
@RequestMapping(PNUserCenterConstant.BASE_URL + "/catalog")
public class CatalogController {
    @Resource
    private CatalogSettingService catalogSettingService;

    @Resource
    private CatalogService catalogService;

    @GetMapping("/list/allCatalogs")
    public BaseResponse<List<CatalogPaveVo>> listAllCatalogs() {
        return ResultUtils.success(catalogService.listAllCatalog());
    }
}
