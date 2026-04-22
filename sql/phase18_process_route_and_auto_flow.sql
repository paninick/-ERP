-- phase18_process_route_and_auto_flow.sql
-- 工艺路线表建立 + 种子数据 + ProduceJobProcess 自动生成 SP
-- 依赖：phase17_process_def_seed.sql 已执行（工序定义数据就绪）

SET NAMES utf8mb4;

-- ============================================================
-- 1. 建 t_erp_process_route 主表
-- ============================================================
CREATE TABLE IF NOT EXISTS `t_erp_process_route` (
  `id`            bigint NOT NULL AUTO_INCREMENT,
  `route_name`    varchar(100) NOT NULL COMMENT '工艺路线名称',
  `product_type`  varchar(50)  DEFAULT NULL COMMENT '适用产品类型（毛衫/拼接/外发）',
  `product_code`  varchar(50)  DEFAULT NULL COMMENT '关联款号前缀（可空，空=通用）',
  `is_default`    tinyint(1)   DEFAULT 0   COMMENT '是否默认路线 0否 1是',
  `status`        char(1)      DEFAULT '0' COMMENT '状态 0正常 1停用',
  `create_by`     varchar(64)  DEFAULT '',
  `create_time`   datetime     DEFAULT NULL,
  `update_by`     varchar(64)  DEFAULT '',
  `update_time`   datetime     DEFAULT NULL,
  `remark`        varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工艺路线主表';

-- ============================================================
-- 2. 建 t_erp_process_route_item 明细表
-- ============================================================
CREATE TABLE IF NOT EXISTS `t_erp_process_route_item` (
  `id`                    bigint NOT NULL AUTO_INCREMENT,
  `route_id`              bigint NOT NULL COMMENT '工艺路线ID',
  `process_id`            bigint NOT NULL COMMENT '工序ID（t_erp_process_def）',
  `sort_order`            int    DEFAULT 0  COMMENT '排序',
  `is_control_point`      tinyint(1) DEFAULT 0 COMMENT '是否齐套控制点',
  `require_complete_ratio` double DEFAULT 1.0 COMMENT '齐套要求比率',
  `allow_force_start`     tinyint(1) DEFAULT 0 COMMENT '允许强制开工',
  `is_outsource`          tinyint(1) DEFAULT 0 COMMENT '是否外协',
  `standard_cycle_hours`  double DEFAULT NULL COMMENT '本段标准工时（小时）',
  `remark`                varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_route_id` (`route_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工艺路线工序明细';

-- ============================================================
-- 3. 种子路线：标准针织毛衫（本厂全流程，12 道核心工序）
-- ============================================================
INSERT IGNORE INTO `t_erp_process_route`
  (id, route_name, product_type, is_default, status, create_by, create_time, remark)
VALUES
  (1, '标准针织毛衫全流程', '毛衫', 1, '0', 'admin', NOW(), '适用于常规针织毛衫，含织片→拼接→后整理全链路'),
  (2, '拼接款混合路线',     '拼接', 0, '0', 'admin', NOW(), '含本厂织片+外协染色+本厂拼接，适用于多色拼接款');

-- 路线1：标准毛衫（选 P01 P03 P04 P05 P06 P07 P08 P09 P12 P13 P14 P17）
INSERT IGNORE INTO `t_erp_process_route_item`
  (route_id, process_id, sort_order, is_control_point, is_outsource, remark)
SELECT 1, id, sort_order, 0, 0, process_name
FROM t_erp_process_def
WHERE process_no IN ('P01','P03','P04','P05','P06','P07','P08','P09','P12','P13','P14','P17')
  AND process_type = '0';

-- 控制点：织片检验（P05）+ 成品检验（P17）标记为齐套控制点
UPDATE t_erp_process_route_item ri
  JOIN t_erp_process_def pd ON ri.process_id = pd.id
SET ri.is_control_point = 1
WHERE ri.route_id = 1 AND pd.process_no IN ('P05','P17');

-- 路线2：拼接款（本厂工序 + 外协染色 O04）
INSERT IGNORE INTO `t_erp_process_route_item`
  (route_id, process_id, sort_order, is_control_point, is_outsource, remark)
SELECT 2, id, sort_order, 0,
  CASE process_type WHEN '1' THEN 1 ELSE 0 END,
  process_name
FROM t_erp_process_def
WHERE process_no IN ('P01','P03','P04','O04','P07','P08','P09','P13','P14','P17');

-- ============================================================
-- 4. 存储过程：工单创建时按路线自动生成 ProduceJobProcess 队列
--    调用：CALL sp_erp_init_job_processes(jobId, routeId);
-- ============================================================
DROP PROCEDURE IF EXISTS sp_erp_init_job_processes;
DELIMITER $$
CREATE PROCEDURE sp_erp_init_job_processes(IN p_job_id BIGINT, IN p_route_id BIGINT)
BEGIN
  DECLARE v_seq INT DEFAULT 0;

  -- 按路线工序顺序插入 ProduceJobProcess（幂等：已存在同 job+process+seq 则跳过）
  INSERT IGNORE INTO t_erp_produce_job_process
    (job_id, process_id, sort_order, process_status, create_by, create_time)
  SELECT
    p_job_id,
    ri.process_id,
    ri.sort_order * 10,   -- 留间距（10/20/30...）方便后续插队
    '0',                   -- process_status 0=待开始
    'system',
    NOW()
  FROM t_erp_process_route_item ri
  WHERE ri.route_id = p_route_id
  ORDER BY ri.sort_order;
END$$
DELIMITER ;

-- ============================================================
-- 5. 验证
-- ============================================================
SELECT r.route_name, COUNT(ri.id) AS step_count
FROM t_erp_process_route r
LEFT JOIN t_erp_process_route_item ri ON r.id = ri.route_id
GROUP BY r.id, r.route_name;
