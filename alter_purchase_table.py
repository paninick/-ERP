import pymysql

conn = pymysql.connect(
    host='localhost',
    port=3306,
    user='root',
    password='',
    database='ry_vue',
    charset='utf8mb4'
)

cursor = conn.cursor()

# 添加新字段
alter_sqls = [
    "ALTER TABLE t_erp_purchase ADD COLUMN type VARCHAR(50) COMMENT '类型' AFTER sn",
    "ALTER TABLE t_erp_purchase ADD COLUMN bulk_order_no VARCHAR(255) COMMENT '大货款号' AFTER type",
    "ALTER TABLE t_erp_purchase ADD COLUMN description TEXT COMMENT '说明' AFTER bulk_order_no",
    "ALTER TABLE t_erp_purchase ADD COLUMN purchase_name VARCHAR(100) COMMENT '采购员(文本)' AFTER expected_delivery_date",
    "ALTER TABLE t_erp_purchase ADD COLUMN confirm_time DATE COMMENT '确认时间' AFTER purchase_user_id",
    "ALTER TABLE t_erp_purchase ADD COLUMN status VARCHAR(50) COMMENT '状态' AFTER confirm_time"
]

for sql in alter_sqls:
    try:
        cursor.execute(sql)
        print(f"✅ 执行成功: {sql}")
    except Exception as e:
        print(f"⚠️  字段可能已存在: {sql}")
        print(f"   错误: {e}")

conn.commit()

print("\n✅ 数据库表结构更新完成！")

cursor.close()
conn.close()
