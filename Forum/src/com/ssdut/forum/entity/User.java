package com.ssdut.forum.entity;

import com.ssdut.forum.role.Role;
import com.ssdut.forum.service.impl.BaseServiceImpl;

import java.util.LinkedList;
import java.util.List;

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
    /**
     * 权限
     */
    private int authority;

    public int getAuthority() {
        return authority;
    }

    public void setAuthority(int authority) {
        this.authority = authority;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

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

    public String getRoleDescription() {
        return role.getDescription();
    }

    public User login(String username, String password) {
        return new BaseServiceImpl().checkLogin(userName, password);
    }

    public boolean register(User user) {
        return new BaseServiceImpl().register(user);
    }

    /**
     * 查询所有板块信息
     * @return List<Board>
     */
    public List<Board> getBoards(){
        return role.getBoard();
    }

    /**
     * 显示版块下所有主帖
     * @return List<Post>
     */
    public List<Post> getAllPost(int boardId,int ownerId){
        return role.getAllPost(boardId,ownerId);
    }

    /**
     * 查看已发贴
     * @param userId
     * @return
     */
    public List<Post> queryUserPost(int userId){
        return role.queryUserPost(userId);
    }

    /**
     * 查看主帖及回帖
     * @param postId
     * @param ownerId
     * @return
     */
    public List<Post> getAllReplyByPostId(int postId, int ownerId) {
        return role.getAllReplyByPostId(postId, ownerId);
    }

    public boolean MoveOutBlackList(int userId, int blackId){
        return role.MoveOutBlackList(userId, blackId);
    }

    public boolean AddIntoBlackList(int userId, int blackId){
        return role.AddIntoBlackList(userId, blackId);
    }

    public void showDisableUserList(){
        role.showDisableUserList();
    }

    /**
     * 添加置顶
     * @param postId
     */
    public void addStick(int postId){
        role.StickPost(postId);
    }

    /**
     * 删除置顶
     * @param postId
     */
    public void cancelStick(int postId){
        role.CancelStick(postId);
    }
}
