package com.ssdut.forum.util;


import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public final class JdbcUtil {
    private static String DRIVER = null;
    private static String URL = null;
    private static String USER = null;//自己数据库的账号密码
    private static String PASSWORD = null;//自己数据库的密码
    Connection conn = null;

    //private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
    static {
        init();
    }

    /**
     * 通过配置文件实现属性初始化
     */
    public static void init() {
        Properties params = new Properties();
        String config = "database.properties";
        InputStream is = JdbcUtil.class.getClassLoader().getResourceAsStream(config);
        try {
            params.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DRIVER = params.getProperty("driver");
        URL = params.getProperty("url");
        USER = params.getProperty("user");
        PASSWORD = params.getProperty("password");
    }

    /**
     * 数据库链接
     *
     * @return
     */
    public static Connection getConnection() {
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
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return conn;
    }

    /**
     * 释放资源
     *
     * @param rs
     * @param st
     * @param conn
     */
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
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //threadLocal.remove();
        }
    }

    /**
     * 执行增删改操作
     *
     * @param preparedSql
     * @param param
     * @return
     */
    public static int executeSQL(String preparedSql, Object[] param) {
        Connection conn = null; //数据库链接
        PreparedStatement pstmt = null; //sql语句
        int num = 0; //操作的行数
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(preparedSql);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    pstmt.setObject(i + 1, param[i]);
                }
            }
            num = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(null, pstmt, conn);
        }

        return num;
    }
}

//    public static void main(String[] args) {
//        Connection conn=null;
//        System.out.println(conn=getConnection());
//        PreparedStatement pstmt=null; //sql语句
//        String preparedSql="select * from post";
//        try{
//            conn=getConnection();
//            pstmt=conn.prepareStatement(preparedSql);
//            ResultSet rs=pstmt.executeQuery();
//            while (rs.next()){
//                System.out.println(rs.getString("content"));
//            }
//        }catch (SQLException e){
//            e.printStackTrace();
//        } finally {
//            closeAll(null,pstmt,conn);
//        }
//    }
//}


