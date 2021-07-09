package com.ssdut.forum.entity;

import com.ssdut.forum.role.Role;

import java.util.LinkedList;

public class User {
    /**
     * 用户id
     */
    private int userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 账户密码
     */
    private String passWord;
    /**
     * 用户状态数据库中0表示正常 1表示disable
     */
    private int state;
    /**
     * 黑名单列表
     */
    private LinkedList<String> blacklist;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public LinkedList<String> getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(LinkedList<String> blacklist) {
        this.blacklist = blacklist;
    }

    private Role role = null;


    //TODO login
//    public boolean login(String username, String password){
//
//    }

    //TODO register
}
