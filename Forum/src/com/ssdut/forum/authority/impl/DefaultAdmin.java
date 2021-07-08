package com.ssdut.forum.authority.impl;

import com.ssdut.forum.user.User;
import com.ssdut.forum.authority.Admin;

public class DefaultAdmin implements Admin {

    @Override
    public boolean AuthorizeMgr(User user) {
        return false;
    }
}
