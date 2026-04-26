-- 主料库存字段补齐 (Mapper XML 引用但表缺失)
-- 幂等，可重复执行

SET @c = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_main_material' AND COLUMN_NAME='min_stock_qty');
SET @s = IF(@c=0, 'ALTER TABLE t_erp_main_material ADD COLUMN min_stock_qty DECIMAL(18,3) DEFAULT NULL COMMENT ''最低库存'' AFTER price', 'SELECT 1');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;

SET @c = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_main_material' AND COLUMN_NAME='max_stock_qty');
SET @s = IF(@c=0, 'ALTER TABLE t_erp_main_material ADD COLUMN max_stock_qty DECIMAL(18,3) DEFAULT NULL COMMENT ''最高库存'' AFTER min_stock_qty', 'SELECT 1');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;

SET @c = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_main_material' AND COLUMN_NAME='safe_stock_qty');
SET @s = IF(@c=0, 'ALTER TABLE t_erp_main_material ADD COLUMN safe_stock_qty DECIMAL(18,3) DEFAULT NULL COMMENT ''安全库存'' AFTER max_stock_qty', 'SELECT 1');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;

SET @c = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_main_material' AND COLUMN_NAME='min_order_qty');
SET @s = IF(@c=0, 'ALTER TABLE t_erp_main_material ADD COLUMN min_order_qty DECIMAL(18,3) DEFAULT NULL COMMENT ''最小起订量'' AFTER safe_stock_qty', 'SELECT 1');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;

SELECT '主料库存字段补齐 完成' AS result;
