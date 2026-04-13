-- RuoYi-Vue 服装ERP系统初始化脚本
-- 数据库版本: MySQL 8.0
-- 创建时间: 2026-04-01

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- 1. 订单表
-- ----------------------------
DROP TABLE IF EXISTS `demo_order`;
CREATE TABLE `demo_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `factory_id` bigint(20) NULL DEFAULT NULL COMMENT '工厂ID',
  `order_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单号',
  `style_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '款号',
  `style_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '款式名称',
  `qty` int(11) NULL DEFAULT NULL COMMENT '数量',
  `fob_price` decimal(10, 2) NULL DEFAULT NULL COMMENT 'FOB价格',
  `cif_price` decimal(10, 2) NULL DEFAULT NULL COMMENT 'CIF价格',
  `cnf_price` decimal(10, 2) NULL DEFAULT NULL COMMENT 'CNF价格',
  `exw_price` decimal(10, 2) NULL DEFAULT NULL COMMENT 'EXW价格',
  `revenue` decimal(10, 2) NULL DEFAULT NULL COMMENT '收入',
  `cost` decimal(10, 2) NULL DEFAULT NULL COMMENT '成本',
  `profit` decimal(10, 2) NULL DEFAULT NULL COMMENT '利润',
  `profit_rate` decimal(10, 2) NULL DEFAULT NULL COMMENT '利润率',
  `due_days` int(11) NULL DEFAULT NULL COMMENT '交货期（天）',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_no`(`order_no`) USING BTREE,
  INDEX `idx_style_no`(`style_no`) USING BTREE,
  INDEX `idx_status`(`status`) USING BTREE,
  INDEX `idx_factory_id`(`factory_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- 2. 款式表
-- ----------------------------
DROP TABLE IF EXISTS `demo_style`;
CREATE TABLE `demo_style`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '款式ID',
  `style_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '款号',
  `style_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '款式名称',
  `category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '品类',
  `season` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '季节',
  `fabric_cost` decimal(10, 2) NULL DEFAULT NULL COMMENT '面料费',
  `accessory_cost` decimal(10, 2) NULL DEFAULT NULL COMMENT '辅料费',
  `cmt_cost` decimal(10, 2) NULL DEFAULT NULL COMMENT 'CMT加工费',
  `standard_cost` decimal(10, 2) NULL DEFAULT NULL COMMENT '标准成本',
  `profit_rate` decimal(10, 2) NULL DEFAULT NULL COMMENT '目标利润率',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_style_no`(`style_no`) USING BTREE,
  INDEX `idx_category`(`category`) USING BTREE,
  INDEX `idx_season`(`season`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '款式表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- 3. 外发加工订单表
-- ----------------------------
DROP TABLE IF EXISTS `demo_outsource`;
CREATE TABLE `demo_outsource`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '外发订单ID',
  `factory_id` bigint(20) NULL DEFAULT NULL COMMENT '工厂ID',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '订单ID',
  `factory_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工厂名称',
  `process` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工序',
  `plan_qty` int(11) NULL DEFAULT NULL COMMENT '计划数量',
  `sent_qty` int(11) NULL DEFAULT NULL COMMENT '发出数量',
  `received_qty` int(11) NULL DEFAULT NULL COMMENT '实收数量',
  `loss_qty` int(11) NULL DEFAULT NULL COMMENT '损耗数量',
  `loss_rate` decimal(10, 4) NULL DEFAULT NULL COMMENT '损耗率',
  `unit_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '加工单价',
  `total_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '加工费总额',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_id`(`order_id`) USING BTREE,
  INDEX `idx_factory_name`(`factory_name`) USING BTREE,
  INDEX `idx_process`(`process`) USING BTREE,
  INDEX `idx_factory_id`(`factory_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '外发加工订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- 4. 外发加工补料表
-- ----------------------------
DROP TABLE IF EXISTS `demo_outsource_extra`;
CREATE TABLE `demo_outsource_extra`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '外发补料ID',
  `outsource_id` bigint(20) NULL DEFAULT NULL COMMENT '外发订单ID',
  `extra_qty` int(11) NULL DEFAULT NULL COMMENT '补料数量',
  `reason` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '原因',
  `approved` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审批状态',
  `approved_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审批人',
  `approved_time` datetime NULL DEFAULT NULL COMMENT '审批时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_outsource_id`(`outsource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '外发加工补料表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- 5. 报工表
-- ----------------------------
DROP TABLE IF EXISTS `demo_report`;
CREATE TABLE `demo_report`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '报工ID',
  `factory_id` bigint(20) NULL DEFAULT NULL COMMENT '工厂ID',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '订单ID',
  `process` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工序',
  `work_date` date NULL DEFAULT NULL COMMENT '日期',
  `qty` int(11) NULL DEFAULT NULL COMMENT '数量',
  `is_rework` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '是否返工',
  `rework_cost` decimal(10, 2) NULL DEFAULT NULL COMMENT '返工成本',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_id`(`order_id`) USING BTREE,
  INDEX `idx_process`(`process`) USING BTREE,
  INDEX `idx_factory_id`(`factory_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '报工表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- 6. 排程表
-- ----------------------------
DROP TABLE IF EXISTS `demo_schedule`;
CREATE TABLE `demo_schedule`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '排程ID',
  `factory_id` bigint(20) NULL DEFAULT NULL COMMENT '工厂ID',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '订单ID',
  `work_day` date NULL DEFAULT NULL COMMENT '工作日',
  `process` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工序',
  `plan_qty` int(11) NULL DEFAULT NULL COMMENT '计划数量',
  `capacity` int(11) NULL DEFAULT NULL COMMENT '产能',
  `load` decimal(10, 2) NULL DEFAULT NULL COMMENT '负载率',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_id`(`order_id`) USING BTREE,
  INDEX `idx_work_day`(`work_day`) USING BTREE,
  INDEX `idx_process`(`process`) USING BTREE,
  INDEX `idx_factory_id`(`factory_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '排程表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- 插入初始化数据
-- ----------------------------

-- 1. 订单表初始化数据
INSERT INTO `demo_order` VALUES (1, 1, 'ORD-20260401-001', 'STY-20260401', '春季T恤', 1000, 15.00, 16.50, 15.80, 14.20, 15000.00, 12000.00, 3000.00, 20.00, 30, '0', 'admin', '2026-04-01 09:00:00', 'admin', '2026-04-01 09:00:00', '0');
INSERT INTO `demo_order` VALUES (2, 1, 'ORD-20260401-002', 'STY-20260402', '夏季短裤', 2000, 12.50, 13.80, 13.20, 11.80, 25000.00, 20000.00, 5000.00, 20.00, 25, '0', 'admin', '2026-04-01 09:30:00', 'admin', '2026-04-01 09:30:00', '0');

-- 2. 款式表初始化数据
INSERT INTO `demo_style` VALUES (1, 'STY-20260401', '春季T恤', 'T恤', '春季', 8.50, 2.50, 4.00, 15.00, 20.00, 'admin', '2026-04-01 08:00:00', 'admin', '2026-04-01 08:00:00', '0');
INSERT INTO `demo_style` VALUES (2, 'STY-20260402', '夏季短裤', '短裤', '夏季', 7.00, 2.00, 3.50, 12.50, 20.00, 'admin', '2026-04-01 08:30:00', 'admin', '2026-04-01 08:30:00', '0');

-- 3. 外发加工订单表初始化数据
INSERT INTO `demo_outsource` VALUES (1, 1, 1, '宏发制衣厂', '裁剪', 1000, 1000, 980, 20, 0.0200, 1.50, 1470.00, '0', 'admin', '2026-04-01 10:00:00', 'admin', '2026-04-01 10:00:00', '0');
INSERT INTO `demo_outsource` VALUES (2, 1, 1, '华盛缝纫厂', '缝纫', 1000, 1000, 950, 50, 0.0500, 2.00, 1900.00, '0', 'admin', '2026-04-01 10:30:00', 'admin', '2026-04-01 10:30:00', '0');

-- 4. 外发加工补料表初始化数据
INSERT INTO `demo_outsource_extra` VALUES (1, 2, 30, '布料质量问题', '0', NULL, NULL, 'admin', '2026-04-01 11:00:00', 'admin', '2026-04-01 11:00:00', '0');

-- 5. 报工表初始化数据
INSERT INTO `demo_report` VALUES (1, 1, 1, '裁剪', '2026-04-01', 980, '0', 0.00, 'admin', '2026-04-01 16:00:00', 'admin', '2026-04-01 16:00:00', '0');
INSERT INTO `demo_report` VALUES (2, 1, 1, '缝纫', '2026-04-01', 950, '0', 0.00, 'admin', '2026-04-01 17:00:00', 'admin', '2026-04-01 17:00:00', '0');

-- 6. 排程表初始化数据
INSERT INTO `demo_schedule` VALUES (1, 1, 1, '2026-04-01', '裁剪', 1000, 1000, 1.00, 'admin', '2026-04-01 08:00:00', 'admin', '2026-04-01 08:00:00', '0');
INSERT INTO `demo_schedule` VALUES (2, 1, 1, '2026-04-01', '缝纫', 1000, 1500, 0.67, 'admin', '2026-04-01 08:30:00', 'admin', '2026-04-01 08:30:00', '0');
INSERT INTO `demo_schedule` VALUES (3, 1, 2, '2026-04-01', '裁剪', 1000, 1500, 0.67, 'admin', '2026-04-01 09:00:00', 'admin', '2026-04-01 09:00:00', '0');

SET FOREIGN_KEY_CHECKS = 1;
