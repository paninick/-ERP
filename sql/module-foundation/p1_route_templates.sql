-- P1-H: 三类产品族工艺路线模板
-- 基于现有 t_erp_process_def 工序
-- 幂等: 检查是否已存在模板数据

SET @t1 = (SELECT COUNT(*) FROM t_erp_process_route WHERE product_type = 'SWEATER' AND is_default = 1);
SET @s1 = IF(@t1 = 0,
  'INSERT INTO t_erp_process_route (route_name, product_type, product_code, is_default, status, create_by, create_time)
   VALUES (''毛衫标准工艺路线'', ''SWEATER'', NULL, 1, ''0'', ''admin'', NOW())',
  'SELECT 1');
PREPARE p FROM @s1; EXECUTE p; DEALLOCATE PREPARE p;

SET @route1 = LAST_INSERT_ID();

SET @t2 = (SELECT COUNT(*) FROM t_erp_process_route WHERE product_type = 'SPLICE' AND is_default = 1);
SET @s2 = IF(@t2 = 0,
  'INSERT INTO t_erp_process_route (route_name, product_type, product_code, is_default, status, create_by, create_time)
   VALUES (''拼接款标准工艺路线'', ''SPLICE'', NULL, 1, ''0'', ''admin'', NOW())',
  'SELECT 1');
PREPARE p FROM @s2; EXECUTE p; DEALLOCATE PREPARE p;

SET @route2 = LAST_INSERT_ID();

SET @t3 = (SELECT COUNT(*) FROM t_erp_process_route WHERE product_type = 'KNIT_TOP' AND is_default = 1);
SET @s3 = IF(@t3 = 0,
  'INSERT INTO t_erp_process_route (route_name, product_type, product_code, is_default, status, create_by, create_time)
   VALUES (''普通针织衫标准工艺路线'', ''KNIT_TOP'', NULL, 1, ''0'', ''admin'', NOW())',
  'SELECT 1');
PREPARE p FROM @s3; EXECUTE p; DEALLOCATE PREPARE p;

SET @route3 = LAST_INSERT_ID();

-- 毛衫路线工序: 横机(3)→拼接(7)→水洗(4)→织片检验(5)→扣子(11)→标签(15)→整烫(13)→后整理(12)→分级检验(14)
DELETE FROM t_erp_process_route_item WHERE route_id IN (SELECT id FROM t_erp_process_route WHERE product_type = 'SWEATER' AND is_default = 1);
INSERT INTO t_erp_process_route_item (route_id, process_id, sort_order, is_control_point, qc_required, loss_tracked, piece_wage_applicable)
SELECT r.id, p.process_id, p.sort_order, p.is_control_point, p.qc_required, p.loss_tracked, p.piece_wage_applicable
FROM (SELECT id FROM t_erp_process_route WHERE product_type = 'SWEATER' AND is_default = 1) r
CROSS JOIN (
  SELECT 3 AS process_id, 1 AS sort_order, 0 AS is_control_point, 0 AS qc_required, 1 AS loss_tracked, 1 AS piece_wage_applicable
  UNION ALL SELECT 7, 2, 0, 0, 0, 1
  UNION ALL SELECT 4, 3, 0, 0, 0, 0
  UNION ALL SELECT 5, 4, 1, 1, 1, 0
  UNION ALL SELECT 11, 5, 0, 0, 0, 1
  UNION ALL SELECT 15, 6, 0, 0, 0, 1
  UNION ALL SELECT 13, 7, 0, 1, 0, 1
  UNION ALL SELECT 12, 8, 0, 0, 0, 0
  UNION ALL SELECT 14, 9, 1, 1, 0, 0
) p;

-- 拼接款路线: 横机(3)→拼接(7)→水洗(4)→织片检验(5)→整烫(13)→后整理(12)→分级检验(14)
DELETE FROM t_erp_process_route_item WHERE route_id IN (SELECT id FROM t_erp_process_route WHERE product_type = 'SPLICE' AND is_default = 1);
INSERT INTO t_erp_process_route_item (route_id, process_id, sort_order, is_control_point, qc_required, loss_tracked, piece_wage_applicable)
SELECT r.id, p.process_id, p.sort_order, p.is_control_point, p.qc_required, p.loss_tracked, p.piece_wage_applicable
FROM (SELECT id FROM t_erp_process_route WHERE product_type = 'SPLICE' AND is_default = 1) r
CROSS JOIN (
  SELECT 3 AS process_id, 1 AS sort_order, 0 AS is_control_point, 0 AS qc_required, 1 AS loss_tracked, 1 AS piece_wage_applicable
  UNION ALL SELECT 7, 2, 0, 0, 0, 1
  UNION ALL SELECT 4, 3, 0, 0, 0, 0
  UNION ALL SELECT 5, 4, 1, 1, 1, 0
  UNION ALL SELECT 13, 5, 0, 1, 0, 1
  UNION ALL SELECT 12, 6, 0, 0, 0, 0
  UNION ALL SELECT 14, 7, 1, 1, 0, 0
) p;

-- 普通针织衫路线: 裁片(6)→拼接(7)→整烫(13)→分级检验(14)→后整理(12)
DELETE FROM t_erp_process_route_item WHERE route_id IN (SELECT id FROM t_erp_process_route WHERE product_type = 'KNIT_TOP' AND is_default = 1);
INSERT INTO t_erp_process_route_item (route_id, process_id, sort_order, is_control_point, qc_required, loss_tracked, piece_wage_applicable)
SELECT r.id, p.process_id, p.sort_order, p.is_control_point, p.qc_required, p.loss_tracked, p.piece_wage_applicable
FROM (SELECT id FROM t_erp_process_route WHERE product_type = 'KNIT_TOP' AND is_default = 1) r
CROSS JOIN (
  SELECT 6 AS process_id, 1 AS sort_order, 0 AS is_control_point, 0 AS qc_required, 0 AS loss_tracked, 1 AS piece_wage_applicable
  UNION ALL SELECT 7, 2, 0, 0, 0, 1
  UNION ALL SELECT 13, 3, 0, 1, 0, 1
  UNION ALL SELECT 14, 4, 1, 1, 0, 0
  UNION ALL SELECT 12, 5, 0, 0, 0, 0
) p;

SELECT CONCAT('P1-H: 工艺路线模板已创建 (毛衫/拼接/普通针织衫)') AS result;
