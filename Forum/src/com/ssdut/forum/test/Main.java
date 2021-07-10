package com.ssdut.forum.test;


import com.ssdut.forum.authority.impl.DefaultAdmin;
import com.ssdut.forum.authority.impl.DefaultBoardMgr;
import com.ssdut.forum.authority.impl.DefaultUser;
import com.ssdut.forum.entity.Board;
import com.ssdut.forum.entity.User;
import com.ssdut.forum.entity.Post;
import com.ssdut.forum.role.Role;

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

    /**
     * 系统入口方法
     * @param args
     */
    public static void main(String[] args) {
        Main.startForumSys();
    }

    private static void startForumSys(){
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
                        if((u=login(user))!=null){
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
                        }else{
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
    private static void  homeScreen(User user) {
        boolean bHome = false;

        while (!bHome) {
            System.out.println("-----------主页面-----------");
            System.out.println("1.查看版块");
            System.out.println("2.查看已发帖");
            System.out.println("3.用户管理");
            System.out.println("4.退出系统");
            System.out.println("请输入对应编号进行操作：");
            int iSelect = input.nextInt();
            switch (iSelect) {
                case 1:
                    //查看版块
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
     * 查看版块
     * @param user
     */
    private static void boardScreen(User user){
        while(true){
            //显示所有版块
            List<Board> boardList=user.getBoards();
            printBoards(boardList);
            //选择进入哪个版块
            System.out.println("请选择进入哪一个版块：(输入0返回主界面)");
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
            }
        }
    }

    /**
     * 显示版块下内容并进行相应操作
     * @param user
     */
    private static void boardContentScreen(Board board,User user){
        //显示版块内容
        System.out.println("--------版块："+board.getBoardName()+"--------");
        printPosts(user.getAllPost(board.getBoardId(),user.getUserId()));
        System.out.println("可进行操作：");
        System.out.println("1.");
        //如果是版块管理员 显示管理员操作

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
            //TODO 输入帖号查看已发贴所在主帖
        }
    }

    /**
     * 拉黑管理
     * @param user
     */
    private static void blackListScreen(User user){

    }

    /**
     * 管理用户
     * @param user
     */
    private static void mgrUserScreen(User user){

    }
}
