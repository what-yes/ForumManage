package com.ssdut.forum.service.impl;

import com.ssdut.forum.entity.Post;
import com.ssdut.forum.service.PostService;

import java.util.List;

public class PostServiceImpl implements PostService {
    @Override
    public List<Post> getAllPost(int boardId) {
        return null;
    }

    @Override
    public boolean deletePost(int postId) {
        return false;
    }

    @Override
    public List<Post> getAllReplyByPostId(int postId) {
        return null;
    }

    @Override
    public boolean deleteAllReplyByPostId(int postId) {
        return false;
    }

    @Override
    public int countByBoardId(int boardId) {
        return 0;
    }
}
