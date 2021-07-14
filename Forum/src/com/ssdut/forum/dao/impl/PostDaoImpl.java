package com.ssdut.forum.dao.impl;
import com.ssdut.forum.dao.PostDao;
import com.ssdut.forum.entity.Post;
import com.ssdut.forum.util.JdbcUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDaoImpl implements PostDao{
    private Connection conn = null; // 保存数据库连接

    private PreparedStatement st = null; // 用于执行SQL语句

    private ResultSet rs = null; // 用户保存查询结果集

    @Override
    public int savePost(Post post) {
        int affectedRow = 0;
        try {
            conn = JdbcUtil.getConnection();
            st = conn.prepareStatement("insert into post(title, content, userId, boardId, replyTo, belongTo, stick) values(?,?,?,?,?,?,?)");

            st.setString(1, post.getTitle());
            st.setString(2, post.getContent());
            st.setInt(3, post.getUserId());
            st.setInt(4, post.getBoardId());
            if(post.getReplyTo() == 0){
                st.setNull(5, Types.INTEGER);
            }else{
                st.setInt(5, post.getReplyTo());
            }
            if(post.getBelongTo() == 0){
                st.setNull(6, Types.INTEGER);
            }else{
                st.setInt(6, post.getBelongTo());
            }
            st.setInt(7, 0);
            affectedRow = st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeAll(rs, st, conn);
        }
        return affectedRow;
    }

    /**
     * 普通用户删帖
     * @param userId
     * @param postId 帖子编号
     * @return
     */
    @Override
    public int deletePost(int userId,int postId) {

        Post post = null;
        List<Post> list = new ArrayList<>();
        int affectedRow = 0;
        try {
            conn = JdbcUtil.getConnection();
            st = conn.prepareStatement("delete from post where userId=? and belongTo=?");
            st.setInt(1, userId);
            st.setInt(2, postId);
            affectedRow += st.executeUpdate();
            st = conn.prepareStatement("delete from post where userId=? and postId=?");
            st.setInt(1, userId);
            st.setInt(2, postId);
            affectedRow += st.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            JdbcUtil.closeAll(rs, st, conn);
        }
        return affectedRow;
    }

    /**
     * 管理员删帖
     * @param postId
     * @return
     */
    @Override
    public int deletePost(int postId) {

        Post post = null;
        List<Post> list = new ArrayList<>();
        int affectedRow = 0;
        try {
            conn = JdbcUtil.getConnection();
            st = conn.prepareStatement("delete from post where belongTo=?");
            st.setInt(1, postId);
            affectedRow += st.executeUpdate();
            st = conn.prepareStatement("delete from post where postId=?");
            st.setInt(1, postId);
            affectedRow += st.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            JdbcUtil.closeAll(rs, st, conn);
        }
        return affectedRow;
    }

    @Override
    public List<Post> queryPostByBoardID(int boardId,int ownerId) {
        List<Post> list = new ArrayList<>();
        Post post;
        try{
            conn = JdbcUtil.getConnection();
            st = conn.prepareStatement("SELECT post.*,userName " +
                    "FROM post JOIN `user` ON post.userId=`user`.userId " +
                    "WHERE boardId=? AND belongTo is NULL AND post.userId NOT IN( " +
                    "SELECT blackUserId " +
                    "FROM blacklist " +
                    "WHERE userId=?) " +
                    "ORDER BY stick DESC");
            st.setInt(1, boardId);
            st.setInt(2, ownerId);
            rs = st.executeQuery();
            while(rs.next()) {
                post = new Post();
                post.setPostId(rs.getInt("postId"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                post.setUserId(rs.getInt("userId"));
                post.setUserName(rs.getString("userName"));
                post.setBoardId(rs.getInt("boardId"));
                post.setReplyTo(rs.getInt("replyTo"));
                post.setBelongTo(rs.getInt("belongTo"));
                post.setStick(rs.getInt("stick"));
                list.add(post);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            JdbcUtil.closeAll(rs, st, conn);
        }
        return list;
    }

    @Override
    public List<Post> queryReplyByPostID(int postId,int ownerId) {
        List<Post> list = new ArrayList<>();
        Post post;
        try{
            conn = JdbcUtil.getConnection();
//            st = conn.prepareStatement("select * from post where belongTo is null and postId = ?");
//            st.setInt(1, postId);
//            rs = st.executeQuery();
//            post = new Post();
//            post.setPostId(rs.getInt("postId"));
//            post.setTitle(rs.getString("title"));
//            post.setContent(rs.getString("content"));
//            post.setUserId(rs.getInt("userId"));
//            post.setBoardId(rs.getInt("boardId"));
//            post.setReplyTo(rs.getInt("replyTo"));
//            post.setBelongTo(rs.getInt("belongTo"));
//            post.setStick(rs.getInt("stick"));
//            list.add(post);

            st = conn.prepareStatement("SELECT * " +
                    "FROM post JOIN `user` ON post.userId=`user`.userId " +
                    "WHERE (postId = ? and belongTo is null) or (belongTo=? AND post.userId NOT IN( " +
                    "SELECT blackUserId " +
                    "FROM blacklist " +
                    "WHERE userId=?)) " +
                    "order by belongTo");
            st.setInt(1, postId);
            st.setInt(2, postId);
            st.setInt(3,ownerId);
            rs = st.executeQuery();

            while(rs.next()) {
                post = new Post();
                post.setPostId(rs.getInt("postId"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                post.setUserId(rs.getInt("userId"));
                post.setUserName(rs.getString("userName"));
                post.setBoardId(rs.getInt("boardId"));
                post.setReplyTo(rs.getInt("replyTo"));
                post.setBelongTo(rs.getInt("belongTo"));
                post.setStick(rs.getInt("stick"));
                list.add(post);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            JdbcUtil.closeAll(rs, st, conn);
        }
        return list;
    }

    @Override
    public boolean ChangeField(int postId, String field, Object newValue) {
        boolean isChanged = false;
        try{
            conn = JdbcUtil.getConnection();
            st = conn.prepareStatement("update post " +
                    "set " + field +" =? " +
                    "where postId = ?");
            //st.setString(1, field);
            st.setObject(1, newValue);
            st.setInt(2, postId);
            isChanged = st.executeUpdate()>0;

        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            JdbcUtil.closeAll(rs, st, conn);
        }
        return isChanged;
    }

    @Override
    public List<Post> queryUserPost(int userId) {
        List<Post> list = new ArrayList<>();
        Post post;
        try{
            conn = JdbcUtil.getConnection();
            st = conn.prepareStatement("select post.*,user.userName from post join user on post.userId=user.userId where post.userId=?");
            st.setInt(1, userId);
            rs = st.executeQuery();
            while(rs.next()){
                post = new Post();
                post.setPostId(rs.getInt("postId"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                post.setUserId(rs.getInt("userId"));
                post.setUserName(rs.getString("userName"));
                post.setBoardId(rs.getInt("boardId"));
                post.setReplyTo(rs.getInt("replyTo"));
                post.setBelongTo(rs.getInt("belongTo"));
                post.setStick(rs.getInt("stick"));
                list.add(post);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closeAll(rs, st, conn);
        }
        return list;
    }
}