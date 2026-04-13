-- 为打样通知表添加缺失字段
-- 数据库: ry_vue

-- 添加字段：打样编号
ALTER TABLE t_erp_sample_notice ADD COLUMN sample_no VARCHAR(64) COMMENT '打样编号' AFTER id;

-- 添加字段：款式大类
ALTER TABLE t_erp_sample_notice ADD COLUMN style_category VARCHAR(64) COMMENT '款式大类' AFTER customer_id;

-- 添加字段：款式小类
ALTER TABLE t_erp_sample_notice ADD COLUMN style_sub_category VARCHAR(64) COMMENT '款式小类' AFTER style_category;

-- 添加字段：流程实例ID
ALTER TABLE t_erp_sample_notice ADD COLUMN process_instance_id VARCHAR(64) COMMENT '流程实例ID' AFTER audit_time;

-- 添加字段：大货款号
ALTER TABLE t_erp_sample_notice ADD COLUMN bulk_order_no VARCHAR(64) COMMENT '大货款号' AFTER process_instance_id;

-- 验证字段添加
DESCRIBE t_erp_sample_notice;
