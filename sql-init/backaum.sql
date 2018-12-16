/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 5.7.23
Source Host           : localhost:3306
Source Database       : backaum

Target Server Type    : MYSQL
Target Server Version : 5.7.23
File Encoding         : 65001

Date: 2018-12-03 19:24:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_data_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_group`;
CREATE TABLE `sys_data_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(256) DEFAULT NULL COMMENT '描述',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级id',
  `name` varchar(256) DEFAULT NULL COMMENT '名称',
  `is_final` int(11) DEFAULT '1' COMMENT '是否可删除',
  `rank` bigint(20) DEFAULT '0' COMMENT '排序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT '0' COMMENT '创建人',
  `update_by` bigint(20) DEFAULT '0' COMMENT '更热人',
  `status` tinyint(4) DEFAULT '1' COMMENT '数据状态,1:正常,2:删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_data_group
-- ----------------------------
INSERT INTO `sys_data_group` VALUES ('1', '极限验证', '0', '极限验证', '2', '0', '2016-10-31 22:08:28', '2016-10-31 22:08:28', '0', '0', '1');
INSERT INTO `sys_data_group` VALUES ('2', '日志输出控制', '0', '日志', '2', '0', '2016-10-31 22:09:38', '2016-10-31 22:09:38', '0', '0', '1');
INSERT INTO `sys_data_group` VALUES ('3', '是否开启Ip拦截', '0', 'IP拦截', '2', '0', '2016-11-05 18:47:22', '2016-11-05 18:47:22', '0', '0', '1');
INSERT INTO `sys_data_group` VALUES ('4', '系统配置信息', '0', '系统', '2', '0', '2016-11-15 17:33:57', '2016-11-15 17:33:57', '0', '0', '1');

-- ----------------------------
-- Table structure for sys_data_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_item`;
CREATE TABLE `sys_data_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sys_data_group_id` bigint(20) DEFAULT NULL COMMENT '组id',
  `key_value` varchar(256) DEFAULT NULL COMMENT '值',
  `key_name` varchar(256) DEFAULT NULL COMMENT '名称',
  `is_final` int(11) DEFAULT '1' COMMENT '是否可删除',
  `rank` bigint(20) DEFAULT '0' COMMENT '排序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT '0' COMMENT '创建人',
  `update_by` bigint(20) DEFAULT '0' COMMENT '更热人',
  `status` tinyint(4) DEFAULT '1' COMMENT '数据状态,1:正常,2:删除,3:禁用账号',
  `description` varchar(256) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_data_item
-- ----------------------------
INSERT INTO `sys_data_item` VALUES ('1', '1', 'c7668adb58225df1a0fa08b7809addd0', 'geetest_id', '2', '0', '2016-10-31 22:08:48', '2018-11-18 12:15:38', '0', '0', '1', 'geetest_id');
INSERT INTO `sys_data_item` VALUES ('2', '1', '520f453387943869a888ee711587a457', 'geetest_key', '2', '0', '2016-10-31 22:09:19', '2018-11-18 12:15:42', '0', '0', '1', 'geetest_key');
INSERT INTO `sys_data_item` VALUES ('3', '2', 'true', 'error_detail', '2', '0', '2016-10-31 22:10:21', '2016-10-31 23:51:13', '0', '0', '1', '是否输出错误日志详情');
INSERT INTO `sys_data_item` VALUES ('4', '3', 'true', 'ip_forbidden', '2', '0', '2016-11-05 18:55:58', '2016-11-15 18:03:13', '0', '0', '1', '是否开启ip拦截');
INSERT INTO `sys_data_item` VALUES ('5', '4', '/backaum', 'basePath', '2', '0', '2016-11-15 17:34:27', '2016-11-15 19:07:37', '0', '0', '1', '系统root路径');

-- ----------------------------
-- Table structure for sys_ip_forbidden
-- ----------------------------
DROP TABLE IF EXISTS `sys_ip_forbidden`;
CREATE TABLE `sys_ip_forbidden` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `is_final` int(11) DEFAULT '1' COMMENT '是否可删除',
  `rank` bigint(20) DEFAULT '0' COMMENT '排序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT '0' COMMENT '创建人',
  `update_by` bigint(20) DEFAULT '0' COMMENT '更热人',
  `status` tinyint(4) DEFAULT '1' COMMENT '数据状态,1:正常,2:删除',
  `expire_time` datetime DEFAULT NULL COMMENT '到期时间',
  `description` varchar(256) DEFAULT NULL COMMENT '说明',
  `ip` varchar(256) DEFAULT NULL COMMENT 'IP地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_ip_forbidden
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `rank` bigint(20) DEFAULT '0' COMMENT '排序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT '0' COMMENT '创建人',
  `update_by` bigint(20) DEFAULT '0' COMMENT '更热人',
  `status` tinyint(4) DEFAULT '1' COMMENT '数据状态,1:正常,2:删除',
  `ip` varchar(256) DEFAULT NULL COMMENT '请求ip',
  `user_id` varchar(256) DEFAULT NULL COMMENT '操作用户id',
  `method` varchar(2048) DEFAULT NULL COMMENT '请求方法',
  `param` text COMMENT '请求参数',
  `result` text COMMENT '请求结果',
  `duration` bigint(20) DEFAULT NULL COMMENT '持续时间',
  `url` varchar(512) DEFAULT NULL COMMENT '请求url',
  `user_agent` varchar(512) DEFAULT NULL COMMENT '请求ua',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3327 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_login_status
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_status`;
CREATE TABLE `sys_login_status` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sys_user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `session_id` varchar(256) DEFAULT NULL COMMENT 'session id',
  `session_expires` datetime DEFAULT NULL COMMENT 'session过期时间',
  `sys_user_login_name` varchar(256) DEFAULT NULL COMMENT '登录名',
  `sys_user_zh_name` varchar(256) DEFAULT NULL COMMENT '中文名',
  `last_login_time` datetime DEFAULT NULL COMMENT '上一次登录时间',
  `platform` tinyint(4) DEFAULT NULL COMMENT '登录平台 1:web 2:android 3:ios',
  `rank` bigint(20) DEFAULT '0' COMMENT '排序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT '0' COMMENT '创建人',
  `update_by` bigint(20) DEFAULT '0' COMMENT '更热人',
  `status` tinyint(4) DEFAULT '1' COMMENT '数据状态,1:正常,2:删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_login_status
-- ----------------------------

-- ----------------------------
-- Table structure for sys_organization
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization`;
CREATE TABLE `sys_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL COMMENT '名称',
  `description` varchar(1024) DEFAULT NULL COMMENT '描述',
  `is_final` int(11) DEFAULT '1' COMMENT '是否可删除',
  `parent_id` bigint(20) DEFAULT '0',
  `rank` bigint(20) DEFAULT '0' COMMENT '排序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `update_by` bigint(20) DEFAULT '0' COMMENT '更新人id',
  `status` tinyint(4) DEFAULT '1' COMMENT '数据状态,1:正常,2:删除',
  `full_name` varchar(256) DEFAULT NULL COMMENT '全称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_organization
-- ----------------------------
INSERT INTO `sys_organization` VALUES ('1', '系统', '系统', '2', '0', '0', '2016-10-29 14:21:13', '2016-10-31 23:51:20', '0', '0', '1', '系统');
INSERT INTO `sys_organization` VALUES ('2', 'xxx集团', 'xxx集团', '2', '1', '0', '2016-10-29 14:21:34', '2016-10-31 23:51:20', '0', '0', '1', 'xxx集团');
INSERT INTO `sys_organization` VALUES ('3', 'xxx深圳分公司', 'xxx深圳分公司', '2', '2', '0', '2016-10-29 14:26:27', '2016-10-31 23:51:21', '0', '0', '1', 'xxx深圳分公司');
INSERT INTO `sys_organization` VALUES ('4', 'xxx长沙分公司', 'xxx长沙分公司', '2', '2', '0', '2016-10-29 14:27:18', '2016-10-31 23:51:22', '0', '0', '1', 'xxx长沙分公司');
INSERT INTO `sys_organization` VALUES ('5', '财务部', 'xxx深圳分公司财务部', '2', '3', '0', '2016-10-29 14:27:42', '2016-10-31 23:51:23', '0', '0', '1', 'xxx深圳分公司财务部');
INSERT INTO `sys_organization` VALUES ('6', '研发部', 'xxx深圳分公司研发部', '2', '3', '0', '2016-10-29 14:28:38', '2016-10-31 23:51:24', '0', '0', '1', 'xxx深圳分公司研发部');
INSERT INTO `sys_organization` VALUES ('7', '市场部', 'xxx深圳分公司市场部', '2', '3', '0', '2016-10-29 14:28:49', '2016-10-31 23:51:24', '0', '0', '1', 'xxx深圳分公司市场部');
INSERT INTO `sys_organization` VALUES ('8', 'xxx福州分公司', 'xxx福州分公司', '1', '2', '0', '2018-12-03 16:37:58', '2018-12-03 16:37:58', '0', '0', '0', 'xxx福州分公司');
INSERT INTO `sys_organization` VALUES ('10', '财务部', 'xxx长沙分公司财务部', '1', '4', '0', '2018-12-03 18:01:36', '2018-12-03 19:21:08', '0', '0', '0', 'xxx长沙分公司财务部');

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL COMMENT '名称',
  `description` varchar(256) DEFAULT NULL COMMENT '描述',
  `code` varchar(256) DEFAULT NULL COMMENT '编码',
  `sys_permission_group_id` bigint(20) DEFAULT NULL COMMENT '分组id',
  `is_final` int(11) DEFAULT '1' COMMENT '是否可删除',
  `rank` bigint(20) DEFAULT '0' COMMENT '排序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `update_by` bigint(20) DEFAULT '0' COMMENT '更新人id',
  `status` tinyint(4) DEFAULT '1' COMMENT '数据状态,1:正常,2:删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', '新增', '新增用户', 'user:insert', '1', '2', '0', '2016-10-29 11:45:09', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('2', '删除', '删除用户', 'user:delete', '1', '2', '0', '2016-10-29 11:46:08', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('3', '更新', '更新用户', 'user:update', '1', '2', '0', '2016-10-29 11:46:44', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('4', '查询', '查询用户', 'user:select', '1', '2', '0', '2016-10-29 11:46:57', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('5', '列表', '查询用户列表', 'user:list', '1', '2', '0', '2016-10-29 11:47:20', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('6', '新增', '新增权限', 'permission:insert', '2', '2', '0', '2016-10-29 11:45:09', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('7', '删除', '删除权限', 'permission:delete', '2', '2', '0', '2016-10-29 11:46:08', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('8', '更新', '更新权限', 'permission:update', '2', '2', '0', '2016-10-29 11:46:44', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('9', '查询', '查询权限', 'permission:select', '2', '2', '0', '2016-10-29 11:46:57', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('10', '列表', '查询权限列表', 'permission:list', '2', '2', '0', '2016-10-29 11:47:20', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('11', '新增', '新增角色', 'role:insert', '3', '2', '0', '2016-10-29 11:45:09', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('12', '删除', '删除角色', 'role:delete', '3', '2', '0', '2016-10-29 11:46:08', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('13', '更新', '更新角色', 'role:update', '3', '2', '0', '2016-10-29 11:46:44', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('14', '查询', '查询角色', 'role:select', '3', '2', '0', '2016-10-29 11:46:57', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('15', '列表', '查询角色列表', 'role:list', '3', '2', '0', '2016-10-29 11:47:20', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('16', '新增', '新增组织机构', 'organization:insert', '4', '2', '0', '2016-10-29 11:45:09', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('17', '删除', '删除组织机构', 'organization:delete', '4', '2', '0', '2016-10-29 11:46:08', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('18', '更新', '更新组织机构', 'organization:update', '4', '2', '0', '2016-10-29 11:46:44', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('19', '查询', '查询组织机构', 'organization:select', '4', '2', '0', '2016-10-29 11:46:57', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('20', '列表', '查询组织机构列表', 'organization:list', '4', '2', '0', '2016-10-29 11:47:20', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('21', '新增', '新增职位', 'job:insert', '5', '2', '0', '2016-10-29 11:45:09', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('22', '删除', '删除职位', 'job:delete', '5', '2', '0', '2016-10-29 11:46:08', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('23', '更新', '更新职位', 'job:update', '5', '2', '0', '2016-10-29 11:46:44', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('24', '查询', '查询职位', 'job:select', '5', '2', '0', '2016-10-29 11:46:57', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('25', '列表', '查询职位列表', 'job:list', '5', '2', '0', '2016-10-29 11:47:20', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('26', '新增', '新增字典', 'data:insert', '6', '2', '0', '2016-10-29 11:45:09', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('27', '删除', '删除字典', 'data:delete', '6', '2', '0', '2016-10-29 11:46:08', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('28', '更新', '更新字典', 'data:update', '6', '2', '0', '2016-10-29 11:46:44', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('29', '查询', '查询字典', 'data:select', '6', '2', '0', '2016-10-29 11:46:57', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('30', '列表', '查询字典列表', 'data:list', '6', '2', '0', '2016-10-29 11:47:20', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('31', '启用', '启用用户', 'user:enable', '1', '2', '0', '2016-10-29 11:58:15', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('32', '禁用', '禁用用户', 'user:forbidden', '1', '2', '0', '2016-10-29 11:58:45', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('33', '密码', '修改密码', 'user:updatePassword', '1', '2', '0', '2016-10-29 12:00:01', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('34', '查看数据库监控', '查看数据库监控', 'db:select', '7', '2', '0', '2016-10-31 21:31:34', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('35', '下线', '下线用户', 'user:loginout', '1', '2', '0', '2016-10-31 21:32:45', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('36', '用户在线列表', '用户在线列表', 'user:loginStatu:list', '1', '2', '0', '2016-10-31 21:33:26', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('37', '新建权限组', '新建权限组', 'permission:group:insert', '2', '2', '0', '2016-10-31 21:34:12', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('38', '权限组列表', '权限组列表', 'permission:group:list', '2', '2', '0', '2016-10-31 21:35:10', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('39', '新增', '新增IP', 'ip:insert', '8', '2', '0', '2016-10-31 21:44:18', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('40', '更新', '更新ip', 'ip:update', '8', '2', '0', '2016-10-31 21:44:44', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('41', '删除', '删除ip', 'ip:delete', '8', '2', '0', '2016-10-31 21:44:59', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('42', '查看', '查看ip', 'ip:select', '8', '2', '0', '2016-10-31 21:45:14', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('43', '列表', 'ip列表', 'ip:list', '8', '2', '0', '2016-10-31 21:45:37', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('44', '字典组列表', '字典组列表', 'data:group:list', '6', '2', '0', '2016-10-31 22:25:32', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('45', '新增字典组', '新增字典组', 'data:group:insert', '6', '2', '0', '2016-10-31 22:26:24', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('46', '列表', '列表', 'log:list', '9', '2', '0', '2016-10-31 23:48:43', '2016-10-31 23:51:47', '0', '0', '1');
INSERT INTO `sys_permission` VALUES ('47', '引导', '引导界面', 'system:index', '10', '1', '0', '2016-11-01 22:45:40', '2016-11-01 22:45:40', '0', '0', '1');

-- ----------------------------
-- Table structure for sys_permission_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission_group`;
CREATE TABLE `sys_permission_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL COMMENT '名称',
  `description` varchar(256) DEFAULT NULL COMMENT '描述',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父级id',
  `is_final` int(11) DEFAULT '1' COMMENT '是否可删除',
  `rank` bigint(20) DEFAULT '0' COMMENT '排序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `update_by` bigint(20) DEFAULT '0' COMMENT '更新人id',
  `status` tinyint(4) DEFAULT '1' COMMENT '数据状态,1:正常,2:删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_permission_group
-- ----------------------------
INSERT INTO `sys_permission_group` VALUES ('1', '用户管理', '用户数据增删改查', '0', '2', '0', '2016-10-29 11:43:55', '2016-10-29 11:43:55', '0', '0', '1');
INSERT INTO `sys_permission_group` VALUES ('2', '权限管理', '权限数据增删改查', '0', '2', '0', '2016-10-29 11:43:55', '2016-10-29 11:43:55', '0', '0', '1');
INSERT INTO `sys_permission_group` VALUES ('3', '角色管理', '角色数据增删改查', '0', '2', '0', '2016-10-29 11:43:55', '2016-10-29 11:43:55', '0', '0', '1');
INSERT INTO `sys_permission_group` VALUES ('4', '组织机构管理', '组织机构数据增删改查', '0', '2', '0', '2016-10-29 11:43:55', '2016-10-29 11:43:55', '0', '0', '1');
INSERT INTO `sys_permission_group` VALUES ('5', '职位管理', '职位数据增删改查', '0', '2', '0', '2016-10-29 11:43:55', '2016-10-29 11:43:55', '0', '0', '1');
INSERT INTO `sys_permission_group` VALUES ('6', '字典管理', '字典数据增删改查', '0', '2', '0', '2016-10-29 11:43:55', '2016-10-29 11:43:55', '0', '0', '1');
INSERT INTO `sys_permission_group` VALUES ('7', '数据库', '数据库监控', '0', '2', '0', '2016-10-31 21:31:01', '2016-10-31 21:31:01', '0', '0', '1');
INSERT INTO `sys_permission_group` VALUES ('8', '安全', '安全', '0', '2', '0', '2016-10-31 21:43:47', '2016-10-31 21:43:47', '0', '0', '1');
INSERT INTO `sys_permission_group` VALUES ('9', '日志', '日志', '0', '2', '0', '2016-10-31 23:47:47', '2016-10-31 23:47:47', '0', '0', '1');
INSERT INTO `sys_permission_group` VALUES ('10', '其他', '其他', '0', '2', '0', '2016-11-01 22:44:42', '2016-11-01 22:44:42', '0', '0', '1');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(1024) DEFAULT NULL,
  `name` varchar(256) DEFAULT NULL,
  `rank` bigint(20) DEFAULT '0' COMMENT '排序',
  `is_final` int(11) DEFAULT '1' COMMENT '是否可删除',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `update_by` bigint(20) DEFAULT '0' COMMENT '更新人id',
  `status` tinyint(4) DEFAULT '1' COMMENT '数据状态,1:正常,2:删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', '超级管理员', '0', '2', '2016-10-29 14:18:43', '2016-10-31 23:51:57', '0', '0', '1');
INSERT INTO `sys_role` VALUES ('2', '管理员', '管理员', '0', '1', '2016-10-29 14:19:05', '2016-11-01 23:11:39', '0', '0', '1');
INSERT INTO `sys_role` VALUES ('3', '分公司总经理', '分公司总经理', '0', '2', '2016-10-29 14:36:24', '2016-10-31 23:51:58', '0', '0', '1');
INSERT INTO `sys_role` VALUES ('4', '分公司部门经理', '分公司部门经理', '0', '2', '2016-10-29 14:37:46', '2016-10-31 23:51:59', '0', '0', '1');
INSERT INTO `sys_role` VALUES ('5', '集团总经理', '集团总经理', '0', '2', '2016-10-29 14:59:17', '2016-10-31 23:51:59', '0', '0', '1');

-- ----------------------------
-- Table structure for sys_role_organization
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_organization`;
CREATE TABLE `sys_role_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sys_organization_id` bigint(20) DEFAULT NULL COMMENT '组织id',
  `sys_role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级id',
  `name` varchar(256) DEFAULT NULL,
  `full_name` varchar(256) DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  `rank` bigint(20) DEFAULT '0' COMMENT '排序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `update_by` bigint(20) DEFAULT '0' COMMENT '更新人id',
  `status` tinyint(4) DEFAULT '1' COMMENT '数据状态,1:正常,2:删除',
  `is_final` tinyint(4) DEFAULT NULL COMMENT '是否能修改',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_organization
-- ----------------------------
INSERT INTO `sys_role_organization` VALUES ('1', '0', '0', '0', '系统', '系统', '系统', '0', '2016-10-29 14:53:47', '2016-10-31 23:52:09', '0', '0', '1', '2');
INSERT INTO `sys_role_organization` VALUES ('2', '2', '1', '1', '系统超级管理员', '系统超级管理员', '系统超级管理员', '0', '2016-10-29 14:54:22', '2016-10-31 23:52:09', '0', '0', '1', '2');
INSERT INTO `sys_role_organization` VALUES ('3', '2', '5', '1', '集团总经理', '集团总经理', '集团总经理', '0', '2016-10-29 15:01:06', '2016-10-31 23:52:10', '0', '0', '1', '2');
INSERT INTO `sys_role_organization` VALUES ('4', '3', '3', '3', '深圳分公司总经理', 'xxx深圳分公司总经理', 'xxx深圳分公司总经理', '0', '2016-10-29 15:01:47', '2016-10-31 23:52:11', '0', '0', '1', '2');
INSERT INTO `sys_role_organization` VALUES ('5', '6', '4', '4', 'xxx深圳分公司研发部经理', 'xxx深圳分公司研发部经理', 'xxx深圳分公司研发部经理', '0', '2016-10-29 15:02:56', '2016-10-31 23:52:11', '0', '0', '1', '2');
INSERT INTO `sys_role_organization` VALUES ('6', '2', '2', '2', '系统管理员', '系统管理员', '系统管理员', '0', '2016-10-29 15:07:01', '2016-10-31 23:52:12', '0', '0', '1', '2');
INSERT INTO `sys_role_organization` VALUES ('7', '4', '3', '3', 'xxx长沙分公司', 'xxxxxx长沙分公司总经理', 'xxxxxx长沙分公司总经理', '0', '2018-12-03 16:32:53', '2018-12-03 16:32:53', '0', '0', '0', '1');
INSERT INTO `sys_role_organization` VALUES ('8', '3', '3', '3', '福州分公司总经理', 'xxx福州分公司总经理', 'xxx福州分公司总经理', '0', '2018-12-03 16:37:07', '2018-12-03 16:37:07', '0', '0', '0', '1');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sys_permission_id` bigint(20) DEFAULT NULL COMMENT '权限id',
  `sys_role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  `rank` bigint(20) DEFAULT '0' COMMENT '排序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `update_by` bigint(20) DEFAULT '0' COMMENT '更新人id',
  `status` tinyint(4) DEFAULT '1' COMMENT '数据状态,1:正常,2:删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=228 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('1', '1', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('2', '2', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('3', '3', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('4', '4', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('5', '5', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('6', '31', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('7', '32', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('8', '33', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('9', '6', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('10', '7', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('11', '8', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('12', '9', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('13', '10', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('14', '11', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('15', '12', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('16', '13', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('17', '14', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('18', '15', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('19', '16', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('20', '17', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('21', '18', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('22', '19', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('23', '20', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('24', '21', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('25', '22', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('26', '23', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('27', '24', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('28', '25', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('29', '26', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('30', '27', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('31', '28', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('32', '29', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('33', '30', '1', '0', '2016-10-29 14:18:43', '2016-10-31 23:49:03', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('34', '1', '2', '0', '2016-10-29 14:19:05', '2016-10-31 23:10:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('35', '3', '2', '0', '2016-10-29 14:19:05', '2016-10-31 23:10:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('36', '4', '2', '0', '2016-10-29 14:19:05', '2016-10-31 23:10:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('37', '5', '2', '0', '2016-10-29 14:19:05', '2016-10-31 23:10:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('38', '31', '2', '0', '2016-10-29 14:19:05', '2016-10-31 23:10:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('39', '32', '2', '0', '2016-10-29 14:19:05', '2016-10-31 23:10:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('40', '33', '2', '0', '2016-10-29 14:19:05', '2016-10-31 23:10:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('41', '6', '2', '0', '2016-10-29 14:19:05', '2016-10-31 23:10:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('42', '8', '2', '0', '2016-10-29 14:19:05', '2016-10-31 23:10:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('43', '9', '2', '0', '2016-10-29 14:19:05', '2016-10-31 23:10:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('44', '10', '2', '0', '2016-10-29 14:19:05', '2016-10-31 23:10:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('45', '11', '2', '0', '2016-10-29 14:19:05', '2016-10-31 23:10:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('46', '13', '2', '0', '2016-10-29 14:19:05', '2016-10-31 23:10:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('47', '14', '2', '0', '2016-10-29 14:19:05', '2016-10-31 23:10:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('48', '15', '2', '0', '2016-10-29 14:19:05', '2016-10-31 23:10:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('49', '16', '2', '0', '2016-10-29 14:19:05', '2016-10-31 23:10:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('50', '18', '2', '0', '2016-10-29 14:19:05', '2016-10-31 23:10:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('51', '19', '2', '0', '2016-10-29 14:19:05', '2016-10-31 23:10:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('52', '20', '2', '0', '2016-10-29 14:19:05', '2016-10-31 23:10:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('53', '21', '2', '0', '2016-10-29 14:19:05', '2016-10-31 23:10:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('54', '23', '2', '0', '2016-10-29 14:19:05', '2016-10-31 23:10:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('55', '24', '2', '0', '2016-10-29 14:19:05', '2016-10-31 23:10:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('56', '25', '2', '0', '2016-10-29 14:19:05', '2016-10-31 23:10:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('57', '26', '2', '0', '2016-10-29 14:19:05', '2016-10-31 23:10:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('58', '28', '2', '0', '2016-10-29 14:19:05', '2016-10-31 23:10:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('59', '29', '2', '0', '2016-10-29 14:19:05', '2016-10-31 23:10:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('60', '30', '2', '0', '2016-10-29 14:19:05', '2016-10-31 23:10:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('61', '5', '3', '0', '2016-10-29 14:36:24', '2016-10-29 14:37:16', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('62', '9', '3', '0', '2016-10-29 14:36:24', '2016-10-29 14:37:16', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('63', '10', '3', '0', '2016-10-29 14:36:24', '2016-10-29 14:37:16', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('64', '14', '3', '0', '2016-10-29 14:36:24', '2016-10-29 14:37:16', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('65', '15', '3', '0', '2016-10-29 14:36:24', '2016-10-29 14:37:16', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('66', '19', '3', '0', '2016-10-29 14:36:24', '2016-10-29 14:37:16', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('67', '20', '3', '0', '2016-10-29 14:36:24', '2016-10-29 14:37:16', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('68', '24', '3', '0', '2016-10-29 14:36:24', '2016-10-29 14:37:16', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('69', '25', '3', '0', '2016-10-29 14:36:24', '2016-10-29 14:37:16', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('70', '29', '3', '0', '2016-10-29 14:36:24', '2016-10-29 14:37:16', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('71', '30', '3', '0', '2016-10-29 14:36:24', '2016-10-29 14:37:16', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('72', '4', '3', '0', '2016-10-29 14:36:24', '2016-10-29 14:37:16', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('73', '5', '3', '0', '2016-10-29 14:37:16', '2016-10-29 14:37:16', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('74', '9', '3', '0', '2016-10-29 14:37:16', '2016-10-29 14:37:16', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('75', '10', '3', '0', '2016-10-29 14:37:16', '2016-10-29 14:37:16', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('76', '14', '3', '0', '2016-10-29 14:37:16', '2016-10-29 14:37:16', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('77', '15', '3', '0', '2016-10-29 14:37:16', '2016-10-29 14:37:16', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('78', '19', '3', '0', '2016-10-29 14:37:16', '2016-10-29 14:37:16', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('79', '20', '3', '0', '2016-10-29 14:37:16', '2016-10-29 14:37:16', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('80', '24', '3', '0', '2016-10-29 14:37:16', '2016-10-29 14:37:16', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('81', '25', '3', '0', '2016-10-29 14:37:16', '2016-10-29 14:37:16', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('82', '29', '3', '0', '2016-10-29 14:37:16', '2016-10-29 14:37:16', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('83', '30', '3', '0', '2016-10-29 14:37:16', '2016-10-29 14:37:16', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('84', '4', '3', '0', '2016-10-29 14:37:16', '2016-10-29 14:37:16', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('85', '25', '4', '0', '2016-10-29 14:37:46', '2016-10-29 14:37:46', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('86', '24', '4', '0', '2016-10-29 14:37:46', '2016-10-29 14:37:46', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('87', '4', '4', '0', '2016-10-29 14:37:46', '2016-10-29 14:37:46', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('88', '5', '4', '0', '2016-10-29 14:37:46', '2016-10-29 14:37:46', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('89', '9', '4', '0', '2016-10-29 14:37:46', '2016-10-29 14:37:46', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('90', '10', '4', '0', '2016-10-29 14:37:46', '2016-10-29 14:37:46', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('91', '14', '4', '0', '2016-10-29 14:37:46', '2016-10-29 14:37:46', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('92', '15', '4', '0', '2016-10-29 14:37:46', '2016-10-29 14:37:46', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('93', '19', '4', '0', '2016-10-29 14:37:46', '2016-10-29 14:37:46', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('94', '20', '4', '0', '2016-10-29 14:37:46', '2016-10-29 14:37:46', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('95', '29', '4', '0', '2016-10-29 14:37:46', '2016-10-29 14:37:46', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('96', '30', '4', '0', '2016-10-29 14:37:46', '2016-10-29 14:37:46', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('97', '4', '5', '0', '2016-10-29 14:59:17', '2016-10-29 14:59:17', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('98', '5', '5', '0', '2016-10-29 14:59:17', '2016-10-29 14:59:17', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('99', '9', '5', '0', '2016-10-29 14:59:17', '2016-10-29 14:59:17', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('100', '10', '5', '0', '2016-10-29 14:59:17', '2016-10-29 14:59:17', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('101', '14', '5', '0', '2016-10-29 14:59:17', '2016-10-29 14:59:17', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('102', '15', '5', '0', '2016-10-29 14:59:17', '2016-10-29 14:59:17', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('103', '20', '5', '0', '2016-10-29 14:59:17', '2016-10-29 14:59:17', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('104', '19', '5', '0', '2016-10-29 14:59:17', '2016-10-29 14:59:17', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('105', '24', '5', '0', '2016-10-29 14:59:17', '2016-10-29 14:59:17', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('106', '25', '5', '0', '2016-10-29 14:59:17', '2016-10-29 14:59:17', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('107', '29', '5', '0', '2016-10-29 14:59:17', '2016-10-29 14:59:17', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('108', '30', '5', '0', '2016-10-29 14:59:17', '2016-10-29 14:59:17', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('109', '5', '2', '0', '2016-10-31 23:10:32', '2016-10-31 23:31:17', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('110', '5', '2', '0', '2016-10-31 23:31:17', '2016-10-31 23:33:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('111', '10', '2', '0', '2016-10-31 23:31:17', '2016-10-31 23:33:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('112', '15', '2', '0', '2016-10-31 23:31:17', '2016-10-31 23:33:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('113', '20', '2', '0', '2016-10-31 23:31:17', '2016-10-31 23:33:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('114', '25', '2', '0', '2016-10-31 23:31:17', '2016-10-31 23:33:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('115', '30', '2', '0', '2016-10-31 23:31:17', '2016-10-31 23:33:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('116', '43', '2', '0', '2016-10-31 23:31:17', '2016-10-31 23:33:32', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('117', '5', '2', '0', '2016-10-31 23:33:32', '2016-10-31 23:44:42', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('118', '10', '2', '0', '2016-10-31 23:33:32', '2016-10-31 23:44:42', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('119', '15', '2', '0', '2016-10-31 23:33:32', '2016-10-31 23:44:42', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('120', '20', '2', '0', '2016-10-31 23:33:32', '2016-10-31 23:44:42', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('121', '25', '2', '0', '2016-10-31 23:33:32', '2016-10-31 23:44:42', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('122', '30', '2', '0', '2016-10-31 23:33:32', '2016-10-31 23:44:42', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('123', '43', '2', '0', '2016-10-31 23:33:32', '2016-10-31 23:44:42', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('124', '38', '2', '0', '2016-10-31 23:33:32', '2016-10-31 23:44:42', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('125', '44', '2', '0', '2016-10-31 23:33:32', '2016-10-31 23:44:42', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('126', '5', '2', '0', '2016-10-31 23:44:42', '2016-10-31 23:44:54', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('127', '10', '2', '0', '2016-10-31 23:44:42', '2016-10-31 23:44:54', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('128', '15', '2', '0', '2016-10-31 23:44:42', '2016-10-31 23:44:54', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('129', '20', '2', '0', '2016-10-31 23:44:42', '2016-10-31 23:44:54', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('130', '25', '2', '0', '2016-10-31 23:44:42', '2016-10-31 23:44:54', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('131', '30', '2', '0', '2016-10-31 23:44:42', '2016-10-31 23:44:54', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('132', '43', '2', '0', '2016-10-31 23:44:42', '2016-10-31 23:44:54', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('133', '38', '2', '0', '2016-10-31 23:44:42', '2016-10-31 23:44:54', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('134', '44', '2', '0', '2016-10-31 23:44:42', '2016-10-31 23:44:54', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('135', '36', '2', '0', '2016-10-31 23:44:42', '2016-10-31 23:44:54', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('136', '5', '2', '0', '2016-10-31 23:44:54', '2016-10-31 23:50:17', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('137', '10', '2', '0', '2016-10-31 23:44:54', '2016-10-31 23:50:17', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('138', '15', '2', '0', '2016-10-31 23:44:54', '2016-10-31 23:50:17', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('139', '20', '2', '0', '2016-10-31 23:44:54', '2016-10-31 23:50:17', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('140', '25', '2', '0', '2016-10-31 23:44:54', '2016-10-31 23:50:17', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('141', '30', '2', '0', '2016-10-31 23:44:54', '2016-10-31 23:50:17', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('142', '43', '2', '0', '2016-10-31 23:44:54', '2016-10-31 23:50:17', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('143', '38', '2', '0', '2016-10-31 23:44:54', '2016-10-31 23:50:17', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('144', '44', '2', '0', '2016-10-31 23:44:54', '2016-10-31 23:50:17', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('145', '36', '2', '0', '2016-10-31 23:44:54', '2016-10-31 23:50:17', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('146', '34', '2', '0', '2016-10-31 23:44:54', '2016-10-31 23:50:17', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('147', '1', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('148', '2', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('149', '3', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('150', '4', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('151', '5', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('152', '31', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('153', '32', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('154', '33', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('155', '6', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('156', '7', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('157', '8', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('158', '9', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('159', '10', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('160', '11', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('161', '12', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('162', '13', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('163', '14', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('164', '15', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('165', '16', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('166', '17', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('167', '18', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('168', '19', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('169', '20', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('170', '21', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('171', '22', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('172', '23', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('173', '24', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('174', '25', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('175', '26', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('176', '27', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('177', '28', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('178', '29', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('179', '30', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('180', '35', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('181', '36', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('182', '37', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('183', '38', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('184', '44', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('185', '45', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('186', '34', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('187', '39', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('188', '40', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('189', '41', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('190', '42', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('191', '43', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('192', '46', '1', '0', '2016-10-31 23:49:03', '2016-10-31 23:49:03', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('193', '5', '2', '0', '2016-10-31 23:50:17', '2016-11-01 23:12:01', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('194', '10', '2', '0', '2016-10-31 23:50:17', '2016-11-01 23:12:01', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('195', '15', '2', '0', '2016-10-31 23:50:17', '2016-11-01 23:12:01', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('196', '20', '2', '0', '2016-10-31 23:50:17', '2016-11-01 23:12:01', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('197', '25', '2', '0', '2016-10-31 23:50:17', '2016-11-01 23:12:01', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('198', '30', '2', '0', '2016-10-31 23:50:17', '2016-11-01 23:12:01', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('199', '43', '2', '0', '2016-10-31 23:50:17', '2016-11-01 23:12:01', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('200', '38', '2', '0', '2016-10-31 23:50:17', '2016-11-01 23:12:01', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('201', '44', '2', '0', '2016-10-31 23:50:17', '2016-11-01 23:12:01', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('202', '36', '2', '0', '2016-10-31 23:50:17', '2016-11-01 23:12:01', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('203', '34', '2', '0', '2016-10-31 23:50:17', '2016-11-01 23:12:01', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('204', '46', '2', '0', '2016-10-31 23:50:17', '2016-11-01 23:12:01', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('205', '5', '2', '0', '2016-11-01 23:12:01', '2016-11-01 23:13:37', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('206', '10', '2', '0', '2016-11-01 23:12:01', '2016-11-01 23:13:37', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('207', '15', '2', '0', '2016-11-01 23:12:01', '2016-11-01 23:13:37', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('208', '20', '2', '0', '2016-11-01 23:12:01', '2016-11-01 23:13:37', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('209', '25', '2', '0', '2016-11-01 23:12:01', '2016-11-01 23:13:37', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('210', '30', '2', '0', '2016-11-01 23:12:01', '2016-11-01 23:13:37', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('211', '38', '2', '0', '2016-11-01 23:12:01', '2016-11-01 23:13:37', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('212', '44', '2', '0', '2016-11-01 23:12:01', '2016-11-01 23:13:37', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('213', '36', '2', '0', '2016-11-01 23:12:01', '2016-11-01 23:13:37', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('214', '34', '2', '0', '2016-11-01 23:12:01', '2016-11-01 23:13:37', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('215', '46', '2', '0', '2016-11-01 23:12:01', '2016-11-01 23:13:37', '0', '0', '2');
INSERT INTO `sys_role_permission` VALUES ('216', '5', '2', '0', '2016-11-01 23:13:37', '2016-11-01 23:13:37', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('217', '10', '2', '0', '2016-11-01 23:13:37', '2016-11-01 23:13:37', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('218', '15', '2', '0', '2016-11-01 23:13:37', '2016-11-01 23:13:37', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('219', '20', '2', '0', '2016-11-01 23:13:37', '2016-11-01 23:13:37', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('220', '25', '2', '0', '2016-11-01 23:13:37', '2016-11-01 23:13:37', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('221', '30', '2', '0', '2016-11-01 23:13:37', '2016-11-01 23:13:37', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('222', '38', '2', '0', '2016-11-01 23:13:37', '2016-11-01 23:13:37', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('223', '44', '2', '0', '2016-11-01 23:13:37', '2016-11-01 23:13:37', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('224', '36', '2', '0', '2016-11-01 23:13:37', '2016-11-01 23:13:37', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('225', '34', '2', '0', '2016-11-01 23:13:37', '2016-11-01 23:13:37', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('226', '46', '2', '0', '2016-11-01 23:13:37', '2016-11-01 23:13:37', '0', '0', '1');
INSERT INTO `sys_role_permission` VALUES ('227', '43', '2', '0', '2016-11-01 23:13:37', '2016-11-01 23:13:37', '0', '0', '1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(256) DEFAULT NULL COMMENT '登陆名',
  `zh_name` varchar(256) DEFAULT NULL COMMENT '中文名',
  `en_name` varchar(256) DEFAULT NULL COMMENT '英文名',
  `sex` int(11) DEFAULT '0' COMMENT '性别',
  `birth` varchar(256) DEFAULT NULL COMMENT '生日',
  `email` varchar(256) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(256) DEFAULT NULL COMMENT '电话',
  `address` varchar(1024) DEFAULT NULL COMMENT '地址',
  `password` varchar(256) DEFAULT NULL COMMENT '密码',
  `password_salt` varchar(256) DEFAULT NULL COMMENT '密码盐',
  `rank` bigint(20) DEFAULT '0' COMMENT '排序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT '0' COMMENT '创建人',
  `update_by` bigint(20) DEFAULT '0' COMMENT '更热人',
  `status` tinyint(4) DEFAULT '1' COMMENT '数据状态,1:正常,2:删除,3:禁用账号',
  `is_final` tinyint(4) DEFAULT NULL COMMENT '是否能修改',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', 'admin', 'admin', '1', '2018-11-01', 'xx@163.com', '13888888888', '海外', 'f893d078cee0c79c90e8747e1df8f54b', '0e1e5f9114dc4d60a7ea9e13c60bdff8', '0', '2018-11-19 15:06:05', '2018-11-19 17:36:37', '0', '0', '1', '1');
INSERT INTO `sys_user` VALUES ('2', 'admin2', 'admin2管理员', 'admin2', '1', null, null, null, null, '2f38e9c59827c9d457a2d82d38ba4e37', 'b80407eb849f4cd88c89c3e59f97b089', '0', '2018-11-19 15:07:39', '2018-11-19 23:53:30', '0', '0', '1', '2');
INSERT INTO `sys_user` VALUES ('3', 'guest', '客人', 'guest', '1', '2017-11-07', 'xxx@163.com', '', '', '322be265e8bc987aaeec9f54efd86c2f', 'a4147c6ee41647be962d5e3c7578ec61', null, null, '2018-12-03 16:09:14', null, null, '1', '1');

-- ----------------------------
-- Table structure for sys_user_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_permission`;
CREATE TABLE `sys_user_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sys_user_id` bigint(20) DEFAULT NULL,
  `sys_permission_id` bigint(20) DEFAULT NULL,
  `is_final` int(11) DEFAULT '1',
  `rank` bigint(20) DEFAULT '0' COMMENT '排序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `update_by` bigint(20) DEFAULT '0' COMMENT '更新人id',
  `status` tinyint(4) DEFAULT '1' COMMENT '数据状态,1:正常,2:删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=179 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_permission
-- ----------------------------
INSERT INTO `sys_user_permission` VALUES ('1', '1', '1', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('2', '1', '2', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('3', '1', '3', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('4', '1', '4', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('5', '1', '5', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('6', '1', '31', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('7', '1', '32', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('8', '1', '33', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('9', '1', '6', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('10', '1', '7', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('11', '1', '8', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('12', '1', '9', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('13', '1', '10', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('14', '1', '11', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('15', '1', '12', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('16', '1', '13', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('17', '1', '14', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('18', '1', '15', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('19', '1', '16', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('20', '1', '17', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('21', '1', '18', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('22', '1', '19', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('23', '1', '20', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('24', '1', '21', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('25', '1', '22', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('26', '1', '23', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('27', '1', '24', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('28', '1', '25', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('29', '1', '26', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('30', '1', '27', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('31', '1', '28', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('32', '1', '29', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('33', '1', '30', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('34', '2', '1', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('35', '2', '2', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('36', '2', '3', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('37', '2', '4', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('38', '2', '5', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('39', '2', '31', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('40', '2', '32', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('41', '2', '33', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('42', '2', '6', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('43', '2', '7', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('44', '2', '8', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('45', '2', '9', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('46', '2', '10', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('47', '2', '11', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('48', '2', '12', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('49', '2', '13', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('50', '2', '14', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('51', '2', '15', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('52', '2', '16', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('53', '2', '17', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('54', '2', '18', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('55', '2', '19', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('56', '2', '20', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('57', '2', '21', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('58', '2', '22', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('59', '2', '23', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('60', '2', '24', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('61', '2', '25', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('62', '2', '26', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('63', '2', '27', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('64', '2', '28', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('65', '2', '29', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('66', '2', '30', '2', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('67', '1', '1', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('68', '1', '2', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('69', '1', '3', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('70', '1', '4', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('71', '1', '5', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('72', '1', '31', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('73', '1', '32', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('74', '1', '33', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('75', '1', '6', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('76', '1', '7', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('77', '1', '8', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('78', '1', '9', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('79', '1', '10', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('80', '1', '11', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('81', '1', '12', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('82', '1', '13', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('83', '1', '14', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('84', '1', '15', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('85', '1', '16', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('86', '1', '17', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('87', '1', '18', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('88', '1', '19', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('89', '1', '20', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('90', '1', '21', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('91', '1', '22', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('92', '1', '23', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('93', '1', '24', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('94', '1', '25', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('95', '1', '26', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('96', '1', '27', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('97', '1', '28', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('98', '1', '29', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('99', '1', '30', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('100', '1', '35', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('101', '1', '36', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('102', '1', '37', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('103', '1', '38', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('104', '1', '44', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('105', '1', '45', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('106', '1', '34', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('107', '1', '39', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('108', '1', '40', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('109', '1', '41', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('110', '1', '42', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('111', '1', '43', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:48', '0', '0', '2');
INSERT INTO `sys_user_permission` VALUES ('112', '1', '1', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('113', '1', '2', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('114', '1', '3', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('115', '1', '4', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('116', '1', '5', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('117', '1', '31', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('118', '1', '32', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('119', '1', '33', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('120', '1', '35', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('121', '1', '36', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('122', '1', '6', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('123', '1', '7', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('124', '1', '8', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('125', '1', '9', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('126', '1', '10', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('127', '1', '37', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('128', '1', '38', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('129', '1', '11', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('130', '1', '12', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('131', '1', '13', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('132', '1', '14', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('133', '1', '15', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('134', '1', '16', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('135', '1', '17', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('136', '1', '18', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('137', '1', '19', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('138', '1', '20', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('139', '1', '21', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('140', '1', '22', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('141', '1', '23', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('142', '1', '24', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('143', '1', '25', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('144', '1', '26', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('145', '1', '27', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('146', '1', '28', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('147', '1', '29', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('148', '1', '30', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('149', '1', '44', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('150', '1', '45', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('151', '1', '34', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('152', '1', '39', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('153', '1', '40', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('154', '1', '41', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('155', '1', '42', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('156', '1', '43', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('157', '1', '46', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('158', '1', '47', '1', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1');
INSERT INTO `sys_user_permission` VALUES ('159', '3', '4', '1', null, null, null, null, null, null);
INSERT INTO `sys_user_permission` VALUES ('160', '3', '5', '1', null, null, null, null, null, null);
INSERT INTO `sys_user_permission` VALUES ('161', '3', '9', '1', null, null, null, null, null, null);
INSERT INTO `sys_user_permission` VALUES ('162', '3', '10', '1', null, null, null, null, null, null);
INSERT INTO `sys_user_permission` VALUES ('163', '3', '36', '1', null, null, null, null, null, null);
INSERT INTO `sys_user_permission` VALUES ('164', '3', '38', '1', null, null, null, null, null, null);
INSERT INTO `sys_user_permission` VALUES ('165', '3', '14', '1', null, null, null, null, null, null);
INSERT INTO `sys_user_permission` VALUES ('166', '3', '15', '1', null, null, null, null, null, null);
INSERT INTO `sys_user_permission` VALUES ('167', '3', '19', '1', null, null, null, null, null, null);
INSERT INTO `sys_user_permission` VALUES ('168', '3', '20', '1', null, null, null, null, null, null);
INSERT INTO `sys_user_permission` VALUES ('169', '3', '29', '1', null, null, null, null, null, null);
INSERT INTO `sys_user_permission` VALUES ('170', '3', '30', '1', null, null, null, null, null, null);
INSERT INTO `sys_user_permission` VALUES ('171', '3', '44', '1', null, null, null, null, null, null);
INSERT INTO `sys_user_permission` VALUES ('172', '3', '34', '1', null, null, null, null, null, null);
INSERT INTO `sys_user_permission` VALUES ('173', '3', '42', '1', null, null, null, null, null, null);
INSERT INTO `sys_user_permission` VALUES ('174', '3', '43', '1', null, null, null, null, null, null);
INSERT INTO `sys_user_permission` VALUES ('175', '3', '46', '1', null, null, null, null, null, null);
INSERT INTO `sys_user_permission` VALUES ('176', '3', '47', '1', null, null, null, null, null, null);
INSERT INTO `sys_user_permission` VALUES ('177', '4', '41', '1', null, null, '2018-12-03 16:09:00', null, null, '2');
INSERT INTO `sys_user_permission` VALUES ('178', '4', '45', '1', null, null, '2018-12-03 16:09:00', null, null, '2');

-- ----------------------------
-- Table structure for sys_user_role_organization
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role_organization`;
CREATE TABLE `sys_user_role_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sys_user_id` bigint(20) DEFAULT NULL,
  `sys_role_organization_id` bigint(20) DEFAULT NULL,
  `rank` bigint(20) DEFAULT '0' COMMENT '排序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `update_by` bigint(20) DEFAULT '0' COMMENT '更新人id',
  `status` tinyint(4) DEFAULT '1' COMMENT '数据状态,1:正常,2:删除',
  `is_final` tinyint(4) DEFAULT NULL COMMENT '是否能修改',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role_organization
-- ----------------------------
INSERT INTO `sys_user_role_organization` VALUES ('1', '1', '2', '0', '2016-10-29 15:06:05', '2016-10-31 23:53:54', '0', '0', '2', '2');
INSERT INTO `sys_user_role_organization` VALUES ('2', '2', '6', '0', '2016-10-29 15:07:39', '2016-10-31 23:53:54', '0', '0', '2', '2');
INSERT INTO `sys_user_role_organization` VALUES ('3', '1', '2', '0', '2016-10-31 22:55:17', '2016-10-31 23:53:54', '0', '0', '2', '2');
INSERT INTO `sys_user_role_organization` VALUES ('6', '2', '6', '0', '2016-10-31 23:14:42', '2016-10-31 23:53:54', '0', '0', '1', '2');
INSERT INTO `sys_user_role_organization` VALUES ('7', '1', '2', '0', '2016-10-31 23:49:14', '2018-11-19 17:36:52', '0', '0', '2', '2');
INSERT INTO `sys_user_role_organization` VALUES ('8', '1', '2', '0', '2018-11-19 17:36:52', '2018-11-20 22:04:21', '0', '0', '2', '1');
INSERT INTO `sys_user_role_organization` VALUES ('9', '1', '2', '0', '2018-11-20 22:04:21', '2018-11-20 22:04:40', '0', '0', '2', '1');
INSERT INTO `sys_user_role_organization` VALUES ('10', '1', '2', '0', '2018-11-20 22:04:40', '2018-11-20 22:04:40', '0', '0', '1', '1');
INSERT INTO `sys_user_role_organization` VALUES ('11', '3', '5', null, null, null, null, null, '0', '1');
INSERT INTO `sys_user_role_organization` VALUES ('12', '4', '5', null, null, '2018-12-03 16:09:00', null, null, '2', '1');
