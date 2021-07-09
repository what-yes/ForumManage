package com.ssdut.forum.util;

import com.ssdut.forum.entity.User;

import java.util.List;

public class ResultPrintUtil {
    /**
     * （管理员）打印用户信息
     */
    public static void printUserInfo(List<User> list){
        System.out.println("用户ID\t\t用户名\t\t用户状态");
    }

    /**
     * （用户）显示自己的黑名单
     */
    public static void printBlackList(){}

    /**
     * 打印帖子内容
     */
    public static void printPosts(){}

    /**
     *
     */
    public static void printBoards(){}
}
