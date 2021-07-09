package com.ssdut.forum.service;

import com.ssdut.forum.entity.Post;

import java.util.List;

/**
 * ClassName: PostService
 * Description: 管理主贴类
 * Date: 2021/7/8 18:22
 *
 * @author Crescent
 * @version 1.0
 * @since JDK 1.8
 */
public interface PostService {
    /**
     * 显示版块下所有主帖
     * @return List<Post>
     */
    List<Post> getAllPost(int boardId,int ownerId);

    /**
     * 根据帖Id删除帖
     * @param postId
     * @return
     */
    boolean deletePost(int postId);

    /**
     * 根据帖Id查询所有回帖
     * @param postId
     * @return List<Post>
     */
    List<Post> getAllReplyByPostId(int postId,int ownerId);

    /**
     * 根据帖Id置顶帖子
     * @param postId
     * @return
     */
    boolean StickPost(int postId);

    /**
     * 根据帖Id取消置顶
     * @param postId
     * @return
     */
    boolean CancelStick(int postId);
}
