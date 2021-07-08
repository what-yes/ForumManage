package com.ssdut.forum.service.impl;

import com.ssdut.forum.dao.impl.BoardDaoImpl;
import com.ssdut.forum.entity.Board;
import com.ssdut.forum.service.BoardService;
import com.ssdut.forum.util.ResultSetPrintUtil;

import java.util.List;

public class BoardServiceImpl implements BoardService {

    BoardDaoImpl bdi = new BoardDaoImpl();
    @Override
    public List<Board> getBoard() {
         List<Board> list = bdi.ListBoard();
         return list;
    }

    @Override
    public boolean addBoard(Board board ) {
       return bdi.saveBoard(board) > 0;
    }

    @Override
    public boolean deleteBoard(int boardId) {
        return false;
    }
}
