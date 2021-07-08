package com.ssdut.forum.service.impl;

import com.ssdut.forum.dao.impl.UserDaoImpl;
import com.ssdut.forum.entity.User;
import com.ssdut.forum.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    UserDaoImpl udi = new UserDaoImpl();

    @Override
    public List<User> getAllUsers() {
        List<User> list = udi.listAllUser();
        return list;
    }

    @Override
    public List<User> getAllUsersByState(int state) {
        List<User> list = udi.listByState(state);
        return list;
    }

    @Override
    public boolean setUserState(int userId, int state) {
        if(state == 1)
            return udi.disableUser(userId);
        else
            return udi.enableUser(userId);
    }
}
