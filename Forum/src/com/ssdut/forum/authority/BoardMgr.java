package com.ssdut.forum.authority;

import com.ssdut.forum.entity.Post;
import com.ssdut.forum.entity.User;

/**
 * 板块管理员权限
 */
public interface BoardMgr {
    /**
     * 置顶帖子
     * @param postId
     * @return
     */
    public boolean StickPost(int postId);

    /**
     * 取消置顶
     * @param postId
     * @return
     */
    public boolean CancelStick(int postId);

    /**
     * 删除帖子  可删除所有帖子
     * @param postId
     * @return
     */
    public boolean DeletePost(int postId);

    /**
     * 禁用用户
     * @param userId
     * @return
     */
    public boolean DisableUser(int userId);

    /**
     * 取消禁用
     * @param userId
     * @return
     */
    public boolean CancelDisable(int userId);

    public void showDisableUserList();

}
