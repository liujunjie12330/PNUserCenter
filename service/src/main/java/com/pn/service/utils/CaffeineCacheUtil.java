package com.pn.service.utils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

/**
 * @author javadadi
 * 本地缓存
 */

public class CaffeineCacheUtil {
    private static final Cache<Object, Object> cache = Caffeine.newBuilder()
            //初始数量
            .initialCapacity(10)
            //最大条数
            .maximumSize(10)
            //expireAfterWrite和expireAfterAccess同时存在时，以expireAfterWrite为准
            //最后一次写操作后经过指定时间过期
            .expireAfterWrite(720, TimeUnit.MINUTES)
            //最后一次读或写操作后经过指定时间过期
            .expireAfterAccess(720, TimeUnit.MINUTES)
            //监听缓存被移除
            .removalListener((key, val, removalCause) -> {
            })
            //记录命中
            .recordStats()
            .build();

    public static void add(String key, Object value) {
        cache.put(key, value);
    }

    // 根据键获取缓存，使用泛型来确保类型安全
    public static <T> T get(String key, Class<T> type) {
        Object value = cache.getIfPresent(key);
        if (value == null) {
            return null; // 如果缓存不存在，返回 null
        }
        // 校验类型并返回
        if (type.isInstance(value)) {
            return type.cast(value); // 类型安全地返回值
        }
        throw new ClassCastException("Cached value is not of type: " + type.getName());
    }

    public static Object get(String key) {
        return cache.getIfPresent(key);
    }

    public static boolean hasCache(String key) {
        return cache.asMap().containsKey(key);
    }

    public static void remove(String key) {
        cache.invalidate(key);
    }
}
