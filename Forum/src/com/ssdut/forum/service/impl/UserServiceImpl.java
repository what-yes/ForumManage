package com.ssdut.forum.service.impl;

import com.ssdut.forum.dao.BoardDao;
import com.ssdut.forum.dao.UserDao;
import com.ssdut.forum.dao.impl.BoardDaoImpl;
import com.ssdut.forum.dao.impl.UserDaoImpl;
import com.ssdut.forum.entity.User;
import com.ssdut.forum.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    UserDao udi = new UserDaoImpl();
    BoardDao bdi = new BoardDaoImpl();
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

    @Override
    public boolean setBoardMgr(int userId, int boardId) {
        return bdi.setBoardMgr(userId, boardId);
    }

    @Override
    public boolean deleteBoardMgr(int boardId) {
        return bdi.deleteBoardMgr(boardId);
    }
}
