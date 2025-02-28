package com.pn.web.controller.article;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pn.common.base.BaseResponse;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.reqParams.article.ArticleIndexParam;
import com.pn.common.reqParams.article.ArticleSaveParams;
import com.pn.common.utils.ResultUtils;
import com.pn.common.vos.article.ArticleIndexVo;
import com.pn.service.ArticleReadService;
import com.pn.service.ArticleWriteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 文章前台接口
 */
@RestController
@RequestMapping(PNUserCenterConstant.BASE_URL + "/article/index")
public class ArticleController {
    @Resource
    private ArticleReadService readService;

    @Resource
    private ArticleWriteService articleWriteService;

    @PostMapping("/page")
    public BaseResponse<Page<ArticleIndexVo>> pageArticle(@RequestBody ArticleIndexParam param) {
        return ResultUtils.success(readService.page(param));
    }

    @PostMapping("/save")
    public BaseResponse<Long> saveArticle(@RequestBody ArticleSaveParams param){
        return ResultUtils.success(articleWriteService.save(param));
    }
}
