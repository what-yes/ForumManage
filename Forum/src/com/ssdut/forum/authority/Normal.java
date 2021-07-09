package com.ssdut.forum.authority;


import com.ssdut.forum.entity.Board;
import com.ssdut.forum.entity.Post;
import com.ssdut.forum.entity.User;

import java.util.List;

/**
 * 普通用户权限
 */

public interface Normal {
    /**
     * @description 删帖  只能删除自己的帖子
     * @param postId
     * @return
     */
    boolean deletePost(int postId);

    /**
     * 查询所有板块信息
     * @return List<Board>
     */
    List<Board> getBoard();

    /**
     * 显示版块下所有主帖
     * @return List<Post>
     */
    List<Post> getAllPost(int boardId,int ownerId);

    /**
     * 根据帖Id查询所有回帖
     * @param postId
     * @return List<Post>
     */
    List<Post> getAllReplyByPostId(int postId,int ownerId);


    /**
     * @desription 发帖/回帖
     * @param post
     * @return
     */
    boolean addPost(Post post);

    /**
     * 添加用户到黑名单
     * @param userId
     * @param blackUserId
     * @return
     */
    public boolean AddIntoBlackList(int userId,int blackUserId);

    /**
     * 将用户移除黑名单
     * @param userId
     * @param userId
     * @param blackUserId
     * @return
     */
    public boolean MoveOutBlackList(int userId,int blackUserId);

    /**
     * 获得黑名单
     * @param userId
     * @return
     */
    public List<User> getBlackList(int userId);
}
