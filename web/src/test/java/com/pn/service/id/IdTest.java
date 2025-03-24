package com.pn.service.id;

import cn.hutool.core.lang.Snowflake;
import com.pn.service.utils.id.IdUtil;
import io.github.classgraph.json.Id;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author: javadadi
 * @Time: 18:40
 * @ClassName: IdTest
 */
@SpringBootTest
public class IdTest {
    public static void main(String[] args) {
        System.out.println(LocalDate.now());
    }
}
