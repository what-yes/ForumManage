package com.ssdut.forum.entity;

public class Board {
    private int boardId; //板块id
    private String boardName; //板块名称
    private String boardMgrId; //版主Id

    public String getBoardMgrId() {
        return boardMgrId;
    }

    public void setBoardMgrId(String boardMgrId) {
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
