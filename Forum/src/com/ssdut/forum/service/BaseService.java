package com.ssdut.forum.service;

import com.ssdut.forum.dao.BoardDao;
import com.ssdut.forum.dao.impl.BoardDaoImpl;
import com.ssdut.forum.dao.impl.UserDaoImpl;
import com.ssdut.forum.entity.Post;
import com.ssdut.forum.entity.User;

import java.util.List;

/**
 * ClassName: PostService
 * Description: 用户基础操作
 * Date: 2021/7/8 18:22
 *
 * @author Crescent
 * @version 1.0
 * @since JDK 1.8
 */
public interface BaseService {

    /**
     * @desciption 根据判断账号密码是否正确决定是否能登录
     * @param userName
     * @param password
     * @return
     */
    User checkLogin(String userName, String password);

    /**
     * @description 注册
     * @param user
     * @return
     */
    boolean register(User user);

    /**
     * @desription 发帖/回帖
     * @param post
     * @return
     */
    boolean addPost(Post post);

    /**
     * @description 删帖
     * @param postId
     * @return
     */
    boolean deletePost(int postId);

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
