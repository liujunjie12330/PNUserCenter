package com.pn.service.utils.cover;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页转换工具
 */
public class PageUtil<T> {

    /**
     * 转换vo的分页工具
     *
     * @param sourcePage
     * @param targetFunction
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T, V> Page<V> coverToPageVo(Page<T> sourcePage, Function<T, V> targetFunction) {
        List<V> vos = sourcePage.getRecords()
                .stream()
                .map(targetFunction)
                .collect(Collectors.toList());
        Page<V> pageVo = new Page<>(sourcePage.getCurrent(), sourcePage.getSize(), sourcePage.getTotal());
        pageVo.setRecords(vos);
        return pageVo;
    }
}
