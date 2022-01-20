package com.qmw.util;

import com.qmw.exception.CustomException;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class JdbcUtil {

    public static List<Map<String, Object>> query(String url, String username, String password, String sql, Database database) {
        try {
            Class.forName(database.getDriverClassName());
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            List<Map<String, Object>> list = new ArrayList<>();
            while (result.next()) {
                Map<String, Object> map = new LinkedHashMap<>();
                ResultSetMetaData data = result.getMetaData();
                for (int i = 1; i <= data.getColumnCount(); i++)
                    map.put(data.getColumnName(i), result.getObject(data.getColumnName(i)));
                list.add(map);
            }
            result.close();
            statement.close();
            connection.close();
            return list;
        } catch (Exception e) {
            log.error("com.qmw.util.JdbcUtil 异常：", e);
            throw new CustomException(e.getMessage());
        }
    }

    public static void test(String url, String username, String password, Database database) {
        query(url, username, password, "select 1", database);
    }

    public enum Database {

        MySQL("com.mysql.cj.jdbc.Driver"),
        SQLServer("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        private final String driverClassName;

        Database(String driverClassName) {
            this.driverClassName = driverClassName;
        }

        public String getDriverClassName() {
            return driverClassName;
        }

    }

}
