package com.pn.web.controller.article;

import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pn.common.base.BaseResponse;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.reqParams.article.ArticleIndexParam;
import com.pn.common.reqParams.article.ArticleSaveParams;
import com.pn.common.utils.ResultUtils;
import com.pn.common.vos.article.ArticleIndexVo;
import com.pn.common.vos.article.ArticleVO;
import com.pn.service.ArticleReadService;
import com.pn.service.ArticleWriteService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

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


    @GetMapping("/read/{id}")
    public BaseResponse<ArticleVO> read(@PathVariable("id") Long id, HttpServletResponse response) throws AlipayApiException {
        ArticleVO read = readService.read(id);
        return ResultUtils.success(read);
    }

    @GetMapping("/read/isPaid/{articleId}")
    public BaseResponse<Boolean> isPaid(@PathVariable("articleId") Long articleId) {
        if (Objects.isNull(articleId) || articleId <= 0) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        return ResultUtils.success(readService.isPaid(articleId));
    }

    @PostMapping("/page")
    public BaseResponse<Page<ArticleIndexVo>> pageArticle(@RequestBody ArticleIndexParam param) {
        return ResultUtils.success(readService.page(param));
    }

    @PostMapping("/save")
    public BaseResponse<Long> saveArticle(@RequestBody ArticleSaveParams param) {
        return ResultUtils.success(articleWriteService.save(param));
    }
}
