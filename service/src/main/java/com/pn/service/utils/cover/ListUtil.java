package com.pn.service.utils.cover;


import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 列表工具类
 */
public class ListUtil {

    /**
     * 列表转换vo
     * @param sourceList
     * @param targetFunction
     * @return
     * @param <T>
     * @param <V>
     */
    public static <T, V> List<V> coverToListVo(List<T> sourceList, Function<T, V> targetFunction) {
        return sourceList
                .stream()
                .map(targetFunction)
                .collect(Collectors.toList());
    }
}
