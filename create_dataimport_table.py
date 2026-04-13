import mysql.connector

conn = mysql.connector.connect(host='localhost', port=3306, user='root', password='', database='ry_vue')
cur = conn.cursor()

print("=== 创建 data_import 表 ===\n")

sql = """
CREATE TABLE IF NOT EXISTS `data_import` (
  `import_id` bigint NOT NULL AUTO_INCREMENT COMMENT '导入ID',
  `import_name` varchar(200) DEFAULT NULL COMMENT '导入名称',
  `table_name` varchar(200) DEFAULT NULL COMMENT '导入表名',
  `file_name` varchar(500) DEFAULT NULL COMMENT '文件名',
  `file_path` varchar(1000) DEFAULT NULL COMMENT '文件路径',
  `total_count` int DEFAULT NULL COMMENT '总记录数',
  `success_count` int DEFAULT NULL COMMENT '成功数',
  `fail_count` int DEFAULT NULL COMMENT '失败数',
  `status` char(1) DEFAULT NULL COMMENT '状态（0处理中 1成功 2失败）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`import_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据导入记录表';
"""

try:
    cur.execute(sql)
    conn.commit()
    print("✅ data_import 表创建成功")
except Exception as e:
    if "already exists" in str(e):
        print("⚠️ 表已存在")
    else:
        print(f"❌ 错误: {e}")

# 验证
cur.execute("SELECT COUNT(*) FROM data_import")
print(f"\n当前记录数: {cur.fetchone()[0]}")

conn.close()
print("\nDONE!")
