package com.ssdut.forum.authority.impl;

import com.ssdut.forum.entity.Board;
import com.ssdut.forum.authority.Admin;
import com.ssdut.forum.entity.User;

public class DefaultAdmin implements Admin {
    @Override
    public boolean addBoardMgr(User user) {
        return false;
    }

    @Override
    public boolean deleteBoardMgr(User user) {
        return false;
    }

    @Override
    public boolean addBoard(Board board) {
        return false;
    }

    @Override
    public boolean deleteBoard(Board board) {
        return false;
    }
}
