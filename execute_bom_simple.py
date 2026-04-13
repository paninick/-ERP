
import pymysql
from db_config import get_connection

def execute_sql():
    conn = None
    try:
        conn = get_connection()
        cursor = conn.cursor()
        
        print("=" * 80)
        print("开始执行SQL")
        print("=" * 80)
        
        # 1. 备份原表
        print("\n1. 备份原表...")
        cursor.execute("CREATE TABLE IF NOT EXISTS t_erp_bom_backup AS SELECT * FROM t_erp_bom")
        conn.commit()
        print("备份完成")
        
        # 2. 删除原表
        print("\n2. 删除原表...")
        cursor.execute("DROP TABLE IF EXISTS t_erp_bom")
        conn.commit()
        print("删除完成")
        
        # 3. 创建新表
        print("\n3. 创建新表...")
        create_sql = """
        CREATE TABLE t_erp_bom (
            id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
            sample_no VARCHAR(64) DEFAULT NULL COMMENT '打样编号',
            sample_type VARCHAR(64) DEFAULT NULL COMMENT '打样类型',
            customer_id BIGINT DEFAULT NULL COMMENT '客户id',
            customer_name VARCHAR(255) DEFAULT NULL COMMENT '客户名称',
            style_category VARCHAR(64) DEFAULT NULL COMMENT '款式大类',
            style_sub_category VARCHAR(64) DEFAULT NULL COMMENT '款式小类',
            style_type VARCHAR(64) DEFAULT NULL COMMENT '样品款式',
            sample_category_type VARCHAR(64) DEFAULT NULL COMMENT '样品种类',
            style_code VARCHAR(64) DEFAULT NULL COMMENT '打样款号',
            due_date DATETIME DEFAULT NULL COMMENT '要求交期',
            emergency_type VARCHAR(64) DEFAULT NULL COMMENT '紧急程度',
            picture_url VARCHAR(500) DEFAULT NULL COMMENT '款式图片',
            audit_status VARCHAR(64) DEFAULT NULL COMMENT '审批状态',
            progress_status VARCHAR(64) DEFAULT NULL COMMENT '进行状态',
            audit_by VARCHAR(255) DEFAULT NULL COMMENT '审批人id',
            audit_by_nick_name VARCHAR(255) DEFAULT NULL COMMENT '审批人昵称',
            sales_id BIGINT DEFAULT NULL COMMENT '业务员ID',
            sales_name VARCHAR(100) DEFAULT NULL COMMENT '业务员',
            tech_no VARCHAR(64) DEFAULT NULL COMMENT '工艺书编号',
            audit_time DATETIME DEFAULT NULL COMMENT '审批时间',
            process_instance_id VARCHAR(64) DEFAULT NULL COMMENT '流程实例id',
            bulk_order_no VARCHAR(64) DEFAULT NULL COMMENT '大货款号',
            create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
            create_time DATETIME DEFAULT NULL COMMENT '创建时间',
            update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
            update_time DATETIME DEFAULT NULL COMMENT '更新时间',
            remark VARCHAR(500) DEFAULT '' COMMENT '备注',
            PRIMARY KEY (id)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='样衣BOM表'
        """
        cursor.execute(create_sql)
        conn.commit()
        print("创建完成")
        
        print("\n" + "=" * 80)
        print("SQL执行完成")
        print("=" * 80)
        
        # 验证表结构
        print("\n验证表结构:")
        cursor.execute("DESCRIBE t_erp_bom")
        for row in cursor.fetchall():
            print(row)
            
    except Exception as e:
        print(f"错误: {e}")
        import traceback
        traceback.print_exc()
        if conn:
            conn.rollback()
    finally:
        if conn:
            conn.close()

if __name__ == "__main__":
    execute_sql()

