package com.pn.dao.article;
import java.util.Date;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pn.common.reqParams.article.ArticleAdminParam;
import com.pn.dao.bo.article.PnArticleBo;
import com.pn.dao.mapper.PnArticleMapper;
import com.pn.web.PNUserCenterApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author: javadadi
 * @Time: 22:21
 * @ClassName: ArticleTest
 */
@SpringBootTest(classes = PNUserCenterApp.class)
@RunWith(SpringRunner.class)
public class ArticleTest {

    @Resource
    PnArticleMapper mapper;

    @Test
    public void test(){
        ArticleAdminParam param = new ArticleAdminParam();
        param.setArticleId(0L);
        param.setAuthorId(0L);
        param.setAuthorName("");
        param.setTitle("");
        param.setStatus(0);
        param.setOfficalStat(0);
        param.setToppingStat(0);
        param.setCreamStat(0);
        param.setUpdateTime(new Date());
        param.setSize(0L);
        param.setCurrent(0L);
        Page<PnArticleBo> page = new Page<>(param.getCurrent(),param.getSize());
        Page<PnArticleBo> page1 = mapper.page(page, param);
    }

}
