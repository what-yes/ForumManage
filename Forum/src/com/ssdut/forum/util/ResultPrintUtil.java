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

        Map<Integer, String> authority = new HashMap<>();
        authority.put(1,"普通用户");
        authority.put(2,"版主");
        authority.put(1,"管理员");

        System.out.println("用户ID\t\t用户名\t\t用户身份\t\t用户状态\n");
        for(User user : list){
            System.out.printf("%-8d|%-8s|%-4s\n",user.getUserId(),user.getUserName(),authority.get(user.getAuthority()),state.get(user.getState()));
        }

    }

    /**
     * （用户）显示自己的黑名单
     */
    public static void printBlackList(List<User> list){
        System.out.println("用户ID\t\t用户名\n");
        for(User user : list){
            System.out.printf("%-8d|%-8s\n",user.getUserId(),user.getUserName());
        }
    }

    /**
     * 打印帖子内容
     */
    public static void printPosts(List<Post> list){
        List<Board> boards = new BoardServiceImpl().getBoard();
        Map<Integer,String> stick = new HashMap<>();
        stick.put(0,"非置顶");
        stick.put(1,"置顶");
        System.out.printf("帖子编号\t帖子标题\t帖子内容\t用户名\t置顶状态\n");
        for(Post post : list){
            String content = post.getContent().substring(0,5) + "...";
            System.out.printf("%-3d|%-6s|%-10s|%-8s|%-3s\n",post.getPostId(),post.getTitle(),content,post.getUserName(),stick.get(post.getStick()));
        }
    }

    /**
     * 打印所有版块
     */
    public static void printBoards(List<Board> list){
        for(Board board : list){
            System.out.printf("%-4d%-4s\n",board.getBoardId(),board.getBoardName());
        }
    }

}
