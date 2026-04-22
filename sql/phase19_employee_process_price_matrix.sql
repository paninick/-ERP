-- phase19_employee_process_price_matrix.sql
-- 工序单价矩阵（员工×工序=单价）
-- 支持：全局默认单价（employee_id=0）+ 员工个人单价（覆盖默认）
-- 计算规则：先查个人单价，无则查工序默认单价（来自 t_erp_process_def.unit_price）

SET NAMES utf8mb4;

-- ============================================================
-- 1. 工序单价矩阵表
-- ============================================================
CREATE TABLE IF NOT EXISTS `t_erp_employee_process_price` (
  `id`           bigint  NOT NULL AUTO_INCREMENT,
  `employee_id`  bigint  NOT NULL COMMENT '员工ID（0=全局默认，覆盖 process_def.unit_price）',
  `process_id`   bigint  NOT NULL COMMENT '工序ID（t_erp_process_def）',
  `unit_price`   decimal(10,4) NOT NULL COMMENT '计件单价（元/件）',
  `effective_from` date  DEFAULT NULL COMMENT '生效起始日，NULL=永久',
  `effective_to`   date  DEFAULT NULL COMMENT '生效截止日，NULL=永久',
  `status`       char(1) DEFAULT '0' COMMENT '0正常 1停用',
  `remark`       varchar(200) DEFAULT NULL,
  `create_by`    varchar(64)  DEFAULT '',
  `create_time`  datetime DEFAULT NULL,
  `update_by`    varchar(64)  DEFAULT '',
  `update_time`  datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_emp_process` (`employee_id`, `process_id`, `effective_from`),
  KEY `idx_process_id` (`process_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='员工工序计件单价矩阵';

-- ============================================================
-- 2. 从 process_def 导入默认单价到矩阵（employee_id=0 表示通用默认）
-- ============================================================
INSERT IGNORE INTO `t_erp_employee_process_price`
  (employee_id, process_id, unit_price, status, remark, create_by, create_time)
SELECT 0, id, unit_price, '0', CONCAT('默认单价来自工序定义：', process_name), 'system', NOW()
FROM t_erp_process_def
WHERE unit_price IS NOT NULL AND unit_price > 0;

-- ============================================================
-- 3. 计件工资结算视图（方便查询汇总）
-- ============================================================
CREATE OR REPLACE VIEW v_erp_piece_wage_summary AS
SELECT
  pjp.employee_id,
  pjp.employee_name,
  DATE_FORMAT(pj.create_time, '%Y-%m') AS wage_month,
  pd.process_no,
  pd.process_name,
  SUM(pjp.out_qty)    AS total_ok_qty,
  COALESCE(emp_p.unit_price, def_p.unit_price, 0) AS unit_price,
  ROUND(SUM(pjp.out_qty) * COALESCE(emp_p.unit_price, def_p.unit_price, 0), 2) AS should_wage
FROM t_erp_produce_job_process pjp
JOIN t_erp_produce_job pj    ON pjp.job_id    = pj.id
JOIN t_erp_process_def pd    ON pjp.process_id = pd.id
-- 个人单价（优先）
LEFT JOIN t_erp_employee_process_price emp_p
  ON emp_p.employee_id = pjp.employee_id
  AND emp_p.process_id = pjp.process_id
  AND emp_p.status = '0'
-- 全局默认单价（兜底）
LEFT JOIN t_erp_employee_process_price def_p
  ON def_p.employee_id = 0
  AND def_p.process_id = pjp.process_id
  AND def_p.status = '0'
WHERE pjp.employee_id IS NOT NULL
  AND pjp.out_qty > 0
GROUP BY pjp.employee_id, pjp.employee_name, wage_month, pjp.process_id, pd.process_no, pd.process_name,
         emp_p.unit_price, def_p.unit_price;

-- ============================================================
-- 4. 验证
-- ============================================================
SELECT COUNT(*) AS default_price_rows FROM t_erp_employee_process_price WHERE employee_id=0;
