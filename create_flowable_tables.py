
import subprocess
import sys

sql_statements = [
    """
    CREATE TABLE IF NOT EXISTS flowable_form (
        id bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
        form_name varchar(100) DEFAULT '' COMMENT '表单名称',
        form_key varchar(200) DEFAULT '' COMMENT '表单KEY',
        form_type varchar(20) DEFAULT 'static' COMMENT '表单类型',
        status char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
        remark varchar(500) DEFAULT NULL COMMENT '备注',
        create_by varchar(64) DEFAULT '' COMMENT '创建者',
        create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
        update_by varchar(64) DEFAULT '' COMMENT '更新者',
        update_time datetime DEFAULT NULL COMMENT '更新时间',
        PRIMARY KEY (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程表单配置';
    """,
    """
    CREATE TABLE IF NOT EXISTS flowable_expression (
        id bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
        expression_name varchar(100) DEFAULT '' COMMENT '表达式名称',
        expression_type varchar(50) DEFAULT '' COMMENT '表达式类型',
        expression_text text COMMENT '表达式内容',
        process_def_id varchar(64) DEFAULT '' COMMENT '流程定义ID',
        status char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
        remark varchar(500) DEFAULT NULL COMMENT '备注',
        create_by varchar(64) DEFAULT '' COMMENT '创建者',
        create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
        update_by varchar(64) DEFAULT '' COMMENT '更新者',
        update_time datetime DEFAULT NULL COMMENT '更新时间',
        PRIMARY KEY (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程表达式';
    """,
    """
    CREATE TABLE IF NOT EXISTS flowable_listener (
        id bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
        listener_name varchar(100) DEFAULT '' COMMENT '监听器名称',
        listener_type varchar(50) DEFAULT '' COMMENT '监听器类型',
        listener_value varchar(500) DEFAULT '' COMMENT '监听器值',
        event_type varchar(50) DEFAULT '' COMMENT '事件类型',
        process_def_id varchar(64) DEFAULT '' COMMENT '流程定义ID',
        status char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
        remark varchar(500) DEFAULT NULL COMMENT '备注',
        create_by varchar(64) DEFAULT '' COMMENT '创建者',
        create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
        update_by varchar(64) DEFAULT '' COMMENT '更新者',
        update_time datetime DEFAULT NULL COMMENT '更新时间',
        PRIMARY KEY (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程监听器';
    """
]

print("=" * 80)
print("创建 Flowable 数据库表")
print("=" * 80)

mysql_path = r"C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe"

for i, sql in enumerate(sql_statements, 1):
    try:
        result = subprocess.run(
            [mysql_path, '-u', 'root', '-e', sql, 'ry_vue'],
            capture_output=True,
            text=True,
            encoding='utf-8'
        )
        if result.returncode == 0:
            print(f"  [OK] 表 {i} 创建成功")
        else:
            print(f"  [FAIL] 表 {i} 创建失败: {result.stderr}")
    except Exception as e:
        print(f"  [ERROR] 表 {i} 创建异常: {str(e)}")

print("\n" + "=" * 80)
print("数据库表创建完成!")
print("=" * 80)
