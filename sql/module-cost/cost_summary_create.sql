-- Wave 4: 统一成本汇总表
-- 幂等，可重复执行

CREATE TABLE IF NOT EXISTS t_erp_cost_summary (
  id              BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键',
  produce_job_id  BIGINT        COMMENT '生产单ID',
  produce_plan_id BIGINT        COMMENT '生产计划ID',
  style_code      VARCHAR(50)   COMMENT '款号',
  sales_no        VARCHAR(50)   COMMENT '销售单号',
  period          VARCHAR(10)   COMMENT '汇总期间 yyyy-MM',
  material_cost   DECIMAL(18,4) DEFAULT 0 COMMENT '物料成本',
  wage_cost       DECIMAL(18,4) DEFAULT 0 COMMENT '计件工资成本',
  outsource_cost  DECIMAL(18,4) DEFAULT 0 COMMENT '外协成本',
  freight_cost    DECIMAL(18,4) DEFAULT 0 COMMENT '运费成本',
  quality_loss    DECIMAL(18,4) DEFAULT 0 COMMENT '质量损耗成本',
  other_cost      DECIMAL(18,4) DEFAULT 0 COMMENT '其他成本',
  total_cost      DECIMAL(18,4) DEFAULT 0 COMMENT '合计成本',
  finish_qty      DECIMAL(18,3) DEFAULT 0 COMMENT '完工数量',
  unit_cost       DECIMAL(18,6) DEFAULT 0 COMMENT '单件成本',
  calc_time       DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '计算时间',
  factory_id      BIGINT        COMMENT '工厂ID',
  remark          VARCHAR(500)  COMMENT '备注',
  create_by       VARCHAR(64),
  create_time     DATETIME      DEFAULT CURRENT_TIMESTAMP,
  update_by       VARCHAR(64),
  update_time     DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY idx_produce_job_id (produce_job_id),
  KEY idx_produce_plan_id (produce_plan_id),
  KEY idx_style_code (style_code),
  KEY idx_period (period)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='统一成本汇总';

SELECT 'cost_summary_create.sql 执行完成' AS result;
