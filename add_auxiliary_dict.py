import pymysql

# 数据库连接配置
db_config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '123456',
    'database': 'ry-vue',
    'charset': 'utf8mb4'
}

print("=" * 60)
print("添加辅料类型字典")
print("=" * 60)

try:
    conn = pymysql.connect(**db_config)
    cursor = conn.cursor()
    
    # 检查字典类型是否存在
    cursor.execute("SELECT dict_id FROM sys_dict_type WHERE dict_type = 'erp_auxiliary_material_type'")
    dict_type = cursor.fetchone()
    
    if not dict_type:
        print("\n[1/2] 添加字典类型...")
        cursor.execute("""
        INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
        VALUES ('辅料类型', 'erp_auxiliary_material_type', '0', 'admin', NOW(), '辅料类型字典')
        """)
        print("    ✅ 字典类型添加成功")
    else:
        print("\n[1/2] 字典类型已存在，跳过")
    
    # 添加字典数据
    print("\n[2/2] 添加字典数据...")
    
    dict_data = [
        (1, '纽扣', '1', 'erp_auxiliary_material_type', '0', 'admin', '纽扣类辅料'),
        (2, '拉链', '2', 'erp_auxiliary_material_type', '0', 'admin', '拉链类辅料'),
        (3, '织带', '3', 'erp_auxiliary_material_type', '0', 'admin', '织带类辅料'),
        (4, '衬布', '4', 'erp_auxiliary_material_type', '0', 'admin', '衬布类辅料'),
        (5, '线', '5', 'erp_auxiliary_material_type', '0', 'admin', '线类辅料'),
        (6, '商标', '6', 'erp_auxiliary_material_type', '0', 'admin', '商标类辅料'),
        (7, '包装', '7', 'erp_auxiliary_material_type', '0', 'admin', '包装类辅料'),
        (8, '其他', '8', 'erp_auxiliary_material_type', '0', 'admin', '其他辅料'),
        (9, '衬料', '9', 'erp_auxiliary_material_type', '0', 'admin', '衬料类辅料'),
    ]
    
    added_count = 0
    for item in dict_data:
        cursor.execute("""
        INSERT IGNORE INTO sys_dict_data 
        (dict_sort, dict_label, dict_value, dict_type, status, create_by, remark)
        VALUES (%s, %s, %s, %s, %s, %s, %s)
        """, item)
        if cursor.rowcount > 0:
            added_count += 1
    
    print(f"    ✅ 添加了 {added_count} 条字典数据")
    
    # 验证
    cursor.execute("SELECT dict_label, dict_value FROM sys_dict_data WHERE dict_type = 'erp_auxiliary_material_type' ORDER BY dict_sort")
    results = cursor.fetchall()
    print("\n当前字典配置:")
    for row in results:
        print(f"    {row[1]} - {row[0]}")
    
    conn.commit()
    cursor.close()
    conn.close()
    
    print("\n" + "=" * 60)
    print("字典配置完成！")
    print("=" * 60)
    
except Exception as e:
    print(f"\n❌ 失败: {e}")
    import traceback
    traceback.print_exc()
