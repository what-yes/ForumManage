package com.ssdut.forum.util;

import com.ssdut.forum.entity.Board;
import com.ssdut.forum.entity.Post;
import com.ssdut.forum.entity.User;
import com.ssdut.forum.service.BoardService;
import com.ssdut.forum.service.impl.BoardServiceImpl;

import java.util.*;
import java.util.stream.Stream;

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
        authority.put(3,"管理员");

        System.out.printf("%-8s|%-8s|%-4s%-4s\n","用户ID","用户名","用户身份","用户状态");
        for(User user : list){
            System.out.printf("%-8d%-8s%-4s%-3d\n",user.getUserId(),user.getUserName(),authority.get(user.getAuthority()),state.get(user.getState()));
        }

    }

    /**
     * （用户）显示自己的黑名单
     */
    public static void printBlackList(List<User> list){


        int length_1 = list.size();
        String[][] data = new String[length_1+1][2];
        String[] elements = new String[2];
        elements[0] = "UserID";
        elements[1] = "UserName";
        data[0] = elements;
        for(int i=1;i<=length_1;i++){
            data[i] = new String[]{Integer.toString(list.get(i-1).getUserId()),list.get(i-1).getUserName()};
        }

        tableWithLinesAndMaxWidth(data);
    }

    /**
     * 打印帖子内容
     */
    public static void printPosts(List<Post> list){
//        List<Board> boards = new BoardServiceImpl().getBoard();
        Map<Integer,String> stick = new HashMap<>();
        stick.put(0,"非置顶");
        stick.put(1,"置顶");


        int length_1 = list.size();
        System.out.println(length_1);
        String[][] data = new String[length_1+1][7];
        String[] elements = new String[7];
        elements[0] = "postID";
        elements[1] = "userID";
        elements[2] = "postTopic";
        elements[3] = "postContent";
        elements[4] = "isStick";
        elements[5] = "replyTo";
        elements[6] = "belongTo";
        data[0] = elements;
        for(int i=1;i<=length_1;i++){
            String userId = Integer.toString(list.get(i-1).getUserId());
            String content = list.get(i-1).getContent().length()>=5 ? list.get(i-1).getContent().substring(0,5) + "..." :list.get(i-1).getContent();
            String replyTo = list.get(i-1).getReplyTo() == 0 ? "" : Integer.toString(list.get(i-1).getReplyTo());
            String belongTo = list.get(i-1).getBelongTo() == 0 ? "" : Integer.toString(list.get(i-1).getBelongTo());
            data[i] = new String[]{Integer.toString(list.get(i-1).getPostId()),userId,list.get(i-1).getTitle(), content, stick.get(list.get(i-1).getStick()), replyTo, belongTo};
        }

        tableWithLinesAndMaxWidth(data);
    }

    /**
     * 打印所有版块
     */
    public static void printBoards(List<Board> list){

        int length_1 = list.size();
        System.out.println(length_1);
        String[][] data = new String[length_1+1][2];
        String[] elements = new String[2];
        elements[0] = "BoardID";
        elements[1] = "BoardName";
        data[0] = elements;
        for(int i=1;i<=length_1;i++){
            data[i] = new String[]{Integer.toString(list.get(i-1).getBoardId()),list.get(i-1).getBoardName()};
        }

        tableWithLinesAndMaxWidth(data);
    }



    public static void tableWithLinesAndMaxWidth(String[][] table) {
        /*
         * leftJustifiedRows - If true, it will add "-" as a flag to format string to
         * make it left justified. Otherwise right justified.
         */
        boolean leftJustifiedRows = true;

        /*
         * Maximum allowed width. Line will be wrapped beyond this width.
         */
        int maxWidth = 30;

        /*
         * Table to print in console in 2-dimensional array. Each sub-array is a row.
         */

        /*
         * Create new table array with wrapped rows
         */
        List<String[]> tableList = new ArrayList<>(Arrays.asList(table));
        List<String[]> finalTableList = new ArrayList<>();
        for (String[] row : tableList) {
            // If any cell data is more than max width, then it will need extra row.
            boolean needExtraRow = false;
            // Count of extra split row.
            int splitRow = 0;
            do {
                needExtraRow = false;
                String[] newRow = new String[row.length];
                for (int i = 0; i < row.length; i++) {
                    // If data is less than max width, use that as it is.
                    if (row[i].length() < maxWidth) {
                        newRow[i] = splitRow == 0 ? row[i] : "";
                    } else if ((row[i].length() > (splitRow * maxWidth))) {
                        // If data is more than max width, then crop data at maxwidth.
                        // Remaining cropped data will be part of next row.
                        int end = row[i].length() > ((splitRow * maxWidth) + maxWidth)
                                ? (splitRow * maxWidth) + maxWidth
                                : row[i].length();
                        newRow[i] = row[i].substring((splitRow * maxWidth), end);
                        needExtraRow = true;
                    } else {
                        newRow[i] = "";
                    }
                }
                finalTableList.add(newRow);
                if (needExtraRow) {
                    splitRow++;
                }
            } while (needExtraRow);
        }
        String[][] finalTable = new String[finalTableList.size()][finalTableList.get(0).length];
        for (int i = 0; i < finalTable.length; i++) {
            finalTable[i] = finalTableList.get(i);
        }

        /*
         * Calculate appropriate Length of each column by looking at width of data in
         * each column.
         *
         * Map columnLengths is <column_number, column_length>
         */
        Map<Integer, Integer> columnLengths = new HashMap<>();
        Arrays.stream(finalTable).forEach(a -> Stream.iterate(0, (i -> i < a.length), (i -> ++i)).forEach(i -> {
            if (columnLengths.get(i) == null) {
                columnLengths.put(i, 0);
            }
            if (columnLengths.get(i) < a[i].length()) {
                columnLengths.put(i, a[i].length());
            }
        }));
//        System.out.println("columnLengths = " + columnLengths);

        /*
         * Prepare format String
         */
        final StringBuilder formatString = new StringBuilder("");
        String flag = leftJustifiedRows ? "-" : "";
        columnLengths.entrySet().stream().forEach(e -> formatString.append("| %" + flag + e.getValue() + "s "));
        formatString.append("|\n");
//        System.out.println("formatString = " + formatString.toString());

        /*
         * Prepare line for top, bottom & below header row.
         */
        String line = columnLengths.entrySet().stream().reduce("", (ln, b) -> {
            String templn = "+-";
            templn = templn + Stream.iterate(0, (i -> i < b.getValue()), (i -> ++i)).reduce("", (ln1, b1) -> ln1 + "-",
                    (a1, b1) -> a1 + b1);
            templn = templn + "-";
            return ln + templn;
        }, (a, b) -> a + b);
        line = line + "+\n";
//        System.out.println("Line = " + line);

        /*
         * Print table
         */
//        System.out.print(line);
        Arrays.stream(finalTable).limit(1).forEach(a -> System.out.printf(formatString.toString(), a));
        System.out.print(line);

        Stream.iterate(1, (i -> i < finalTable.length), (i -> ++i))
                .forEach(a -> System.out.printf(formatString.toString(), finalTable[a]));
        System.out.print(line);
    }


}
