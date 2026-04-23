-- 2026-04-23 smoke demo seed
-- Minimal linked records for ERP smoke verification.

INSERT INTO t_erp_employee (
  employee_code, employee_name, phone, department, station, entry_date, status, remark, create_by, create_time
)
SELECT 'SMOKE-E001', '冒烟样例员工', '13800000001', '样衣车间', '锁边', '2026-04-23', '0', 'SMOKE seed', 'codex', NOW()
FROM DUAL
WHERE NOT EXISTS (
  SELECT 1 FROM t_erp_employee WHERE employee_code = 'SMOKE-E001'
);

INSERT INTO t_erp_standard_color (
  color_code, color_name, color_lab, default_delta_e, remark, create_by, create_time
)
SELECT 'SMOKE-BLK', '冒烟标准黑', '12.00,0.20,-0.50', 1.50, 'SMOKE seed', 'codex', NOW()
FROM DUAL
WHERE NOT EXISTS (
  SELECT 1 FROM t_erp_standard_color WHERE color_code = 'SMOKE-BLK'
);

INSERT INTO t_erp_process_loss_matrix (
  material_a_type, material_b_type, process_code, standard_loss_rate, actual_avg_loss, remark, create_by, create_time
)
SELECT '针织主布', '氨纶辅料', 'P01', 0.0350, 0.0280, 'SMOKE seed', 'codex', NOW()
FROM DUAL
WHERE NOT EXISTS (
  SELECT 1
  FROM t_erp_process_loss_matrix
  WHERE material_a_type = '针织主布'
    AND material_b_type = '氨纶辅料'
    AND process_code = 'P01'
);

INSERT INTO t_erp_employee_process_price (
  employee_id, process_id, unit_price, effective_from, status, remark, create_by, create_time
)
SELECT e.id, p.id, IFNULL(p.unit_price, 0.2000), CURDATE(), '0', 'SMOKE seed', 'codex', NOW()
FROM t_erp_employee e
JOIN t_erp_process_def p ON p.process_no = 'P01'
WHERE e.employee_code = 'SMOKE-E001'
  AND NOT EXISTS (
    SELECT 1
    FROM t_erp_employee_process_price x
    WHERE x.employee_id = e.id
      AND x.process_id = p.id
  );

INSERT INTO t_erp_product_serial (
  serial_no, order_id, job_id, produce_plan_id, color_code, size_code, current_process_id, current_process_name,
  status, create_time, remark
)
SELECT
  'SMOKE-SN-20260423-001',
  900001,
  900001,
  (SELECT COALESCE(MAX(id), 1) FROM t_erp_produce_plan),
  'SMOKE-BLK',
  'M',
  p.id,
  p.process_name,
  '0',
  NOW(),
  'SMOKE seed'
FROM t_erp_process_def p
WHERE p.process_no = 'P01'
  AND NOT EXISTS (
    SELECT 1 FROM t_erp_product_serial WHERE serial_no = 'SMOKE-SN-20260423-001'
  );

INSERT INTO t_erp_produce_job_process (
  job_id, process_id, process_seq, employee_id, employee_name, in_qty, out_qty, defect_qty,
  start_time, finish_time, process_status, loss_qty, loss_exceed, create_by, create_time, remark
)
SELECT
  900001,
  p.id,
  1,
  e.id,
  e.employee_name,
  12,
  12,
  0,
  DATE_SUB(NOW(), INTERVAL 2 HOUR),
  DATE_SUB(NOW(), INTERVAL 1 HOUR),
  'DONE',
  0,
  '0',
  'codex',
  NOW(),
  'SMOKE seed finished'
FROM t_erp_employee e
JOIN t_erp_process_def p ON p.process_no = 'P01'
WHERE e.employee_code = 'SMOKE-E001'
  AND NOT EXISTS (
    SELECT 1
    FROM t_erp_produce_job_process x
    WHERE x.job_id = 900001
      AND x.process_id = p.id
      AND x.employee_id = e.id
  );

INSERT INTO t_erp_produce_job_process (
  job_id, process_id, process_seq, employee_id, employee_name, in_qty, out_qty, defect_qty,
  start_time, finish_time, process_status, loss_qty, loss_exceed, create_by, create_time, remark
)
SELECT
  900002,
  p.id,
  2,
  e.id,
  e.employee_name,
  20,
  8,
  1,
  DATE_SUB(NOW(), INTERVAL 30 MINUTE),
  NULL,
  'IN_PROGRESS',
  1,
  '0',
  'codex',
  NOW(),
  'SMOKE seed in progress'
FROM t_erp_employee e
JOIN t_erp_process_def p ON p.process_no = 'P02'
WHERE e.employee_code = 'SMOKE-E001'
  AND NOT EXISTS (
    SELECT 1
    FROM t_erp_produce_job_process x
    WHERE x.job_id = 900002
      AND x.process_id = p.id
      AND x.employee_id = e.id
  );

INSERT INTO t_erp_piece_wage (
  employee_id, employee_name, wage_month, total_process_count, total_ok_qty, total_defect_qty,
  should_wage, deduct_wage, actual_wage, status, remark, create_by, create_time
)
SELECT
  e.id,
  e.employee_name,
  '2026-04',
  2,
  20,
  1,
  4.80,
  0.30,
  4.50,
  '0',
  'SMOKE seed',
  'codex',
  NOW()
FROM t_erp_employee e
WHERE e.employee_code = 'SMOKE-E001'
  AND NOT EXISTS (
    SELECT 1
    FROM t_erp_piece_wage w
    WHERE w.employee_id = e.id
      AND w.wage_month = '2026-04'
  );

INSERT INTO t_erp_piece_wage_detail (
  wage_id, employee_id, process_id, process_name, job_id, ok_qty, defect_qty,
  process_price, should_wage, deduct_wage, actual_wage, remark, create_by, create_time
)
SELECT
  w.id,
  e.id,
  p.id,
  p.process_name,
  900001,
  12,
  0,
  IFNULL(p.unit_price, 0.2000),
  ROUND(12 * IFNULL(p.unit_price, 0.2000), 2),
  0.00,
  ROUND(12 * IFNULL(p.unit_price, 0.2000), 2),
  'SMOKE seed',
  'codex',
  NOW()
FROM t_erp_piece_wage w
JOIN t_erp_employee e ON e.id = w.employee_id
JOIN t_erp_process_def p ON p.process_no = 'P01'
WHERE e.employee_code = 'SMOKE-E001'
  AND w.wage_month = '2026-04'
  AND NOT EXISTS (
    SELECT 1
    FROM t_erp_piece_wage_detail d
    WHERE d.wage_id = w.id
      AND d.process_id = p.id
      AND d.job_id = 900001
  );

INSERT INTO t_erp_piece_wage_detail (
  wage_id, employee_id, process_id, process_name, job_id, ok_qty, defect_qty,
  process_price, should_wage, deduct_wage, actual_wage, remark, create_by, create_time
)
SELECT
  w.id,
  e.id,
  p.id,
  p.process_name,
  900002,
  8,
  1,
  IFNULL(p.unit_price, 0.3000),
  ROUND(8 * IFNULL(p.unit_price, 0.3000), 2),
  0.30,
  ROUND(8 * IFNULL(p.unit_price, 0.3000), 2) - 0.30,
  'SMOKE seed',
  'codex',
  NOW()
FROM t_erp_piece_wage w
JOIN t_erp_employee e ON e.id = w.employee_id
JOIN t_erp_process_def p ON p.process_no = 'P02'
WHERE e.employee_code = 'SMOKE-E001'
  AND w.wage_month = '2026-04'
  AND NOT EXISTS (
    SELECT 1
    FROM t_erp_piece_wage_detail d
    WHERE d.wage_id = w.id
      AND d.process_id = p.id
      AND d.job_id = 900002
  );

INSERT INTO t_erp_produce_job (
  job_no, produce_plan_id, order_id, color_code, size_code, plan_qty, actual_qty, defect_qty,
  current_process_id, current_process_status, start_time, finish_time, confirm_qty,
  status, create_by, create_time, remark
)
SELECT
  'SMOKE-JOB-20260423-001',
  (SELECT COALESCE(MAX(id), 1) FROM t_erp_produce_plan),
  900001,
  'SMOKE-BLK',
  'M',
  12,
  12,
  0,
  p.id,
  '2',
  DATE_SUB(NOW(), INTERVAL 1 DAY),
  DATE_SUB(NOW(), INTERVAL 22 HOUR),
  12,
  '2',
  'codex',
  NOW(),
  'SMOKE seed completed job'
FROM t_erp_process_def p
WHERE p.process_no = 'P02'
  AND NOT EXISTS (
    SELECT 1 FROM t_erp_produce_job WHERE job_no = 'SMOKE-JOB-20260423-001'
  );

INSERT INTO t_erp_produce_job (
  job_no, produce_plan_id, order_id, color_code, size_code, plan_qty, actual_qty, defect_qty,
  current_process_id, current_process_status, start_time, finish_time, confirm_qty,
  status, create_by, create_time, remark
)
SELECT
  'SMOKE-JOB-20260423-002',
  (SELECT COALESCE(MAX(id), 1) FROM t_erp_produce_plan),
  900002,
  'SMOKE-BLK',
  'L',
  120,
  18,
  2,
  p.id,
  '1',
  DATE_SUB(NOW(), INTERVAL 4 HOUR),
  NULL,
  18,
  '1',
  'codex',
  NOW(),
  'SMOKE seed wip job'
FROM t_erp_process_def p
WHERE p.process_no = 'P02'
  AND NOT EXISTS (
    SELECT 1 FROM t_erp_produce_job WHERE job_no = 'SMOKE-JOB-20260423-002'
  );
