-- 样衣BOM表
CREATE TABLE IF NOT EXISTS `t_erp_bom` (
  `bom_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'BOM ID',
  `bom_code` varchar(50) NOT NULL COMMENT 'BOM编号',
  `style_no` varchar(50) NOT NULL COMMENT '款号',
  `version` varchar(20) NOT NULL COMMENT '版本',
  `material_count` int(11) DEFAULT '0' COMMENT '主料数量',
  `auxiliary_count` int(11) DEFAULT '0' COMMENT '辅料数量',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `status` char(1) DEFAULT '0' COMMENT '状态',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`bom_id`),
  UNIQUE KEY `uk_bom_code` (`bom_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='样衣BOM表';

-- 样衣BOM主料表
CREATE TABLE IF NOT EXISTS `t_erp_bom_material` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `bom_id` bigint(20) NOT NULL COMMENT 'BOM ID',
  `material_id` bigint(20) NOT NULL COMMENT '主料ID',
  `material_name` varchar(100) NOT NULL COMMENT '主料名称',
  `spec` varchar(100) DEFAULT NULL COMMENT '规格',
  `unit` varchar(20) DEFAULT NULL COMMENT '单位',
  `quantity` decimal(10,2) NOT NULL COMMENT '数量',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '单价',
  `amount` decimal(10,2) DEFAULT '0.00' COMMENT '金额',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_bom_id` (`bom_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='样衣BOM主料表';

-- 样衣BOM辅料表
CREATE TABLE IF NOT EXISTS `t_erp_bom_auxiliary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `bom_id` bigint(20) NOT NULL COMMENT 'BOM ID',
  `auxiliary_id` bigint(20) NOT NULL COMMENT '辅料ID',
  `auxiliary_name` varchar(100) NOT NULL COMMENT '辅料名称',
  `spec` varchar(100) DEFAULT NULL COMMENT '规格',
  `unit` varchar(20) DEFAULT NULL COMMENT '单位',
  `quantity` decimal(10,2) NOT NULL COMMENT '数量',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '单价',
  `amount` decimal(10,2) DEFAULT '0.00' COMMENT '金额',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_bom_id` (`bom_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='样衣BOM辅料表';
