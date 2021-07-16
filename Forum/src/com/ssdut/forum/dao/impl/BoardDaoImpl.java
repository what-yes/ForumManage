package com.ssdut.forum.dao.impl;

import com.ssdut.forum.dao.BoardDao;
import com.ssdut.forum.entity.Board;
import com.ssdut.forum.entity.User;
import com.ssdut.forum.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BoardDaoImpl implements BoardDao {
    Connection conn = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    @Override
    public int saveBoard(String boardName) {

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
        int affectedRow = 0;
        try{
            conn = JdbcUtil.getConnection();
            //删除版块所包含的所有帖子
            st=conn.prepareStatement("delete from post where boardId=? ");
            st.setInt(1, boardId);
            affectedRow = st.executeUpdate();
            //删除版块
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
                board.setBoardMgrId(rs.getInt("boardMgrId"));
                list.add(board);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closeAll(rs, st, conn);
        }
        return list;
    }

    @Override
    public boolean setBoardMgr(int userId, int boardId) {
        boolean isChanged = false;
        try{
            conn = JdbcUtil.getConnection();
            st = conn.prepareStatement("update Board set boardMgrId = ? where boardId = ?");
            st.setInt(1, userId);
            st.setInt(2, boardId);

            isChanged = st.executeUpdate()>0;
            //如果本来是普通用户，则设置为管理员
            st = conn.prepareStatement("select * from user where userId=?");
            st.setInt(1,userId);
            rs = st.executeQuery();
            int authority=1;
            if(rs.next()){
                authority=rs.getInt("authority");
            }
            if(isChanged && authority==1){
                //设置版主权限值
                st = conn.prepareStatement("update user set authority = 2 where userId = ?");
                st.setInt(1, userId);
                isChanged = st.executeUpdate()>0;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closeAll(rs, st, conn);
        }
        return isChanged;
    }

    @Override
    public boolean deleteBoardMgr(Board board) {
        boolean isChanged = false;
        int boardAgedMgrId=board.getBoardMgrId(); //原来的管理者Id

        try{
            conn = JdbcUtil.getConnection();
            //改变版块的管理者为null
            st = conn.prepareStatement("update Board set boardMgrId = null where boardId = ?");
            st.setInt(1, board.getBoardId());
            isChanged = st.executeUpdate()>0;

            //判断原来的管理者是否变成普通用户
            st=conn.prepareStatement("select * from board where boardMgrId=?");
            st.setInt(1,boardAgedMgrId);
            //如果变为普通用户  改变其权限
            rs=st.executeQuery();
            if(!rs.next()){
                st = conn.prepareStatement("update user set authority = 1 where userId = ?");
                st.setInt(1, boardAgedMgrId);
                isChanged=st.executeUpdate()>0;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closeAll(rs, st, conn);
        }
        return isChanged;
    }

    @Override
    public boolean isBoardMgr(int userId, int boarId) {
        boolean isBoardMgr=false;

        try{
            conn = JdbcUtil.getConnection();
            st=conn.prepareStatement("SELECT * FROM board WHERE boardId=? AND boardMgrId=?");
            st.setInt(1,boarId);
            st.setInt(2,userId);
            rs = st.executeQuery();
            if(rs.next()){
                isBoardMgr=true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closeAll(rs,st,conn);
        }

        return isBoardMgr;
    }
}
