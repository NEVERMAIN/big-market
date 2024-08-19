/*
 Navicat Premium Data Transfer

 Source Server         : localhost_MySQL
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : big_market_01

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 15/08/2024 20:26:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for raffle_activity_account
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_account`;
CREATE TABLE `raffle_activity_account`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
  `total_count` int(8) NOT NULL COMMENT '总次数',
  `total_count_surplus` int(8) NOT NULL COMMENT '总次数-剩余',
  `day_count` int(8) NOT NULL COMMENT '日次数',
  `day_count_surplus` int(8) NOT NULL COMMENT '日次数-剩余',
  `month_count` int(8) NOT NULL COMMENT '月次数',
  `month_count_surplus` int(8) NOT NULL COMMENT '月次数-剩余',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_user_id_activity_id`(`user_id`, `activity_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抽奖活动账户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of raffle_activity_account
-- ----------------------------
INSERT INTO `raffle_activity_account` VALUES (3, 'xiaofuge', 100301, 8930, 68, 130, 106, 130, 106, '2024-03-23 16:38:57', '2024-08-13 21:07:49');
INSERT INTO `raffle_activity_account` VALUES (4, '12345', 100301, 10, 10, 10, 10, 10, 10, '2024-05-01 15:28:50', '2024-05-01 15:28:50');
INSERT INTO `raffle_activity_account` VALUES (5, 'user003', 100301, 20, 19, 20, 19, 20, 19, '2024-08-11 00:13:44', '2024-08-11 00:16:21');
INSERT INTO `raffle_activity_account` VALUES (6, 'user004', 100301, 20, 19, 20, 19, 20, 19, '2024-08-11 00:15:05', '2024-08-11 00:22:27');

-- ----------------------------
-- Table structure for raffle_activity_account_day
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_account_day`;
CREATE TABLE `raffle_activity_account_day`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
  `day` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日期（yyyy-mm-dd）',
  `day_count` int(8) NOT NULL COMMENT '日次数',
  `day_count_surplus` int(8) NOT NULL COMMENT '日次数-剩余',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_user_id_activity_id_day`(`user_id`, `activity_id`, `day`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抽奖活动账户表-日次数' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of raffle_activity_account_day
-- ----------------------------
INSERT INTO `raffle_activity_account_day` VALUES (2, 'xiaofuge', 100301, '2024-04-05', 45, 44, '2024-04-05 17:10:31', '2024-04-05 17:10:31');
INSERT INTO `raffle_activity_account_day` VALUES (3, 'xiaofuge', 100301, '2024-04-08', 45, 44, '2024-04-08 22:52:47', '2024-04-08 22:52:47');
INSERT INTO `raffle_activity_account_day` VALUES (4, 'xiaofuge', 100301, '2024-04-13', 45, 23, '2024-04-13 11:44:10', '2024-04-20 10:51:09');
INSERT INTO `raffle_activity_account_day` VALUES (7, 'xiaofuge', 100301, '2024-04-20', 45, 13, '2024-04-20 16:50:38', '2024-04-20 16:50:38');
INSERT INTO `raffle_activity_account_day` VALUES (11, 'xiaofuge', 100301, '2024-05-01', 60, 40, '2024-05-01 14:51:45', '2024-05-01 17:45:10');
INSERT INTO `raffle_activity_account_day` VALUES (12, 'xiaofuge', 100301, '2024-05-03', 90, 86, '2024-05-03 09:00:28', '2024-05-03 13:28:42');
INSERT INTO `raffle_activity_account_day` VALUES (13, 'user003', 100301, '2024-08-11', 20, 19, '2024-08-11 00:16:21', '2024-08-11 00:16:21');
INSERT INTO `raffle_activity_account_day` VALUES (14, 'user004', 100301, '2024-08-11', 20, 19, '2024-08-11 00:22:27', '2024-08-11 00:22:27');

-- ----------------------------
-- Table structure for raffle_activity_account_month
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_account_month`;
CREATE TABLE `raffle_activity_account_month`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
  `month` varchar(7) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '月（yyyy-mm）',
  `month_count` int(8) NOT NULL COMMENT '月次数',
  `month_count_surplus` int(8) NOT NULL COMMENT '月次数-剩余',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_user_id_activity_id_month`(`user_id`, `activity_id`, `month`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抽奖活动账户表-月次数' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of raffle_activity_account_month
-- ----------------------------
INSERT INTO `raffle_activity_account_month` VALUES (7, 'xiaofuge', 100301, '2024-05', 70, 46, '2024-05-01 14:51:45', '2024-05-03 13:28:42');
INSERT INTO `raffle_activity_account_month` VALUES (8, 'user003', 100301, '2024-08', 20, 19, '2024-08-11 00:16:21', '2024-08-11 00:16:21');
INSERT INTO `raffle_activity_account_month` VALUES (9, 'user004', 100301, '2024-08', 20, 19, '2024-08-11 00:22:27', '2024-08-11 00:22:27');

-- ----------------------------
-- Table structure for raffle_activity_order_000
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_order_000`;
CREATE TABLE `raffle_activity_order_000`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `sku` bigint(12) NOT NULL COMMENT '商品sku',
  `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '活动名称',
  `strategy_id` bigint(8) NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `total_count` int(8) NOT NULL COMMENT '总次数',
  `day_count` int(8) NOT NULL COMMENT '日次数',
  `month_count` int(8) NOT NULL COMMENT '月次数',
  `pay_amount` decimal(10, 2) NOT NULL COMMENT '商品金额【积分】',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'complete' COMMENT '订单状态（complete）',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务仿重ID - 外部透传的，确保幂等',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id`) USING BTREE,
  UNIQUE INDEX `uq_out_business_no`(`out_business_no`) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id`, `activity_id`, `state`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抽奖活动单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of raffle_activity_order_000
-- ----------------------------

-- ----------------------------
-- Table structure for raffle_activity_order_001
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_order_001`;
CREATE TABLE `raffle_activity_order_001`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `sku` bigint(12) NOT NULL COMMENT '商品sku',
  `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '活动名称',
  `strategy_id` bigint(8) NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `total_count` int(8) NOT NULL COMMENT '总次数',
  `day_count` int(8) NOT NULL COMMENT '日次数',
  `month_count` int(8) NOT NULL COMMENT '月次数',
  `pay_amount` decimal(10, 2) NOT NULL COMMENT '商品金额【积分】',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'complete' COMMENT '订单状态（complete）',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务仿重ID - 外部透传的，确保幂等',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id`) USING BTREE,
  UNIQUE INDEX `uq_out_business_no`(`out_business_no`) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id`, `activity_id`, `state`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4238 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抽奖活动单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of raffle_activity_order_001
-- ----------------------------
INSERT INTO `raffle_activity_order_001` VALUES (71, 'xiaofuge', 9011, 100301, '测试活动', 100006, '761345538871', '2024-04-21 10:40:25', 1, 1, 1, 0.00, 'completed', '073735003829', '2024-04-21 18:40:25', '2024-04-21 18:40:25');
INSERT INTO `raffle_activity_order_001` VALUES (72, 'xiaofuge', 9011, 100301, '测试活动', 100006, '837744050164', '2024-04-21 10:40:25', 1, 1, 1, 0.00, 'completed', '613036507854', '2024-04-21 18:40:25', '2024-04-21 18:40:25');
INSERT INTO `raffle_activity_order_001` VALUES (73, 'xiaofuge', 9011, 100301, '测试活动', 100006, '766742523760', '2024-04-21 10:40:25', 1, 1, 1, 0.00, 'completed', '649099837249', '2024-04-21 18:40:25', '2024-04-21 18:40:25');
INSERT INTO `raffle_activity_order_001` VALUES (74, 'xiaofuge', 9011, 100301, '测试活动', 100006, '856474163547', '2024-04-21 10:40:25', 1, 1, 1, 0.00, 'completed', '652903372986', '2024-04-21 18:40:25', '2024-04-21 18:40:25');
INSERT INTO `raffle_activity_order_001` VALUES (75, 'xiaofuge', 9011, 100301, '测试活动', 100006, '668775949799', '2024-04-21 10:40:25', 1, 1, 1, 0.00, 'completed', '097066347980', '2024-04-21 18:40:25', '2024-04-21 18:40:25');
INSERT INTO `raffle_activity_order_001` VALUES (76, 'xiaofuge', 9011, 100301, '测试活动', 100006, '164452591012', '2024-05-01 06:44:26', 10, 10, 10, 0.00, 'completed', 'xiaofuge_sku_2024042903', '2024-05-01 14:44:26', '2024-05-01 14:44:26');
INSERT INTO `raffle_activity_order_001` VALUES (77, 'xiaofuge', 9011, 100301, '测试活动', 100006, '492597085813', '2024-05-01 06:51:45', 10, 10, 10, 0.00, 'completed', 'xiaofuge_sku_2024042904', '2024-05-01 14:51:45', '2024-05-01 14:51:45');
INSERT INTO `raffle_activity_order_001` VALUES (78, 'xiaofuge', 9011, 100301, '测试活动', 100006, '031706643902', '2024-05-01 06:54:36', 10, 10, 10, 0.00, 'completed', 'xiaofuge_sku_2024042905', '2024-05-01 14:54:36', '2024-05-01 14:54:36');
INSERT INTO `raffle_activity_order_001` VALUES (79, 'xiaofuge', 9011, 100301, '测试活动', 100006, '460855930969', '2024-05-01 07:00:12', 10, 10, 10, 0.00, 'completed', 'xiaofuge_sku_2024042906', '2024-05-01 15:00:12', '2024-05-01 15:00:12');
INSERT INTO `raffle_activity_order_001` VALUES (1096, 'xiaofuge', 9011, 100301, '测试活动', 100006, '364757830401', '2024-05-01 09:14:43', 10, 10, 10, 0.00, 'completed', 'xiaofuge_sku_20240501', '2024-05-01 17:14:43', '2024-05-01 17:14:43');
INSERT INTO `raffle_activity_order_001` VALUES (1097, 'xiaofuge', 9011, 100301, '测试活动', 100006, '157026402583', '2024-05-01 09:39:40', 10, 10, 10, 0.00, 'completed', 'xiaofuge_sku_20240420', '2024-05-01 17:39:40', '2024-05-01 17:39:40');
INSERT INTO `raffle_activity_order_001` VALUES (1098, 'xiaofuge', 9011, 100301, '测试活动', 100006, '481116019750', '2024-05-01 09:41:53', 10, 10, 10, 0.00, 'completed', 'xiaofuge_sku_20240401', '2024-05-01 17:41:53', '2024-05-01 17:41:53');
INSERT INTO `raffle_activity_order_001` VALUES (1099, 'xiaofuge', 9011, 100301, '测试活动', 100006, '639151059221', '2024-05-01 09:45:10', 10, 10, 10, 0.00, 'completed', 'xiaofuge_sku_20240402', '2024-05-01 17:45:10', '2024-05-01 17:45:10');
INSERT INTO `raffle_activity_order_001` VALUES (4234, 'xiaofuge', 9011, 100301, '测试活动', 100006, '129360973197', '2024-05-03 05:28:43', 10, 10, 10, 0.00, 'completed', 'xiaofuge_sku_20240503', '2024-05-03 13:28:42', '2024-05-03 13:28:42');
INSERT INTO `raffle_activity_order_001` VALUES (4235, 'xiaofuge', 9011, 100301, '测试活动', 100006, '772362698454', '2024-08-13 13:06:23', 20, 20, 20, 0.00, 'completed', 'xiaofuge_sku_715091006810', '2024-08-13 21:06:23', '2024-08-13 21:06:23');
INSERT INTO `raffle_activity_order_001` VALUES (4237, 'xiaofuge', 9011, 100301, '测试活动', 100006, '933215014198', '2024-08-13 13:07:50', 20, 20, 20, 0.00, 'completed', 'xiaofuge_sku_353583185102', '2024-08-13 21:07:49', '2024-08-13 21:07:49');

-- ----------------------------
-- Table structure for raffle_activity_order_002
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_order_002`;
CREATE TABLE `raffle_activity_order_002`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `sku` bigint(12) NOT NULL COMMENT '商品sku',
  `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '活动名称',
  `strategy_id` bigint(8) NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `total_count` int(8) NOT NULL COMMENT '总次数',
  `day_count` int(8) NOT NULL COMMENT '日次数',
  `month_count` int(8) NOT NULL COMMENT '月次数',
  `pay_amount` decimal(10, 2) NOT NULL COMMENT '商品金额【积分】',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'complete' COMMENT '订单状态（complete）',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务仿重ID - 外部透传的，确保幂等',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id`) USING BTREE,
  UNIQUE INDEX `uq_out_business_no`(`out_business_no`) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id`, `activity_id`, `state`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抽奖活动单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of raffle_activity_order_002
-- ----------------------------
INSERT INTO `raffle_activity_order_002` VALUES (1, 'user003', 9011, 100301, '测试活动', 100006, '301647580770', '2024-08-10 16:13:45', 20, 20, 20, 0.00, 'completed', 'user003_sku_20240811', '2024-08-11 00:13:44', '2024-08-11 00:13:44');

-- ----------------------------
-- Table structure for raffle_activity_order_003
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_order_003`;
CREATE TABLE `raffle_activity_order_003`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `sku` bigint(12) NOT NULL COMMENT '商品sku',
  `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '活动名称',
  `strategy_id` bigint(8) NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `total_count` int(8) NOT NULL COMMENT '总次数',
  `day_count` int(8) NOT NULL COMMENT '日次数',
  `month_count` int(8) NOT NULL COMMENT '月次数',
  `pay_amount` decimal(10, 2) NOT NULL COMMENT '商品金额【积分】',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'complete' COMMENT '订单状态（complete）',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务仿重ID - 外部透传的，确保幂等',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id`) USING BTREE,
  UNIQUE INDEX `uq_out_business_no`(`out_business_no`) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id`, `activity_id`, `state`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抽奖活动单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of raffle_activity_order_003
-- ----------------------------
INSERT INTO `raffle_activity_order_003` VALUES (1, 'user004', 9011, 100301, '测试活动', 100006, '911017425365', '2024-08-10 16:15:06', 20, 20, 20, 0.00, 'completed', 'user004_sku_20240811', '2024-08-11 00:15:05', '2024-08-11 00:15:05');

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `topic` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息主题',
  `message_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息编号',
  `message` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息主体',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'create' COMMENT '任务状态；create-创建、completed-完成、fail-失败',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_message_id`(`message_id`) USING BTREE,
  INDEX `idx_state`(`state`) USING BTREE,
  INDEX `idx_create_time`(`update_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 155 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '任务表，发送MQ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of task
-- ----------------------------
INSERT INTO `task` VALUES (147, 'user004', 'send_rebate', '23171189563', '{\"data\":{\"bizId\":\"user004_sku_20240811\",\"rebateConfig\":\"9011\",\"rebateDesc\":\"签到返利-sku额度\",\"rebateType\":\"sku\",\"userId\":\"user004\"},\"id\":\"23171189563\",\"timestamp\":1723306505939}', 'complete', '2024-08-11 00:15:05', '2024-08-11 00:15:15');
INSERT INTO `task` VALUES (148, 'user004', 'send_rebate', '34929582672', '{\"data\":{\"bizId\":\"user004_integral_20240811\",\"rebateConfig\":\"10\",\"rebateDesc\":\"签到返利-积分\",\"rebateType\":\"integral\",\"userId\":\"user004\"},\"id\":\"34929582672\",\"timestamp\":1723306505939}', 'complete', '2024-08-11 00:15:05', '2024-08-11 00:15:15');
INSERT INTO `task` VALUES (149, 'user003', 'send_award', '612848730376', '{\"data\":{\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"999182993606\",\"userId\":\"user003\"},\"id\":\"612848730376\",\"timestamp\":1723306581437}', 'complete', '2024-08-11 00:16:21', '2024-08-11 00:16:21');
INSERT INTO `task` VALUES (150, 'user004', 'send_award', '813579233347', '{\"data\":{\"awardId\":102,\"awardTitle\":\"OpenAI会员卡\",\"orderId\":\"243204132453\",\"userId\":\"user004\"},\"id\":\"813579233347\",\"timestamp\":1723306947340}', 'complete', '2024-08-11 00:22:27', '2024-08-11 00:22:27');
INSERT INTO `task` VALUES (151, 'xiaofuge', 'send_rebate', '05295978494', '{\"data\":{\"bizId\":\"xiaofuge_sku_715091006810\",\"rebateConfig\":\"9011\",\"rebateDesc\":\"签到返利-sku额度\",\"rebateType\":\"sku\",\"userId\":\"xiaofuge\"},\"id\":\"05295978494\",\"timestamp\":1723554382865}', 'complete', '2024-08-13 21:06:23', '2024-08-13 21:06:30');
INSERT INTO `task` VALUES (152, 'xiaofuge', 'send_rebate', '89632704304', '{\"data\":{\"bizId\":\"xiaofuge_integral_715091006810\",\"rebateConfig\":\"10\",\"rebateDesc\":\"签到返利-积分\",\"rebateType\":\"integral\",\"userId\":\"xiaofuge\"},\"id\":\"89632704304\",\"timestamp\":1723554382866}', 'complete', '2024-08-13 21:06:23', '2024-08-13 21:06:30');
INSERT INTO `task` VALUES (153, 'xiaofuge', 'send_rebate', '51333993361', '{\"data\":{\"bizId\":\"xiaofuge_sku_353583185102\",\"rebateConfig\":\"9011\",\"rebateDesc\":\"签到返利-sku额度\",\"rebateType\":\"sku\",\"userId\":\"xiaofuge\"},\"id\":\"51333993361\",\"timestamp\":1723554469321}', 'complete', '2024-08-13 21:07:49', '2024-08-13 21:08:00');
INSERT INTO `task` VALUES (154, 'xiaofuge', 'send_rebate', '52299219619', '{\"data\":{\"bizId\":\"xiaofuge_integral_353583185102\",\"rebateConfig\":\"10\",\"rebateDesc\":\"签到返利-积分\",\"rebateType\":\"integral\",\"userId\":\"xiaofuge\"},\"id\":\"52299219619\",\"timestamp\":1723554469323}', 'complete', '2024-08-13 21:07:49', '2024-08-13 21:08:00');

-- ----------------------------
-- Table structure for user_award_record_000
-- ----------------------------
DROP TABLE IF EXISTS `user_award_record_000`;
CREATE TABLE `user_award_record_000`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
  `strategy_id` bigint(8) NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '抽奖订单ID【作为幂等使用】',
  `award_id` int(11) NOT NULL COMMENT '奖品ID',
  `award_title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '奖品标题（名称）',
  `award_time` datetime NOT NULL COMMENT '中奖时间',
  `award_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'create' COMMENT '奖品状态；create-创建、completed-发奖完成',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_activity_id`(`activity_id`) USING BTREE,
  INDEX `idx_award_id`(`strategy_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户中奖记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_award_record_000
-- ----------------------------

-- ----------------------------
-- Table structure for user_award_record_001
-- ----------------------------
DROP TABLE IF EXISTS `user_award_record_001`;
CREATE TABLE `user_award_record_001`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
  `strategy_id` bigint(8) NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '抽奖订单ID【作为幂等使用】',
  `award_id` int(11) NOT NULL COMMENT '奖品ID',
  `award_title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '奖品标题（名称）',
  `award_time` datetime NOT NULL COMMENT '中奖时间',
  `award_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'create' COMMENT '奖品状态；create-创建、completed-发奖完成',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_activity_id`(`activity_id`) USING BTREE,
  INDEX `idx_award_id`(`strategy_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 111 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户中奖记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_award_record_001
-- ----------------------------
INSERT INTO `user_award_record_001` VALUES (1, 'xiaofuge', 100301, 100006, '313091076458', 101, 'OpenAI 增加使用次数', '2024-04-06 03:41:50', 'create', '2024-04-06 11:41:50', '2024-04-06 11:41:50');
INSERT INTO `user_award_record_001` VALUES (3, 'xiaofuge', 100301, 100006, '313091076459', 101, 'OpenAI 增加使用次数', '2024-04-06 03:47:54', 'create', '2024-04-06 11:47:54', '2024-04-06 11:47:54');
INSERT INTO `user_award_record_001` VALUES (6, 'xiaofuge', 100301, 100006, '658772889112', 101, 'OpenAI 增加使用次数', '2024-04-06 04:16:50', 'create', '2024-04-06 12:16:50', '2024-04-06 12:16:50');
INSERT INTO `user_award_record_001` VALUES (7, 'xiaofuge', 100301, 100006, '623291703218', 101, 'OpenAI 增加使用次数', '2024-04-06 04:16:51', 'create', '2024-04-06 12:16:50', '2024-04-06 12:16:50');
INSERT INTO `user_award_record_001` VALUES (8, 'xiaofuge', 100301, 100006, '619841045154', 101, 'OpenAI 增加使用次数', '2024-04-06 04:16:51', 'create', '2024-04-06 12:16:51', '2024-04-06 12:16:51');
INSERT INTO `user_award_record_001` VALUES (9, 'xiaofuge', 100301, 100006, '696947604604', 101, 'OpenAI 增加使用次数', '2024-04-06 04:16:52', 'create', '2024-04-06 12:16:51', '2024-04-06 12:16:51');
INSERT INTO `user_award_record_001` VALUES (10, 'xiaofuge', 100301, 100006, '239997053403', 101, 'OpenAI 增加使用次数', '2024-04-06 04:16:52', 'create', '2024-04-06 12:16:52', '2024-04-06 12:16:52');
INSERT INTO `user_award_record_001` VALUES (11, 'xiaofuge', 100301, 100006, '837228766840', 101, 'OpenAI 增加使用次数', '2024-04-06 04:16:53', 'create', '2024-04-06 12:16:52', '2024-04-06 12:16:52');
INSERT INTO `user_award_record_001` VALUES (12, 'xiaofuge', 100301, 100006, '012609968231', 101, 'OpenAI 增加使用次数', '2024-04-06 04:16:53', 'create', '2024-04-06 12:16:53', '2024-04-06 12:16:53');
INSERT INTO `user_award_record_001` VALUES (13, 'xiaofuge', 100301, 100006, '540056227059', 101, 'OpenAI 增加使用次数', '2024-04-06 04:16:54', 'create', '2024-04-06 12:16:54', '2024-04-06 12:16:54');
INSERT INTO `user_award_record_001` VALUES (14, 'xiaofuge', 100301, 100006, '757384073568', 101, 'OpenAI 增加使用次数', '2024-04-06 04:16:55', 'create', '2024-04-06 12:16:54', '2024-04-06 12:16:54');
INSERT INTO `user_award_record_001` VALUES (15, 'xiaofuge', 100301, 100006, '270533634609', 101, 'OpenAI 增加使用次数', '2024-04-06 04:16:55', 'create', '2024-04-06 12:16:55', '2024-04-06 12:16:55');
INSERT INTO `user_award_record_001` VALUES (16, 'xiaofuge', 100301, 100006, '143517041178', 101, 'OpenAI 增加使用次数', '2024-04-06 04:16:56', 'create', '2024-04-06 12:16:55', '2024-04-06 12:16:55');
INSERT INTO `user_award_record_001` VALUES (17, 'xiaofuge', 100301, 100006, '880610933571', 101, 'OpenAI 增加使用次数', '2024-04-06 04:16:56', 'create', '2024-04-06 12:16:56', '2024-04-06 12:16:56');
INSERT INTO `user_award_record_001` VALUES (18, 'xiaofuge', 100301, 100006, '288939138548', 101, 'OpenAI 增加使用次数', '2024-04-06 04:16:57', 'create', '2024-04-06 12:16:56', '2024-04-06 12:16:56');
INSERT INTO `user_award_record_001` VALUES (19, 'xiaofuge', 100301, 100006, '401825045013', 101, 'OpenAI 增加使用次数', '2024-04-06 04:16:57', 'create', '2024-04-06 12:16:57', '2024-04-06 12:16:57');
INSERT INTO `user_award_record_001` VALUES (20, 'xiaofuge', 100301, 100006, '644483213191', 101, 'OpenAI 增加使用次数', '2024-04-06 04:16:58', 'create', '2024-04-06 12:16:57', '2024-04-06 12:16:57');
INSERT INTO `user_award_record_001` VALUES (21, 'xiaofuge', 100301, 100006, '126942948062', 101, 'OpenAI 增加使用次数', '2024-04-06 04:16:58', 'create', '2024-04-06 12:16:58', '2024-04-06 12:16:58');
INSERT INTO `user_award_record_001` VALUES (22, 'xiaofuge', 100301, 100006, '388415276639', 101, 'OpenAI 增加使用次数', '2024-04-06 08:10:38', 'create', '2024-04-06 16:10:38', '2024-04-06 16:10:38');
INSERT INTO `user_award_record_001` VALUES (23, 'xiaofuge', 100301, 100006, '295404830039', 101, 'OpenAI 增加使用次数', '2024-04-06 08:10:39', 'create', '2024-04-06 16:10:39', '2024-04-06 16:10:39');
INSERT INTO `user_award_record_001` VALUES (24, 'xiaofuge', 100301, 100006, '396378174546', 101, 'OpenAI 增加使用次数', '2024-04-06 08:10:40', 'create', '2024-04-06 16:10:39', '2024-04-06 16:10:39');
INSERT INTO `user_award_record_001` VALUES (25, 'xiaofuge', 100301, 100006, '216034880115', 101, 'OpenAI 增加使用次数', '2024-04-06 08:10:40', 'create', '2024-04-06 16:10:40', '2024-04-06 16:10:40');
INSERT INTO `user_award_record_001` VALUES (26, 'xiaofuge', 100301, 100006, '587083012812', 101, 'OpenAI 增加使用次数', '2024-04-06 08:10:41', 'create', '2024-04-06 16:10:40', '2024-04-06 16:10:40');
INSERT INTO `user_award_record_001` VALUES (27, 'xiaofuge', 100301, 100006, '179931564604', 101, 'OpenAI 增加使用次数', '2024-04-06 08:10:41', 'create', '2024-04-06 16:10:41', '2024-04-06 16:10:41');
INSERT INTO `user_award_record_001` VALUES (28, 'xiaofuge', 100301, 100006, '266603270575', 101, 'OpenAI 增加使用次数', '2024-04-06 08:10:42', 'create', '2024-04-06 16:10:41', '2024-04-06 16:10:41');
INSERT INTO `user_award_record_001` VALUES (29, 'xiaofuge', 100301, 100006, '708306230375', 101, 'OpenAI 增加使用次数', '2024-04-06 08:10:42', 'create', '2024-04-06 16:10:42', '2024-04-06 16:10:42');
INSERT INTO `user_award_record_001` VALUES (30, 'xiaofuge', 100301, 100006, '099363576226', 101, 'OpenAI 增加使用次数', '2024-04-06 08:10:43', 'create', '2024-04-06 16:10:42', '2024-04-06 16:10:42');
INSERT INTO `user_award_record_001` VALUES (31, 'xiaofuge', 100301, 100006, '330354920093', 101, 'OpenAI 增加使用次数', '2024-04-06 08:10:43', 'create', '2024-04-06 16:10:43', '2024-04-06 16:10:43');
INSERT INTO `user_award_record_001` VALUES (32, 'xiaofuge', 100301, 100006, '824752758415', 101, 'OpenAI 增加使用次数', '2024-04-06 08:10:44', 'create', '2024-04-06 16:10:43', '2024-04-06 16:10:43');
INSERT INTO `user_award_record_001` VALUES (33, 'xiaofuge', 100301, 100006, '064058506146', 101, 'OpenAI 增加使用次数', '2024-04-06 08:10:44', 'create', '2024-04-06 16:10:44', '2024-04-06 16:10:44');
INSERT INTO `user_award_record_001` VALUES (34, 'xiaofuge', 100301, 100006, '923475474250', 101, 'OpenAI 增加使用次数', '2024-04-06 08:10:45', 'create', '2024-04-06 16:10:44', '2024-04-06 16:10:44');
INSERT INTO `user_award_record_001` VALUES (35, 'xiaofuge', 100301, 100006, '565064446034', 101, 'OpenAI 增加使用次数', '2024-04-06 08:10:45', 'create', '2024-04-06 16:10:45', '2024-04-06 16:10:45');
INSERT INTO `user_award_record_001` VALUES (36, 'xiaofuge', 100301, 100006, '665449773785', 101, 'OpenAI 增加使用次数', '2024-04-06 08:10:46', 'create', '2024-04-06 16:10:45', '2024-04-06 16:10:45');
INSERT INTO `user_award_record_001` VALUES (37, 'xiaofuge', 100301, 100006, '099294776536', 101, 'OpenAI 增加使用次数', '2024-04-06 08:10:46', 'create', '2024-04-06 16:10:46', '2024-04-06 16:10:46');
INSERT INTO `user_award_record_001` VALUES (38, 'xiaofuge', 100301, 100006, '166766849249', 101, 'OpenAI 增加使用次数', '2024-04-06 08:10:47', 'create', '2024-04-06 16:10:47', '2024-04-06 16:10:47');
INSERT INTO `user_award_record_001` VALUES (39, 'xiaofuge', 100301, 100006, '569856975978', 107, '2等奖', '2024-04-13 03:44:02', 'create', '2024-04-13 11:44:01', '2024-04-13 11:44:01');
INSERT INTO `user_award_record_001` VALUES (40, 'xiaofuge', 100301, 100006, '867136698684', 103, '6等奖', '2024-04-13 03:44:11', 'create', '2024-04-13 11:44:10', '2024-04-13 11:44:10');
INSERT INTO `user_award_record_001` VALUES (41, 'xiaofuge', 100301, 100006, '250425115608', 101, '随机积分', '2024-04-13 03:44:14', 'create', '2024-04-13 11:44:14', '2024-04-13 11:44:14');
INSERT INTO `user_award_record_001` VALUES (42, 'xiaofuge', 100301, 100006, '663910993767', 106, '3等奖', '2024-04-13 03:44:16', 'create', '2024-04-13 11:44:15', '2024-04-13 11:44:15');
INSERT INTO `user_award_record_001` VALUES (43, 'xiaofuge', 100301, 100006, '218374542905', 106, '3等奖', '2024-04-13 03:44:17', 'create', '2024-04-13 11:44:16', '2024-04-13 11:44:16');
INSERT INTO `user_award_record_001` VALUES (44, 'xiaofuge', 100301, 100006, '225714694474', 104, '5等奖', '2024-04-13 03:44:18', 'create', '2024-04-13 11:44:17', '2024-04-13 11:44:17');
INSERT INTO `user_award_record_001` VALUES (45, 'xiaofuge', 100301, 100006, '431167372778', 102, '7等奖', '2024-04-13 03:44:19', 'create', '2024-04-13 11:44:18', '2024-04-13 11:44:18');
INSERT INTO `user_award_record_001` VALUES (46, 'xiaofuge', 100301, 100006, '434079846497', 103, '6等奖', '2024-04-13 03:45:30', 'create', '2024-04-13 11:45:29', '2024-04-13 11:45:29');
INSERT INTO `user_award_record_001` VALUES (47, 'xiaofuge', 100301, 100006, '965685676429', 102, '7等奖', '2024-04-13 03:45:31', 'create', '2024-04-13 11:45:30', '2024-04-13 11:45:30');
INSERT INTO `user_award_record_001` VALUES (48, 'xiaofuge', 100301, 100006, '940273728363', 102, '7等奖', '2024-04-13 04:14:53', 'create', '2024-04-13 12:14:53', '2024-04-13 12:14:53');
INSERT INTO `user_award_record_001` VALUES (49, 'xiaofuge', 100301, 100006, '240699246294', 104, '5等奖', '2024-04-13 04:17:34', 'create', '2024-04-13 12:17:33', '2024-04-13 12:17:33');
INSERT INTO `user_award_record_001` VALUES (50, 'xiaofuge', 100301, 100006, '298101180210', 103, '6等奖', '2024-04-13 04:18:17', 'create', '2024-04-13 12:18:16', '2024-04-13 12:18:16');
INSERT INTO `user_award_record_001` VALUES (51, 'xiaofuge', 100301, 100006, '565655944488', 103, '6等奖', '2024-04-13 04:18:23', 'create', '2024-04-13 12:18:23', '2024-04-13 12:18:23');
INSERT INTO `user_award_record_001` VALUES (52, 'xiaofuge', 100301, 100006, '090289257534', 104, '5等奖', '2024-04-13 04:18:25', 'create', '2024-04-13 12:18:24', '2024-04-13 12:18:24');
INSERT INTO `user_award_record_001` VALUES (53, 'xiaofuge', 100301, 100006, '668356046426', 105, '4等奖', '2024-04-13 04:18:26', 'create', '2024-04-13 12:18:25', '2024-04-13 12:18:25');
INSERT INTO `user_award_record_001` VALUES (54, 'xiaofuge', 100301, 100006, '745680068300', 107, '2等奖', '2024-04-13 04:18:37', 'create', '2024-04-13 12:18:36', '2024-04-13 12:18:36');
INSERT INTO `user_award_record_001` VALUES (55, 'xiaofuge', 100301, 100006, '285300597983', 102, '7等奖', '2024-04-13 04:20:08', 'create', '2024-04-13 12:20:07', '2024-04-13 12:20:07');
INSERT INTO `user_award_record_001` VALUES (56, 'xiaofuge', 100301, 100006, '999361306023', 104, '5等奖', '2024-04-13 04:20:10', 'create', '2024-04-13 12:20:09', '2024-04-13 12:20:09');
INSERT INTO `user_award_record_001` VALUES (57, 'xiaofuge', 100301, 100006, '063682699381', 104, '5等奖', '2024-04-13 04:20:14', 'create', '2024-04-13 12:20:13', '2024-04-13 12:20:13');
INSERT INTO `user_award_record_001` VALUES (58, 'xiaofuge', 100301, 100006, '680488311338', 105, '4等奖', '2024-04-13 04:20:15', 'create', '2024-04-13 12:20:14', '2024-04-13 12:20:14');
INSERT INTO `user_award_record_001` VALUES (59, 'xiaofuge', 100301, 100006, '399058527457', 102, '7等奖', '2024-04-13 04:20:16', 'create', '2024-04-13 12:20:16', '2024-04-13 12:20:16');
INSERT INTO `user_award_record_001` VALUES (60, 'xiaofuge', 100301, 100006, '579122416749', 106, '3等奖', '2024-04-13 04:20:21', 'create', '2024-04-13 12:20:20', '2024-04-13 12:20:20');
INSERT INTO `user_award_record_001` VALUES (61, 'xiaofuge', 100301, 100006, '854484054432', 102, '7等奖', '2024-04-13 06:03:59', 'create', '2024-04-13 14:03:59', '2024-04-13 14:03:59');
INSERT INTO `user_award_record_001` VALUES (62, 'xiaofuge', 100301, 100006, '066823147917', 104, '5等奖', '2024-04-13 07:25:06', 'create', '2024-04-13 15:25:06', '2024-04-13 15:25:06');
INSERT INTO `user_award_record_001` VALUES (63, 'xiaofuge', 100301, 100006, '022620846137', 102, '7等奖', '2024-04-13 07:25:11', 'create', '2024-04-13 15:25:11', '2024-04-13 15:25:11');
INSERT INTO `user_award_record_001` VALUES (64, 'xiaofuge', 100301, 100006, '605666354632', 101, '随机积分', '2024-04-13 07:25:12', 'create', '2024-04-13 15:25:12', '2024-04-13 15:25:12');
INSERT INTO `user_award_record_001` VALUES (65, 'xiaofuge', 100301, 100006, '604661560037', 107, '2等奖', '2024-04-13 07:25:21', 'create', '2024-04-13 15:25:21', '2024-04-13 15:25:21');
INSERT INTO `user_award_record_001` VALUES (66, 'xiaofuge', 100301, 100006, '212448652580', 105, '4等奖', '2024-04-20 04:09:31', 'create', '2024-04-20 12:09:30', '2024-04-20 12:09:30');
INSERT INTO `user_award_record_001` VALUES (67, 'xiaofuge', 100301, 100006, '356729331179', 106, '3等奖', '2024-04-20 04:11:24', 'create', '2024-04-20 12:11:24', '2024-04-20 12:11:24');
INSERT INTO `user_award_record_001` VALUES (68, 'xiaofuge', 100301, 100006, '102669494145', 105, '4等奖', '2024-04-20 07:10:58', 'create', '2024-04-20 15:10:58', '2024-04-20 15:10:58');
INSERT INTO `user_award_record_001` VALUES (69, 'xiaofuge', 100301, 100006, '730186113832', 107, '2等奖', '2024-04-20 07:43:13', 'create', '2024-04-20 15:43:13', '2024-04-20 15:43:13');
INSERT INTO `user_award_record_001` VALUES (70, 'xiaofuge', 100301, 100006, '472281891603', 106, '3等奖', '2024-04-20 08:50:39', 'create', '2024-04-20 16:50:39', '2024-04-20 16:50:39');
INSERT INTO `user_award_record_001` VALUES (71, 'xiaofuge', 100301, 100006, '931181504757', 108, '暴走玩偶', '2024-04-27 05:19:39', 'create', '2024-04-27 13:19:38', '2024-04-27 13:19:38');
INSERT INTO `user_award_record_001` VALUES (72, 'xiaofuge', 100301, 100006, '664993621684', 101, '随机积分', '2024-04-27 05:27:17', 'create', '2024-04-27 13:27:17', '2024-04-27 13:27:17');
INSERT INTO `user_award_record_001` VALUES (73, 'xiaofuge', 100301, 100006, '757674779249', 104, '小米台灯', '2024-04-27 05:27:45', 'create', '2024-04-27 13:27:45', '2024-04-27 13:27:45');
INSERT INTO `user_award_record_001` VALUES (74, 'xiaofuge', 100301, 100006, '623885952534', 102, 'OpenAI会员卡', '2024-04-27 05:28:02', 'create', '2024-04-27 13:28:01', '2024-04-27 13:28:01');
INSERT INTO `user_award_record_001` VALUES (75, 'xiaofuge', 100301, 100006, '351875766756', 106, '轻奢办公椅', '2024-04-27 05:29:05', 'create', '2024-04-27 13:29:05', '2024-04-27 13:29:05');
INSERT INTO `user_award_record_001` VALUES (76, 'xiaofuge', 100301, 100006, '803227763198', 103, '支付优惠券', '2024-04-27 05:29:32', 'create', '2024-04-27 13:29:31', '2024-04-27 13:29:31');
INSERT INTO `user_award_record_001` VALUES (77, 'xiaofuge', 100301, 100006, '587527322073', 106, '轻奢办公椅', '2024-04-27 05:29:57', 'create', '2024-04-27 13:29:57', '2024-04-27 13:29:57');
INSERT INTO `user_award_record_001` VALUES (78, 'xiaofuge', 100301, 100006, '552928609772', 107, '小霸王游戏机', '2024-04-27 05:30:11', 'create', '2024-04-27 13:30:11', '2024-04-27 13:30:11');
INSERT INTO `user_award_record_001` VALUES (79, 'xiaofuge', 100301, 100006, '407462568156', 107, '小霸王游戏机', '2024-04-27 05:36:27', 'create', '2024-04-27 13:36:27', '2024-04-27 13:36:27');
INSERT INTO `user_award_record_001` VALUES (80, 'xiaofuge', 100301, 100006, '688519386935', 101, '随机积分', '2024-04-27 05:38:00', 'create', '2024-04-27 13:38:00', '2024-04-27 13:38:00');
INSERT INTO `user_award_record_001` VALUES (81, 'xiaofuge', 100301, 100006, '148984382545', 104, '小米台灯', '2024-04-27 05:38:56', 'create', '2024-04-27 13:38:55', '2024-04-27 13:38:55');
INSERT INTO `user_award_record_001` VALUES (82, 'xiaofuge', 100301, 100006, '410701479648', 101, '随机积分', '2024-04-27 05:39:18', 'create', '2024-04-27 13:39:18', '2024-04-27 13:39:18');
INSERT INTO `user_award_record_001` VALUES (83, 'xiaofuge', 100301, 100006, '521226371540', 101, '随机积分', '2024-04-27 06:59:56', 'create', '2024-04-27 14:59:56', '2024-04-27 14:59:56');
INSERT INTO `user_award_record_001` VALUES (84, 'xiaofuge', 100301, 100006, '167000751553', 102, 'OpenAI会员卡', '2024-04-27 07:00:14', 'create', '2024-04-27 15:00:14', '2024-04-27 15:00:14');
INSERT INTO `user_award_record_001` VALUES (85, 'xiaofuge', 100301, 100006, '685179511666', 104, '小米台灯', '2024-04-27 07:00:23', 'create', '2024-04-27 15:00:22', '2024-04-27 15:00:22');
INSERT INTO `user_award_record_001` VALUES (86, 'xiaofuge', 100301, 100006, '308424817839', 108, '暴走玩偶', '2024-05-01 06:57:39', 'create', '2024-05-01 14:57:39', '2024-05-01 14:57:39');
INSERT INTO `user_award_record_001` VALUES (87, 'xiaofuge', 100301, 100006, '116865823300', 101, '随机积分', '2024-05-01 06:58:09', 'create', '2024-05-01 14:58:08', '2024-05-01 14:58:08');
INSERT INTO `user_award_record_001` VALUES (88, 'xiaofuge', 100301, 100006, '272157347851', 107, '小霸王游戏机', '2024-05-01 06:59:33', 'create', '2024-05-01 14:59:33', '2024-05-01 14:59:33');
INSERT INTO `user_award_record_001` VALUES (89, 'xiaofuge', 100301, 100006, '400772556300', 103, '支付优惠券', '2024-05-01 06:59:33', 'create', '2024-05-01 14:59:33', '2024-05-01 14:59:33');
INSERT INTO `user_award_record_001` VALUES (90, 'xiaofuge', 100301, 100006, '494705137752', 101, '随机积分', '2024-05-01 06:59:33', 'create', '2024-05-01 14:59:33', '2024-05-01 14:59:33');
INSERT INTO `user_award_record_001` VALUES (91, 'xiaofuge', 100301, 100006, '728609897262', 106, '轻奢办公椅', '2024-05-01 06:59:34', 'create', '2024-05-01 14:59:33', '2024-05-01 14:59:33');
INSERT INTO `user_award_record_001` VALUES (92, 'xiaofuge', 100301, 100006, '085750418120', 104, '小米台灯', '2024-05-01 06:59:34', 'create', '2024-05-01 14:59:33', '2024-05-01 14:59:33');
INSERT INTO `user_award_record_001` VALUES (93, 'xiaofuge', 100301, 100006, '884615410376', 107, '小霸王游戏机', '2024-05-01 06:59:34', 'create', '2024-05-01 14:59:33', '2024-05-01 14:59:33');
INSERT INTO `user_award_record_001` VALUES (94, 'xiaofuge', 100301, 100006, '380693771158', 107, '小霸王游戏机', '2024-05-01 06:59:34', 'create', '2024-05-01 14:59:33', '2024-05-01 14:59:33');
INSERT INTO `user_award_record_001` VALUES (95, 'xiaofuge', 100301, 100006, '801793933954', 103, '支付优惠券', '2024-05-01 06:59:34', 'create', '2024-05-01 14:59:33', '2024-05-01 14:59:33');
INSERT INTO `user_award_record_001` VALUES (96, 'xiaofuge', 100301, 100006, '153569048026', 108, '暴走玩偶', '2024-05-01 06:59:34', 'create', '2024-05-01 14:59:33', '2024-05-01 14:59:33');
INSERT INTO `user_award_record_001` VALUES (97, 'xiaofuge', 100301, 100006, '239541957386', 108, '暴走玩偶', '2024-05-01 06:59:34', 'create', '2024-05-01 14:59:33', '2024-05-01 14:59:33');
INSERT INTO `user_award_record_001` VALUES (98, 'xiaofuge', 100301, 100006, '417247136950', 108, '暴走玩偶', '2024-05-01 06:59:34', 'create', '2024-05-01 14:59:34', '2024-05-01 14:59:34');
INSERT INTO `user_award_record_001` VALUES (99, 'xiaofuge', 100301, 100006, '556248667355', 107, '小霸王游戏机', '2024-05-01 06:59:34', 'create', '2024-05-01 14:59:34', '2024-05-01 14:59:34');
INSERT INTO `user_award_record_001` VALUES (100, 'xiaofuge', 100301, 100006, '828955445464', 107, '小霸王游戏机', '2024-05-01 06:59:34', 'create', '2024-05-01 14:59:34', '2024-05-01 14:59:34');
INSERT INTO `user_award_record_001` VALUES (101, 'xiaofuge', 100301, 100006, '756110942449', 102, 'OpenAI会员卡', '2024-05-01 06:59:34', 'create', '2024-05-01 14:59:34', '2024-05-01 14:59:34');
INSERT INTO `user_award_record_001` VALUES (102, 'xiaofuge', 100301, 100006, '440069371435', 101, '随机积分', '2024-05-01 06:59:34', 'create', '2024-05-01 14:59:34', '2024-05-01 14:59:34');
INSERT INTO `user_award_record_001` VALUES (103, 'xiaofuge', 100301, 100006, '421594084633', 108, '暴走玩偶', '2024-05-01 06:59:34', 'create', '2024-05-01 14:59:34', '2024-05-01 14:59:34');
INSERT INTO `user_award_record_001` VALUES (104, 'xiaofuge', 100301, 100006, '500905040429', 104, '小米台灯', '2024-05-01 06:59:34', 'create', '2024-05-01 14:59:34', '2024-05-01 14:59:34');
INSERT INTO `user_award_record_001` VALUES (105, 'xiaofuge', 100301, 100006, '712386571628', 101, '随机积分', '2024-05-01 06:59:34', 'create', '2024-05-01 14:59:34', '2024-05-01 14:59:34');
INSERT INTO `user_award_record_001` VALUES (106, 'xiaofuge', 100301, 100006, '095923542021', 108, '暴走玩偶', '2024-05-01 06:59:35', 'create', '2024-05-01 14:59:34', '2024-05-01 14:59:34');
INSERT INTO `user_award_record_001` VALUES (107, 'xiaofuge', 100301, 100006, '306233708878', 104, '小米台灯', '2024-05-03 01:02:18', 'create', '2024-05-03 09:02:17', '2024-05-03 09:02:17');
INSERT INTO `user_award_record_001` VALUES (108, 'xiaofuge', 100301, 100006, '690124733440', 101, '随机积分', '2024-05-03 01:09:57', 'create', '2024-05-03 09:09:57', '2024-05-03 09:09:57');
INSERT INTO `user_award_record_001` VALUES (109, 'xiaofuge', 100301, 100006, '190487161872', 102, 'OpenAI会员卡', '2024-05-03 01:10:35', 'create', '2024-05-03 09:10:35', '2024-05-03 09:10:35');
INSERT INTO `user_award_record_001` VALUES (110, 'xiaofuge', 100301, 100006, '693117324295', 102, 'OpenAI会员卡', '2024-05-03 01:11:38', 'create', '2024-05-03 09:11:38', '2024-05-03 09:11:38');

-- ----------------------------
-- Table structure for user_award_record_002
-- ----------------------------
DROP TABLE IF EXISTS `user_award_record_002`;
CREATE TABLE `user_award_record_002`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
  `strategy_id` bigint(8) NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '抽奖订单ID【作为幂等使用】',
  `award_id` int(11) NOT NULL COMMENT '奖品ID',
  `award_title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '奖品标题（名称）',
  `award_time` datetime NOT NULL COMMENT '中奖时间',
  `award_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'create' COMMENT '奖品状态；create-创建、completed-发奖完成',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_activity_id`(`activity_id`) USING BTREE,
  INDEX `idx_award_id`(`strategy_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户中奖记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_award_record_002
-- ----------------------------
INSERT INTO `user_award_record_002` VALUES (1, 'user003', 100301, 100006, '999182993606', 101, '随机积分', '2024-08-10 16:16:21', 'complete', '2024-08-11 00:16:21', '2024-08-11 00:16:21');

-- ----------------------------
-- Table structure for user_award_record_003
-- ----------------------------
DROP TABLE IF EXISTS `user_award_record_003`;
CREATE TABLE `user_award_record_003`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
  `strategy_id` bigint(8) NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '抽奖订单ID【作为幂等使用】',
  `award_id` int(11) NOT NULL COMMENT '奖品ID',
  `award_title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '奖品标题（名称）',
  `award_time` datetime NOT NULL COMMENT '中奖时间',
  `award_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'create' COMMENT '奖品状态；create-创建、completed-发奖完成',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_activity_id`(`activity_id`) USING BTREE,
  INDEX `idx_award_id`(`strategy_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户中奖记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_award_record_003
-- ----------------------------
INSERT INTO `user_award_record_003` VALUES (1, 'user004', 100301, 100006, '243204132453', 102, 'OpenAI会员卡', '2024-08-10 16:22:27', 'create', '2024-08-11 00:22:27', '2024-08-11 00:22:27');

-- ----------------------------
-- Table structure for user_behavior_rebate_order_000
-- ----------------------------
DROP TABLE IF EXISTS `user_behavior_rebate_order_000`;
CREATE TABLE `user_behavior_rebate_order_000`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单ID',
  `behavior_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '行为类型（sign 签到、openai_pay 支付）',
  `rebate_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '返利描述',
  `rebate_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '返利类型（sku 活动库存充值商品、integral 用户活动积分）',
  `rebate_config` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '返利配置【sku值，积分值】',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务仿重ID - 外部透传，方便查询使用',
  `biz_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务ID - 拼接的唯一值。拼接 out_business_no + 自身枚举',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id`) USING BTREE,
  UNIQUE INDEX `uq_biz_id`(`biz_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户行为返利流水订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_behavior_rebate_order_000
-- ----------------------------

-- ----------------------------
-- Table structure for user_behavior_rebate_order_001
-- ----------------------------
DROP TABLE IF EXISTS `user_behavior_rebate_order_001`;
CREATE TABLE `user_behavior_rebate_order_001`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单ID',
  `behavior_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '行为类型（sign 签到、openai_pay 支付）',
  `rebate_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '返利描述',
  `rebate_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '返利类型（sku 活动库存充值商品、integral 用户活动积分）',
  `rebate_config` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '返利配置【sku值，积分值】',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务仿重ID - 外部透传，方便查询使用',
  `biz_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务ID - 拼接的唯一值。拼接 out_business_no + 自身枚举',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id`) USING BTREE,
  UNIQUE INDEX `uq_biz_id`(`biz_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户行为返利流水订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_behavior_rebate_order_001
-- ----------------------------
INSERT INTO `user_behavior_rebate_order_001` VALUES (5, 'xiaofuge', '630841674684', 'sign', '签到返利-sku额度', 'sku', '9011', '20240503', 'xiaofuge_sku_20240503', '2024-05-03 13:28:42', '2024-05-03 13:28:42');
INSERT INTO `user_behavior_rebate_order_001` VALUES (6, 'xiaofuge', '552413408368', 'sign', '签到返利-积分', 'integral', '10', '20240503', 'xiaofuge_integral_20240503', '2024-05-03 13:28:42', '2024-05-03 13:28:42');
INSERT INTO `user_behavior_rebate_order_001` VALUES (7, 'xiaofuge', '494645987202', 'sign', '签到返利-sku额度', 'sign', '9011', '715091006810', 'xiaofuge_sku_715091006810', '2024-08-13 21:06:22', '2024-08-13 21:06:22');
INSERT INTO `user_behavior_rebate_order_001` VALUES (8, 'xiaofuge', '020480103619', 'sign', '签到返利-积分', 'sign', '10', '715091006810', 'xiaofuge_integral_715091006810', '2024-08-13 21:06:23', '2024-08-13 21:06:23');
INSERT INTO `user_behavior_rebate_order_001` VALUES (9, 'xiaofuge', '897725909629', 'sign', '签到返利-sku额度', 'sign', '9011', '353583185102', 'xiaofuge_sku_353583185102', '2024-08-13 21:07:49', '2024-08-13 21:07:49');
INSERT INTO `user_behavior_rebate_order_001` VALUES (10, 'xiaofuge', '213174769693', 'sign', '签到返利-积分', 'sign', '10', '353583185102', 'xiaofuge_integral_353583185102', '2024-08-13 21:07:49', '2024-08-13 21:07:49');

-- ----------------------------
-- Table structure for user_behavior_rebate_order_002
-- ----------------------------
DROP TABLE IF EXISTS `user_behavior_rebate_order_002`;
CREATE TABLE `user_behavior_rebate_order_002`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单ID',
  `behavior_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '行为类型（sign 签到、openai_pay 支付）',
  `rebate_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '返利描述',
  `rebate_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '返利类型（sku 活动库存充值商品、integral 用户活动积分）',
  `rebate_config` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '返利配置【sku值，积分值】',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务仿重ID - 外部透传，方便查询使用',
  `biz_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务ID - 拼接的唯一值。拼接 out_business_no + 自身枚举',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id`) USING BTREE,
  UNIQUE INDEX `uq_biz_id`(`biz_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户行为返利流水订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_behavior_rebate_order_002
-- ----------------------------
INSERT INTO `user_behavior_rebate_order_002` VALUES (1, 'user003', '370455016780', 'sign', '签到返利-sku额度', 'sign', '9011', '20240811', 'user003_sku_20240811', '2024-08-11 00:13:44', '2024-08-11 00:13:44');
INSERT INTO `user_behavior_rebate_order_002` VALUES (2, 'user003', '396848459817', 'sign', '签到返利-积分', 'sign', '10', '20240811', 'user003_integral_20240811', '2024-08-11 00:13:44', '2024-08-11 00:13:44');

-- ----------------------------
-- Table structure for user_behavior_rebate_order_003
-- ----------------------------
DROP TABLE IF EXISTS `user_behavior_rebate_order_003`;
CREATE TABLE `user_behavior_rebate_order_003`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单ID',
  `behavior_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '行为类型（sign 签到、openai_pay 支付）',
  `rebate_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '返利描述',
  `rebate_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '返利类型（sku 活动库存充值商品、integral 用户活动积分）',
  `rebate_config` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '返利配置【sku值，积分值】',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务仿重ID - 外部透传，方便查询使用',
  `biz_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务ID - 拼接的唯一值。拼接 out_business_no + 自身枚举',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id`) USING BTREE,
  UNIQUE INDEX `uq_biz_id`(`biz_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户行为返利流水订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_behavior_rebate_order_003
-- ----------------------------
INSERT INTO `user_behavior_rebate_order_003` VALUES (1, 'user004', '603069553552', 'sign', '签到返利-sku额度', 'sign', '9011', '20240811', 'user004_sku_20240811', '2024-08-11 00:15:05', '2024-08-11 00:15:05');
INSERT INTO `user_behavior_rebate_order_003` VALUES (2, 'user004', '045377791685', 'sign', '签到返利-积分', 'sign', '10', '20240811', 'user004_integral_20240811', '2024-08-11 00:15:05', '2024-08-11 00:15:05');

-- ----------------------------
-- Table structure for user_credit_account
-- ----------------------------
DROP TABLE IF EXISTS `user_credit_account`;
CREATE TABLE `user_credit_account`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `total_amount` decimal(10, 2) NOT NULL COMMENT '总积分，显示总账户值，记得一个人获得的总积分',
  `available_amount` decimal(10, 2) NOT NULL COMMENT '可用积分，每次扣减的值',
  `account_status` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账户状态【open - 可用，close - 冻结】',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户积分账户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_credit_account
-- ----------------------------
INSERT INTO `user_credit_account` VALUES (7, 'xiaofuge', 10.00, 10.00, 'open', '2024-08-13 21:07:49', '2024-08-13 21:07:49');

-- ----------------------------
-- Table structure for user_credit_order_000
-- ----------------------------
DROP TABLE IF EXISTS `user_credit_order_000`;
CREATE TABLE `user_credit_order_000`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单ID',
  `trade_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '交易名称',
  `trade_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'forward' COMMENT '交易类型；forward-正向、reverse-逆向',
  `trade_amount` decimal(10, 2) NOT NULL COMMENT '交易金额',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务仿重ID - 外部透传。返利、行为等唯一标识',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id`) USING BTREE,
  UNIQUE INDEX `uq_out_business_no`(`out_business_no`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户积分订单记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_credit_order_000
-- ----------------------------

-- ----------------------------
-- Table structure for user_credit_order_001
-- ----------------------------
DROP TABLE IF EXISTS `user_credit_order_001`;
CREATE TABLE `user_credit_order_001`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单ID',
  `trade_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '交易名称',
  `trade_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'forward' COMMENT '交易类型；forward-正向、reverse-逆向',
  `trade_amount` decimal(10, 2) NOT NULL COMMENT '交易金额',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务仿重ID - 外部透传。返利、行为等唯一标识',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id`) USING BTREE,
  UNIQUE INDEX `uq_out_business_no`(`out_business_no`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户积分订单记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_credit_order_001
-- ----------------------------
INSERT INTO `user_credit_order_001` VALUES (11, 'xiaofuge', '332696163260', '行为返利', 'forward', 10.00, 'xiaofuge_integral_353583185102', '2024-08-13 21:07:49', '2024-08-13 21:07:49');

-- ----------------------------
-- Table structure for user_credit_order_002
-- ----------------------------
DROP TABLE IF EXISTS `user_credit_order_002`;
CREATE TABLE `user_credit_order_002`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单ID',
  `trade_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '交易名称',
  `trade_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'forward' COMMENT '交易类型；forward-正向、reverse-逆向',
  `trade_amount` decimal(10, 2) NOT NULL COMMENT '交易金额',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务仿重ID - 外部透传。返利、行为等唯一标识',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id`) USING BTREE,
  UNIQUE INDEX `uq_out_business_no`(`out_business_no`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户积分订单记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_credit_order_002
-- ----------------------------

-- ----------------------------
-- Table structure for user_credit_order_003
-- ----------------------------
DROP TABLE IF EXISTS `user_credit_order_003`;
CREATE TABLE `user_credit_order_003`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单ID',
  `trade_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '交易名称',
  `trade_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'forward' COMMENT '交易类型；forward-正向、reverse-逆向',
  `trade_amount` decimal(10, 2) NOT NULL COMMENT '交易金额',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务仿重ID - 外部透传。返利、行为等唯一标识',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id`) USING BTREE,
  UNIQUE INDEX `uq_out_business_no`(`out_business_no`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户积分订单记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_credit_order_003
-- ----------------------------

-- ----------------------------
-- Table structure for user_raffle_order_000
-- ----------------------------
DROP TABLE IF EXISTS `user_raffle_order_000`;
CREATE TABLE `user_raffle_order_000`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '活动名称',
  `strategy_id` bigint(8) NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `order_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'create' COMMENT '订单状态；create-创建、used-已使用、cancel-已作废',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id`) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id`, `activity_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户抽奖订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_raffle_order_000
-- ----------------------------

-- ----------------------------
-- Table structure for user_raffle_order_001
-- ----------------------------
DROP TABLE IF EXISTS `user_raffle_order_001`;
CREATE TABLE `user_raffle_order_001`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '活动名称',
  `strategy_id` bigint(8) NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `order_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'create' COMMENT '订单状态；create-创建、used-已使用、cancel-已作废',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id`) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id`, `activity_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 77 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户抽奖订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_raffle_order_001
-- ----------------------------
INSERT INTO `user_raffle_order_001` VALUES (5, 'xiaofuge', 100301, '测试活动', 100006, '569856975978', '2024-04-08 14:52:47', 'used', '2024-04-08 22:52:47', '2024-04-13 11:44:01');
INSERT INTO `user_raffle_order_001` VALUES (6, 'xiaofuge', 100301, '测试活动', 100006, '867136698684', '2024-04-13 03:44:07', 'used', '2024-04-13 11:44:10', '2024-04-13 11:44:10');
INSERT INTO `user_raffle_order_001` VALUES (7, 'xiaofuge', 100301, '测试活动', 100006, '250425115608', '2024-04-13 03:44:14', 'used', '2024-04-13 11:44:14', '2024-04-13 11:44:14');
INSERT INTO `user_raffle_order_001` VALUES (8, 'xiaofuge', 100301, '测试活动', 100006, '663910993767', '2024-04-13 03:44:16', 'used', '2024-04-13 11:44:15', '2024-04-13 11:44:15');
INSERT INTO `user_raffle_order_001` VALUES (9, 'xiaofuge', 100301, '测试活动', 100006, '218374542905', '2024-04-13 03:44:17', 'used', '2024-04-13 11:44:16', '2024-04-13 11:44:16');
INSERT INTO `user_raffle_order_001` VALUES (10, 'xiaofuge', 100301, '测试活动', 100006, '225714694474', '2024-04-13 03:44:18', 'used', '2024-04-13 11:44:17', '2024-04-13 11:44:17');
INSERT INTO `user_raffle_order_001` VALUES (11, 'xiaofuge', 100301, '测试活动', 100006, '431167372778', '2024-04-13 03:44:19', 'used', '2024-04-13 11:44:18', '2024-04-13 11:44:18');
INSERT INTO `user_raffle_order_001` VALUES (12, 'xiaofuge', 100301, '测试活动', 100006, '434079846497', '2024-04-13 03:45:30', 'used', '2024-04-13 11:45:29', '2024-04-13 11:45:29');
INSERT INTO `user_raffle_order_001` VALUES (13, 'xiaofuge', 100301, '测试活动', 100006, '965685676429', '2024-04-13 03:45:31', 'used', '2024-04-13 11:45:30', '2024-04-13 11:45:30');
INSERT INTO `user_raffle_order_001` VALUES (14, 'xiaofuge', 100301, '测试活动', 100006, '940273728363', '2024-04-13 04:14:53', 'used', '2024-04-13 12:14:53', '2024-04-13 12:14:53');
INSERT INTO `user_raffle_order_001` VALUES (15, 'xiaofuge', 100301, '测试活动', 100006, '240699246294', '2024-04-13 04:16:20', 'used', '2024-04-13 12:16:19', '2024-04-13 12:17:33');
INSERT INTO `user_raffle_order_001` VALUES (16, 'xiaofuge', 100301, '测试活动', 100006, '298101180210', '2024-04-13 04:18:05', 'used', '2024-04-13 12:18:05', '2024-04-13 12:18:16');
INSERT INTO `user_raffle_order_001` VALUES (17, 'xiaofuge', 100301, '测试活动', 100006, '565655944488', '2024-04-13 04:18:23', 'used', '2024-04-13 12:18:23', '2024-04-13 12:18:23');
INSERT INTO `user_raffle_order_001` VALUES (18, 'xiaofuge', 100301, '测试活动', 100006, '090289257534', '2024-04-13 04:18:25', 'used', '2024-04-13 12:18:24', '2024-04-13 12:18:24');
INSERT INTO `user_raffle_order_001` VALUES (19, 'xiaofuge', 100301, '测试活动', 100006, '668356046426', '2024-04-13 04:18:26', 'used', '2024-04-13 12:18:25', '2024-04-13 12:18:25');
INSERT INTO `user_raffle_order_001` VALUES (20, 'xiaofuge', 100301, '测试活动', 100006, '745680068300', '2024-04-13 04:18:27', 'used', '2024-04-13 12:18:26', '2024-04-13 12:18:36');
INSERT INTO `user_raffle_order_001` VALUES (21, 'xiaofuge', 100301, '测试活动', 100006, '285300597983', '2024-04-13 04:20:07', 'used', '2024-04-13 12:20:07', '2024-04-13 12:20:07');
INSERT INTO `user_raffle_order_001` VALUES (22, 'xiaofuge', 100301, '测试活动', 100006, '999361306023', '2024-04-13 04:20:10', 'used', '2024-04-13 12:20:09', '2024-04-13 12:20:09');
INSERT INTO `user_raffle_order_001` VALUES (23, 'xiaofuge', 100301, '测试活动', 100006, '063682699381', '2024-04-13 04:20:14', 'used', '2024-04-13 12:20:13', '2024-04-13 12:20:13');
INSERT INTO `user_raffle_order_001` VALUES (24, 'xiaofuge', 100301, '测试活动', 100006, '680488311338', '2024-04-13 04:20:15', 'used', '2024-04-13 12:20:14', '2024-04-13 12:20:14');
INSERT INTO `user_raffle_order_001` VALUES (25, 'xiaofuge', 100301, '测试活动', 100006, '399058527457', '2024-04-13 04:20:16', 'used', '2024-04-13 12:20:16', '2024-04-13 12:20:16');
INSERT INTO `user_raffle_order_001` VALUES (26, 'xiaofuge', 100301, '测试活动', 100006, '579122416749', '2024-04-13 04:20:17', 'used', '2024-04-13 12:20:17', '2024-04-13 12:20:20');
INSERT INTO `user_raffle_order_001` VALUES (27, 'xiaofuge', 100301, '测试活动', 100006, '854484054432', '2024-04-13 06:03:59', 'used', '2024-04-13 14:03:59', '2024-04-13 14:03:59');
INSERT INTO `user_raffle_order_001` VALUES (28, 'xiaofuge', 100301, '测试活动', 100006, '066823147917', '2024-04-13 07:24:59', 'used', '2024-04-13 15:25:05', '2024-04-13 15:25:06');
INSERT INTO `user_raffle_order_001` VALUES (29, 'xiaofuge', 100301, '测试活动', 100006, '022620846137', '2024-04-13 07:25:11', 'used', '2024-04-13 15:25:11', '2024-04-13 15:25:11');
INSERT INTO `user_raffle_order_001` VALUES (30, 'xiaofuge', 100301, '测试活动', 100006, '605666354632', '2024-04-13 07:25:12', 'used', '2024-04-13 15:25:12', '2024-04-13 15:25:12');
INSERT INTO `user_raffle_order_001` VALUES (31, 'xiaofuge', 100301, '测试活动', 100006, '604661560037', '2024-04-13 07:25:13', 'used', '2024-04-13 15:25:13', '2024-04-13 15:25:21');
INSERT INTO `user_raffle_order_001` VALUES (32, 'xiaofuge', 100301, '测试活动', 100006, '212448652580', '2024-04-20 04:09:30', 'used', '2024-04-20 12:09:30', '2024-04-20 12:09:30');
INSERT INTO `user_raffle_order_001` VALUES (33, 'xiaofuge', 100301, '测试活动', 100006, '356729331179', '2024-04-20 04:11:24', 'used', '2024-04-20 12:11:24', '2024-04-20 12:11:24');
INSERT INTO `user_raffle_order_001` VALUES (34, 'xiaofuge', 100301, '测试活动', 100006, '102669494145', '2024-04-20 07:10:44', 'used', '2024-04-20 15:10:44', '2024-04-20 15:10:58');
INSERT INTO `user_raffle_order_001` VALUES (35, 'xiaofuge', 100301, '测试活动', 100006, '730186113832', '2024-04-20 07:43:12', 'used', '2024-04-20 15:43:12', '2024-04-20 15:43:13');
INSERT INTO `user_raffle_order_001` VALUES (36, 'xiaofuge', 100301, '测试活动', 100006, '472281891603', '2024-04-20 08:50:37', 'used', '2024-04-20 16:50:38', '2024-04-20 16:50:39');
INSERT INTO `user_raffle_order_001` VALUES (37, 'xiaofuge', 100301, '测试活动', 100006, '931181504757', '2024-04-27 05:19:26', 'used', '2024-04-27 13:19:26', '2024-04-27 13:19:38');
INSERT INTO `user_raffle_order_001` VALUES (38, 'xiaofuge', 100301, '测试活动', 100006, '664993621684', '2024-04-27 05:26:49', 'used', '2024-04-27 13:26:49', '2024-04-27 13:27:17');
INSERT INTO `user_raffle_order_001` VALUES (39, 'xiaofuge', 100301, '测试活动', 100006, '757674779249', '2024-04-27 05:27:45', 'used', '2024-04-27 13:27:45', '2024-04-27 13:27:45');
INSERT INTO `user_raffle_order_001` VALUES (40, 'xiaofuge', 100301, '测试活动', 100006, '623885952534', '2024-04-27 05:28:02', 'used', '2024-04-27 13:28:01', '2024-04-27 13:28:01');
INSERT INTO `user_raffle_order_001` VALUES (41, 'xiaofuge', 100301, '测试活动', 100006, '351875766756', '2024-04-27 05:29:05', 'used', '2024-04-27 13:29:05', '2024-04-27 13:29:05');
INSERT INTO `user_raffle_order_001` VALUES (42, 'xiaofuge', 100301, '测试活动', 100006, '803227763198', '2024-04-27 05:29:32', 'used', '2024-04-27 13:29:31', '2024-04-27 13:29:31');
INSERT INTO `user_raffle_order_001` VALUES (43, 'xiaofuge', 100301, '测试活动', 100006, '587527322073', '2024-04-27 05:29:57', 'used', '2024-04-27 13:29:57', '2024-04-27 13:29:57');
INSERT INTO `user_raffle_order_001` VALUES (44, 'xiaofuge', 100301, '测试活动', 100006, '552928609772', '2024-04-27 05:30:11', 'used', '2024-04-27 13:30:11', '2024-04-27 13:30:11');
INSERT INTO `user_raffle_order_001` VALUES (45, 'xiaofuge', 100301, '测试活动', 100006, '407462568156', '2024-04-27 05:36:27', 'used', '2024-04-27 13:36:27', '2024-04-27 13:36:27');
INSERT INTO `user_raffle_order_001` VALUES (46, 'xiaofuge', 100301, '测试活动', 100006, '688519386935', '2024-04-27 05:38:00', 'used', '2024-04-27 13:38:00', '2024-04-27 13:38:00');
INSERT INTO `user_raffle_order_001` VALUES (47, 'xiaofuge', 100301, '测试活动', 100006, '148984382545', '2024-04-27 05:38:56', 'used', '2024-04-27 13:38:55', '2024-04-27 13:38:55');
INSERT INTO `user_raffle_order_001` VALUES (48, 'xiaofuge', 100301, '测试活动', 100006, '410701479648', '2024-04-27 05:39:18', 'used', '2024-04-27 13:39:18', '2024-04-27 13:39:18');
INSERT INTO `user_raffle_order_001` VALUES (49, 'xiaofuge', 100301, '测试活动', 100006, '521226371540', '2024-04-27 06:59:56', 'used', '2024-04-27 14:59:56', '2024-04-27 14:59:56');
INSERT INTO `user_raffle_order_001` VALUES (50, 'xiaofuge', 100301, '测试活动', 100006, '167000751553', '2024-04-27 07:00:14', 'used', '2024-04-27 15:00:14', '2024-04-27 15:00:14');
INSERT INTO `user_raffle_order_001` VALUES (51, 'xiaofuge', 100301, '测试活动', 100006, '685179511666', '2024-04-27 07:00:23', 'used', '2024-04-27 15:00:22', '2024-04-27 15:00:22');
INSERT INTO `user_raffle_order_001` VALUES (52, 'xiaofuge', 100301, '测试活动', 100006, '308424817839', '2024-05-01 06:33:42', 'used', '2024-05-01 14:33:43', '2024-05-01 14:57:39');
INSERT INTO `user_raffle_order_001` VALUES (53, 'xiaofuge', 100301, '测试活动', 100006, '116865823300', '2024-05-01 06:58:08', 'used', '2024-05-01 14:58:08', '2024-05-01 14:58:08');
INSERT INTO `user_raffle_order_001` VALUES (54, 'xiaofuge', 100301, '测试活动', 100006, '272157347851', '2024-05-01 06:59:32', 'used', '2024-05-01 14:59:32', '2024-05-01 14:59:33');
INSERT INTO `user_raffle_order_001` VALUES (55, 'xiaofuge', 100301, '测试活动', 100006, '400772556300', '2024-05-01 06:59:33', 'used', '2024-05-01 14:59:33', '2024-05-01 14:59:33');
INSERT INTO `user_raffle_order_001` VALUES (56, 'xiaofuge', 100301, '测试活动', 100006, '494705137752', '2024-05-01 06:59:33', 'used', '2024-05-01 14:59:33', '2024-05-01 14:59:33');
INSERT INTO `user_raffle_order_001` VALUES (57, 'xiaofuge', 100301, '测试活动', 100006, '728609897262', '2024-05-01 06:59:34', 'used', '2024-05-01 14:59:33', '2024-05-01 14:59:33');
INSERT INTO `user_raffle_order_001` VALUES (58, 'xiaofuge', 100301, '测试活动', 100006, '085750418120', '2024-05-01 06:59:34', 'used', '2024-05-01 14:59:33', '2024-05-01 14:59:33');
INSERT INTO `user_raffle_order_001` VALUES (59, 'xiaofuge', 100301, '测试活动', 100006, '884615410376', '2024-05-01 06:59:34', 'used', '2024-05-01 14:59:33', '2024-05-01 14:59:33');
INSERT INTO `user_raffle_order_001` VALUES (60, 'xiaofuge', 100301, '测试活动', 100006, '380693771158', '2024-05-01 06:59:34', 'used', '2024-05-01 14:59:33', '2024-05-01 14:59:33');
INSERT INTO `user_raffle_order_001` VALUES (61, 'xiaofuge', 100301, '测试活动', 100006, '801793933954', '2024-05-01 06:59:34', 'used', '2024-05-01 14:59:33', '2024-05-01 14:59:33');
INSERT INTO `user_raffle_order_001` VALUES (62, 'xiaofuge', 100301, '测试活动', 100006, '153569048026', '2024-05-01 06:59:34', 'used', '2024-05-01 14:59:33', '2024-05-01 14:59:33');
INSERT INTO `user_raffle_order_001` VALUES (63, 'xiaofuge', 100301, '测试活动', 100006, '239541957386', '2024-05-01 06:59:34', 'used', '2024-05-01 14:59:33', '2024-05-01 14:59:33');
INSERT INTO `user_raffle_order_001` VALUES (64, 'xiaofuge', 100301, '测试活动', 100006, '417247136950', '2024-05-01 06:59:34', 'used', '2024-05-01 14:59:33', '2024-05-01 14:59:34');
INSERT INTO `user_raffle_order_001` VALUES (65, 'xiaofuge', 100301, '测试活动', 100006, '556248667355', '2024-05-01 06:59:34', 'used', '2024-05-01 14:59:34', '2024-05-01 14:59:34');
INSERT INTO `user_raffle_order_001` VALUES (66, 'xiaofuge', 100301, '测试活动', 100006, '828955445464', '2024-05-01 06:59:34', 'used', '2024-05-01 14:59:34', '2024-05-01 14:59:34');
INSERT INTO `user_raffle_order_001` VALUES (67, 'xiaofuge', 100301, '测试活动', 100006, '756110942449', '2024-05-01 06:59:34', 'used', '2024-05-01 14:59:34', '2024-05-01 14:59:34');
INSERT INTO `user_raffle_order_001` VALUES (68, 'xiaofuge', 100301, '测试活动', 100006, '440069371435', '2024-05-01 06:59:34', 'used', '2024-05-01 14:59:34', '2024-05-01 14:59:34');
INSERT INTO `user_raffle_order_001` VALUES (69, 'xiaofuge', 100301, '测试活动', 100006, '421594084633', '2024-05-01 06:59:34', 'used', '2024-05-01 14:59:34', '2024-05-01 14:59:34');
INSERT INTO `user_raffle_order_001` VALUES (70, 'xiaofuge', 100301, '测试活动', 100006, '500905040429', '2024-05-01 06:59:34', 'used', '2024-05-01 14:59:34', '2024-05-01 14:59:34');
INSERT INTO `user_raffle_order_001` VALUES (71, 'xiaofuge', 100301, '测试活动', 100006, '712386571628', '2024-05-01 06:59:34', 'used', '2024-05-01 14:59:34', '2024-05-01 14:59:34');
INSERT INTO `user_raffle_order_001` VALUES (72, 'xiaofuge', 100301, '测试活动', 100006, '095923542021', '2024-05-01 06:59:34', 'used', '2024-05-01 14:59:34', '2024-05-01 14:59:34');
INSERT INTO `user_raffle_order_001` VALUES (73, 'xiaofuge', 100301, '测试活动', 100006, '306233708878', '2024-05-03 01:00:27', 'used', '2024-05-03 09:00:28', '2024-05-03 09:02:17');
INSERT INTO `user_raffle_order_001` VALUES (74, 'xiaofuge', 100301, '测试活动', 100006, '690124733440', '2024-05-03 01:09:42', 'used', '2024-05-03 09:09:42', '2024-05-03 09:09:57');
INSERT INTO `user_raffle_order_001` VALUES (75, 'xiaofuge', 100301, '测试活动', 100006, '190487161872', '2024-05-03 01:10:28', 'used', '2024-05-03 09:10:28', '2024-05-03 09:10:35');
INSERT INTO `user_raffle_order_001` VALUES (76, 'xiaofuge', 100301, '测试活动', 100006, '693117324295', '2024-05-03 01:11:32', 'used', '2024-05-03 09:11:32', '2024-05-03 09:11:38');

-- ----------------------------
-- Table structure for user_raffle_order_002
-- ----------------------------
DROP TABLE IF EXISTS `user_raffle_order_002`;
CREATE TABLE `user_raffle_order_002`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '活动名称',
  `strategy_id` bigint(8) NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `order_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'create' COMMENT '订单状态；create-创建、used-已使用、cancel-已作废',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id`) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id`, `activity_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户抽奖订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_raffle_order_002
-- ----------------------------
INSERT INTO `user_raffle_order_002` VALUES (1, 'user003', 100301, '测试活动', 100006, '999182993606', '2024-08-10 16:16:21', 'used', '2024-08-11 00:16:21', '2024-08-11 00:16:21');

-- ----------------------------
-- Table structure for user_raffle_order_003
-- ----------------------------
DROP TABLE IF EXISTS `user_raffle_order_003`;
CREATE TABLE `user_raffle_order_003`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '活动名称',
  `strategy_id` bigint(8) NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `order_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'create' COMMENT '订单状态；create-创建、used-已使用、cancel-已作废',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id`) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id`, `activity_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户抽奖订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_raffle_order_003
-- ----------------------------
INSERT INTO `user_raffle_order_003` VALUES (1, 'user004', 100301, '测试活动', 100006, '243204132453', '2024-08-10 16:22:27', 'used', '2024-08-11 00:22:27', '2024-08-11 00:22:27');

SET FOREIGN_KEY_CHECKS = 1;
