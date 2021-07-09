package com.ssdut.forum.authority.impl;

import com.ssdut.forum.entity.Board;
import com.ssdut.forum.authority.Admin;
import com.ssdut.forum.entity.User;
import com.ssdut.forum.service.BoardService;
import com.ssdut.forum.service.UserService;
import com.ssdut.forum.service.impl.BoardServiceImpl;
import com.ssdut.forum.service.impl.UserServiceImpl;
public class DefaultAdmin implements Admin {
    UserService userService = new UserServiceImpl();
    BoardService boardService = new BoardServiceImpl();
    @Override
    public boolean setBoardMgr(int userId, int boardId) {
        return userService.setBoardMgr(userId, boardId);
    }

    @Override
    public boolean deleteBoardMgr(int boardId) {
        return userService.deleteBoardMgr(boardId);
    }

    @Override
    public boolean addBoard(Board board) {
        return boardService.addBoard(board);
    }

    @Override
    public boolean deleteBoard(int boardId) {
        return boardService.deleteBoard(boardId);
    }
}
