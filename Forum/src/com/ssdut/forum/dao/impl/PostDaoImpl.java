package com.ssdut.forum.dao.impl;
import com.ssdut.forum.dao.PostDao;
import com.ssdut.forum.entity.Post;
import com.ssdut.forum.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            st = conn.prepareStatement("insert into table post(title, content, userId, boardId, replyTo, belongTo) values(?,?,?,?,?,?)");

            st.setString(1, post.getTitle());
            st.setString(2, post.getContent());
            st.setInt(3, post.getUserId());
            st.setInt(4, post.getBoardId());
            st.setInt(5, post.getReplyTo());
            st.setInt(6, post.getBelongTo());
            affectedRow = st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeAll(rs, st, conn);
        }
        return affectedRow;
    }

    @Override
    public int deletePost(int postId) {

        Post post = null;
        List<Post> list = new ArrayList<>();
        int affectedRow = 0;
        try {
            conn = JdbcUtil.getConnection();
            conn.setAutoCommit(false);
            rs = st.executeQuery();
            st = conn.prepareStatement("delete from post where belongTo=?");
            st.setInt(1, postId);
            affectedRow += st.executeUpdate();
            st = conn.prepareStatement("delete from post where postId=?");
            st.setInt(1, postId);
            affectedRow += st.executeUpdate();
            conn.commit();

        } catch (SQLException e){
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1){
                e1.printStackTrace();
            }
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
                    "FROM post " +
                    "WHERE (postId = ? and belongTo is null) or belongTo=? AND userId NOT IN( " +
                    "SELECT blackUserId " +
                    "FROM blacklist " +
                    "WHERE userId=?) " +
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
                    "set ?=? " +
                    "where postId = ?");
            st.setObject(1, field);
            st.setObject(2, newValue);
            st.setInt(3, postId);
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
            st = conn.prepareStatement("select * from post where userId=?");
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