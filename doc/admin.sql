drop database if exists `take_out_admin`;
create database `take_out_admin`;
use `take_out_admin`;

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `tb_employee`;
CREATE TABLE `tb_employee`
(
    `id`          varchar(32) NOT NULL default '' COMMENT '主键',
    `name`        varchar(32) NOT NULL default '' COMMENT '姓名',
    `username`    varchar(32) NOT NULL default '' COMMENT '用户名',
    `password`    varchar(64) NOT NULL default '' COMMENT '密码',
    `phone`       varchar(11) NOT NULL default '' COMMENT '手机号',
    `gender`      varchar(2)  NOT NULL default '' COMMENT '性别',
    `id_number`   varchar(18) NOT NULL default '' COMMENT '身份证号',
    `status`      int(11)     NOT NULL default '' DEFAULT '0' COMMENT '状态 0:禁用，1:正常',
    `create_time` datetime    NOT NULL COMMENT '创建时间',
    `update_time` datetime    NOT NULL COMMENT '更新时间',
    `create_user` varchar(32) NOT NULL COMMENT '创建人',
    `update_user` varchar(32) NOT NULL COMMENT '修改人',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `idx_username` (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='员工信息';

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `tb_employee`
VALUES ('1', '管理员', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '13812312312', '1', '110101199001010047', '1',
        '2021-05-06 17:20:07', '2021-05-10 02:24:09', '1', '1');
