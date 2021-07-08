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
            st = conn.prepareStatement("insert into table post(postId, title, content, userId, boardId, replyTo, belongTo) values(?,?,?,?,?,?,?)");
            st.setInt(1, post.getPostId());
            st.setString(2, post.getTitle());
            st.setString(3, post.getContent());
            st.setInt(4, post.getUserId());
            st.setInt(5, post.getBoardId());
            st.setInt(6, post.getReplyTo());
            st.setInt(7, post.getBelongTo());
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
            st = conn.prepareStatement("select * from post where tid = ?");
            st.setInt(1, postId);
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
    public List<Post> queryPostByBoardID(int boardId) {
        List<Post> list = new ArrayList<>();
        Post post;
        try{
            conn = JdbcUtil.getConnection();
            // TODO 黑名单的帖子不显示
            st = conn.prepareStatement("select * from post where belongTo is null and boardId =?");
            st.setInt(1, boardId);
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
    public List<Post> queryReplyByPostID(int postId) {
        List<Post> list = new ArrayList<>();
        Post post;
        try{
            conn = JdbcUtil.getConnection();
            st = conn.prepareStatement("select * from post where belongTo =?");
            st.setInt(1, postId);
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
    public boolean isExistPost(int postId) {
        boolean flag=false;
        try {
            conn = JdbcUtil.getConnection();
            st = conn.prepareStatement("select * from post where tid=?");
            st.setInt(1, postId);  //TODO
            rs=st.executeQuery();
            while(rs.next()) {
                flag=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeAll(rs, st, conn);
        }
        return flag;
    }
}