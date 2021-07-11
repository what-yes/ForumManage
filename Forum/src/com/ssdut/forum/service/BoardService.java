package com.ssdut.forum.service;

import com.ssdut.forum.entity.Board;

import java.util.List;

/**
 * ClassName: boardService
 * Description: 板块业务
 * Date: 2021/7/8 17:26
 *
 * @author Crescent
 * @version 1.0
 * @since JDK 1.8
 */
public interface BoardService {
    /**
     * 查询所有板块信息
     * @return List<Board>
     */
    List<Board> getBoard();

    /**
     * 添加新的板块 注：用工厂模式实现添加
     * @return 成功返回true
     */
    boolean addBoard(Board board );

    /**
     * 删除板块
     * @return 成功返回true
     */
    boolean deleteBoard(int boardId);

    /**
     * 设置板块管理员
     * @param userId
     * @param boardId
     * @return
     */
    boolean setBoardMgr(int userId, int boardId);

    boolean cancelBoardMgr(int userId, int boardId);
}
