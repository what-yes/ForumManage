package com.ssdut.forum.service;

/**
 * ClassName: boardService
 * Description: 板块业务
 * Date: 2021/7/8 17:26
 *
 * @author Crescent
 * @version 1.0
 * @since JDK 1.8
 */
public interface boardService {
    /**
     * 显示所有板块信息
     */
    void showBoard();

    /**
     * 添加新的板块
     */
    void addBoard();

    /**
     * 删除板块
     */
    void deleteBoard();
}
