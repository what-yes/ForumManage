package com.ssdut.forum.authority.impl;

import com.ssdut.forum.authority.BoardMgr;
import com.ssdut.forum.entity.Post;
import com.ssdut.forum.entity.User;
import com.ssdut.forum.service.*;
import com.ssdut.forum.service.impl.*;


public class DefaultBoardMgr implements BoardMgr {
    //BoardService boardService = new BoardServiceImpl();
    PostService postService = new PostServiceImpl();
    UserService userService = new UserServiceImpl();
    @Override
    public boolean StickPost(int postId) {
        return postService.StickPost(postId);
    }

    @Override
    public boolean CancelStick(int postId) {
        return postService.CancelStick(postId);
    }

    @Override
    public boolean DeletePost(int postId) {
        return postService.deletePost(postId);
    }

    @Override
    public boolean DisableUser(int userId) {
        return userService.setUserState(userId, 1);
    }

    @Override
    public boolean CancelDisable(int userId) {
        return userService.setUserState(userId, 0);
    }

}
