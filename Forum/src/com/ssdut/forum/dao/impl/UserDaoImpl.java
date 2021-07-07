package com.ssdut.forum.dao.impl;

import com.ssdut.forum.dao.UserDao;
import com.ssdut.forum.entity.User;
import com.ssdut.forum.util.JdbcUtil;
import com.ssdut.forum.util.ResultSetPrintUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public User queryByNameAndPass(String userName, String password) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        User user = null;
        try {
            conn = JdbcUtil.getConnection();
            st = conn.prepareStatement("select * from user where userName=? and password=? and state=0");
            st.setString(1,userName);
            st.setString(2,password);
            rs = st.executeQuery();
            while(rs.next()){
                user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setUserName(rs.getString("userName"));
                user.setPassWord(rs.getString("password"));
                user.setState(rs.getInt("state"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtil.closeAll(rs, st, conn);
        }


        return user;
    }

    @Override
    public int saveUser(User user) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        int affectedrow = 0;
        try {
            conn = JdbcUtil.getConnection();
            st = conn.prepareStatement("insert into user values(?,?,0)");
            st.setString(1,user.getUserName());
            st.setString(2,user.getPassWord());
            affectedrow = st.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtil.closeAll(rs, st, conn);
        }
        return affectedrow;
    }

    @Override
    public boolean disableUser(String userId) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        int affectedRow = 0;
        try{
            conn = JdbcUtil.getConnection();
            st = conn.prepareStatement("update user set state=1 where userId=?");
            affectedRow = st.executeUpdate();
            if(affectedRow == 1)
                System.out.println("该用户已是被禁状态，操作无效");
            else
                System.out.println("Id为" + userId +"的用户已被禁止登录");

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closeAll(rs, st, conn);
        }
        return (affectedRow == 1);
    }

    @Override
    public boolean enableUser(String userId) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        int affectedRow = 0;
        try{
            conn = JdbcUtil.getConnection();
            st = conn.prepareStatement("update user set state=1 where userId=?");
            affectedRow = st.executeUpdate();
            if(affectedRow == 0)
                System.out.println("该用户已是正常状态，操作无效");
            else
                System.out.println("Id为" + userId +"的用户已被允许登录");

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closeAll(rs, st, conn);
        }
        return (affectedRow == 1);
    }

    @Override
    public List<User> listByState(int state) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        User user = null;
        List<User> list = new ArrayList<>();
        try {
            conn = JdbcUtil.getConnection();
            st = conn.prepareStatement("select * from user where state=?");
            st.setInt(1,state);
            rs = st.executeQuery();
            ResultSetPrintUtil.printResultSet(rs);
            //我在想上面这句话会不会改变rs的指向而导致下面的语句出错
            while(rs.next()){
                user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setUserName(rs.getString("userName"));
                user.setPassWord(rs.getString("password"));
                user.setState(rs.getInt("state"));

                list.add(user);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closeAll(rs,st,conn);
        }
        return list;
    }

    @Override
    public List<User> listAllUser() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        User user = null;
        List<User> list = new ArrayList<>();
        try {
            conn = JdbcUtil.getConnection();
            st = conn.prepareStatement("select * from user");
            rs = st.executeQuery();
            ResultSetPrintUtil.printResultSet(rs);
            //我在想上面这句话会不会改变rs的指向而导致下面的语句出错
            while(rs.next()){
                user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setUserName(rs.getString("userName"));
                user.setPassWord(rs.getString("password"));
                user.setState(rs.getInt("state"));

                list.add(user);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closeAll(rs,st,conn);
        }
        return list;
    }
}
