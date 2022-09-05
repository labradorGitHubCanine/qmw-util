package com.qmw.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * mysql列数据信息
 * 通过show full columns from ${table_name} 获取
 * 2022年9月5日, PM 02:43:24
 */
@Data
@Accessors(chain = true)
public class ColumnInfo {

    private String Field;
    private String Type;
    private String Collation;
    private String Null;
    private String Key;
    private String Default;
    private String Extra;
    private String Privileges;
    private String Comment;

}
