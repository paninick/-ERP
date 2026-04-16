-- =============================================
-- 阶段五：计件工资计算
-- 创建表：计件工资汇总表、计件工资明细表
-- =============================================

-- ----------------------------
-- 计件工资汇总表
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_piece_wage`;
CREATE TABLE `t_erp_piece_wage` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '工资汇总ID',
  `employee_id` bigint(20) NOT NULL COMMENT '员工ID',
  `employee_name` varchar(64) DEFAULT NULL COMMENT '员工姓名',
  `wage_month` varchar(7) NOT NULL COMMENT '汇总月份',
  `total_process_count` int(11) NOT NULL DEFAULT 0 COMMENT '总工序数量',
  `total_ok_qty` int(11) NOT NULL DEFAULT 0 COMMENT '总合格产量',
  `total_defect_qty` int(11) NOT NULL DEFAULT 0 COMMENT '总次品产量',
  `should_wage` decimal(10,2) DEFAULT NULL COMMENT '应发工资',
  `deduct_wage` decimal(10,2) DEFAULT NULL COMMENT '扣款金额',
  `actual_wage` decimal(10,2) DEFAULT NULL COMMENT '实际工资',
  `status` char(1) DEFAULT '0' COMMENT '状态 0待审核 1已审核 2已发放',
  `audit_by_id` bigint(20) DEFAULT NULL COMMENT '审核人ID',
  `audit_by_name` varchar(64) DEFAULT NULL COMMENT '审核人姓名',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `pay_time` datetime DEFAULT NULL COMMENT '发放时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_employee_id` (`employee_id`),
  KEY `idx_wage_month` (`wage_month`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='计件工资汇总表';

-- ----------------------------
-- 计件工资明细表
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_piece_wage_detail`;
CREATE TABLE `t_erp_piece_wage_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '明细ID',
  `wage_id` bigint(20) NOT NULL COMMENT '工资汇总ID',
  `employee_id` bigint(20) NOT NULL COMMENT '员工ID',
  `process_id` bigint(20) NOT NULL COMMENT '工序ID',
  `process_name` varchar(64) DEFAULT NULL COMMENT '工序名称',
  `job_id` bigint(20) NOT NULL COMMENT '工票ID',
  `ok_qty` int(11) NOT NULL DEFAULT 0 COMMENT '合格数量',
  `defect_qty` int(11) NOT NULL DEFAULT 0 COMMENT '次品数量',
  `process_price` decimal(10,2) DEFAULT NULL COMMENT '工序单价',
  `should_wage` decimal(10,2) DEFAULT NULL COMMENT '应得工资',
  `deduct_wage` decimal(10,2) DEFAULT NULL COMMENT '扣款金额',
  `actual_wage` decimal(10,2) DEFAULT NULL COMMENT '实际工资',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_wage_id` (`wage_id`),
  KEY `idx_employee_id` (`employee_id`),
  KEY `idx_process_id` (`process_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='计件工资明细表';
