package com.ssdut.forum.service;

import com.ssdut.forum.dao.BoardDao;
import com.ssdut.forum.dao.impl.BoardDaoImpl;
import com.ssdut.forum.dao.impl.UserDaoImpl;
import com.ssdut.forum.entity.Post;
import com.ssdut.forum.entity.User;

public interface baseService {

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
    boolean deletePost(String postId);

    /**
     * 回帖 暂时不清楚发帖的Dao代码
     */


}
