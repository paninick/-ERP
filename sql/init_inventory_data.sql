-- =============================================
-- 初始化库存数据
-- 根据已有的入库单和出库单自动计算初始库存
-- 库存 = 入库总数量 - 出库总数量
-- =============================================

-- 说明：
-- 1. 此脚本假设 t_erp_inv_stock 表已创建
-- 2. 此脚本会清除已有数据并重新计算
-- 3. 需要确保入库单明细和出库单明细中的 sku_id 不为空

-- 清空现有库存数据
TRUNCATE TABLE `t_erp_inv_stock`;

-- 插入计算后的初始库存
INSERT INTO `t_erp_inv_stock` (`warehouse_id`, `sku_id`, `material_id`, `inv_qty`, `lock_qty`, `version`, `create_time`, `update_time`)
SELECT
    COALESCE(i.warehouse_id, o.warehouse_id) AS warehouse_id,
    COALESCE(i.sku_id, o.sku_id) AS sku_id,
    COALESCE(i.material_id, o.material_id) AS material_id,
    COALESCE(i.total_in, 0) - COALESCE(i.total_out, 0) AS inv_qty,
    0 AS lock_qty,
    0 AS version,
    NOW() AS create_time,
    NOW() AS update_time
FROM
    (
        -- 计算每个仓库 + SKU 的入库总量
        SELECT
            si.warehouse_id,
            sii.sku_id,
            sii.material_id,
            SUM(sii.qty) AS total_in
        FROM `t_erp_stock_in` si
        INNER JOIN `t_erp_stock_in_item` sii ON si.id = sii.stock_in_id
        WHERE si.confirm_status = '1' -- 只计算已确认的入库
        GROUP BY si.warehouse_id, sii.sku_id, sii.material_id
    ) i
LEFT JOIN
    (
        -- 计算每个仓库 + SKU 的出库总量
        SELECT
            so.warehouse_id,
            soi.sku_id,
            soi.material_id,
            SUM(soi.qty) AS total_out
        FROM `t_erp_stock_out` so
        INNER JOIN `t_erp_stock_out_item` soi ON so.id = soi.stock_out_id
        WHERE so.confirm_status = '1' -- 只计算已确认的出库
        GROUP BY so.warehouse_id, soi.sku_id, soi.material_id
    ) o
ON i.warehouse_id = o.warehouse_id AND i.sku_id = o.sku_id

UNION

-- 处理只有出库没有入库的情况（负数库存，需要检查修正）
SELECT
    o.warehouse_id,
    o.sku_id,
    o.material_id,
    0 - o.total_out AS inv_qty,
    0 AS lock_qty,
    0 AS version,
    NOW() AS create_time,
    NOW() AS update_time
FROM
    (
        SELECT
            so.warehouse_id,
            soi.sku_id,
            soi.material_id,
            SUM(soi.qty) AS total_out
        FROM `t_erp_stock_out` so
        INNER JOIN `t_erp_stock_out_item` soi ON so.id = soi.stock_out_id
        WHERE so.confirm_status = '1'
        GROUP BY so.warehouse_id, soi.sku_id, soi.material_id
    ) o
WHERE NOT EXISTS (
    SELECT 1 FROM `t_erp_stock_in` si
    INNER JOIN `t_erp_stock_in_item` sii ON si.id = sii.stock_in_id
    WHERE si.warehouse_id = o.warehouse_id AND sii.sku_id = o.sku_id
);

-- 查询结果验证
-- SELECT * FROM `t_erp_inv_stock` WHERE inv_qty < 0;
-- 如果有负数库存，说明数据有问题（出库大于入库），需要人工检查修正
