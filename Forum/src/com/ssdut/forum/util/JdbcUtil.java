package com.ssdut.forum.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class JdbcUtil {
    private static String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    private static String URL="jdbc:mysql:///bbs";
    private static String USER = "root";//自己数据库的账号密码
    private static String PASSWORD = "123456";//自己数据库的密码
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
    static {
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection()  {
	/*	Connection conn = threadLocal.get();
		if(conn == null) {
			try {
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			threadLocal.set(conn);
		}

		return conn;*/

        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static void closeAll(ResultSet rs, Statement st, Connection conn) {
        try {
            if (rs != null)
                rs.close();
            if (st != null)
                st.close();
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void closeStatement(Statement st) {
        try {
            if (st != null)
                st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void closeConnection() {
        Connection conn = getConnection();
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            threadLocal.remove();
        }
    }

    public static void main(String[] args) {
        System.out.println(getConnection());
        closeConnection();
        System.out.println(getConnection());
        System.out.println(getConnection());
        System.out.println(getConnection());
    }
}
