package com.pn.service.listener;

import com.pn.common.constant.CountConstant;
import com.pn.common.enums.NotifyEnum;
import com.pn.common.vos.count.CommentDO;
import com.pn.common.vos.notify.NotifyMsgEvent;
import com.pn.dao.entity.PnUserFoot;
import com.pn.service.impls.listener.UserStatisticEventListener;
import com.pn.web.PNUserCenterApp;
import java.util.Date;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEvent;
import org.springframework.test.context.junit4.SpringRunner;



/**
*文章计数测试
**/
@SpringBootTest(classes = PNUserCenterApp.class)
@RunWith(SpringRunner.class)
public class UserTest {

    public static void main(String[] args) {
         final String userStatistic = CountConstant.USER_STATISTIC_INFO+"%d";
        System.out.println(String.format(userStatistic, 2));
    }

    @Resource
   private UserStatisticEventListener listener;

    @Test
    public void test() {
        PnUserFoot userFoot = new PnUserFoot();
        userFoot.setUserId(0L);
        userFoot.setDocumentId(0L);
        userFoot.setDocumentType(0);
        userFoot.setDocumentUserId(0L);
        userFoot.setCollectionStat(0);
        userFoot.setReadStat(0);
        userFoot.setCommentStat(0);
        userFoot.setPraiseStat(0);
        userFoot.setCreateAt(new Date());
        userFoot.setUpdateAt(new Date());
        userFoot.setCreateBy(0L);
        userFoot.setUpdateBy(0L);
        userFoot.setIsDeleted(0);
        userFoot.setId(0L);
        CommentDO commentDO = new CommentDO();
        commentDO.setArticleId(0L);
        commentDO.setUserId(0L);
        commentDO.setDeleted(0);

        NotifyMsgEvent<CommentDO> event = new NotifyMsgEvent<>(new Object() , NotifyEnum.PRAISE, commentDO);
        listener.listenEvent(event);
    }
}
