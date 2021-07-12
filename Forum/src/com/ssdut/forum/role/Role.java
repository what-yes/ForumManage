package com.ssdut.forum.role;


import com.ssdut.forum.authority.Admin;
import com.ssdut.forum.authority.BoardMgr;
import com.ssdut.forum.authority.Normal;
import com.ssdut.forum.entity.Board;
import com.ssdut.forum.entity.Post;
import com.ssdut.forum.entity.User;

import java.util.List;

public class Role {
    private String description; //角色名
    private Normal normal = null;
    private BoardMgr boardMgr = null;
    private Admin admin = null;

    public Role(String description,Normal normal,BoardMgr boardMgr,Admin admin){
        this.description=description;
        this.normal=normal;
        this.boardMgr=boardMgr;
        this.admin=admin;
    }

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

    /**
     * @desription 发帖
     * @param post
     * @return
     */
    public boolean addPost(Post post){
        boolean flag=false;
        if(normal==null){
            System.out.println("您还没有登录，请登陆后再操作");
            return false;
        }
        flag=normal.addPost(post);
        return flag;
    }

    /**
     * 删除帖子，用户只可以删除自己的，管理员可以删除所有帖子
     * @param postId
     * @return
     */
    public boolean deletePost(int postId){
        boolean flag=false;

        if(normal==null){
            System.out.println("您还没有登录，请登陆后再操作");
        }else if(admin==null&&boardMgr==null){
            flag=normal.deletePost(postId);
        }else{
            flag=boardMgr.DeletePost(postId);
        }

        return flag;
    }

    /**
     * 查询所有板块信息
     * @return List<Board>
     */
    public List<Board> getBoard(){
        if(normal==null){
            System.out.println("您还没有登录，请登陆后再操作");
            return null;
        }

        return normal.getBoard();
    }

    /**
     * 显示版块下所有主帖
     * @return List<Post>
     */
    public List<Post> getAllPost(int boardId,int ownerId){
        if(normal==null){
            System.out.println("您还没有登录，请登陆后再操作");
            return null;
        }

        return normal.getAllPost(boardId,ownerId);
    }

    /**
     * 根据帖Id查询所有回帖
     * @param postId
     * @return List<Post>
     */
    public List<Post> getAllReplyByPostId(int postId,int ownerId){
        if(normal==null){
            System.out.println("您还没有登录，请登陆后再操作");
            return null;
        }

        return normal.getAllReplyByPostId(postId,ownerId);
    }

    /**
     * 添加用户到黑名单
     * @param userId
     * @param blackUserId
     * @return
     */
    public boolean AddIntoBlackList(int userId,int blackUserId){
        if(normal==null){
            System.out.println("您还没有登录，请登陆后再操作");
            return false;
        }

        return normal.AddIntoBlackList(userId,blackUserId);
    }

    /**
     * 将用户移除黑名单
     * @param userId
     * @param userId
     * @param blackUserId
     * @return
     */
    public boolean MoveOutBlackList(int userId,int blackUserId){
        if(normal==null){
            System.out.println("您还没有登录，请登陆后再操作");
            return false;
        }

        return normal.MoveOutBlackList(userId,blackUserId);
    }

    /**
     * 获得黑名单
     * @param userId
     * @return
     */
    public List<User> getBlackList(int userId){
        if(normal==null){
            System.out.println("您还没有登录，请登陆后再操作");
            return null;
        }

        return normal.getBlackList(userId);
    }

    /**
     * 置顶帖子
     * @param postId
     * @return
     */
    public boolean StickPost(int postId){
        boolean flag=false;

        if(normal==null){
            System.out.println("您还没有登录，请登陆后再操作");
        }else if(admin==null&&boardMgr==null){
            System.out.println("抱歉，您不具备该权限");
        }else{
            flag=boardMgr.StickPost(postId);
        }

        return flag;
    }

    /**
     * 取消置顶
     * @param postId
     * @return
     */
    public boolean CancelStick(int postId){
        boolean flag=false;

        if(normal==null){
            System.out.println("您还没有登录，请登陆后再操作");
        }else if(admin==null&&boardMgr==null){
            System.out.println("抱歉，您不具备该权限");
        }else{
            flag=boardMgr.CancelStick(postId);
        }
        return flag;
    }

    /**
     * 禁用用户
     * @param userId
     * @return
     */
    public boolean DisableUser(int userId){
        boolean flag=false;

        if(normal==null){
            System.out.println("您还没有登录，请登陆后再操作");
        }else if(admin==null&&boardMgr==null){
            System.out.println("抱歉，您不具备该权限");
        }else{
            flag=boardMgr.DisableUser(userId);
        }
        return flag;
    }

    /**
     * 取消禁用
     * @param userId
     * @return
     */
    public boolean CancelDisable(int userId){
        boolean flag=false;
        if(normal==null){
            System.out.println("您还没有登录，请登陆后再操作");
        }else if(admin==null&&boardMgr==null){
            System.out.println("抱歉，您不具备该权限");
        }else{
            flag=boardMgr.CancelDisable(userId);
        }
        return flag;
    }

    /**
     * 添加管理员权限
     * @param userId
     * @param boardId
     * @return
     */
    public boolean setBoardMgr(int userId, int boardId){
        boolean flag=false;

        if(normal==null){
            System.out.println("您还没有登录，请登陆后再操作");
        }else if(admin==null){
            System.out.println("抱歉，您不具备该权限");
        }
        flag=admin.setBoardMgr(userId, boardId);

        return flag;
    }

    /**
     * 取消管理员权限
     * @param boardId
     * @return
     */
    public boolean deleteBoardMgr(int boardId){
        boolean flag=false;

        if(normal==null){
            System.out.println("您还没有登录，请登陆后再操作");
        }else if(admin==null){
            System.out.println("抱歉，您不具备该权限");
        }
        flag=admin.deleteBoardMgr(boardId);
        return flag;
    }

    public void showBoardMgrList(){
        admin.showBoardMgrList();
    }

    /**
     * 添加版块
     * @param board
     * @return
     */
    public boolean addBoard(Board board){
        boolean flag=false;

        if(normal==null){
            System.out.println("您还没有登录，请登陆后再操作");
        }else if(admin==null){
            System.out.println("抱歉，您不具备该权限");
        }
        flag=admin.addBoard(board);

        return flag;
    }

    /**
     * 删除板块
     * @param boardId
     * @return
     */
    public boolean deleteBoard(int boardId){
        boolean flag=false;

        if(normal==null){
            System.out.println("您还没有登录，请登陆后再操作");
        }else if(admin==null){
            System.out.println("抱歉，您不具备该权限");
        }
        flag=admin.deleteBoard(boardId);

        return flag;
    }

    /**
     * 查看已发帖
     * @param userId
     * @return
     */
    public List<Post> queryUserPost(int userId){
        if(normal==null){
            System.out.println("您还没有登录，请登陆后再操作");
            return null;
        }
        return normal.queryUserPost(userId);
    }

    public void showDisableUserList(){
        boardMgr.showDisableUserList();
    }

    public boolean getUserById(int userId){ return admin.getUserById(userId); }
}
