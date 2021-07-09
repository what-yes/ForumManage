package com.ssdut.forum.authority.impl;

import com.ssdut.forum.authority.Normal;
import com.ssdut.forum.entity.Board;
import com.ssdut.forum.entity.Post;
import com.ssdut.forum.entity.User;
import com.ssdut.forum.service.BaseService;
import com.ssdut.forum.service.BoardService;
import com.ssdut.forum.service.PostService;
import com.ssdut.forum.service.impl.*;

import java.security.Provider;
import java.util.List;

/**
 * 默认用户
 */
public class DefaultUser implements Normal {
    BoardService boardService = new BoardServiceImpl();
    PostService postService = new PostServiceImpl();
    BaseService baseService = new BaseServiceImpl();

    /**
     * 重写发帖/回帖
     * @param post
     * @return
     */
    @Override
    public boolean addPost(Post post) {
        return baseService.addPost(post);
    }

    /**
     * 重写删帖
     * @param postId
     * @return
     */
    @Override
    public boolean deletePost(int postId) {
        return baseService.deletePost(postId);
    }

    /**
     * 重写获取所有板块
     * @return
     */
    @Override
    public List<Board> getBoard() {
        return boardService.getBoard();
    }

    /**
     * 重写获取板块内所有主帖
     * @param boardId
     * @param ownerId
     * @return
     */
    @Override
    public List<Post> getAllPost(int boardId, int ownerId) {
        return postService.getAllPost(boardId, ownerId);
    }

    /**
     * 重写获取主帖内所有回帖
     * @param postId
     * @param ownerId
     * @return
     */
    @Override
    public List<Post> getAllReplyByPostId(int postId, int ownerId) {
        return postService.getAllReplyByPostId(postId, ownerId);
    }

    /**
     * 重写添加黑名单
     * @param userId
     * @param blackUserId
     * @return
     */
    @Override
    public boolean AddIntoBlackList(int userId, int blackUserId) {
        return baseService.AddIntoBlackList(userId, blackUserId);
    }

    /**
     * 重写移出黑名单
     * @param userId
     * @param blackUserId
     * @return
     */
    @Override
    public boolean MoveOutBlackList(int userId, int blackUserId) {
        return baseService.MoveOutBlackList(userId, blackUserId);
    }

    /**
     * 重写获取黑名单
     * @param userId
     * @return
     */
    @Override
    public List<User> getBlackList(int userId) {
        return baseService.getBlackList(userId);
    }
}
