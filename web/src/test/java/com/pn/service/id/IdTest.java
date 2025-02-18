package com.pn.service.id;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: javadadi
 * @Time: 18:40
 * @ClassName: IdTest
 */
@SpringBootTest
public class IdTest {
    public static void main(String[] args) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        long id = snowflake.nextId();
        System.out.println(id);
//简单使用
        long s = IdUtil.getSnowflakeNextId();
        System.out.println(s);
    }
}
