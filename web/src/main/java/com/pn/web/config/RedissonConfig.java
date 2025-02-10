package com.pn.web.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author:liujunjie
 * @version:1.0 Time:11:49
 * CreatedBy:IntelliJ IDEA
 * ClassName:RedissionConfig
 */
@Configuration
public class RedissonConfig {
    @Value("${spring.redis.host:localhost}")
    private String redisHost;

    @Value("${spring.redis.port:6379}")
    private String redisPort;
    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        String redisAddress = "redis://"+redisHost+":"+redisPort;
        SingleServerConfig singleServerConfig = config.useSingleServer().setAddress(redisAddress).setDatabase(5);
        return Redisson.create(config);
    }
}
