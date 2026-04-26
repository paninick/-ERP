-- 主料价格+库存字段补齐 (Mapper XML 引用但表缺失)
-- 幂等，可重复执行

SET @c=(SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_main_material' AND COLUMN_NAME='purchase_price');
SET @s=IF(@c=0,'ALTER TABLE t_erp_main_material ADD COLUMN purchase_price DECIMAL(18,4) DEFAULT NULL COMMENT ''采购价'' AFTER min_order_qty','SELECT 1');PREPARE p FROM @s;EXECUTE p;DEALLOCATE PREPARE p;

SET @c=(SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_main_material' AND COLUMN_NAME='outsource_price');
SET @s=IF(@c=0,'ALTER TABLE t_erp_main_material ADD COLUMN outsource_price DECIMAL(18,4) DEFAULT NULL COMMENT ''外发价'' AFTER purchase_price','SELECT 1');PREPARE p FROM @s;EXECUTE p;DEALLOCATE PREPARE p;

SET @c=(SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_main_material' AND COLUMN_NAME='standard_cost');
SET @s=IF(@c=0,'ALTER TABLE t_erp_main_material ADD COLUMN standard_cost DECIMAL(18,4) DEFAULT NULL COMMENT ''标准成本'' AFTER outsource_price','SELECT 1');PREPARE p FROM @s;EXECUTE p;DEALLOCATE PREPARE p;

SET @c=(SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_main_material' AND COLUMN_NAME='retail_price');
SET @s=IF(@c=0,'ALTER TABLE t_erp_main_material ADD COLUMN retail_price DECIMAL(18,4) DEFAULT NULL COMMENT ''零售价'' AFTER standard_cost','SELECT 1');PREPARE p FROM @s;EXECUTE p;DEALLOCATE PREPARE p;

SET @c=(SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_main_material' AND COLUMN_NAME='wholesale_price');
SET @s=IF(@c=0,'ALTER TABLE t_erp_main_material ADD COLUMN wholesale_price DECIMAL(18,4) DEFAULT NULL COMMENT ''批发价'' AFTER retail_price','SELECT 1');PREPARE p FROM @s;EXECUTE p;DEALLOCATE PREPARE p;

SET @c=(SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_main_material' AND COLUMN_NAME='current_stock_qty');
SET @s=IF(@c=0,'ALTER TABLE t_erp_main_material ADD COLUMN current_stock_qty DECIMAL(18,3) DEFAULT 0 COMMENT ''当前库存'' AFTER wholesale_price','SELECT 1');PREPARE p FROM @s;EXECUTE p;DEALLOCATE PREPARE p;

SELECT 'main_material 价格+库存列补齐完成' AS result;
