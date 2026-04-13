
-- ============================================================
-- 为主料表添加price字段
-- ============================================================

-- 1. 查看表结构
DESCRIBE t_erp_main_material;

-- 2. 添加price字段（如果不存在）
ALTER TABLE t_erp_main_material 
ADD COLUMN IF NOT EXISTS price DECIMAL(10,2) DEFAULT NULL COMMENT '单价' 
AFTER pictrue_url;

-- 3. 验证字段是否添加成功
DESCRIBE t_erp_main_material;

