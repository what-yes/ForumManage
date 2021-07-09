package com.ssdut.forum.authority;

import com.ssdut.forum.entity.Post;
import com.ssdut.forum.entity.User;

/**
 * 板块管理员权限
 */
public interface BoardMgr {
    /**
     * 置顶帖子
     * @param post
     * @return
     */
    public boolean StickPost(Post post);

    /**
     * 取消置顶
     * @param post
     * @return
     */
    public boolean CancelStick(Post post);

    /**
     * 删除帖子  可删除所有帖子
     * @param postId
     * @return
     */
    public boolean DeletePost(int postId);

    /**
     * 禁用用户
     * @param user
     * @return
     */
    public boolean DisableUser(User user);

}
