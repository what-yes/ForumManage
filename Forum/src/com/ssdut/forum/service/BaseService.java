package com.ssdut.forum.service;

import com.ssdut.forum.dao.BoardDao;
import com.ssdut.forum.dao.impl.BoardDaoImpl;
import com.ssdut.forum.dao.impl.UserDaoImpl;
import com.ssdut.forum.entity.Post;
import com.ssdut.forum.entity.User;
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
     * @param userId
     * @param password
     * @return
     */
    User checkLogin(int userId, String password);

    /**
     * @description 注册
     * @param user
     * @return
     */
    boolean register(User user);

    /**
     * @desription 发帖
     * @param post
     * @return
     */
    boolean post(Post post);

    /**
     * @description 删帖
     * @param postId
     * @return
     */
    boolean deletePost(int postId);

    /**
     * 回帖
     * @param post
     * @return
     */
    boolean replyPost(Post post);

}
