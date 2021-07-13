package com.ssdut.forum.util;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 敏感词屏蔽工具类
 */
public class SensitiveWordFilterUtil {
    private static Map sensitiveWordMap = null;  //使用DFA后的敏感词树
    private static int minMatchType = 1;
    private static Set<String> sensitiveWordSet= null;
    private static FileThreadUtil fileThreadUtil = new FileThreadUtil();

    public static Map getSensitiveWordMap() {
        return sensitiveWordMap;
    }

    public static Set<String> getSensitiveWordSet() {
        return sensitiveWordSet;
    }

    /**
     * 过滤器初始化
     * @throws InterruptedException
     */
    public void FilterInit() throws InterruptedException {
        SensitiveWordFilterUtil swfu= new SensitiveWordFilterUtil();
        swfu.ReadFileToSet();
        TimeUnit.MILLISECONDS.sleep(10);
        sensitiveWordSet = fileThreadUtil.getReadOutSet();
        swfu.addSensitiveWordToHashMap(sensitiveWordSet);
    }

    /**
     * 使用多线程读取文件到敏感词Set
     * @throws InterruptedException
     */
    public void ReadFileToSet() throws InterruptedException {
        fileThreadUtil.startRead();
        sensitiveWordSet = fileThreadUtil.getReadOutSet();
    }

    /**
     * 使用DFA算法构建敏感词树
     * @param keyWordSet
     */
    private void addSensitiveWordToHashMap(Set<String> keyWordSet) {
        sensitiveWordMap = new HashMap(keyWordSet.size());     //初始化敏感词容器，减少扩容操作
        String key = null;
        Map nowMap = null;
        Map<String, String> newWorMap = null;
        //迭代keyWordSet
        Iterator<String> iterator = keyWordSet.iterator();
        while(iterator.hasNext()){
            key = iterator.next();    //关键字
            nowMap = sensitiveWordMap;
            for(int i = 0 ; i < key.length() ; i++){
                char keyChar = key.charAt(i);       //转换成char型
                Object wordMap = nowMap.get(keyChar);       //获取
                if(wordMap != null){        //如果存在该key，直接赋值
                    nowMap = (Map) wordMap;
                } else{     //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = new HashMap<String,String>();
                    newWorMap.put("isEnd", "0");     //不是最后一个
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }
                if(i == key.length() - 1){
                    nowMap.put("isEnd", "1");    //最后一个
                }
            }
        }
    }

    /**
     * 检查字符串中是否包含敏感词
     * @param txt
     * @param beginIndex
     * @param matchType 最小/最大匹配规则
     * @return 如果存在，返回敏感词字符长度，不存在则返回0
     */
    public int CheckSensitiveWord(String txt,int beginIndex,int matchType){
        boolean  flag = false;    //敏感词结束标识位：用于敏感词只有1位的情况
        int matchFlag = 0;     //匹配标识数默认为0
        char word = 0;
        Map nowMap = sensitiveWordMap;
        for(int i = beginIndex; i < txt.length() ; i++){
            word = txt.charAt(i);
            nowMap = (Map) nowMap.get(word);     //获取指定key
            if(nowMap != null){     //存在，则判断是否为最后一个
                matchFlag++;     //找到相应key，匹配标识+1
                if("1".equals(nowMap.get("isEnd"))){       //如果为最后一个匹配规则,结束循环，返回匹配标识数
                    flag = true;       //结束标志位为true
                    if(SensitiveWordFilterUtil.minMatchType == matchType){    //最小规则，直接返回,最大规则还需继续查找
                        break;
                    }
                }
            } else {     //不存在，直接返回
                break;
            }
        }
        if(matchFlag < 2 && !flag){
            matchFlag = 0;
        }
        return matchFlag;
    }

    //获取文字中的敏感词
    public Set<String> getSensitiveWord(String txt, int matchType){
        Set<String> sensitiveWordList = new HashSet<>();
        for(int i = 0; i < txt.length(); i++){
            int length = CheckSensitiveWord(txt, i, matchType);
            if(length > 0){
                sensitiveWordList.add(txt.substring(i, i+length));
                i = i + length -1;
            }
        }
        return sensitiveWordList;
    }

    /**
     * 替换字符串中的敏感词
     * @param txt 原字符串
     * @param matchType 最小/最大匹配规则
     * @param replaceChar 替换的字符
     * @return 替换后的字符串
     */
    public String replaceSensitiveWord(String txt, int matchType, String replaceChar){
        String resultTxt = txt;
        Set<String> set = getSensitiveWord(txt, matchType);
        Iterator<String> iterator = set.iterator();
        String word = null;
        String replaceString = null;
        while(iterator.hasNext()) {
            word = iterator.next();
            replaceString = getReplaceChars(replaceChar, word.length());
            resultTxt = resultTxt.replaceAll(word, replaceString);
        }
        return resultTxt;
    }

    /**
     * 获取对应长度的替换词字符串
     * @param replaceChar 替换字符
     * @param length 长度
     * @return
     */
    private String getReplaceChars(String replaceChar, int length) {
        String resultReplace = replaceChar;
        for(int i = 1; i < length; i++) {
            resultReplace += replaceChar;
        }
        return resultReplace;
    }

}