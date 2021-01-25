package com.test.ge.common.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 分页对象工具类
 *
 * @author lxq
 */
public class PageUtil<T> {

    /**
     * 默认页
     */
    private final static Long DEFAULT_PAGE = 1L;

    /**
     * 默认单页限制
     */
    private final static Long DEFAULT_SIZE = 10L;

    /**
     * 获取分页对象
     *
     * @param page 请求页
     * @param size 单页限制
     * @return Page<T>
     */
    public Page<T> getPage(Long page,
                           Long size){
        if(size == null && page == null){
            return new Page<>(DEFAULT_PAGE, DEFAULT_SIZE);
        }
        if(size == null){
            return new Page<>(page, DEFAULT_SIZE);
        }
        if(page == null){
            return new Page<>(DEFAULT_PAGE, size);
        }
        return new Page<>(page, size);
    }

}
