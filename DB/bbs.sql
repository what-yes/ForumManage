/*
 Navicat Premium Data Transfer

 Source Server         : local_MySQL
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : bbs

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 14/07/2021 09:40:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blacklist
-- ----------------------------
DROP TABLE IF EXISTS `blacklist`;
CREATE TABLE `blacklist`  (
  `userId` int(0) NOT NULL COMMENT '黑名单所属的用户Id',
  `blackUserId` int(0) NOT NULL COMMENT '被列入黑名单的用户Id',
  PRIMARY KEY (`userId`, `blackUserId`) USING BTREE,
  INDEX `blackUserId_blackList`(`blackUserId`) USING BTREE,
  CONSTRAINT `blackUserId_blackList` FOREIGN KEY (`blackUserId`) REFERENCES `user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `userId_blackList` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blacklist
-- ----------------------------
INSERT INTO `blacklist` VALUES (9, 1);
INSERT INTO `blacklist` VALUES (1, 2);

-- ----------------------------
-- Table structure for board
-- ----------------------------
DROP TABLE IF EXISTS `board`;
CREATE TABLE `board`  (
  `boardId` int(0) NOT NULL AUTO_INCREMENT COMMENT '板块id',
  `boardName` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '板块名称',
  `boardMgrId` int(0) NULL DEFAULT NULL COMMENT '版主Id',
  PRIMARY KEY (`boardId`) USING BTREE,
  INDEX `boardMgrId`(`boardMgrId`) USING BTREE,
  CONSTRAINT `boardMgrId` FOREIGN KEY (`boardMgrId`) REFERENCES `user` (`userId`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of board
-- ----------------------------
INSERT INTO `board` VALUES (1, '板块1', 2);
INSERT INTO `board` VALUES (2, '板块2', NULL);

-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post`  (
  `postId` int(0) NOT NULL AUTO_INCREMENT COMMENT '帖子编号',
  `title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '帖子标题',
  `content` varchar(8000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '内容',
  `userId` int(0) NULL DEFAULT NULL COMMENT '用户ID',
  `boardId` int(0) NULL DEFAULT NULL COMMENT '板块ID',
  `replyTo` int(0) NULL DEFAULT NULL COMMENT '所回复贴的id',
  `belongTo` int(0) NULL DEFAULT NULL COMMENT '属于哪个主贴',
  `stick` int(0) NULL DEFAULT NULL COMMENT '是否置顶',
  PRIMARY KEY (`postId`) USING BTREE,
  INDEX `userId_post`(`userId`) USING BTREE,
  INDEX `boardId_post`(`boardId`) USING BTREE,
  INDEX `replyTo_post`(`replyTo`) USING BTREE,
  INDEX `belongTo_post`(`belongTo`) USING BTREE,
  CONSTRAINT `belongTo_post` FOREIGN KEY (`belongTo`) REFERENCES `post` (`postId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `boardId_post` FOREIGN KEY (`boardId`) REFERENCES `board` (`boardId`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `replyTo_post` FOREIGN KEY (`replyTo`) REFERENCES `post` (`postId`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `userId_post` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of post
-- ----------------------------
INSERT INTO `post` VALUES (2, '帖子2', '这是用户2发的在版块2的帖2', 2, 2, NULL, NULL, NULL);
INSERT INTO `post` VALUES (3, '帖子3', '这是用户3在板块1下的主贴1下回复帖1的帖3', 3, 1, NULL, NULL, NULL);
INSERT INTO `post` VALUES (5, '帖子5', '这是用户2发的在版块1的帖5', 2, 1, NULL, NULL, 1);
INSERT INTO `post` VALUES (6, '帖子6', '这是用户3在板块1下的主贴1下回复帖4的帖6', 3, 1, NULL, NULL, 1);
INSERT INTO `post` VALUES (8, '1', '1', 1, 1, 2, 2, 0);
INSERT INTO `post` VALUES (17, '****，+**', '你你你要吗', 8, 1, NULL, NULL, 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `userId` int(0) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `userName` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名称',
  `passWord` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账户密码',
  `state` int(0) NULL DEFAULT NULL COMMENT '用户状态',
  `authority` int(0) NULL DEFAULT NULL COMMENT '用户权限(值越大，权限越大)',
  PRIMARY KEY (`userId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'A', '123456', 0, 1);
INSERT INTO `user` VALUES (2, 'B', '123456', 0, 2);
INSERT INTO `user` VALUES (3, 'C', '123456', 0, 1);
INSERT INTO `user` VALUES (4, 'D', '123456', 0, 1);
INSERT INTO `user` VALUES (5, 'E', '123456', 1, 1);
INSERT INTO `user` VALUES (6, 'h', '123321', 0, 1);
INSERT INTO `user` VALUES (7, 'asd', '123321', 0, 1);
INSERT INTO `user` VALUES (8, 'wrz', '123321', 0, 1);
INSERT INTO `user` VALUES (9, 'admin', 'admin', 0, 3);
INSERT INTO `user` VALUES (10, 'manager', 'manager', 0, 1);
INSERT INTO `user` VALUES (11, 'caoyue', '123321', 0, 1);

SET FOREIGN_KEY_CHECKS = 1;
