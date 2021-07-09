package com.ssdut.forum.authority;

import com.ssdut.forum.entity.Board;
import com.ssdut.forum.user.User;
/**
 * 总管理员权限
 */
public interface Admin {
    /**
     * 添加管理员权限
     * @param user
     * @return
     */
    public boolean addBoardMgr(User user);

    /**
     * 取消管理员权限
     * @param user
     * @return
     */
    public boolean deleteBoardMgr(User user);

    /**
     * 添加版块
     * @param board
     * @return
     */
    public boolean addBoard(Board board);

    /**
     * 删除板块
     * @param board
     * @return
     */
    public boolean deleteBoard(Board board);
}
