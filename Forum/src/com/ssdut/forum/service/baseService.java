package com.ssdut.forum.service;

import com.ssdut.forum.dao.BoardDao;
import com.ssdut.forum.dao.impl.BoardDaoImpl;
import com.ssdut.forum.dao.impl.UserDaoImpl;
import com.ssdut.forum.entity.Post;
import com.ssdut.forum.entity.User;

public interface baseService {
    UserDaoImpl udi = new UserDaoImpl();
    BoardDaoImpl bdi = new BoardDaoImpl();

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
     * @desription
     * @param psot
     * @return
     */
    boolean post(Post psot);
}
