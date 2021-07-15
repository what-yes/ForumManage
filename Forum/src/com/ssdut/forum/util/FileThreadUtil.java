package com.ssdut.forum.util;


import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * 文件读取工具类
 */

public class FileThreadUtil extends Thread{
    private static CountDownLatch countDownLatch = new CountDownLatch(10);
    private int fileIndex;

    private List<String> fList;  //目录下的文件名List
    private static Set<String> readOutSet = new HashSet<>();    //读出的敏感词Set

    private static String filePath = "sensitive-words\\";

    public void setFileIndex(int fileIndex) {
        this.fileIndex = fileIndex;
    }

    public void setfList(List<String> fList) {
        this.fList = fList;
    }

    public Set<String> getReadOutSet() {
        return readOutSet;
    }


    @Override
    public void run() {
        for(int i = 0; i<fList.size(); i++) {
            if(i % 5 == fileIndex) {
                File readFile = new File(filePath + fList.get(i));
                InputStreamReader isr = null;
                try {
                    isr = new InputStreamReader(new FileInputStream(readFile), "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(isr);
                    String line;
                    while((line = bufferedReader.readLine()) != null){
                        readOutSet.add(line);
                    }
                    bufferedReader.close();
                    isr.close();
                }catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            countDownLatch.countDown();
        }
    }

    //多线程读取
    public void startRead() {
        File file = new File(filePath);
        String[] fileList = file.list();
        List<String> fList = new ArrayList<String>();
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].endsWith(".txt")) {
                fList.add(fileList[i]);
            }
        }
        for (int i = 0; i < 5; i++) {
            FileThreadUtil fileThreadUtil = new FileThreadUtil();
            fileThreadUtil.setFileIndex(i);
            fileThreadUtil.setfList(fList);
            fileThreadUtil.start();
        }
    }
}


