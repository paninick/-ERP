-- E2E 全流程测试数据: 客户→销售→计划→生产→报工→消耗→质检→出货
-- 幂等: 使用 IGNORE 跳过已存在的记录
SET @now = NOW();

-- 1. 客户 (日单)
INSERT IGNORE INTO t_erp_customer (id, customer_no, customer_name, is_japan_order, require_inspection, aql_level, create_by, create_time)
VALUES (1001, 'CUS-001', '東京スタイル株式会社', 1, 1, 'AQL_2_5', 'admin', @now);

-- 2. 款号
INSERT IGNORE INTO t_erp_style (id, style_code, style_name, product_family, status, create_by, create_time)
VALUES (1001, 'SW-2026-001', '精纺羊绒圆领衫', 'SWEATER', 'ACTIVE', 'admin', @now);

-- 3. 销售订单
INSERT IGNORE INTO t_erp_sales_order (id, sales_no, style_code, style_no, customer_id, customer_name, color_confirmed, order_status, create_by, create_time)
VALUES (1001, 'SO-2026-0001', 'SW-2026-001', 'SW-2026-001', 1001, '東京スタイル株式会社', 'N', 'DRAFT', 'admin', @now);

-- 4. 销售材料(客供纱线)
INSERT IGNORE INTO t_erp_sales_order_material (id, sales_order_id, material_type, supply_type, plan_quantity, create_by, create_time)
VALUES (1001, 1001, 'MAIN', 'CUSTOMER', 500, 'admin', @now);

-- 5. BOM (fabric_type = SWEATER)
INSERT IGNORE INTO t_erp_bom (id, fabric_type, audit_status, create_by, create_time)
VALUES (1001, 'SWEATER', 'APPROVED', 'admin', @now);

-- 6. BOM材料
INSERT IGNORE INTO t_erp_bom_material (id, bom_id, material_name, supply_type, quantity, unit, create_by, create_time)
VALUES (1001, 1001, '48S/2 精纺羊绒纱', 'CUSTOMER', 0.35, 'KG', 'admin', @now);

-- 7. 生产计划
INSERT IGNORE INTO t_erp_produce_plan (id, plan_no, bulk_order_no, sales_order_id, style_code, plan_status, priority, produce_type, customer_name, create_by, create_time)
VALUES (1001, 'PLAN-2026-0001', 'SO-2026-0001', 1001, 'SW-2026-001', 'APPROVED', 'NORMAL', 'SWEATER', '東京スタイル株式会社', 'admin', @now);

-- 8. 生产单
INSERT IGNORE INTO t_erp_produce_job (id, job_no, produce_plan_id, style_code, order_id, plan_qty, status, create_by, create_time)
VALUES (1001, 'JOB-2026-0001', 1001, 'SW-2026-001', 1001, 500, 'RUNNING', 'admin', @now);

-- 9. 工序快照(横机)
INSERT IGNORE INTO t_erp_produce_job_process (id, job_id, process_id, process_seq, employee_name, in_qty, process_status, create_by, create_time)
VALUES (1001, 1001, 3, 1, '张师傅', 500, 'RUNNING', 'admin', @now);

-- 10. 报工日志
INSERT IGNORE INTO t_erp_produce_report_log (id, job_id, job_process_id, process_id, employee_id, employee_name, completed_qty, defect_qty, report_type, validation_status, event_time, del_flag, create_by, create_time)
VALUES (1001, 1001, 1001, 3, 1, '张师傅', 480, 20, 'SCAN', 'PASS', @now, '0', 'admin', @now);

-- 11. 物料消耗
INSERT IGNORE INTO t_erp_produce_material_consume (id, produce_plan_id, report_log_id, material_name, bom_qty, actual_qty, unit, event_source, create_by, create_time)
VALUES (1001, 1001, 1001, '48S/2 羊绒纱', 0.35, 0.36, 'KG', 'REPORT_LOG', 'admin', @now);

-- 12. 质检
INSERT IGNORE INTO t_erp_qc_inspection (id, job_id, process_id, batch_no, order_no, qc_type, result, factory_id, create_by, create_time)
VALUES (1001, 1001, 3, 'JOB-2026-0001', 'SO-2026-0001', 'IPQC', 'PASS', 1, 'admin', @now);

-- 13. 检品预约
INSERT IGNORE INTO t_erp_inspection_booking (id, booking_no, sales_order_id, sales_no, style_code, inspection_company_id, booking_date, status, create_by, create_time)
VALUES (1001, 'BK-2026-0001', 1001, 'SO-2026-0001', 'SW-2026-001', 1, CURDATE() + INTERVAL 7 DAY, 'WAIT_BOOKING', 'admin', @now);

-- 14. 出货单
INSERT IGNORE INTO t_erp_shipment (id, shipment_no, sales_order_id, sales_no, style_code, customer_name, total_qty, total_carton, vessel_name, etd, eta, port_of_loading, port_of_discharge, status, release_status, create_by, create_time)
VALUES (1001, 'SHIP-2026-0001', 1001, 'SO-2026-0001', 'SW-2026-001', '東京スタイル株式会社', 480, 24, 'EVER FORTUNE 062E', CURDATE() + INTERVAL 14 DAY, CURDATE() + INTERVAL 21 DAY, '上海', '东京', 'DRAFT', 'PENDING', 'admin', @now);

SELECT 'E2E全链路: 客户→订单→计划→生产→报工→消耗→质检→检品→出货 ✓' AS result;
