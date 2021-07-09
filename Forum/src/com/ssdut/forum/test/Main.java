package com.ssdut.forum.test;


import com.ssdut.forum.entity.User;

import java.util.Scanner;

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
        User user=null; // 登录的角色
        Scanner input=new Scanner(System.in);
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
                        login();
                    }
                    break;
                case 2:
                    //注册

                    break;
                case 3:
                    //退出系统
                    bBegin=false;
                    return;
                default:
                    System.out.println("无此操作，请重新输入：");
                    break;
            }
        }
    }

    /**
     * 登录信息
     * @return
     */
    private static User login(){
        Scanner input=new Scanner(System.in);
        System.out.print("用户名：");
        String username=input.next();
        System.out.print("密码：");
        String password=input.next();

        return null;
    }
}
