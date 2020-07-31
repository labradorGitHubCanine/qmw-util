package com.qmw.entity;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.HashMap;
import java.util.Map;

public class PageX<T> extends Page<T> implements IPage<T> {

    // 自定义字段
    private Map<String, Object> fields;

    public PageX(long current, long size) {
        super(current, size);
    }

    public PageX<T> addField(String key, Object value) {
        if (fields == null)
            fields = new HashMap<>();
        fields.put(key, value);
        return this;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }

}
