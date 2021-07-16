package com.ssdut.forum.dao;

import com.ssdut.forum.entity.Post;

import java.util.List;

public interface PostDao {
    /**
     * 添加主帖/跟帖
     * @param post
     * @return 返回受影响行数
     */
    int savePost(Post post);

    /**
     * 普通用户根据用户Id和帖Id删除帖
     * @param userId
     * @param postId
     * @return
     */
    int deletePost(int userId,int postId);
    /**
     * 管理员根据帖Id删除帖
     * @param postId
     * @return
     */
    int deletePost(int postId);

    /**
     * 根据板块号显示主帖
     * @param boardId
     * @return
     */
    List<Post> queryPostByBoardID(int boardId,int ownerId);

    /**
     * 根据主帖号显示跟帖 (注：不会显示主帖，只显示他的跟帖)
     * @param postId
     * @return
     */
    List<Post>  queryReplyByPostID(int postId,int ownerId);

    /**
     * 更改各种字段内容，用于修改内容或置顶等
     * @param postId
     * @param field
     * @param newValue
     * @return
     */
    boolean ChangeField(int postId, String field, Object newValue);

    /**
     * 查看用户已发贴
     * @param userId
     * @return
     */
    List<Post> queryUserPost(int userId);
//    /**
//     * 根据回帖查看主帖是否存在
//     * @param postId
//     * @return
//     */
//    boolean isExistPost(int postId);


}