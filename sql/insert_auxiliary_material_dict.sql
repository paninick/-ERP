-- 辅料类型字典配置
-- 字典类型：erp_auxiliary_material_type

-- 检查当前字典配置
SELECT * FROM sys_dict_type WHERE dict_type = 'erp_auxiliary_material_type';

-- 如果不存在，先添加字典类型
INSERT IGNORE INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
VALUES ('辅料类型', 'erp_auxiliary_material_type', '0', 'admin', NOW(), '辅料类型字典');

-- 获取字典类型ID（如果需要）
-- SELECT dict_id FROM sys_dict_type WHERE dict_type = 'erp_auxiliary_material_type';

-- 添加字典数据
INSERT IGNORE INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time, remark)
VALUES 
(1, '纽扣', '1', 'erp_auxiliary_material_type', '0', 'admin', NOW(), '纽扣类辅料'),
(2, '拉链', '2', 'erp_auxiliary_material_type', '0', 'admin', NOW(), '拉链类辅料'),
(3, '织带', '3', 'erp_auxiliary_material_type', '0', 'admin', NOW(), '织带类辅料'),
(4, '衬布', '4', 'erp_auxiliary_material_type', '0', 'admin', NOW(), '衬布类辅料'),
(5, '线', '5', 'erp_auxiliary_material_type', '0', 'admin', NOW(), '线类辅料'),
(6, '商标', '6', 'erp_auxiliary_material_type', '0', 'admin', NOW(), '商标类辅料'),
(7, '包装', '7', 'erp_auxiliary_material_type', '0', 'admin', NOW(), '包装类辅料'),
(8, '其他', '8', 'erp_auxiliary_material_type', '0', 'admin', NOW(), '其他辅料'),
(9, '衬料', '9', 'erp_auxiliary_material_type', '0', 'admin', NOW(), '衬料类辅料');

-- 验证字典数据
SELECT * FROM sys_dict_data WHERE dict_type = 'erp_auxiliary_material_type' ORDER BY dict_sort;
