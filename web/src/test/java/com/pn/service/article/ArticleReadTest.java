package com.pn.service.article;

import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pn.common.base.UserTokenThreadHolder;
import com.pn.common.reqParams.article.ArticleIndexParam;
import com.pn.common.vos.article.ArticleIndexVo;
import com.pn.common.vos.login.UserVo;
import com.pn.service.ArticleReadService;
import com.pn.web.PNUserCenterApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author: javadadi
 * @Time: 15:50
 * @ClassName: ArticleReadTest
 */
@SpringBootTest(classes = PNUserCenterApp.class)
@RunWith(SpringRunner.class)
public class ArticleReadTest {
    @Resource
    private ArticleReadService readService;

    @Test
    public void test() {
        Page<ArticleIndexVo> page =
                readService.page(new ArticleIndexParam());
        System.out.println(page.getRecords());
    }

    @Test
        public void testPayArticle() throws AlipayApiException {
        UserVo userVo = UserVo.builder().id(1L).build();
        UserTokenThreadHolder.addCurrentUser(userVo);
        readService.read(2506425347227649L);
    }
}
