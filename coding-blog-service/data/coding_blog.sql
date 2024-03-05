/*
 Navicat Premium Data Transfer

 Source Server         : mysql8.0
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : coding_blog

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 26/12/2023 20:29:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `admin_role_relation`;
CREATE TABLE `admin_role_relation`  (
  `admin_role_relation_id` bigint NOT NULL AUTO_INCREMENT,
  `users_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `role_id` bigint NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`admin_role_relation_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '后台用户和角色关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_role_relation
-- ----------------------------
INSERT INTO `admin_role_relation` VALUES (23, 16, 12);
INSERT INTO `admin_role_relation` VALUES (26, 15, 11);

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`  (
  `comment_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `comment_post_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '对应文章ID',
  `comment_author` tinytext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NULL COMMENT '评论者',
  `comment_author_email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NULL DEFAULT NULL COMMENT '评论者邮箱',
  `comment_author_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NULL DEFAULT NULL COMMENT '评论者网址',
  `comment_author_ip` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NULL DEFAULT NULL COMMENT '评论者IP',
  `comment_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '评论时间',
  `comment_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NULL COMMENT '评论正文',
  `comment_approved` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NULL DEFAULT NULL COMMENT '评论是否被批准',
  `comment_agent` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NULL DEFAULT NULL COMMENT '评论者的USER AGENT',
  `comment_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NULL DEFAULT NULL COMMENT '评论类型(pingback/普通)',
  `comment_parent` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '父评论ID',
  `user_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '评论者用户ID（不一定存在）',
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_520_ci COMMENT = '评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comments
-- ----------------------------

-- ----------------------------
-- Table structure for links
-- ----------------------------
DROP TABLE IF EXISTS `links`;
CREATE TABLE `links`  (
  `link_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `link_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NULL DEFAULT NULL COMMENT '链接URL',
  `link_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NULL DEFAULT NULL COMMENT '链接标题',
  `link_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NULL DEFAULT NULL COMMENT '链接图片',
  `link_target` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NULL DEFAULT NULL COMMENT '链接打开方式',
  `link_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NULL DEFAULT NULL COMMENT '链接描述',
  `link_visible` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NULL DEFAULT NULL COMMENT '是否可见（Y/N）',
  `link_owner` bigint UNSIGNED NOT NULL DEFAULT 1 COMMENT '添加者用户ID',
  PRIMARY KEY (`link_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_520_ci COMMENT = '链接信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of links
-- ----------------------------

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `menu_id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父级ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `level` int NULL DEFAULT NULL COMMENT '菜单级数',
  `sort` int NULL DEFAULT NULL COMMENT '菜单排序',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端名称',
  `icon` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端图标',
  `hidden` int NULL DEFAULT NULL COMMENT '前端隐藏',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '后台菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (26, 0, '2023-12-08 15:46:56', '系统管理', 0, 0, 'system-management', 'el-icon-setting', 0);
INSERT INTO `menu` VALUES (27, 26, '2023-12-08 15:48:31', '用户管理', 1, 0, 'users-management', 'el-icon-user', 0);
INSERT INTO `menu` VALUES (28, 26, '2023-12-08 15:48:45', '角色管理', 1, 0, 'roles-management', 'iconfont-role-management', 0);
INSERT INTO `menu` VALUES (31, 0, '2023-12-25 11:15:01', '内容管理', 0, 0, 'content-management', 'el-icon-setting', 0);
INSERT INTO `menu` VALUES (32, 31, '2023-12-25 11:17:47', '创建文章', 1, 0, 'article-editing', 'el-icon-edit-outline', 0);
INSERT INTO `menu` VALUES (33, 31, '2023-12-25 11:18:16', '编辑文章', 1, 0, 'article-modify', 'el-icon-edit-outline', 0);
INSERT INTO `menu` VALUES (34, 31, '2023-12-25 11:19:09', '文章列表', 1, 0, 'article-management', 'iconpark-list-two', 0);
INSERT INTO `menu` VALUES (35, 26, '2023-12-25 14:20:28', '菜单管理', 1, 0, 'menus-management', 'iconfont-menu-management', 0);
INSERT INTO `menu` VALUES (36, 26, '2023-12-25 14:21:07', '资源管理', 1, 0, 'sources-management', 'iconfont-source-management', 0);
INSERT INTO `menu` VALUES (37, 26, '2023-12-25 14:21:37', '资源分类管理', 1, 0, 'source-categories-management', 'el-icon-coin', 0);
INSERT INTO `menu` VALUES (38, 26, '2023-12-25 14:25:34', '标签管理', 1, 0, 'tags-management', ' el-icon-price-tag', 0);
INSERT INTO `menu` VALUES (39, 26, '2023-12-25 15:37:49', '站点配置', 1, 0, 'site-configuration', 'iconpark-config', 0);

-- ----------------------------
-- Table structure for post_tag
-- ----------------------------
DROP TABLE IF EXISTS `post_tag`;
CREATE TABLE `post_tag`  (
  `post_tag_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'post_tag_id',
  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NULL COMMENT '标签名称',
  PRIMARY KEY (`post_tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_520_ci COMMENT = '标签表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of post_tag
-- ----------------------------
INSERT INTO `post_tag` VALUES (31, 'SpringBoot');
INSERT INTO `post_tag` VALUES (32, 'Spring');
INSERT INTO `post_tag` VALUES (33, 'SpringMVC');
INSERT INTO `post_tag` VALUES (34, 'Redis');
INSERT INTO `post_tag` VALUES (35, 'MySql');
INSERT INTO `post_tag` VALUES (36, 'Java');
INSERT INTO `post_tag` VALUES (37, 'Vue');

-- ----------------------------
-- Table structure for post_tag_relation
-- ----------------------------
DROP TABLE IF EXISTS `post_tag_relation`;
CREATE TABLE `post_tag_relation`  (
  `post_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '对应文章ID',
  `post_tag_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '标签ID',
  `term_order` int NOT NULL DEFAULT 0 COMMENT '排序',
  PRIMARY KEY (`post_id`, `post_tag_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_520_ci COMMENT = '标签文章关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of post_tag_relation
-- ----------------------------
INSERT INTO `post_tag_relation` VALUES (69, 35, 0);
INSERT INTO `post_tag_relation` VALUES (70, 34, 0);

-- ----------------------------
-- Table structure for posts
-- ----------------------------
DROP TABLE IF EXISTS `posts`;
CREATE TABLE `posts`  (
  `posts_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'posts_id',
  `post_author` bigint UNSIGNED NULL DEFAULT 0 COMMENT '对应作者ID',
  `post_date` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `post_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NULL COMMENT '正文',
  `post_title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NULL COMMENT '标题',
  `post_excerpt` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NULL COMMENT '摘录',
  `post_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NULL DEFAULT NULL COMMENT '文章状态',
  `comment_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NULL DEFAULT NULL COMMENT '评论状态',
  `post_modified` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `menu_order` int NOT NULL DEFAULT 0 COMMENT '排序ID',
  `post_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NULL DEFAULT NULL COMMENT '文章类型（post/page等）',
  `comment_count` bigint NULL DEFAULT 0 COMMENT '评论总数',
  `attribute` json NULL COMMENT '属性',
  `html_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NULL COMMENT '正文html',
  `page_view` bigint NULL DEFAULT NULL COMMENT '浏览量',
  PRIMARY KEY (`posts_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 73 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_520_ci COMMENT = '文章' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of posts
-- ----------------------------
INSERT INTO `posts` VALUES (69, 15, '2023-12-15 19:21:46', '如果你的面前有阴影，那是因为你的背后有阳光', 'Mysql测试文章', '当你不去旅行，不去冒险，不去谈一场恋爱，不过没试过的生活，每天只是挂着QQ,刷着微博，逛着淘宝，干着岁都能做的事情，那么你要青春有什么用。', 'PUBLISHED', NULL, '2023-12-15 19:21:46', 0, NULL, 0, NULL, '<p>如果你的面前有阴影，那是因为你的背后有阳光</p>', 0);
INSERT INTO `posts` VALUES (70, 15, '2023-12-15 19:22:34', '抽出时间去学习，凡事从小做起，不怕单调和重复，长期的积累坚持，想不成功，也难', 'Redis测试文章', '人生就有许多这样的奇迹，看似比登天还难的事，有时轻而易举就可以做到，其中的差别就在于非凡的信念', 'PUBLISHED', NULL, '2023-12-15 19:22:34', 0, NULL, 0, NULL, '<p>抽出时间去学习，凡事从小做起，不怕单调和重复，长期的积累坚持，想不成功，也难</p>', 0);
INSERT INTO `posts` VALUES (73, 15, '2023-12-26 20:18:42', '\n1. 一级 1\n2. 一级 2\n3. 一级 3', '测试标题', '一级 1 一级 2 一级 3', 'PUBLISHED', NULL, '2023-12-26 20:18:42', 0, NULL, 0, '{\"articleCoverUrl\": \"\"}', '<ol>\n<li>一级 1</li>\n<li>一级 2</li>\n<li>一级 3</li>\n</ol>\n', 0);

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource`  (
  `resource_id` bigint NOT NULL AUTO_INCREMENT,
  `category_id` bigint NULL DEFAULT NULL COMMENT '资源分类ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源名称',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源URL',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '后台资源表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES (54, 15, '2023-12-10 15:32:28', '注册用户信息', '/users/register', '用于注册用户信息');
INSERT INTO `resource` VALUES (55, 15, '2023-12-10 15:33:04', '删除用户信息', '/users/delete', NULL);
INSERT INTO `resource` VALUES (56, 15, '2023-12-10 15:33:20', '批量删除用户信息', '/users/deleteBatch', NULL);
INSERT INTO `resource` VALUES (57, 15, '2023-12-10 15:33:36', '修改用户状态', ' /users/enableOrDisable', NULL);
INSERT INTO `resource` VALUES (58, 15, '2023-12-10 15:34:03', '查询所有用户详细信息', '/users/getAllUserDetail', NULL);
INSERT INTO `resource` VALUES (59, 15, '2023-12-10 15:34:20', '查询用户详细信息', '/users/getUserDetail', NULL);
INSERT INTO `resource` VALUES (60, 15, '2023-12-10 15:34:45', '登录用户并返回token', '/users/login', NULL);

-- ----------------------------
-- Table structure for resource_category
-- ----------------------------
DROP TABLE IF EXISTS `resource_category`;
CREATE TABLE `resource_category`  (
  `resource_category_id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`resource_category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '资源分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resource_category
-- ----------------------------
INSERT INTO `resource_category` VALUES (14, '2023-12-09 17:17:45', '角色管理', 0);
INSERT INTO `resource_category` VALUES (15, '2023-12-09 17:17:51', '用户管理', 0);
INSERT INTO `resource_category` VALUES (16, '2023-12-09 17:17:57', '菜单管理', 0);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `status` int NULL DEFAULT 1 COMMENT '启用状态：0->禁用；1->启用',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '后台用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (10, '普通管理员', '可以发布编辑文章，但是只能管理自己的文章，不能调节系统参数', '2023-12-07 09:24:41', 1);
INSERT INTO `role` VALUES (11, '超级管理员', '我就是这个系统的神！！！', '2023-12-07 09:25:13', 1);
INSERT INTO `role` VALUES (12, '体验者', '只能体现一些基本功能', '2023-12-07 09:25:47', 1);

-- ----------------------------
-- Table structure for role_menu_relation
-- ----------------------------
DROP TABLE IF EXISTS `role_menu_relation`;
CREATE TABLE `role_menu_relation`  (
  `role_menu_relation_id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint NULL DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint NULL DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_menu_relation_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 90 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '后台角色菜单关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menu_relation
-- ----------------------------
INSERT INTO `role_menu_relation` VALUES (68, 12, 26);
INSERT INTO `role_menu_relation` VALUES (69, 12, 27);
INSERT INTO `role_menu_relation` VALUES (70, 12, 28);
INSERT INTO `role_menu_relation` VALUES (71, 11, 26);
INSERT INTO `role_menu_relation` VALUES (72, 11, 27);
INSERT INTO `role_menu_relation` VALUES (73, 11, 28);
INSERT INTO `role_menu_relation` VALUES (80, 11, 31);
INSERT INTO `role_menu_relation` VALUES (81, 11, 32);
INSERT INTO `role_menu_relation` VALUES (82, 11, 33);
INSERT INTO `role_menu_relation` VALUES (83, 11, 34);
INSERT INTO `role_menu_relation` VALUES (84, 11, 35);
INSERT INTO `role_menu_relation` VALUES (85, 11, 36);
INSERT INTO `role_menu_relation` VALUES (86, 11, 37);
INSERT INTO `role_menu_relation` VALUES (87, 11, 38);
INSERT INTO `role_menu_relation` VALUES (88, 11, 39);
INSERT INTO `role_menu_relation` VALUES (89, 12, 39);

-- ----------------------------
-- Table structure for role_resource_relation
-- ----------------------------
DROP TABLE IF EXISTS `role_resource_relation`;
CREATE TABLE `role_resource_relation`  (
  `role_resource_relation_id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint NULL DEFAULT NULL COMMENT '角色ID',
  `resource_id` bigint NULL DEFAULT NULL COMMENT '资源ID',
  PRIMARY KEY (`role_resource_relation_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 207 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '后台角色资源关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_resource_relation
-- ----------------------------
INSERT INTO `role_resource_relation` VALUES (202, 11, 54);
INSERT INTO `role_resource_relation` VALUES (203, 11, 55);
INSERT INTO `role_resource_relation` VALUES (204, 11, 56);
INSERT INTO `role_resource_relation` VALUES (205, 11, 57);
INSERT INTO `role_resource_relation` VALUES (206, 11, 58);

-- ----------------------------
-- Table structure for site
-- ----------------------------
DROP TABLE IF EXISTS `site`;
CREATE TABLE `site`  (
  `site_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'site_id',
  `site_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `keywords` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关键字',
  `site_desc` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '介绍',
  `attribute` json NULL COMMENT '属性',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`site_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '站点配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of site
-- ----------------------------

-- ----------------------------
-- Table structure for term_relationships
-- ----------------------------
DROP TABLE IF EXISTS `term_relationships`;
CREATE TABLE `term_relationships`  (
  `term_relationships_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '对应文章ID/链接ID',
  `term_taxonomy_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '栏目ID',
  `term_order` int NOT NULL DEFAULT 0 COMMENT '排序',
  `type` int NOT NULL COMMENT '类型,0:内容,1:链接',
  PRIMARY KEY (`term_relationships_id`, `term_taxonomy_id`, `type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_520_ci COMMENT = '文章栏目关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of term_relationships
-- ----------------------------
INSERT INTO `term_relationships` VALUES (69, 20, 0, 1);
INSERT INTO `term_relationships` VALUES (70, 21, 0, 1);

-- ----------------------------
-- Table structure for term_taxonomy
-- ----------------------------
DROP TABLE IF EXISTS `term_taxonomy`;
CREATE TABLE `term_taxonomy`  (
  `term_taxonomy_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NULL COMMENT '说明',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL COMMENT '栏目名称',
  `parent_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '父栏目id',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `attribute` json NULL COMMENT '属性',
  PRIMARY KEY (`term_taxonomy_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_520_ci COMMENT = '栏目' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of term_taxonomy
-- ----------------------------
INSERT INTO `term_taxonomy` VALUES (15, '后端所有信息归属', '后端', 0, 15, '2023-12-13 15:50:40', '2023-12-13 15:50:40', NULL);
INSERT INTO `term_taxonomy` VALUES (16, 'SpringBoot文章归属', 'SpingBoot', 15, 15, '2023-12-13 15:51:17', '2023-12-13 15:51:17', NULL);
INSERT INTO `term_taxonomy` VALUES (17, 'Spring框架文章归属', 'Sping', 15, 15, '2023-12-13 15:51:29', '2023-12-13 15:51:29', NULL);
INSERT INTO `term_taxonomy` VALUES (18, 'SpingMVC框架文章归属', 'SpingMVC', 15, 15, '2023-12-13 15:51:36', '2023-12-13 15:51:36', NULL);
INSERT INTO `term_taxonomy` VALUES (19, '常用SQL文章信息', 'SQL', 0, 15, '2023-12-13 15:52:03', '2023-12-13 15:52:03', NULL);
INSERT INTO `term_taxonomy` VALUES (20, 'MySql文章的总结信息', 'MySql', 19, 15, '2023-12-13 15:52:42', '2023-12-13 15:52:42', NULL);
INSERT INTO `term_taxonomy` VALUES (21, '常见缓存文章信息', 'Cache', 0, 15, '2023-12-13 15:53:06', '2023-12-13 15:53:06', NULL);
INSERT INTO `term_taxonomy` VALUES (22, '持久化数据缓存', 'Redis', 21, 15, '2023-12-13 15:53:37', '2023-12-13 15:53:37', NULL);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  ( --密码都默认是123456
  `users_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'users_id',
  `user_login` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NULL DEFAULT NULL COMMENT '登录名',
  `user_pass` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NULL DEFAULT NULL COMMENT '密码',
  `user_nicename` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NULL DEFAULT NULL COMMENT '昵称',
  `user_email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NULL DEFAULT NULL COMMENT 'Email',
  `user_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NULL DEFAULT NULL COMMENT '网址',
  `user_registered` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '注册时间',
  `user_activation_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NULL DEFAULT NULL COMMENT '激活码',
  `user_status` int NOT NULL DEFAULT 0 COMMENT '用户状态',
  `display_name` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NULL DEFAULT NULL COMMENT '图像',
  `user_type` int NULL DEFAULT NULL COMMENT '用户类型 0 :后台 1：前端',
  `open_id` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NULL DEFAULT NULL COMMENT 'open_id',
  `attribute` json NULL COMMENT '属性',
  PRIMARY KEY (`users_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_520_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (15, 'admin', '$2a$10$2LEcYcyPharStVbfTDmGGeaLu9QuqE4Ui65i6orWlJtGfZSezwgoe', '超级管理员', '1808870333@126.com', NULL, '2023-12-06 13:16:09', NULL, 0, 'hello.png', 0, NULL, NULL);
INSERT INTO `users` VALUES (16, 'test', '$2a$10$Kieyx9OjZ.t8OPaOrDeFBuBxiQjGAsyIhn4WroGcgb5bHqctH8ymu', 'test管理员', '1808870333@126.com', NULL, '2023-12-06 16:32:20', NULL, 0, 'test', 0, NULL, NULL);
INSERT INTO `users` VALUES (17, 'test1', '$2a$10$E/xCtTn5YHFHaVB4OYReSew262aNC4dkftBBr2YdXtR2RxpHHkBz6', 'test管理员', '1808870333@126.com', NULL, '2023-12-06 16:33:17', NULL, 0, 'test2', 0, NULL, NULL);
INSERT INTO `users` VALUES (18, 'test2', '$2a$10$xLkbFgltqucag4ep167tM.mgiObZhovf4c/S.kV2hs9fxNPCmC4zO', 'test管理员', '1808870333@126.com', NULL, '2023-12-06 16:33:21', NULL, 0, 'test2', 0, NULL, NULL);
INSERT INTO `users` VALUES (19, 'test3', '$2a$10$XyzrIh8XPjoDrGQNwMy.UeleOMX3kz16KF4N1V1x2J00eofb55aVi', 'test管理员', '1808870333@126.com', NULL, '2023-12-06 16:33:25', NULL, 1, 'test2', 0, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
