package com.ssdut.forum.dao.impl;

import com.ssdut.forum.dao.BoardDao;
import com.ssdut.forum.entity.Board;
import com.ssdut.forum.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BoardDaoImpl implements BoardDao {
    @Override
    public int saveBoard(String boardName) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        int affectedRow = 0;
        try{
            conn = JdbcUtil.getConnection();
            st = conn.prepareStatement("insert into board values(?)");
            st.setString(1, boardName);
            affectedRow = st.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closeAll(rs,st,conn);
        }
        return affectedRow;
    }

    @Override
    public int saveBoard(Board board) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        int affectedRow = 0;
        try{
            conn = JdbcUtil.getConnection();

            //如果板块已经存在
            st = conn.prepareStatement("select * from board where boardName=?");
            st.setString(1, board.getBoardName());
            rs = st.executeQuery();
            if(rs.next()){
                // System.out.println("'"+board.getBoardName()+"'"+"板块已存在");
                return affectedRow;
            }

            st = conn.prepareStatement("insert into board values(?)");
            st.setString(1, board.getBoardName());
            affectedRow = st.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closeAll(rs,st,conn);
        }
        return affectedRow;
    }

    @Override
    public int deleteBoard(int boardId) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        int affectedRow = 0;
        try{
            conn = JdbcUtil.getConnection();
            st = conn.prepareStatement("delete from board where boardId=?");
            st.setInt(1, boardId);
            affectedRow = st.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closeAll(rs,st,conn);
        }
        return affectedRow;
    }

    @Override
    public List<Board> ListBoard() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Board> list = new ArrayList<>();
        Board board = null;

        try{
            conn = JdbcUtil.getConnection();
            st = conn.prepareStatement("select * from Board");
            rs = st.executeQuery();
            while(rs.next()){
                board = new Board();
                board.setBoardId(rs.getInt("boardId"));
                board.setBoardName(rs.getString("boardName"));
                list.add(board);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closeAll(rs, st, conn);
        }
        return list;
    }
}
