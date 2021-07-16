package com.ssdut.forum.test;


import com.ssdut.forum.authority.impl.DefaultAdmin;
import com.ssdut.forum.authority.impl.DefaultBoardMgr;
import com.ssdut.forum.authority.impl.DefaultUser;
import com.ssdut.forum.entity.Board;
import com.ssdut.forum.entity.User;
import com.ssdut.forum.entity.Post;
import com.ssdut.forum.role.Role;
import com.ssdut.forum.util.ResultPrintUtil;
import com.ssdut.forum.util.SensitiveWordFilterUtil;

import java.util.List;
import java.util.Scanner;

import static com.ssdut.forum.util.ResultPrintUtil.printBoards;
import static com.ssdut.forum.util.ResultPrintUtil.printPosts;

/**
 * ClassName: Main
 * Description: 系统入口类
 * Date: 2021/7/8 1:40
 *
 * @author Crescent
 * @version 1.0
 * @since JDK 1.8
 */
public class Main {
    private static Scanner input=new Scanner(System.in);
    private static SensitiveWordFilterUtil sensitiveWordFilterUtil = new SensitiveWordFilterUtil();
    /**
     * 系统入口方法
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        Main.startForumSys();
    }

    private static void startForumSys() throws InterruptedException {
        System.out.println("------------论坛------------");
        boolean bBegin=true;
        User user=new User(); // 登录的角色
        int nBegin=0;
        while (bBegin){
            System.out.println("--------请选择您的操作（输入对应序号）--------");
            System.out.println("1. 登录");
            System.out.println("2. 注册");
            System.out.println("3. 退出");
            nBegin= input.nextInt();

            switch (nBegin){
                case 1:
                    // 登录操作
                    boolean bLogin=false;//登录是否成功
                    while(!bLogin){
                        User u=null;
                        if((u=login(user))!=null&&u.getState()!=1){
                            // 登录成功
                            System.out.println("登录成功！");
                            user=u;
                            bLogin=true;
                            // 进入主界面
                            int roleAuthority =user.getAuthority();
                            if(roleAuthority==3){
                                user.setRole(new Role("管理员",new DefaultUser(),
                                        new DefaultBoardMgr(),new DefaultAdmin()));
                                //打开管理员页面
                                homeScreen(user);
                            }else if(roleAuthority==2){
                                user.setRole(new Role("版主",new DefaultUser(),
                                        new DefaultBoardMgr(),null));
                                //打开版主页面
                                homeScreen(user);
                            }else if(roleAuthority==1){
                                user.setRole(new Role("用户",new DefaultUser(),
                                        null,null));
                                //打开用户页面
                                homeScreen(user);
                            }else {
                                System.out.println("未知用户权限！");
                            }
                        }else if(u!=null&&u.getState()==1) {
                            System.out.println("该账户已被禁用！");
                        }else {
                            System.out.println("账户或密码错误！请重新输入：");
                        }
                    }
                    bBegin=false;
                    break;
                case 2:
                    //注册
                    boolean bRegister=false;//注册是否成功
                    while(!bRegister){
                        if(register(user)){
                            bRegister=true;
                            System.out.println("注册成功！");
                        }else{
                            System.out.println("注册失败！请重新注册：");
                        }
                    }
                    break;
                case 3:
                    //退出系统
                    bBegin=false;
                    return;
                default:
                    System.out.println("无此操作，请重新选择！");
                    break;
            }
        }
    }

    /**
     * 登录
     * @return
     */
    private static User login(User user){
        System.out.print("用户名：");
        user.setUserName(input.next());
        System.out.print("密码：");
        user.setPassWord(input.next());

        return user.login(user.getUserName(), user.getPassWord());
    }

    /**
     * 注册
     * @param user
     * @return
     */
    private static boolean register(User user){
        System.out.println("-----------注册用户-----------");
        System.out.println("用户名：");
        user.setUserName(input.next());
        System.out.println("密码：");
        user.setPassWord(input.next());
        return user.register(user);
    }

    /**
     * 主页面
     * @param user
     */
    private static void  homeScreen(User user) throws InterruptedException {

        //敏感词屏蔽初始化
        SensitiveWordFilterUtil swfu= new SensitiveWordFilterUtil();
        swfu.FilterInit();

        boolean bHome = false;

        while (!bHome) {
            System.out.println("-----------主页面-----------");
            System.out.println("1.版块管理");
            System.out.println("2.查看已发帖");
            System.out.println("3.用户管理");
            System.out.println("4.退出系统");
            System.out.println("请输入对应编号进行操作：");
            int iSelect = input.nextInt();
            switch (iSelect) {
                case 1:
                    //版块管理
                    boardScreen(user);
                    break;
                case 2:
                    //查看已发帖
                    userPostScreen(user);
                    break;
                case 3:
                    //管理用户
                    mgrUserScreen(user);
                    break;
                case 4:
                    //退出系统
                    bHome = true;
                    break;
                default:
                    System.out.println("无此功能，敬请期待！请重新选择其他功能：");
                    break;
            }
        }
    }
    /**
     * 显示版块下内容并进行相应操作
     * @param user
     */
    private static void boardScreen(User user) throws InterruptedException {

        boolean selectTrue=false; //是否选择正确
        while(true){
            System.out.println("----------版块管理----------");
            System.out.println("0.退回主界面");
            System.out.println("1.查看版块");
            //如果是版主 显示额外操作
            if(user.getAuthority()==3){
                System.out.println("2.添加版块");
                System.out.println("3.删除版块");
            }
            int iSelect=input.nextInt();
            switch (iSelect){
                case 0:
                    //回到主界面
                    return;
                case 1:
                    //查看版块
                    selectTrue=true;
                    boardShow(user);
                    break;
                default:
                    if(user.getAuthority()!=3){
                        selectTrue=false;
                    }
                    break;
            }
            //如果是版主进行额外操作
            if((iSelect>1||iSelect<0)&&(user.getAuthority()==3)){
                if(iSelect==2){
                    //添加版块
                    selectTrue=true;
                    addBoard(user);
                }else  if(iSelect==3) {
                    //删除版块
                    selectTrue=true;
                    deleteBoard(user);
                }else {
                    selectTrue=false;
                }
            }
            if(!selectTrue){
                System.out.println("无此功能，敬请期待！请重新选择其他功能：");
            }
        }
    }

    /**
     * 查看版块
     * @param user
     */
    private static void boardShow(User user) throws InterruptedException {
        while(true){
            //显示所有版块
            List<Board> boardList=user.getBoards();
            printBoards(boardList);
            //选择进入哪个版块
            System.out.println("请选择进入哪一个版块：(输入0返回)");
            int boardId=input.nextInt();
            Board board=null;
            boolean haveBoardId=false;
            if(boardId==0){
                return;
            }
            for (Board b:boardList){
                if(b.getBoardId()==boardId)
                {
                    haveBoardId=true;
                    board=b;
                    break;
                }
            }
            if(!haveBoardId){
                System.out.println("并无此版块，请重新输入：");
            }else {
                //显示版块下内容并进行相应操作
                boardContentScreen(board,user);
                //回到主界面
                return;
            }
        }
    }

    /**
     * 添加版块
     * @param user
     */
    private static void addBoard(User user){
        while (true){
            Board board=new Board();
            System.out.println("请输入您想创建的版块名字(输入0退出):");
            String boardName=input.next();
            if(boardName.equals("0")){
                return;
            }
            board.setBoardName(boardName);
            while (true){
                System.out.println("请输入您想任命该版块的管理员id(输入-1暂时不任命，输入0退出):");
                int boardMgrId=input.nextInt();
                if(boardMgrId==0){
                    return;
                }else if (boardMgrId!=-1){
                    if(user.getUserById(boardMgrId)==null){//如果该userId不存在
                        System.out.println("该用户不存在，请重新输入用户id！");
                    }else {
                        board.setBoardMgrId(boardMgrId);
                        break;
                    }
                }
            }

            if(!user.addBoard(board)){
                System.out.println("创建失败，请重新创建！");
            }else{
                System.out.println("创建成功！");
                return;
            }
        }
    }

    /**
     * 删除版块
     * @param user
     */
    private static void deleteBoard(User user){
        while (true){
            System.out.println("请输入您想删除的版块Id(输入0退出):");
            int boardId =input.nextInt();
            if(boardId ==0){
                return;
            }
            if(!user.deleteBoard(boardId)){
                System.out.println("删除失败，请重新输入！");
            }else {
                System.out.println("删除成功！");
                return;
            }
        }
    }

    /**
     * 显示版块下内容并进行相应操作
     * @param user
     */
    private static void boardContentScreen(Board board,User user) throws InterruptedException {

        boolean selectTrue=false; //是否选择正确
        while(true){
            //TODO 分页查看
            //显示版块内容
            System.out.println("--------版块："+board.getBoardName()+"--------");
            printPosts(user.getAllPost(board.getBoardId(),user.getUserId()));
            System.out.println("可进行操作：");
            System.out.println("0.退回主界面");
            System.out.println("1.切换版块");
            System.out.println("2.查看帖子");
            System.out.println("3.发主题帖");
            System.out.println("4.删除帖子");
            //如果是版主或管理员 显示额外操作
            if((user.getAuthority()==2 && board.getBoardMgrId()==user.getUserId())
                    ||user.getAuthority()==3){
                System.out.println("5.置顶帖子");
                System.out.println("6.取消置顶");
            }
            int iSelect=input.nextInt();
            switch (iSelect){
                case 0:
                    //回到主界面
                    return;
                case 1:
                    //切换版块
                    boardScreen(user);
                    return; //不能使用break
                case 2:
                    //查看帖子
                    selectTrue=true;
                    showPost(user, board);
                    break;
                case 3:
                    //发主题帖
                    selectTrue=true;
                    addPost(user, board);
                    break;
                case 4:
                    //删除帖子
                    selectTrue=true;
                    deletePost(user);
                    break;
                default:
                    if(user.getAuthority()==1){
                        selectTrue=false;
                    }
                    break;
            }
            //如果是版主或管理员 进行额外操作
            if((iSelect>4||iSelect<0)&&((user.getAuthority()==2 && board.getBoardMgrId()==user.getUserId())
                    ||user.getAuthority()==3)){
                if(iSelect==5){
                    //置顶帖子
                    selectTrue=true;
                    addStick(user,board);
                }else  if(iSelect==6) {
                    //取消置顶
                    selectTrue=true;
                    cancelStick(user,board);
                }else {
                    selectTrue=false;
                }
            }
            if(!selectTrue){
                System.out.println("无此功能，敬请期待！请重新选择其他功能：");
            }
        }
    }

    /**
     * 显示主题帖以及其回帖
     * @param user
     */
    private static void showPost(User user, Board board){
        while (true){
            System.out.print("请输入主帖号查看相应帖子（输入0返回）：");
            int inputNumber = input.nextInt();
            if(inputNumber == 0){
                return;
            }
            List<Post> AvailablePostList = user.getAllPost(board.getBoardId(), user.getUserId());
            boolean isInBoard = false;
            for(Post post:AvailablePostList){
                if(post.getPostId()==inputNumber){
                    isInBoard = true;
                }
            }
            List<Post> postList = user.getAllReplyByPostId(inputNumber, user.getUserId());
            if(postList==null||isInBoard==false){
                System.out.print("帖号不合法！");
                System.out.println("");
                continue;
            }else{
                printPosts(postList);
            }
            System.out.println("回复请按1，退出该贴请按0");
            int inputNumber2 = input.nextInt();
            if(inputNumber2 == 0){
                return;
            }
            addPost(user, board, inputNumber);
        }
    }

    /**
     * 发帖
     * @param user
     */
    private static void addPost(User user, Board board){
        Post newPost = new Post();
        newPost.setUserId(user.getUserId());
        newPost.setBoardId(board.getBoardId());
        String inputString;
        System.out.println("请输入帖子标题：");
        inputString = input.next();
        System.out.println(inputString);
        String newString = sensitiveWordFilterUtil.replaceSensitiveWord(inputString, 1, "*");
        System.out.println(newString);
        newPost.setTitle(newString);
        System.out.println("请输入帖子内容：");
        inputString = input.next();
        System.out.println(inputString);
        newPost.setContent(sensitiveWordFilterUtil.replaceSensitiveWord(inputString, 1, "*"));
        user.addPost(newPost);
    }

    /**
     * 回复帖
     * @param user
     * @param board
     * @param postId
     */
    private static void addPost(User user, Board board, int postId){
        Post newPost = new Post();
        newPost.setUserId(user.getUserId());
        newPost.setBoardId(board.getBoardId());
        newPost.setBelongTo(postId);
        String inputString;
        System.out.println("是否回复其他跟帖？输入postId：回复相应跟帖；输入0：回复主帖");
        int inputNumber = input.nextInt();
        if(inputNumber == 0){
            System.out.println("请输入回复内容：");
            inputString = input.next();
            newPost.setContent(sensitiveWordFilterUtil.replaceSensitiveWord(inputString, 1, "*"));
        }else{
            List<Post> AvailablePostList = user.getAllReplyByPostId(postId, user.getUserId());
            boolean isInPost = false;
            for(Post post:AvailablePostList){
                if(post.getPostId()==inputNumber){
                    isInPost = true;
                }
            }
            if(isInPost==false){
                System.out.print("帖号不合法！");
                System.out.println("");
                return;
            }else{
                newPost.setReplyTo(inputNumber);

                System.out.println("请输入回复内容：");
                inputString = input.next();
                newPost.setContent(sensitiveWordFilterUtil.replaceSensitiveWord(inputString, 1, "*"));
            }
        }
        user.addPost(newPost);
    }

    /**
     * 删除帖子
     * @param user
     */
    private static void deletePost(User user){
        System.out.println("请输入您想删除的帖子Id(输入0退出):");
        int postId=input.nextInt();
        if(postId==0){
            return;
        }
        if(!user.deletePost(user.getUserId(),postId)){
            System.out.println("删除失败！");
        }else {
            System.out.println("删除成功！");
        }
    }

    /**
     * 添加置顶
     * @param user
     */
    private static void addStick(User user,Board board){
        System.out.println("请输入您要置顶的帖子ID(输入0返回)：");
        int postid=0;
        while (true){
            postid=input.nextInt();
            if(postid == 0){
                return;
            }
            boolean isExist= user.getAllReplyByPostId(postid, user.getUserId()) != null;
            if(isExist){
                List<Post> AvailablePostList = user.getAllPost(board.getBoardId(), user.getUserId());
                boolean isInBoard = false;
                for(Post post:AvailablePostList){
                    if(post.getPostId()==postid){
                        isInBoard = true;
                    }
                }
                isExist=isInBoard;
            }
            if(isExist){
                user.addStick(postid);
                System.out.println("置顶成功！");
                return;
            }else {
                System.out.println("无此帖子，请重新输入(输入0返回)：");
            }
        }
    }

    /**
     * 删除置顶
     * @param user
     */
    private static void cancelStick(User user,Board board){
        System.out.println("请输入您要取消置顶的帖子ID(输入0返回)：");
        int postid=0;
        while (true){
            postid=input.nextInt();
            if(postid == 0){
                return;
            }
            boolean isExist= user.getAllReplyByPostId(postid, user.getUserId()) != null;
            if(isExist){
                List<Post> AvailablePostList = user.getAllPost(board.getBoardId(), user.getUserId());
                boolean isInBoard = false;
                for(Post post:AvailablePostList){
                    if(post.getPostId()==postid){
                        isInBoard = true;
                    }
                }
                isExist=isInBoard;
            }
            if(isExist){
                user.cancelStick(postid);
                System.out.println("取消置顶成功！");
                return;
            }else {
                System.out.println("无此帖子，请重新输入(输入0返回)：");
            }
        }
    }

    /**
     * 查看已发帖
     * @param user
     */
    private static void userPostScreen(User user){
        while(true){
            List<Post> postList = user.queryUserPost(user.getUserId());
            printPosts(postList);
            System.out.println("输入0返回主界面");
            int inputNumber = input.nextInt();
            if(inputNumber == 0){
                return;
            }
        }
    }

    /**
     * 用户管理
     * @param user
     */
    private static void mgrUserScreen(User user){
        boolean selectTrue=false;
        while(true){
            System.out.println("--------用户管理--------");
            System.out.println("可进行操作：");
            System.out.println("0.退回主界面");
            System.out.println("1.查看黑名单");
            System.out.println("2.拉黑用户");
            System.out.println("3.取消拉黑用户");
            //如果是版主或管理员 显示禁用操作
            if(user.getAuthority()==2 || user.getAuthority()==3){
                System.out.println("4.查看禁用用户列表");
                System.out.println("5.禁用用户");
                System.out.println("6.取消禁用用户");
            }
            //如果是管理员   显示任免版主功能
            if(user.getAuthority()==3){
                System.out.println("7.查看版主信息");
                System.out.println("8.添加版主"); //需设置管理的版块对象
                System.out.println("9.取消版主"); //如用户已无管理的版块  则取消版主身份
            }
            int iSelect=input.nextInt();
            switch (iSelect){
                case 0:
                    //回到主界面
                    return;
                case 1:
                    //查看黑名单
                    selectTrue=true;
                    showBlackList(user);
                    break; //不能使用break
                case 2:
                    //拉黑用户
                    selectTrue=true;
                    addIntoBlackList(user);
                    break;
                case 3:
                    //取消拉黑用户
                    selectTrue=true;
                    moveOutBlackList(user);
                    break;
                default:
                    if(user.getAuthority()==1){
                        selectTrue=false;
                    }
                    break;
            }
            //如果是版主或管理员 进行额外操作
            if((user.getAuthority()==2 ||user.getAuthority()==3)&&(iSelect>3||iSelect<0)){
                switch (iSelect){
                    case 4:
                        selectTrue=true;
                        //查看禁用用户列表
                        showDisableUserList(user);
                        break;
                    case 5:
                        selectTrue=true;
                        //禁用用户
                        addDisableUser(user);
                        break; //不能使用break
                    case 6:
                        selectTrue=true;
                        //取消禁用用户
                        CancelDisableUser(user);
                        break;
                    default:
                        if(user.getAuthority()==2){
                            selectTrue=false;
                        }
                        break;
                }
            }
            //如果是管理员 进行额外操作
            if((user.getAuthority()==3)&&(iSelect>6||iSelect<0)){
                switch (iSelect){
                    case 7:
                        selectTrue=true;
                        //查看版主信息
                        showBoardMgrList(user);
                        break;
                    case 8:
                        selectTrue=true;
                        //添加版主
                        addBoardMgr(user);
                        break; //不能使用break
                    case 9:
                        selectTrue=true;
                        //取消版主
                        cancelBoardMgr(user);
                        break;
                    default:
                        selectTrue=false;
                        break;
                }
            }
            if(!selectTrue){
                System.out.println("无此功能，敬请期待！请重新选择其他功能：");
            }
        }
    }

    /**
     * 查看黑名单
     */
    private static void showBlackList(User user) {
        ResultPrintUtil.printBlackList(user.getRole().getBlackList(user.getUserId()));
    }
    /**
     * 拉黑用户
     */
    private static void addIntoBlackList(User user) {
        showBlackList(user);
        System.out.print("请输入你想拉黑的ID：(输入0退出)");
        int newAdd = input.nextInt();
        if(newAdd == 0)
            return;
        while(!user.AddIntoBlackList(user.getUserId(), newAdd)){
            System.out.print("请重新输入你想拉黑的ID：(输入0退出)");
            newAdd = input.nextInt();
            if(newAdd == 0)
                return;
        }
    }
    /**
     * 取消拉黑用户
     */
    private static void moveOutBlackList(User user) {
        showBlackList(user);
        System.out.print("请输入你想移除拉黑的ID:(输入0返回):");
        int newAdd = input.nextInt();
        if(newAdd == 0)
            return;
        while(!user.getRole().MoveOutBlackList(user.getUserId(), newAdd)){
            System.out.print("请重新输入你想移除拉黑的ID：(输入0退出)");
            newAdd = input.nextInt();
            if(newAdd == 0)
                return;
        }

    }

    /**
     * 查看禁用用户列表
     * @param user
     */
    private static void showDisableUserList(User user) {
        user.showDisableUserList();
    }

    /**
     * 禁用用户
     * @param user
     */
    private static void addDisableUser(User user) {
        //user.getRole().DisableUser(user.getUserId());
        System.out.println("请输入你想禁用的用户ID(输入0返回)：");
        int userId = input.nextInt();
        if(userId == 0)
            return ;
        while(!user.getRole().DisableUser(userId)){
            System.out.println("请重新输入:(输入0返回):");
            userId = input.nextInt();
            if(userId == 0)
                return;
        }
    }

    /**
     * 取消禁用用户
     * @param user
     */
    private static void CancelDisableUser(User user) {
        showDisableUserList(user);
        System.out.println("请输入你想取消禁用的用户ID:(输入0返回):");
        int userId = input.nextInt();
        if(userId == 0)
            return ;
        while(!user.getRole().CancelDisable(userId)){
            System.out.println("请重新输入:(输入0返回):");
            userId = input.nextInt();
            if(userId == 0)
                return ;
        }

    }

    /**
     * 查看版主信息
     * @param user
     */
    private static void showBoardMgrList(User user) {
        user.getRole().showBoardMgrList();
    }

    /**
     * 添加版主
     * @param user
     */
    private static void addBoardMgr(User user) {
        int boardId=0;
        int boardMgrId=0;
        while(true){
            System.out.println("请输入要设置的版块Id(输入0返回)：");
            boardId=input.nextInt();
            if(boardId==0){
                return;
            }
            List<Board> boards= user.getBoards();
            boolean boardExist=false;
            Board board=null;
            for (Board b:boards){
                if (b.getBoardId() == boardId) {
                    boardExist = true;
                    board=b;
                    break;
                }
            }
            //判断是否存在该版块
            while (!boardExist){
                if(boardId==0){
                    return;
                }
                System.out.println("不存在该版块，请重新输入(输入0返回)：");
                boardId= input.nextInt();
                for (Board b:boards){
                    if (b.getBoardId() == boardId) {
                        boardExist = true;
                        board=b;
                        break;
                    }
                }
            }
            System.out.println("请输入要设置的版主Id(输入0返回)");
            boardMgrId=input.nextInt();
            if(boardMgrId==0){
                return;
            }
            //判断是否存在该用户Id
            User user1=user.getUserById(boardMgrId);
            while (user1==null||user1.getAuthority()==3){
                if(boardMgrId==0){
                    return;
                }
                System.out.println("该用户不存在或者其身份为admin，不可设置为管理员，请重新输入(输入0返回)：");
                boardMgrId= input.nextInt();
                user1=user.getUserById(boardMgrId);
            }
            //取消掉原来的版主，实际为了实现被取消管理版块的原版主的可能发生的权限更改
            user.cancelBoardMgr(board);
            if(user.setBoardMgr(boardMgrId,boardId)){
                System.out.println("设置版主成功！");
                break;
            }else {
                System.out.println("设置失败请重新设置");
            }
        }
    }

    /**
     * 取消版主
     * @param user
     */
    private static void cancelBoardMgr(User user) {
        int boardId=0;
        while(true){
            System.out.println("请输入要取消版主的版块Id(输入0返回)：");
            boardId=input.nextInt();
            if(boardId==0){
                return;
            }
            List<Board> boards= user.getBoards();
            boolean boardExist=false;
            Board board=null;
            for (Board b:boards){
                if (b.getBoardId() == boardId) {
                    boardExist = true;
                    board=b;
                    break;
                }
            }
            //判断是否存在该版块
            while (!boardExist){
                if(boardId==0){
                    return;
                }
                System.out.println("不存在该版块，请重新输入(输入0返回)：");
                boardId= input.nextInt();
                for (Board b:boards){
                    if (b.getBoardId() == boardId) {
                        boardExist = true;
                        board=b;
                        break;
                    }
                }
            }
            //取消掉原来的版主，实际为了实现被取消管理版块的原版主的可能发生的权限更改
            if(user.cancelBoardMgr(board)){
                System.out.println("取消版主成功！");
                break;
            }else {
                System.out.println("设置失败请重新设置");
            }
        }
    }
}
