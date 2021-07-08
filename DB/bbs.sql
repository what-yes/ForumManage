/*
 Navicat Premium Data Transfer

 Source Server         : Mysql
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : bbs

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 08/07/2021 10:02:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blacklist
-- ----------------------------
DROP TABLE IF EXISTS `blacklist`;
CREATE TABLE `blacklist`  (
  `blackListId` int NOT NULL AUTO_INCREMENT COMMENT '黑名单列表ID',
  `userId` int NULL DEFAULT NULL COMMENT '黑名单所属的用户Id',
  `blackUserId` int NULL DEFAULT NULL COMMENT '被列入黑名单的用户Id',
  PRIMARY KEY (`blackListId`) USING BTREE,
  INDEX `userId_blackList`(`userId`) USING BTREE,
  INDEX `blackUserId_blackList`(`blackUserId`) USING BTREE,
  CONSTRAINT `blackUserId_blackList` FOREIGN KEY (`blackUserId`) REFERENCES `user` (`userId`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `userId_blackList` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blacklist
-- ----------------------------
INSERT INTO `blacklist` VALUES (1, 1, 2);

-- ----------------------------
-- Table structure for board
-- ----------------------------
DROP TABLE IF EXISTS `board`;
CREATE TABLE `board`  (
  `boardId` int NOT NULL AUTO_INCREMENT COMMENT '板块id',
  `boardName` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '板块名称',
  PRIMARY KEY (`boardId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of board
-- ----------------------------
INSERT INTO `board` VALUES (1, '板块1');
INSERT INTO `board` VALUES (2, '板块2');

-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post`  (
  `postId` int NOT NULL COMMENT '帖子编号',
  `title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '帖子标题',
  `content` varchar(8000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '内容',
  `userId` int NULL DEFAULT NULL COMMENT '用户ID',
  `boardId` int NULL DEFAULT NULL COMMENT '板块ID',
  `replyTo` int NULL DEFAULT NULL COMMENT '所回复贴的id',
  PRIMARY KEY (`postId`) USING BTREE,
  INDEX `userId_post`(`userId`) USING BTREE,
  INDEX `boardId_post`(`boardId`) USING BTREE,
  INDEX `replyTo_post`(`replyTo`) USING BTREE,
  CONSTRAINT `boardId_post` FOREIGN KEY (`boardId`) REFERENCES `board` (`boardId`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `replyTo_post` FOREIGN KEY (`replyTo`) REFERENCES `post` (`postId`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `userId_post` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of post
-- ----------------------------
INSERT INTO `post` VALUES (1, '帖子1', '这是用户1发的在版块1的帖1', 1, 1, NULL);
INSERT INTO `post` VALUES (2, '帖子2', '这是用户2发的在版块2的帖2', 2, 2, NULL);
INSERT INTO `post` VALUES (3, '帖子3', '这是用户3回复帖1的在版块1的帖3', 3, 1, 1);
INSERT INTO `post` VALUES (4, '帖子4', '这是用户1回复帖3的在版块1的帖子4', 1, 1, 3);
INSERT INTO `post` VALUES (5, '帖子5', '这是用户2发的在版块1的帖5', 2, 1, NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `userId` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `userName` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名称',
  `passWord` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账户密码',
  `state` int NULL DEFAULT NULL COMMENT '用户状态',
  PRIMARY KEY (`userId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'A', '123456', 0);
INSERT INTO `user` VALUES (2, 'B', '123456', 0);
INSERT INTO `user` VALUES (3, 'C', '123456', 0);
INSERT INTO `user` VALUES (4, 'D', '123456', 1);
INSERT INTO `user` VALUES (5, 'E', '123456', 1);

SET FOREIGN_KEY_CHECKS = 1;
