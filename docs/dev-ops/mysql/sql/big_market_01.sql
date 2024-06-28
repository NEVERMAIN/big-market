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

 Date: 28/06/2024 16:40:10
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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抽奖活动账户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of raffle_activity_account
-- ----------------------------

-- ----------------------------
-- Table structure for raffle_activity_account_flow_000
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_account_flow_000`;
CREATE TABLE `raffle_activity_account_flow_000`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
  `total_count` int(8) NOT NULL COMMENT '总次数',
  `day_count` int(8) NOT NULL COMMENT '日次数',
  `month_count` int(8) NOT NULL COMMENT '月次数',
  `flow_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '流水ID - 生成的唯一ID',
  `flow_channel` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'activity' COMMENT '流水渠道（activity-活动领取、sale-购买、redeem-兑换、free-免费赠送）',
  `biz_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务ID（外部透传，活动ID、订单ID）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_flow_id`(`flow_id`) USING BTREE,
  UNIQUE INDEX `uq_biz_id`(`biz_id`) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id`, `activity_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抽奖活动账户流水表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of raffle_activity_account_flow_000
-- ----------------------------

-- ----------------------------
-- Table structure for raffle_activity_account_flow_001
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_account_flow_001`;
CREATE TABLE `raffle_activity_account_flow_001`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
  `total_count` int(8) NOT NULL COMMENT '总次数',
  `day_count` int(8) NOT NULL COMMENT '日次数',
  `month_count` int(8) NOT NULL COMMENT '月次数',
  `flow_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '流水ID - 生成的唯一ID',
  `flow_channel` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'activity' COMMENT '流水渠道（activity-活动领取、sale-购买、redeem-兑换、free-免费赠送）',
  `biz_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务ID（外部透传，活动ID、订单ID）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_flow_id`(`flow_id`) USING BTREE,
  UNIQUE INDEX `uq_biz_id`(`biz_id`) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id`, `activity_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抽奖活动账户流水表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of raffle_activity_account_flow_001
-- ----------------------------

-- ----------------------------
-- Table structure for raffle_activity_account_flow_002
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_account_flow_002`;
CREATE TABLE `raffle_activity_account_flow_002`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
  `total_count` int(8) NOT NULL COMMENT '总次数',
  `day_count` int(8) NOT NULL COMMENT '日次数',
  `month_count` int(8) NOT NULL COMMENT '月次数',
  `flow_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '流水ID - 生成的唯一ID',
  `flow_channel` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'activity' COMMENT '流水渠道（activity-活动领取、sale-购买、redeem-兑换、free-免费赠送）',
  `biz_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务ID（外部透传，活动ID、订单ID）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_flow_id`(`flow_id`) USING BTREE,
  UNIQUE INDEX `uq_biz_id`(`biz_id`) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id`, `activity_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抽奖活动账户流水表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of raffle_activity_account_flow_002
-- ----------------------------

-- ----------------------------
-- Table structure for raffle_activity_account_flow_003
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_account_flow_003`;
CREATE TABLE `raffle_activity_account_flow_003`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
  `total_count` int(8) NOT NULL COMMENT '总次数',
  `day_count` int(8) NOT NULL COMMENT '日次数',
  `month_count` int(8) NOT NULL COMMENT '月次数',
  `flow_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '流水ID - 生成的唯一ID',
  `flow_channel` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'activity' COMMENT '流水渠道（activity-活动领取、sale-购买、redeem-兑换、free-免费赠送）',
  `biz_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务ID（外部透传，活动ID、订单ID）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_flow_id`(`flow_id`) USING BTREE,
  UNIQUE INDEX `uq_biz_id`(`biz_id`) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id`, `activity_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抽奖活动账户流水表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of raffle_activity_account_flow_003
-- ----------------------------

-- ----------------------------
-- Table structure for raffle_activity_order_000
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_order_000`;
CREATE TABLE `raffle_activity_order_000`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '活动名称',
  `strategy_id` bigint(8) NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `state` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单状态（not_used、used、expire）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id`) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id`, `activity_id`, `state`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抽奖活动单' ROW_FORMAT = Dynamic;

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
  `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '活动名称',
  `strategy_id` bigint(8) NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `state` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单状态（not_used、used、expire）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id`) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id`, `activity_id`, `state`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抽奖活动单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of raffle_activity_order_001
-- ----------------------------
INSERT INTO `raffle_activity_order_001` VALUES (13, 'eOMtThyhVNLWUZNRcBaQKxI', 100301, '测试活动', 100006, '455188601971', '2024-06-28 08:38:15', 'not_used', '2024-06-28 16:38:14', '2024-06-28 16:38:14');
INSERT INTO `raffle_activity_order_001` VALUES (14, 'eOMtThyhVNLWUZNRcBaQKxI', 100301, '测试活动', 100006, '695404561244', '2024-06-28 08:38:31', 'not_used', '2024-06-28 16:38:31', '2024-06-28 16:38:31');

-- ----------------------------
-- Table structure for raffle_activity_order_002
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_order_002`;
CREATE TABLE `raffle_activity_order_002`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '活动名称',
  `strategy_id` bigint(8) NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `state` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单状态（not_used、used、expire）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id`) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id`, `activity_id`, `state`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抽奖活动单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of raffle_activity_order_002
-- ----------------------------
INSERT INTO `raffle_activity_order_002` VALUES (12, 'yedUsFwdkelQbxeTeQOvaScfqIOOmaa', 100301, '测试活动', 100006, '581329902875', '2024-06-28 08:38:15', 'not_used', '2024-06-28 16:38:14', '2024-06-28 16:38:14');
INSERT INTO `raffle_activity_order_002` VALUES (13, 'yedUsFwdkelQbxeTeQOvaScfqIOOmaa', 100301, '测试活动', 100006, '107298708777', '2024-06-28 08:38:31', 'not_used', '2024-06-28 16:38:31', '2024-06-28 16:38:31');

-- ----------------------------
-- Table structure for raffle_activity_order_003
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_order_003`;
CREATE TABLE `raffle_activity_order_003`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '活动名称',
  `strategy_id` bigint(8) NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `state` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单状态（not_used、used、expire）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id`) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id`, `activity_id`, `state`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '抽奖活动单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of raffle_activity_order_003
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
