-- Material consume stock-out linkage enhancement
-- Date: 2026-04-25

SET @c1 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_produce_material_consume' AND COLUMN_NAME='stock_out_id');
SET @s1 = IF(@c1=0, 'ALTER TABLE t_erp_produce_material_consume ADD COLUMN stock_out_id BIGINT DEFAULT NULL COMMENT ''出库单ID'' AFTER report_log_id', 'SELECT 1');
PREPARE p FROM @s1; EXECUTE p; DEALLOCATE PREPARE p;

SET @c2 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_produce_material_consume' AND COLUMN_NAME='stock_out_item_id');
SET @s2 = IF(@c2=0, 'ALTER TABLE t_erp_produce_material_consume ADD COLUMN stock_out_item_id BIGINT DEFAULT NULL COMMENT ''出库单明细ID'' AFTER stock_out_id', 'SELECT 1');
PREPARE p FROM @s2; EXECUTE p; DEALLOCATE PREPARE p;

SET @idx_stock_out_exists := (
    SELECT COUNT(1)
    FROM information_schema.statistics
    WHERE table_schema = DATABASE()
      AND table_name = 't_erp_produce_material_consume'
      AND index_name = 'idx_erp_pmc_stock_out'
);
SET @idx_stock_out_sql := IF(@idx_stock_out_exists = 0,
    'CREATE INDEX `idx_erp_pmc_stock_out` ON `t_erp_produce_material_consume` (`stock_out_id`)',
    'SELECT 1');
PREPARE stmt FROM @idx_stock_out_sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @idx_stock_out_item_exists := (
    SELECT COUNT(1)
    FROM information_schema.statistics
    WHERE table_schema = DATABASE()
      AND table_name = 't_erp_produce_material_consume'
      AND index_name = 'idx_erp_pmc_stock_out_item'
);
-- UNIQUE constraint to prevent duplicate records from concurrent syncByStockOut calls
SET @uk_stock_out_item_exists := (
    SELECT COUNT(1) FROM information_schema.statistics
    WHERE table_schema = DATABASE() AND table_name = 't_erp_produce_material_consume'
    AND index_name = 'uk_stock_out_item'
);
SET @uk_stock_out_item_sql := IF(@uk_stock_out_item_exists = 0,
    'ALTER TABLE t_erp_produce_material_consume ADD UNIQUE INDEX uk_stock_out_item (stock_out_item_id)',
    'SELECT 1');
PREPARE stmt FROM @uk_stock_out_item_sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
