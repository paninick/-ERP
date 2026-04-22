-- phase21_product_category_and_supplier_rating.sql
-- 品类拓展（梭织/羽绒/牛仔）+ 供应商评级体系

SET NAMES utf8mb4;

-- ============================================================
-- 1. 品类字段扩展
-- ============================================================
-- t_erp_main_material 加品类字段（梭织/羽绒/牛仔 可与针织共用物料库）
CALL sp_erp_add_column('t_erp_main_material', 'fabric_type',
  'VARCHAR(20) DEFAULT ''knitted'' COMMENT ''布料品类：knitted=针织 woven=梭织 down=羽绒 denim=牛仔 other=其他''');

-- t_erp_sales_order 加品类（对日出口时客户可能同时订不同品类）
CALL sp_erp_add_column('t_erp_sales_order', 'fabric_type',
  'VARCHAR(20) DEFAULT ''knitted'' COMMENT ''品类：knitted/woven/down/denim/other''');

-- t_erp_bom 加品类（BOM 按品类分类）
CALL sp_erp_add_column('t_erp_bom', 'fabric_type',
  'VARCHAR(20) DEFAULT ''knitted'' COMMENT ''品类：knitted/woven/down/denim/other''');

-- ============================================================
-- 2. 品类字典
-- ============================================================
INSERT IGNORE INTO sys_dict_type (dict_type, dict_name, status, create_by, create_time, remark)
VALUES ('erp_fabric_type', '布料品类', '0', 'admin', NOW(), '针织/梭织/羽绒/牛仔/其他');

INSERT IGNORE INTO sys_dict_data
  (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
VALUES
  (1,  '针织', 'knitted', 'erp_fabric_type', '0', 'admin', NOW()),
  (2,  '梭织', 'woven',   'erp_fabric_type', '0', 'admin', NOW()),
  (3,  '羽绒', 'down',    'erp_fabric_type', '0', 'admin', NOW()),
  (4,  '牛仔', 'denim',   'erp_fabric_type', '0', 'admin', NOW()),
  (5,  '其他', 'other',   'erp_fabric_type', '0', 'admin', NOW());

-- ============================================================
-- 3. 供应商评级字段
-- ============================================================
CALL sp_erp_add_column('t_erp_supplier', 'quality_score',
  'DECIMAL(3,1) DEFAULT NULL COMMENT ''质量评分（0-10），基于不良品率反算''');
CALL sp_erp_add_column('t_erp_supplier', 'delivery_score',
  'DECIMAL(3,1) DEFAULT NULL COMMENT ''交期评分（0-10），基于准时交货率''');
CALL sp_erp_add_column('t_erp_supplier', 'price_score',
  'DECIMAL(3,1) DEFAULT NULL COMMENT ''价格竞争力评分（0-10）''');
CALL sp_erp_add_column('t_erp_supplier', 'overall_rating',
  'DECIMAL(3,1) DEFAULT NULL COMMENT ''综合评级（0-10，三项加权均值）''');
CALL sp_erp_add_column('t_erp_supplier', 'rating_level',
  'CHAR(1) DEFAULT NULL COMMENT ''等级：A=优秀(≥8) B=良好(≥6) C=一般(≥4) D=淘汰(<4)''');
CALL sp_erp_add_column('t_erp_supplier', 'last_rating_time',
  'DATETIME DEFAULT NULL COMMENT ''上次评级时间''');

-- ============================================================
-- 4. 供应商评级计算存储过程
--    CALL sp_erp_rate_supplier(supplier_id);
--    基于 t_erp_purchase + t_erp_stock_in 数据自动计算
-- ============================================================
DROP PROCEDURE IF EXISTS sp_erp_rate_supplier;
DELIMITER $$
CREATE PROCEDURE sp_erp_rate_supplier(IN p_supplier_id BIGINT)
BEGIN
  DECLARE v_quality  DECIMAL(3,1) DEFAULT 5.0;
  DECLARE v_delivery DECIMAL(3,1) DEFAULT 5.0;
  DECLARE v_price    DECIMAL(3,1) DEFAULT 5.0;
  DECLARE v_overall  DECIMAL(3,1);
  DECLARE v_level    CHAR(1);

  -- 质量评分：无采购记录默认 5.0，否则 10 - (缺陷率 × 100)
  -- （这里用占位逻辑，实际需关联 t_erp_produce_defect 的外协缺陷数据）
  SET v_quality = COALESCE(
    (SELECT 10 - LEAST(6.0,
      SUM(si.defect_count) * 1.0 / NULLIF(SUM(si.total_count), 0) * 100)
     FROM (
       SELECT p.id, 0 AS defect_count, COUNT(pi.id) AS total_count
       FROM t_erp_purchase p
       JOIN t_erp_purchase_item pi ON pi.purchase_id = p.id
       WHERE p.supplier_id = p_supplier_id
     ) si),
    5.0);

  -- 交期评分：占位，默认 7.0（已收货采购单比例，需结合 expected_delivery_date）
  SET v_delivery = 7.0;

  -- 综合评级（质量×50% + 交期×30% + 价格×20%）
  SET v_overall = ROUND(v_quality * 0.5 + v_delivery * 0.3 + v_price * 0.2, 1);
  SET v_level = CASE
    WHEN v_overall >= 8 THEN 'A'
    WHEN v_overall >= 6 THEN 'B'
    WHEN v_overall >= 4 THEN 'C'
    ELSE 'D'
  END;

  UPDATE t_erp_supplier
  SET quality_score   = v_quality,
      delivery_score  = v_delivery,
      price_score     = v_price,
      overall_rating  = v_overall,
      rating_level    = v_level,
      last_rating_time = NOW()
  WHERE id = p_supplier_id;
END$$
DELIMITER ;

-- ============================================================
-- 5. 供应商评级概览视图
-- ============================================================
CREATE OR REPLACE VIEW v_erp_supplier_rating AS
SELECT
  s.id,
  s.supplier_name,
  s.supplier_type,
  s.quality_score,
  s.delivery_score,
  s.price_score,
  s.overall_rating,
  s.rating_level,
  CASE s.rating_level
    WHEN 'A' THEN '优秀'
    WHEN 'B' THEN '良好'
    WHEN 'C' THEN '一般'
    WHEN 'D' THEN '淘汰'
    ELSE '未评级'
  END AS rating_label,
  s.last_rating_time,
  COUNT(p.id) AS purchase_count_12m
FROM t_erp_supplier s
LEFT JOIN t_erp_purchase p ON p.supplier_id = s.id
  AND p.purchase_date >= DATE_SUB(CURDATE(), INTERVAL 12 MONTH)
GROUP BY s.id, s.supplier_name, s.supplier_type,
         s.quality_score, s.delivery_score, s.price_score,
         s.overall_rating, s.rating_level, s.last_rating_time;

-- ============================================================
-- 6. 验证
-- ============================================================
SELECT
  (SELECT COUNT(*) FROM sys_dict_data WHERE dict_type='erp_fabric_type') AS fabric_type_dicts,
  (SELECT COUNT(*) FROM information_schema.COLUMNS
   WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_supplier'
   AND COLUMN_NAME IN('quality_score','delivery_score','price_score','overall_rating','rating_level')) AS supplier_rating_cols;
