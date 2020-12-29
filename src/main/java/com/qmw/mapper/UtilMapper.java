package com.qmw.mapper;

import org.apache.ibatis.annotations.Select;

public interface UtilMapper {

    @Select("select database()")
    String database(); // 当前数据库

    @Select("select version()")
    String version(); // 数据库版本

    @Select("select count(*) from information_schema.TABLES where TABLE_SCHEMA=(select database())")
    int tableCount(); // 表个数

    @Select("select sum(DATA_LENGTH) from information_schema.TABLES where TABLE_SCHEMA=(select database())")
    long dataLength(); // 表总大小

    @Select("select sum(INDEX_LENGTH) from information_schema.TABLES where TABLE_SCHEMA=(select database())")
    long indexLength(); // 索引总大小

}
