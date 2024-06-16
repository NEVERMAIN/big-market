/*
 Navicat Premium Data Transfer

 Source Server         : localhost_MySQL
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : big_market

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 16/06/2024 16:05:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for award
-- ----------------------------
DROP TABLE IF EXISTS `award`;
CREATE TABLE `award`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `award_id` int(8) NOT NULL COMMENT '抽奖奖品ID-内部流转使用',
  `award_key` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '奖品对接标识 - 每一个都是一个对应的发奖策略',
  `award_config` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '奖品配置信息',
  `award_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '奖品内容描述',
  ` create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of award
-- ----------------------------
INSERT INTO `award` VALUES (1, 101, 'user_credit_random', '1,100', '用户积分【优先透彻规则范围，如果没有则走配置】', '2024-06-14 17:54:38', '2024-06-14 17:54:38');
INSERT INTO `award` VALUES (2, 102, 'openai_use_count', '5', 'OpenAI 增加使用次数', '2024-06-14 17:54:38', '2024-06-14 17:54:38');
INSERT INTO `award` VALUES (3, 103, 'openai_use_count', '10', 'OpenAI 增加使用次数', '2024-06-14 17:54:38', '2024-06-14 17:54:38');
INSERT INTO `award` VALUES (4, 104, 'openai_use_count', '20', 'OpenAI 增加使用次数', '2024-06-14 17:54:38', '2024-06-14 17:54:38');
INSERT INTO `award` VALUES (5, 105, 'openai_model', 'gpt-4', 'OpenAI 增加模型', '2024-06-14 17:54:38', '2024-06-14 17:54:38');
INSERT INTO `award` VALUES (6, 106, 'openai_model', 'dall-e-2', 'OpenAI 增加模型', '2024-06-14 17:54:38', '2024-06-14 17:54:38');
INSERT INTO `award` VALUES (7, 107, 'openai_model', 'dall-e-3', 'OpenAI 增加模型', '2024-06-14 17:54:38', '2024-06-14 17:54:38');
INSERT INTO `award` VALUES (8, 108, 'openai_use_count', '100', 'OpenAI 增加使用次数', '2024-06-14 17:54:38', '2024-06-14 17:54:38');
INSERT INTO `award` VALUES (9, 109, 'openai_model', 'gpt-4,dall-e-2,dall-e-3', 'OpenAI 增加模型', '2024-06-14 17:54:38', '2024-06-14 17:54:38');
INSERT INTO `award` VALUES (10, 100, 'user_credit_blacklist', '1', '黑名单积分', '2024-06-16 15:56:24', '2024-06-16 15:56:24');

-- ----------------------------
-- Table structure for strategy
-- ----------------------------
DROP TABLE IF EXISTS `strategy`;
CREATE TABLE `strategy`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `strategy_id` bigint(8) NOT NULL COMMENT '抽奖策略ID',
  `strategy_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '抽奖策略描述',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `rule_models` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规则模型，rule配置的模型同步到此表，便于使用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of strategy
-- ----------------------------
INSERT INTO `strategy` VALUES (1, 100001, '抽奖策略', '2024-06-13 14:27:05', '2024-06-16 16:00:03', 'rule_weight');
INSERT INTO `strategy` VALUES (2, 100002, '抽奖策略-验证lock', '2024-06-16 15:58:01', '2024-06-16 15:58:01', NULL);
INSERT INTO `strategy` VALUES (3, 100003, '抽奖策略-非完整1概率', '2024-06-16 15:58:22', '2024-06-16 15:58:22', NULL);

-- ----------------------------
-- Table structure for strategy_award
-- ----------------------------
DROP TABLE IF EXISTS `strategy_award`;
CREATE TABLE `strategy_award`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `strategy_id` bigint(8) NOT NULL COMMENT '抽奖策略ID',
  `award_id` int(8) NOT NULL COMMENT '抽奖奖品ID-内部流转使用',
  `award_title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '抽奖奖品标题',
  `award_subtitle` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '抽奖奖品副标题',
  `award_count` int(8) NOT NULL DEFAULT 0 COMMENT '奖品库存总量',
  `award_count_surplus` int(8) NOT NULL DEFAULT 0 COMMENT '奖品库存剩余',
  `award_rate` decimal(6, 4) NOT NULL COMMENT '奖品中奖概率',
  `rule_models` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规则模型,rule配置的模型同步到此表,便于使用',
  `sort` int(2) NOT NULL DEFAULT 0 COMMENT '排序',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of strategy_award
-- ----------------------------
INSERT INTO `strategy_award` VALUES (1, 100001, 101, '随机积分', NULL, 80000, 80000, 0.3000, 'rule_random', 1, '2024-06-16 15:52:38', '2024-06-16 15:52:38');
INSERT INTO `strategy_award` VALUES (2, 100001, 102, '5次使用', NULL, 10000, 10000, 0.2000, 'rule_luck_award', 2, '2024-06-16 15:52:38', '2024-06-16 15:52:38');
INSERT INTO `strategy_award` VALUES (3, 100001, 103, '10次使用', NULL, 5000, 5000, 0.2000, 'rule_luck_award', 3, '2024-06-16 15:52:38', '2024-06-16 15:52:38');
INSERT INTO `strategy_award` VALUES (4, 100001, 104, '20次使用', NULL, 4000, 4000, 0.1000, 'rule_luck_award', 4, '2024-06-16 15:52:38', '2024-06-16 15:52:38');
INSERT INTO `strategy_award` VALUES (5, 100001, 105, '增加gpt-4对话模型', NULL, 600, 600, 0.1000, 'rule_luck_award', 5, '2024-06-16 15:52:38', '2024-06-16 15:52:38');
INSERT INTO `strategy_award` VALUES (6, 100001, 106, '增加dall-e-2画图模型', NULL, 200, 200, 0.0500, 'rule_luck_award', 6, '2024-06-16 15:52:38', '2024-06-16 15:52:38');
INSERT INTO `strategy_award` VALUES (7, 100001, 107, '增加dall-e-3画图模型', '抽奖1次后解锁', 200, 200, 0.0400, 'rule_lock,rule_luck_award', 7, '2024-06-16 15:52:38', '2024-06-16 15:52:38');
INSERT INTO `strategy_award` VALUES (8, 100001, 108, '增加100次使用', '抽奖2次后解锁', 199, 199, 0.0099, 'rule_lock,rule_luck_award', 8, '2024-06-16 15:52:38', '2024-06-16 15:52:38');
INSERT INTO `strategy_award` VALUES (9, 100001, 109, '解锁全部模型', '抽奖6次后解锁', 1, 1, 0.0001, 'rule_lock,rule_luck_award', 9, '2024-06-16 15:52:38', '2024-06-16 15:52:38');
INSERT INTO `strategy_award` VALUES (10, 100002, 101, '随机积分', NULL, 1, 1, 0.5000, 'rule_random,rule_luck_award', 1, '2024-06-16 15:52:38', '2024-06-16 15:52:38');
INSERT INTO `strategy_award` VALUES (11, 100002, 102, '5次使用', NULL, 1, 1, 0.1000, 'rule_random,rule_luck_award', 2, '2024-06-16 15:52:38', '2024-06-16 15:52:38');
INSERT INTO `strategy_award` VALUES (12, 100002, 106, '增加dall-e-2画图模型', NULL, 1, 1, 0.0100, 'rule_random,rule_luck_award', 3, '2024-06-16 15:52:38', '2024-06-16 15:52:38');
INSERT INTO `strategy_award` VALUES (13, 100003, 107, '增加dall-e-3画图模型', '抽奖1次后解锁', 200, 200, 0.0400, 'rule_lock,rule_luck_award', 7, '2024-06-16 15:52:38', '2024-06-16 15:52:38');
INSERT INTO `strategy_award` VALUES (14, 100003, 108, '增加100次使用', '抽奖2次后解锁', 199, 199, 0.0099, 'rule_lock,rule_luck_award', 8, '2024-06-16 15:52:38', '2024-06-16 15:52:38');
INSERT INTO `strategy_award` VALUES (15, 100003, 109, '解锁全部模型', '抽奖6次后解锁', 1, 1, 0.0001, 'rule_lock,rule_luck_award', 9, '2024-06-16 15:52:38', '2024-06-16 15:52:38');

-- ----------------------------
-- Table structure for strategy_rule
-- ----------------------------
DROP TABLE IF EXISTS `strategy_rule`;
CREATE TABLE `strategy_rule`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `strategy_id` int(8) NOT NULL COMMENT '抽奖策略ID',
  `award_id` int(8) NULL DEFAULT NULL COMMENT '抽奖奖品ID【规则类型为策略，则不需要奖品ID】',
  `rule_type` tinyint(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0 COMMENT '抽象规则类型；1-策略规则、2-奖品规则',
  `rule_model` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '抽奖规则类型【rule_random - 随机值计算、rule_lock - 抽奖几次后解锁、rule_luck_award - 幸运奖(兜底奖品)】',
  `rule_value` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '抽奖规则比值',
  `rule_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '抽奖规则描述',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_strategy_id_award_id`(`strategy_id`, `award_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of strategy_rule
-- ----------------------------
INSERT INTO `strategy_rule` VALUES (1, 100001, 101, 2, 'rule_random', '1,1000', '随机积分策略', '2023-12-09 10:05:30', '2023-12-09 12:55:52');
INSERT INTO `strategy_rule` VALUES (2, 100001, 107, 2, 'rule_lock', '1', '抽奖1次后解锁', '2023-12-09 10:16:41', '2023-12-09 12:55:53');
INSERT INTO `strategy_rule` VALUES (3, 100001, 108, 2, 'rule_lock', '2', '抽奖2次后解锁', '2023-12-09 10:17:43', '2023-12-09 12:55:54');
INSERT INTO `strategy_rule` VALUES (4, 100001, 109, 2, 'rule_lock', '6', '抽奖6次后解锁', '2023-12-09 10:17:43', '2023-12-09 12:55:54');
INSERT INTO `strategy_rule` VALUES (5, 100001, 107, 2, 'rule_luck_award', '1,100', '兜底奖品100以内随机积分', '2023-12-09 10:30:12', '2023-12-09 12:55:55');
INSERT INTO `strategy_rule` VALUES (6, 100001, 108, 2, 'rule_luck_award', '1,100', '兜底奖品100以内随机积分', '2023-12-09 10:30:43', '2023-12-09 12:55:56');
INSERT INTO `strategy_rule` VALUES (7, 100001, 101, 2, 'rule_luck_award', '1,10', '兜底奖品10以内随机积分', '2023-12-09 10:30:43', '2023-12-09 12:55:57');
INSERT INTO `strategy_rule` VALUES (8, 100001, 102, 2, 'rule_luck_award', '1,20', '兜底奖品20以内随机积分', '2023-12-09 10:30:43', '2023-12-09 12:55:57');
INSERT INTO `strategy_rule` VALUES (9, 100001, 103, 2, 'rule_luck_award', '1,30', '兜底奖品30以内随机积分', '2023-12-09 10:30:43', '2023-12-09 12:55:58');
INSERT INTO `strategy_rule` VALUES (10, 100001, 104, 2, 'rule_luck_award', '1,40', '兜底奖品40以内随机积分', '2023-12-09 10:30:43', '2023-12-09 12:55:59');
INSERT INTO `strategy_rule` VALUES (11, 100001, 105, 2, 'rule_luck_award', '1,50', '兜底奖品50以内随机积分', '2023-12-09 10:30:43', '2023-12-09 12:56:00');
INSERT INTO `strategy_rule` VALUES (12, 100001, 106, 2, 'rule_luck_award', '1,60', '兜底奖品60以内随机积分', '2023-12-09 10:30:43', '2023-12-09 12:56:00');
INSERT INTO `strategy_rule` VALUES (13, 100001, NULL, 1, 'rule_weight', '6000,102,103,104,105,106,107,108,109', '消耗6000分，必中奖范围', '2023-12-09 10:30:43', '2023-12-09 12:58:21');
INSERT INTO `strategy_rule` VALUES (14, 100001, NULL, 1, 'rule_blacklist', '1', '黑名单抽奖，积分兜底', '2023-12-09 12:59:45', '2023-12-09 13:42:23');
INSERT INTO `strategy_rule` VALUES (15, 100003, 107, 2, 'rule_lock', '1', '抽奖1次后解锁', '2024-06-16 16:02:06', '2024-06-16 16:02:06');
INSERT INTO `strategy_rule` VALUES (16, 100003, 108, 2, 'rule_lock', '2', '抽奖2次后解锁', '2024-06-16 16:02:32', '2024-06-16 16:02:32');
INSERT INTO `strategy_rule` VALUES (17, 100003, 109, 2, 'rule_lock', '2', '抽奖6次后解锁', '2024-06-16 16:03:00', '2024-06-16 16:03:00');

SET FOREIGN_KEY_CHECKS = 1;
