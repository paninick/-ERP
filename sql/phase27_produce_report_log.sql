-- Production report event layer
-- Date: 2026-04-25

CREATE TABLE IF NOT EXISTS `t_erp_produce_report_log` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'primary id',
    `job_id` bigint(20) NOT NULL COMMENT 'produce job id',
    `job_process_id` bigint(20) NOT NULL COMMENT 'job process snapshot id',
    `process_id` bigint(20) NOT NULL COMMENT 'process id',
    `process_seq` int(11) DEFAULT NULL COMMENT 'process sequence in job',
    `employee_id` bigint(20) DEFAULT NULL COMMENT 'employee id',
    `employee_name` varchar(64) DEFAULT NULL COMMENT 'employee name',
    `operator_name` varchar(64) DEFAULT NULL COMMENT 'paper operator name',
    `team_name` varchar(64) DEFAULT NULL COMMENT 'team name',
    `machine_no` varchar(64) DEFAULT NULL COMMENT 'machine number',
    `report_qty` int(11) NOT NULL DEFAULT '0' COMMENT 'reported total qty',
    `completed_qty` int(11) NOT NULL DEFAULT '0' COMMENT 'completed qty',
    `defect_qty` int(11) NOT NULL DEFAULT '0' COMMENT 'defect qty',
    `loss_qty` int(11) NOT NULL DEFAULT '0' COMMENT 'loss qty',
    `report_type` varchar(32) DEFAULT 'WORKSHOP' COMMENT 'report type',
    `report_source` varchar(32) DEFAULT 'PAPER_BATCH' COMMENT 'report source',
    `batch_no` varchar(64) DEFAULT NULL COMMENT 'batch no',
    `scan_token` varchar(128) DEFAULT NULL COMMENT 'scan token',
    `billing_group_id` varchar(64) DEFAULT NULL COMMENT 'billing group id',
    `is_outsourced` char(1) DEFAULT '0' COMMENT 'is outsourced',
    `vendor_id` bigint(20) DEFAULT NULL COMMENT 'vendor id',
    `validation_status` varchar(32) DEFAULT 'PASS' COMMENT 'validation status',
    `validation_message` varchar(500) DEFAULT NULL COMMENT 'validation message',
    `event_time` datetime DEFAULT NULL COMMENT 'event time',
    `remark` varchar(500) DEFAULT NULL COMMENT 'remark',
    `del_flag` char(1) DEFAULT '0' COMMENT 'delete flag',
    `create_by` varchar(64) DEFAULT '' COMMENT 'create by',
    `create_time` datetime DEFAULT NULL COMMENT 'create time',
    `update_by` varchar(64) DEFAULT '' COMMENT 'update by',
    `update_time` datetime DEFAULT NULL COMMENT 'update time',
    PRIMARY KEY (`id`),
    KEY `idx_erp_prl_job_process` (`job_process_id`),
    KEY `idx_erp_prl_job` (`job_id`),
    KEY `idx_erp_prl_process` (`process_id`),
    KEY `idx_erp_prl_event_time` (`event_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='ERP produce report event log';

DELETE FROM sys_dict_data WHERE dict_type IN (
    'erp_report_type',
    'erp_report_source',
    'erp_report_validation_status'
);

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
VALUES
(1, '车间转录', 'WORKSHOP', 'erp_report_type', '', 'default', 'Y', '0', 'admin', NOW(), 'ERP report type'),
(2, '外协回港', 'OUTSOURCE_RETURN', 'erp_report_type', '', 'warning', 'N', '0', 'admin', NOW(), 'ERP report type'),
(3, '扫码自主', 'SELF_SCAN', 'erp_report_type', '', 'success', 'N', '0', 'admin', NOW(), 'ERP report type'),
(1, '纸单批录', 'PAPER_BATCH', 'erp_report_source', '', 'default', 'Y', '0', 'admin', NOW(), 'ERP report source'),
(2, '扫码录入', 'SCAN', 'erp_report_source', '', 'success', 'N', '0', 'admin', NOW(), 'ERP report source'),
(3, '移动录入', 'MOBILE', 'erp_report_source', '', 'primary', 'N', '0', 'admin', NOW(), 'ERP report source'),
(4, '外协回港', 'OUTSOURCE_RETURN', 'erp_report_source', '', 'warning', 'N', '0', 'admin', NOW(), 'ERP report source'),
(5, '人工修正', 'MANUAL_ADJUST', 'erp_report_source', '', 'danger', 'N', '0', 'admin', NOW(), 'ERP report source'),
(1, '通过', 'PASS', 'erp_report_validation_status', '', 'success', 'Y', '0', 'admin', NOW(), 'ERP validation status'),
(2, '预警', 'WARN', 'erp_report_validation_status', '', 'warning', 'N', '0', 'admin', NOW(), 'ERP validation status'),
(3, '阻断', 'BLOCK', 'erp_report_validation_status', '', 'danger', 'N', '0', 'admin', NOW(), 'ERP validation status');


-- Menu registration: 报工流水 under 生产管理 (parent=2003)
INSERT IGNORE INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES
(2618, '报工流水', 2003, 5, 'produceReportLog', 'erp/produceReportLog/index', '', 1, 0, 'C', '0', '0', 'erp:produceReportLog:list', 'ep-document', 'admin', NOW(), '', NULL, ''),
(2619, '查询', 2618, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:produceReportLog:query',  '#', 'admin', NOW(), '', NULL, ''),
(2620, '新增', 2618, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:produceReportLog:add',    '#', 'admin', NOW(), '', NULL, ''),
(2621, '修改', 2618, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:produceReportLog:edit',   '#', 'admin', NOW(), '', NULL, ''),
(2622, '删除', 2618, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:produceReportLog:remove', '#', 'admin', NOW(), '', NULL, ''),
(2623, '导出', 2618, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:produceReportLog:export', '#', 'admin', NOW(), '', NULL, '');

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT 1, menu_id FROM sys_menu WHERE menu_id BETWEEN 2618 AND 2623;
