package com.jdbc;

import java.sql.*;

/**
 * @decription: 手写JDBC
 * @author: 180449
 * @date 2021/9/17 15:43
 */
public class MyConnection {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8";
    private static final String USRENAME = "root";
    private static final String PASSWORD = "allen123";

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        try {
            // 1. 加载驱动
            Class.forName(DRIVER);
            // 2. 获得连接
            connection = DriverManager.getConnection(URL, USRENAME, PASSWORD);
            // 3. 获取处理对象
            statement = connection.createStatement();
            // 4. 执行sql语句
            ResultSet resultSet = statement.executeQuery("select user_name,pass_word from user");
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + "\t" + resultSet.getString(2));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // 5. 释放资源
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
