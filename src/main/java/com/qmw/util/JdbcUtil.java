package com.qmw.util;

import com.qmw.exception.CustomException;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JdbcUtil {

    public static List<Map<String, Object>> query(String url, String username, String password, String sql) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
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
            e.printStackTrace();
            throw new CustomException(e.getMessage());
        }
    }

    public static void test(String url, String username, String password) {
        query(url, username, password, "select 1");
    }

}
