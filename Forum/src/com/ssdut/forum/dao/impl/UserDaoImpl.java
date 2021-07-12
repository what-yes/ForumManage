package com.ssdut.forum.dao.impl;

import com.ssdut.forum.dao.UserDao;
import com.ssdut.forum.entity.User;
import com.ssdut.forum.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                user.setAuthority(rs.getInt("authority"));
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
        if(queryByName(user.getUserName()))
            return 0;
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        int affectedrow = 0;
        try {
            conn = JdbcUtil.getConnection();
            st = conn.prepareStatement("insert into user values(null,?,?,0,1)");
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
    public boolean disableUser(int userId) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        int affectedRow = 0;
        try{
            if(getUserById(userId) != null) {
                conn = JdbcUtil.getConnection();
                st = conn.prepareStatement("update user set state=1 where userId=?");
                st.setInt(1, userId);
                affectedRow = st.executeUpdate();
                if (affectedRow == 0)
                    System.out.println("该用户已是被禁状态，操作无效");
                else
                    System.out.println("Id为" + userId + "的用户已被禁止登录");
            }else{
                System.out.println("该用户不存在！");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closeAll(rs, st, conn);
        }
        return (affectedRow == 1);
    }

    @Override
    public boolean enableUser(int userId) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        int affectedRow = 0;
        try{
            if(getUserById(userId) != null) {
                conn = JdbcUtil.getConnection();
                st = conn.prepareStatement("update user set state=0 where userId=?");
                st.setInt(1, userId);
                affectedRow = st.executeUpdate();
                //这种输出信息似乎都应该在工厂模式中
                if (affectedRow == 0)
                    System.out.println("该用户已是正常状态，操作无效");
                else
                    System.out.println("Id为" + userId + "的用户已被允许登录");
            }else{
                System.out.println("该用户不存在");
            }

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
            //我在想上面这句话会不会改变rs的指向而导致下面的语句出错
            while(rs.next()){
                user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setUserName(rs.getString("userName"));
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
    public boolean addBlackList(int userId, int blackUserId) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        int affectedRow = 0;
        try{
            conn = JdbcUtil.getConnection();
            User user = null;
            if(getUserById(blackUserId) != null){
                st = conn.prepareStatement("insert into blacklist values(?,?)");
                st.setInt(1,userId);
                st.setInt(2,blackUserId);
                affectedRow = st.executeUpdate();
                if(affectedRow == 0){
                    System.out.println("添加失败。原因：该用户已在您的黑名单中");
                }
                else{
                    System.out.println("用户"+getUserById(blackUserId).getUserName()+"(ID:"+blackUserId+")" +"已被添加至您的黑名单");
                }
            }
            else{
                System.out.println("您想拉黑的用户不存在");
                return false;
            }



        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closeAll(rs, st, conn);
        }
        return (affectedRow == 1);
    }

    @Override
    public boolean removeBlackList(int userId, int blackUserId) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        int affectedRow = 0;
        try{
            if(getUserById(blackUserId) != null){
                conn = JdbcUtil.getConnection();
                st = conn.prepareStatement("delete from blacklist where userId=? and blackUserId=?");
                st.setInt(1,userId);
                st.setInt(2,blackUserId);
                affectedRow = st.executeUpdate();
                if(affectedRow == 0){
                    System.out.println("移除失败。原因：该用户不在您的黑名单中");
                }
                else{
                    System.out.println("用户"+getUserById(blackUserId). getUserName()+"(ID"+blackUserId+")" +"已从您的黑名单中移除");
                }
            }else{
                System.out.println("您想取消拉黑的用户不存在");
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closeAll(rs, st, conn);
        }
        return (affectedRow == 1);
    }

    @Override
    public List<User> listBlackList(int userId) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        User user = null;
        List<User> list = new ArrayList<>();
        try {
            conn = JdbcUtil.getConnection();
            st = conn.prepareStatement(
                    "select us.userId, us.userName from blacklist bl join user us where bl.blackUserId = us.userId and bl.userId=?");
            st.setInt(1,userId);
            rs = st.executeQuery();
            while(rs.next()){
                user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setUserName(rs.getString("userName"));

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
    public boolean queryByName(String userName) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        List<User> list = new ArrayList<>();
        try {
            conn = JdbcUtil.getConnection();
            st = conn.prepareStatement("select * from user where userName=?");
            st.setString(1,userName);
            rs = st.executeQuery();

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closeAll(rs,st,conn);
        }
        return !(rs==null);
    }

    @Override
    public void showBoardMgrList() {
        Map<Integer, String> state = new HashMap<>();
        state.put(0,"正常");
        state.put(1,"禁用");
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        User user = null;
        List<User> list = new ArrayList<>();
        try {
            conn = JdbcUtil.getConnection();
            st = conn.prepareStatement("select us.userId,us.userName,us.state,bd.boardId,bd.boardName from user us join board bd where us.userId = bd.boardMgrId");
            rs = st.executeQuery();
            //我在想上面这句话会不会改变rs的指向而导致下面的语句出错
            System.out.println("用户ID\t用户名\t用户状态\t板块ID\t板块名");
            while(rs.next()){
                System.out.printf("%-4d%-4s%-4d%-4d%-4s",rs.getInt("userId"),rs.getString("userName"),state.get(rs.getInt("state")),rs.getInt("boardId"),rs.getString("boardName"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closeAll(rs,st,conn);
        }

    }



    public User getUserById(int userId){
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        User user = null;
        List<User> list = new ArrayList<>();
        try {
            conn = JdbcUtil.getConnection();
            st = conn.prepareStatement("select * from user where userId=?");
            st.setInt(1,userId);
            rs = st.executeQuery();
            if(rs.next()){
                user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setUserName(rs.getString("userName"));
                user.setPassWord(rs.getString("password"));
                user.setState(rs.getInt("state"));
            }


        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closeAll(rs,st,conn);
        }
        return user;
    }
}
