package com.ssdut.forum.entity;

public class Post {
    private int postId; //帖子编号
    private String title; //标题
    private String content; //内容
    private int userId; //用户ID
    private int boardId; //板块ID
    private String userName; //用户姓名
    private String boardName; //板块名称
    private String replyTo;  //假设这是一个回给A的帖，replyTo表示A的贴号
    private String replyToUser; //表示发A贴的人的Id

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }


}
