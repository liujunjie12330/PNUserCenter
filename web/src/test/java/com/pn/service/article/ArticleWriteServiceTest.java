package com.pn.service.article;
import java.util.HashSet;
import java.util.List;

import com.pn.common.base.UserTokenThreadHolder;
import com.pn.common.reqParams.article.ArticleSaveParams;
import com.pn.common.vos.login.UserVo;
import com.pn.service.ArticleWriteService;
import com.pn.web.PNUserCenterApp;
import org.junit.Test;
import org.junit.internal.Classes;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author: javadadi
 * @Time: 18:52
 * @ClassName: ArticleWriteServiceTest
 */
@SpringBootTest(classes = PNUserCenterApp.class )
@RunWith(SpringRunner.class)
public class ArticleWriteServiceTest {
    @Resource
    public ArticleWriteService articleWrite;

    @Value("${file.access}")
    private List<String> fileAccess;

    @Test
    public void test2(){
        System.out.println(fileAccess);
    }

    /**
     * 测试保存文章
     */
    @Test
    public void test(){
        UserVo userVo = UserVo.builder().id(1L).isAdmin(0).username("liujunjie").build();
        UserTokenThreadHolder.addCurrentUser(userVo);
        ArticleSaveParams params = new ArticleSaveParams();
        params.setArticleId(1L);
        params.setTitle("");
        params.setShortTitle("");
        params.setCategoryId(0L);
        params.setTagIds(new HashSet<Long>());
        params.setSummary("sss");
        params.setContent("傻逼哦，sanacna9qn9q9as擦擦");
        params.setCover("ssss");
        params.setArticleTypeId(1);
        params.setSource(0);
        params.setStatus(0);
        params.setActionType("POST");
        params.setSourceUrl("");
        params.setColumnId(0L);
        params.setReadType(0);
        params.setPayWay(0);
        params.setPayAmount("");
        params.setPayImageUrl("");
        articleWrite.save(params);
    }
}
