/*
 Navicat Premium Data Transfer

 Source Server         : localhost_MySQL
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : big_market_02

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 28/07/2024 19:41:25
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
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_user_id_activity_id`(`user_id`, `activity_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抽奖活动账户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of raffle_activity_account
-- ----------------------------
INSERT INTO `raffle_activity_account` VALUES (7, '咸鱼12138', 100301, 440, 436, 440, 436, 440, 436, '2024-07-25 20:19:41', '2024-07-28 12:53:45');

-- ----------------------------
-- Table structure for raffle_activity_account_day
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_account_day`;
CREATE TABLE `raffle_activity_account_day`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
  `day` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日期 (yyyy-mm-dd)',
  `day_count` int(8) NOT NULL COMMENT '日次数',
  `day_count_surplus` int(8) NOT NULL COMMENT '日次数-剩余',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_user_id_activity_id_day`(`user_id`, `activity_id`, `day`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抽奖活动账户表-日次数\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of raffle_activity_account_day
-- ----------------------------
INSERT INTO `raffle_activity_account_day` VALUES (1, '咸鱼12138', 100301, '2024-07-25', 20, 19, '2024-07-25 20:20:21', '2024-07-25 20:20:21');
INSERT INTO `raffle_activity_account_day` VALUES (2, '咸鱼12138', 100301, '2024-07-28', 20, 16, '2024-07-28 01:13:33', '2024-07-28 12:52:14');

-- ----------------------------
-- Table structure for raffle_activity_account_month
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_account_month`;
CREATE TABLE `raffle_activity_account_month`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf16 COLLATE utf16_general_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
  `month` varchar(7) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '月（yyyy-mm）',
  `month_count` int(8) NOT NULL COMMENT '月次数',
  `month_count_surplus` int(8) NOT NULL COMMENT '月次数-剩余',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_user_id_activity_id_month`(`user_id`, `activity_id`, `month`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抽奖活动账户表-月次数' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of raffle_activity_account_month
-- ----------------------------
INSERT INTO `raffle_activity_account_month` VALUES (1, '咸鱼12138', 100301, '2024-07', 20, 16, '2024-07-25 20:20:21', '2024-07-28 12:52:14');

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
-- Records of raffle_activity_order_001
-- ----------------------------

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
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'complete' COMMENT '订单状态（complete）',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务仿重ID - 外部透传的，确保幂等',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id`) USING BTREE,
  UNIQUE INDEX `uq_out_business_no`(`out_business_no`) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id`, `activity_id`, `state`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抽奖活动单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of raffle_activity_order_002
-- ----------------------------
INSERT INTO `raffle_activity_order_002` VALUES (6, '咸鱼12138', 9011, 100301, '测试活动', 100006, '659221761100', '2024-07-25 12:19:41', 20, 20, 20, 'completed', '70009100912', '2024-07-25 20:19:41', '2024-07-25 20:19:41');

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
-- Records of raffle_activity_order_003
-- ----------------------------

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户ID',
  `topic` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息主题',
  `message_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息ID',
  `message` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息主体',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'create' COMMENT '任务状态: create-创建、completed-完成、fail-失败',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of task
-- ----------------------------
INSERT INTO `task` VALUES (1, '咸鱼12138', 'send_award', '257129027390', '{\"data\":{\"awardId\":107,\"awardTitle\":\"2等奖\",\"userId\":\"咸鱼12138\"},\"id\":\"257129027390\",\"timestamp\":1722100413506}', 'complete', '2024-07-28 01:13:33', '2024-07-28 01:13:33');
INSERT INTO `task` VALUES (2, '咸鱼12138', 'send_award', '137714455808', '{\"data\":{\"awardId\":102,\"awardTitle\":\"7等奖\",\"userId\":\"咸鱼12138\"},\"id\":\"137714455808\",\"timestamp\":1722142335105}', 'complete', '2024-07-28 12:52:15', '2024-07-28 12:52:15');

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户中奖记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_award_record_001
-- ----------------------------

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
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户中奖记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_award_record_002
-- ----------------------------
INSERT INTO `user_award_record_002` VALUES (1, '咸鱼12138', 100301, 100006, '579716188993', 107, '2等奖', '2024-07-27 17:13:34', 'create', '2024-07-28 01:13:33', '2024-07-28 01:13:33');
INSERT INTO `user_award_record_002` VALUES (2, '咸鱼12138', 100301, 100006, '596655253727', 102, '7等奖', '2024-07-28 04:52:15', 'create', '2024-07-28 12:52:15', '2024-07-28 12:52:15');

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户中奖记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_award_record_003
-- ----------------------------

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
  `biz_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务ID - 拼接的唯一值',
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
  `biz_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务ID - 拼接的唯一值',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id`) USING BTREE,
  UNIQUE INDEX `uq_biz_id`(`biz_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户行为返利流水订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_behavior_rebate_order_001
-- ----------------------------
INSERT INTO `user_behavior_rebate_order_001` VALUES (1, 'xiaofuge', '833814327101', 'sign', '签到返利', 'sku', '9011', 'xiaofuge_sku_20240430', '2024-04-30 18:01:32', '2024-04-30 18:01:32');
INSERT INTO `user_behavior_rebate_order_001` VALUES (3, 'xiaofuge', '509399206701', 'sign', '签到返利-积分', 'integral', '10', 'xiaofuge_integral_20240430', '2024-04-30 18:05:44', '2024-04-30 18:05:44');

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
  `biz_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务ID - 拼接的唯一值',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id`) USING BTREE,
  UNIQUE INDEX `uq_biz_id`(`biz_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户行为返利流水订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_behavior_rebate_order_002
-- ----------------------------

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
  `biz_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务ID - 拼接的唯一值',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id`) USING BTREE,
  UNIQUE INDEX `uq_biz_id`(`biz_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户行为返利流水订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_behavior_rebate_order_003
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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户抽奖订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_raffle_order_001
-- ----------------------------

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
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户抽奖订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_raffle_order_002
-- ----------------------------
INSERT INTO `user_raffle_order_002` VALUES (1, '咸鱼12138', 100301, '测试活动', 100006, '396166851822', '2024-07-25 12:20:21', 'create', '2024-07-25 20:20:21', '2024-07-25 20:20:21');
INSERT INTO `user_raffle_order_002` VALUES (2, '咸鱼12138', 100301, '测试活动', 100006, '579716188993', '2024-07-27 17:13:33', 'used', '2024-07-28 01:13:33', '2024-07-28 01:13:33');
INSERT INTO `user_raffle_order_002` VALUES (3, '咸鱼12138', 100301, '测试活动', 100006, '583724269718', '2024-07-28 03:38:08', 'create', '2024-07-28 11:38:08', '2024-07-28 11:38:08');
INSERT INTO `user_raffle_order_002` VALUES (4, '咸鱼12138', 100301, '测试活动', 100006, '596655253727', '2024-07-28 04:52:15', 'used', '2024-07-28 12:52:14', '2024-07-28 12:52:15');

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户抽奖订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_raffle_order_003
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
