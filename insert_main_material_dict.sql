
-- 插入主料类型字典配置
USE ry_vue;

-- 先检查是否已有配置
SELECT * FROM sys_dict_data WHERE dict_type = 'erp_main_material_type' ORDER BY dict_sort;

-- 插入字典配置
INSERT INTO sys_dict_data 
(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark)
VALUES
(1, '厂供', '1', 'erp_main_material_type', '', 'default', 'Y', '0', 'admin', NOW(), '', NOW(), '厂供'),
(2, '客供', '2', 'erp_main_material_type', '', 'default', 'N', '0', 'admin', NOW(), '', NOW(), '客供');

-- 验证插入结果
SELECT * FROM sys_dict_data WHERE dict_type = 'erp_main_material_type' ORDER BY dict_sort;

