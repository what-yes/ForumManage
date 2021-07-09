package com.ssdut.forum.util;

import com.ssdut.forum.entity.Board;
import com.ssdut.forum.entity.Post;
import com.ssdut.forum.entity.User;
import com.ssdut.forum.service.BoardService;
import com.ssdut.forum.service.impl.BoardServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultPrintUtil {
    /**
     * （管理员）打印用户信息
     */
    public static void printUserInfo(List<User> list){
        Map<Integer, String> state = new HashMap<>();
        state.put(0,"正常");
        state.put(1,"禁用");

        System.out.println("用户ID\t\t用户名\t\t用户身份\t\t用户状态");
        for(User user : list){
            System.out.printf("%8d|%8s|%4s",user.getUserId(),user.getUserName(),state.get(user.getState()));
        }

    }

    /**
     * （用户）显示自己的黑名单
     */
    public static void printBlackList(){}

    /**
     * 打印帖子内容
     */
    public static void printPosts(List<Post> list){
        List<Board> boards = new BoardServiceImpl().getBoard();
        Map<Integer,String> stick = new HashMap<>();
        stick.put(0,"非置顶");
        stick.put(1,"置顶");
        System.out.printf("帖子编号\t帖子标题\t帖子内容\t用户名\t所属板块\t置顶状态");
        for(Post post : list){
            String content = post.getContent().substring(0,5) + "...";
            System.out.printf("%3d|%6s|%10s|%8s|%4s|%3s",post.getPostId(),post.getTitle(),content,post.getUserName(),post.getBoardName(),stick.get(post.getStick()));
        }
    }

    /**
     *
     */
    public static void printBoards(List<Board> list){
        for(Board board : list){
            System.out.printf("%4d",board.getBoardId());
        }
        for(Board board : list){
            System.out.printf("%4s",board.getBoardName());
        }
    }

}
