package com.ssdut.forum.entity;

public class Board {
    private int boardId; //板块id
    private String boardName; //板块名称
    private int boardMgrId; //版主Id

    public int getBoardMgrId() {
        return boardMgrId;
    }

    public void setBoardMgrId(int boardMgrId) {
        this.boardMgrId = boardMgrId;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

}
