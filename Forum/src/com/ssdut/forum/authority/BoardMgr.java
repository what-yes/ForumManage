package com.ssdut.forum.authority;

import com.ssdut.forum.entity.Post;

/**
 * 板块管理员权限
 */
public interface BoardMgr {
    public boolean StickPost(Post post);    //置顶帖子
    public boolean CancelStick(Post post);  //取消置顶
    public boolean DeletePost(Post post);   //删除帖子
}
