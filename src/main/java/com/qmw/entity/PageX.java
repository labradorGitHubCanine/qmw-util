package com.qmw.entity;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

/**
 * mybatis-plus自定义的page工具类
 *
 * @param <T> 泛型
 * @author qmw
 * @since 1.00
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageX<T> extends Page<T> implements IPage<T> {

    // 自定义字段
    private Map<String, Object> extra;

    public PageX() {
        super();
    }

    public PageX(long current, long size) {
        super(current, size);
    }

    public PageX(long current, long size, long total) {
        super(current, size, total);
    }

    public PageX(long current, long size, boolean isSearchCount) {
        super(current, size, isSearchCount);
    }

    public PageX(long current, long size, long total, boolean isSearchCount) {
        super(current, size, total, isSearchCount);
    }

    public PageX<T> putExtra(String key, Object value) {
        if (extra == null)
            extra = new HashMap<>();
        extra.put(key, value);
        return this;
    }

}
