package com.ssdut.forum.authority.impl;

import com.ssdut.forum.authority.Normal;
import com.ssdut.forum.entity.Post;
import com.ssdut.forum.entity.User;

public class DefaultUser implements Normal {
    @Override
    public void showPost() {

    }

    @Override
    public void showReply() {

    }

    @Override
    public Post createPost() {
        return null;
    }

    @Override
    public boolean deletePost(Post post) {
        return false;
    }

    @Override
    public int AddIntoBlackList(User user) {
        return 0;
    }

    @Override
    public int MoveOutBlackList(User user) {
        return 0;
    }

    @Override
    public void showBlackList() {

    }
}
