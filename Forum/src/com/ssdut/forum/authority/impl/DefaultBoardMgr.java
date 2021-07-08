package com.ssdut.forum.authority.impl;

import com.ssdut.forum.authority.BoardMgr;
import com.ssdut.forum.entity.Post;

public class DefaultBoardMgr implements BoardMgr {
    @Override
    public boolean StickPost(Post post) {
        return false;
    }

    @Override
    public boolean CancelStick(Post post) {
        return false;
    }

    @Override
    public boolean DeletePost(Post post) {
        return false;
    }
}
