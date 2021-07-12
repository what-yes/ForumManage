package com.ssdut.forum.authority;

import com.ssdut.forum.entity.Board;
import com.ssdut.forum.entity.User;

/**
 * 总管理员权限
 */
public interface Admin {
    /**
     * 添加管理员权限
     * @param userId
     * @param boardId
     * @return
     */
    public boolean setBoardMgr(int userId, int boardId);

    /**
     * 添加版块
     * @param board
     * @return
     */
    public boolean addBoard(Board board);

    /**
     * 删除板块
     * @param boardId
     * @return
     */
    public boolean deleteBoard(int boardId);

    public void showBoardMgrList();

    public User getUserById(int userId);

    /**
     * 根据boardId取消版主
     * @param board
     * @return
     */
    public boolean cancelBoardMgr(Board board);
}
