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
        return pd.queryReplyByPostID(boardId,ownerId);
    }

    @Override
    public boolean deletePost(int postId) {
        return pd.deletePost(postId) > 0;
    }

    @Override
    public List<Post> getAllReplyByPostId(int postId,int ownerId) {
        return pd.queryReplyByPostID(postId,ownerId);
    }
}
