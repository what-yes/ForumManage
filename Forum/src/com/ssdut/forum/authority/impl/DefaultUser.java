package com.ssdut.forum.authority.impl;

import com.ssdut.forum.authority.Normal;
import com.ssdut.forum.entity.Board;
import com.ssdut.forum.entity.Post;
import com.ssdut.forum.entity.User;

import java.util.List;

public class DefaultUser implements Normal {
    @Override
    public boolean addPost(Post post) {
        return false;
    }

    @Override
    public boolean deletePost(int postId) {
        return false;
    }

    @Override
    public List<Board> getBoard() {
        return null;
    }

    @Override
    public List<Post> getAllPost(int boardId, int ownerId) {
        return null;
    }

    @Override
    public List<Post> getAllReplyByPostId(int postId, int ownerId) {
        return null;
    }


    @Override
    public boolean AddIntoBlackList(int userId, int blackUserId) {
        return false;
    }

    @Override
    public boolean MoveOutBlackList(int userId, int blackUserId) {
        return false;
    }

    @Override
    public List<User> getBlackList(int userId) {
        return null;
    }
}
