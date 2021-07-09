package com.ssdut.forum.service.impl;

import com.ssdut.forum.dao.PostDao;
import com.ssdut.forum.dao.impl.PostDaoImpl;
import com.ssdut.forum.dao.impl.UserDaoImpl;
import com.ssdut.forum.entity.Post;
import com.ssdut.forum.entity.User;
import com.ssdut.forum.service.BaseService;

import java.util.List;

public class BaseServiceImpl implements BaseService {

    UserDaoImpl udi = new UserDaoImpl();
    PostDao pd=new PostDaoImpl();

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
        return pd.savePost(post) > 0;
    }

    @Override
    public boolean deletePost(int postId) {
        return pd.deletePost(postId) > 0;
    }

    @Override
    public boolean replyPost(Post post) {
        return pd.savePost(post) > 0;
    }

    @Override
    public boolean AddIntoBlackList(int userId, int blackUserId) {
        return udi.addBlackList(userId, blackUserId);
    }

    @Override
    public boolean MoveOutBlackList(int userId, int blackUserId) {
        return udi.removeBlackList(userId, blackUserId);
    }

    @Override
    public List<User> getBlackList(int userId) {
        return null;
    }
}
