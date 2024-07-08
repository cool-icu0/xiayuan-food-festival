/*
 Navicat Premium Data Transfer

 Source Server         : a
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : take_out_admin

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 27/06/2024 13:56:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_employee
-- ----------------------------
DROP TABLE IF EXISTS `tb_employee`;
CREATE TABLE `tb_employee`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '姓名',
  `userAccount` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '用户名',
  `userPassword` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '密码',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '手机号',
  `gender` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '性别',
  `id_number` varchar(18) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '身份证号',
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '状态 0:禁用，1:正常',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人',
  `update_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '修改人',
  `userAvatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户头像',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_username`(`userAccount` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '员工信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_employee
-- ----------------------------
INSERT INTO `tb_employee` VALUES ('1', '管理员', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '13812312312', '1', '110101199001010047', 1, '2021-05-06 17:20:07', '2021-05-10 02:24:09', '1', '1', '');

SET FOREIGN_KEY_CHECKS = 1;
