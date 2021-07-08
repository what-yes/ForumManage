package com.ssdut.forum.authority;


import com.ssdut.forum.entity.Post;
import com.ssdut.forum.entity.User;

/**
 * 普通用户权限
 */

public interface Normal {
    public void showPost(); //查看板块帖子
    public void showReply();    //查看主帖
    public Post createPost();   //发帖（跟帖、主帖）
    public boolean deletePost(Post post);   //删除帖子
    public int AddIntoBlackList(User user); //拉黑用户
    public int MoveOutBlackList(User user); //取消拉黑
    public void showBlackList();    //查看黑名单
}
