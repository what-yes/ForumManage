package com.ssdut.forum.service.impl;

import com.ssdut.forum.dao.impl.UserDaoImpl;
import com.ssdut.forum.entity.Post;
import com.ssdut.forum.entity.User;
import com.ssdut.forum.service.BaseService;

public class BaseServiceImpl implements BaseService {

    UserDaoImpl udi = new UserDaoImpl();
    @Override
    public User checkLogin(String userName, String password) {
        User user = udi.queryByNameAndPass(userName, password);
        return user;
    }

    @Override
    public boolean register(User user) {
        return udi.saveUser(user) > 0;
    }

    @Override
    public boolean post(Post post) {
        return false;
    }

    @Override
    public boolean deletePost(int postId) {
        return false;
    }

    @Override
    public boolean replyPost(Post post) {
        return false;
    }
}
