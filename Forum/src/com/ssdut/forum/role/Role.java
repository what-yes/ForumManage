package com.ssdut.forum.role;


import com.ssdut.forum.authority.Admin;
import com.ssdut.forum.authority.BoardMgr;
import com.ssdut.forum.authority.Normal;

public class Role {
    private String description; //角色名
    private Normal normal = null;
    private BoardMgr boardMgr = null;
    private Admin admin = null;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BoardMgr getBoardMgr() {
        return boardMgr;
    }

    public void setBoardMgr(BoardMgr boardMgr) {
        this.boardMgr = boardMgr;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Normal getNormal() {
        return normal;
    }

    public void setNormal(Normal normal) {
        this.normal = normal;
    }
}
