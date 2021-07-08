package com.ssdut.forum.dao;

import com.ssdut.forum.entity.Board;

import java.util.List;

public interface BoardDao {

    /**
     * @description 添加一个版块
     * @param boardName
     */
    int saveBoard(String boardName);

    /**
     * @description 添加一个版块
     * @param board
     */
    int saveBoard(Board board);

    /**
     * @description 根据板块号删除一个版块
     * @param boardId
     */
    int deleteBoard(int boardId);

    /**
     * @description 列出所有版块
     * @return
     */
    List<Board> ListBoard();
}
