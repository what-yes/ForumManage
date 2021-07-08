package com.ssdut.forum.dao;

import com.ssdut.forum.entity.User;

import java.util.List;

public interface UserDao {

    /**
     * @param userName
     * @param password
     * @return 数据表中有该条记录则返回user对象，无则返回null
     */
    //根据用户名和密码查询
    //用于登录时判断
    User queryByNameAndPass(String userName, String password);

    /**
     * 将新注册的用户储存到数据库中
     * @param user
     * @return 返回受影响的行数
     */
    int saveUser(User user);

    /**
     * 管理员拉黑某用户，即使得该用户无法登录
     *
     * @param userId
     * @return true表示成功禁止，false表示禁止失败
     */
    boolean disableUser(String userId);

    /**
     * 管理员将将用户从黑名单中删除
     * @param userId
     * @return
     */
    boolean enableUser(String userId);

    /**
     * 根据用户状态（被禁与否）显示用户  针对管理员而言
     * @param state
     * @return
     */
    List<User> listByState(int state);

    /**
     * 显示所有用户
     * @return
     */
    List<User> listAllUser();

    /**
     * 用户添加黑名单，来决定不看哪些人的帖子.
     * @param userId
     * @return true表示添加成功 false表示添加失败
     */
    boolean addBlackList(String userId, String blackUserId);

    /**
     * 用户将某些人从黑名单中移除
     * @param userId
     * @return
     */
    boolean removeBlackList(String userId, String blackUserId);

}
