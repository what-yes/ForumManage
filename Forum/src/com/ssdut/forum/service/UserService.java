package com.ssdut.forum.service;

import com.ssdut.forum.entity.User;

import java.util.List;

/**
 * ClassName: UserService
 * Description: 管理用户类
 * Date: 2021/7/8 18:24
 *
 * @author Crescent
 * @version 1.0
 * @since JDK 1.8
 */
public interface UserService {
    /**
     * 查询所有用户
     * @return
     */
    List<User> getAllUsers();

    /**
     * 查询状态为state的所有用户
     * @param state
     * @return
     */
    List<User> getAllUsersByState(int state);

    /**
     * 改变用户状态 (state为0正常  为1表示禁用)
     * @param userId
     * @param state
     * @return
     */
    boolean setUserState(int userId,int state);
}
