package com.ssdut.forum.authority;

import com.ssdut.forum.user.User;
/**
 * 总管理员权限
 */
public interface Admin {
    public boolean AuthorizeMgr(User user);
}
