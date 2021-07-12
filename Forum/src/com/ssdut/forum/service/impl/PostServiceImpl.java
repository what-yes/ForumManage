package com.ssdut.forum.service.impl;

import com.ssdut.forum.dao.PostDao;
import com.ssdut.forum.dao.impl.PostDaoImpl;
import com.ssdut.forum.entity.Post;
import com.ssdut.forum.service.PostService;

import java.util.List;

public class PostServiceImpl implements PostService {
    PostDao pd= new PostDaoImpl();

    @Override
    public List<Post> getAllPost(int boardId,int ownerId) {
        return pd.queryPostByBoardID(boardId,ownerId);
    }

    /**
     * 管理员删帖
     * @param postId
     * @return
     */
    @Override
    public boolean deletePost(int postId) {
        return pd.deletePost(postId) > 0;
    }

    /**
     * 用户删帖
     * @param userId
     * @param postId
     * @return
     */
    @Override
    public boolean deletePost(int userId,int postId) {
        return pd.deletePost(userId,postId) > 0;
    }

    @Override
    public List<Post> getAllReplyByPostId(int postId,int ownerId) {
        return pd.queryReplyByPostID(postId,ownerId);
    }

    @Override
    public boolean StickPost(int postId) {
        return pd.ChangeField(postId, "stick", 1);
    }

    @Override
    public boolean CancelStick(int postId) {
        return pd.ChangeField(postId, "stick", 0);
    }

    @Override
    public List<Post> queryUserPost(int userId) {
        return pd.queryUserPost(userId);
    }

}
