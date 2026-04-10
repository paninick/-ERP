
import pandas as pd
import pymysql

config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

def verify_data():
    """验证数据更新结果"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        
        print("="*100)
        print("打样通知数据同步验证")
        print("="*100)
        
        # 1. 统计总数
        print("\n【1】数据统计:")
        print("-"*100)
        sql = "SELECT COUNT(*) as total FROM t_erp_sample_notice"
        df = pd.read_sql(sql, conn)
        print(f"总记录数: {df['total'].iloc[0]}")
        
        # 2. 检查各字段更新情况
        print("\n【2】字段更新情况:")
        print("-"*100)
        
        fields = [
            ('sample_type', '打样类型'),
            ('customer_id', '客户ID'),
            ('style_category', '款式大类'),
            ('sample_category_type', '样品类型'),
            ('audit_status', '审批状态'),
            ('audit_by', '审批人'),
            ('bulk_order_no', '大货款号')
        ]
        
        for field, desc in fields:
            sql = f"SELECT COUNT(*) as cnt FROM t_erp_sample_notice WHERE {field} IS NOT NULL AND {field} != ''"
            df = pd.read_sql(sql, conn)
            print(f"{desc}: {df['cnt'].iloc[0]} 条有数据")
        
        # 3. 显示样本数据
        print("\n【3】样本数据（前10条）:")
        print("-"*100)
        sql = """
            SELECT id, sample_no, sample_type, customer_id, style_category, 
                   sample_category_type, audit_status, audit_by, bulk_order_no
            FROM t_erp_sample_notice 
            ORDER BY id DESC 
            LIMIT 10
        """
        df = pd.read_sql(sql, conn)
        print(df.to_string(index=False))
        
        # 4. 检查图片关联表
        print("\n【4】图片关联检查:")
        print("-"*100)
        
        # 检查是否有图片相关表
        sql = "SHOW TABLES LIKE '%notice%file%' OR Tables_in_ry_vue LIKE '%notice%picture%'"
        cursor = conn.cursor()
        cursor.execute("SHOW TABLES")
        tables = [t[0] for t in cursor.fetchall()]
        
        pic_tables = [t for t in tables if 'notice' in t.lower() and ('file' in t.lower() or 'picture' in t.lower() or 'pic' in t.lower())]
        
        if pic_tables:
            print(f"找到图片关联表: {pic_tables}")
            for pic_table in pic_tables:
                sql = f"SELECT COUNT(*) as cnt FROM {pic_table}"
                df = pd.read_sql(sql, conn)
                print(f"  {pic_table}: {df['cnt'].iloc[0]} 条记录")
        else:
            print("未找到专门的图片关联表")
            # 检查主表的picture_url字段
            sql = "SELECT COUNT(*) as cnt FROM t_erp_sample_notice WHERE picture_url IS NOT NULL AND picture_url != ''"
            df = pd.read_sql(sql, conn)
            print(f"picture_url 字段有数据: {df['cnt'].iloc[0]} 条")
        
        print("\n" + "="*100)
        print("验证完成！")
        print("="*100)
        
    except Exception as e:
        print(f"验证失败: {e}")
    finally:
        if conn:
            conn.close()

if __name__ == "__main__":
    verify_data()

