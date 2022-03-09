package com.qmw.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

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

    @Update("alter table ${param1} AUTO_INCREMENT = 1")
    void resetAutoIncrement(String tableName); // 重置自增主键

    @Select("show full columns from ${param1}")
    List<Map<String, Object>> showFullColumns(String tableName);

    @Select("desc ${param1}")
    List<Map<String, Object>> desc(String tableName);

}
