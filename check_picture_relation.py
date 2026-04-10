
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

def check_picture_data():
    """检查图片关联数据"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        
        print("="*100)
        print("图片关联数据检查")
        print("="*100)
        
        # 1. 检查图片关联表
        print("\n【1】图片关联表检查:")
        print("-"*100)
        
        # 检查所有相关表
        cursor = conn.cursor()
        cursor.execute("SHOW TABLES")
        tables = [t[0] for t in cursor.fetchall()]
        
        pic_tables = [t for t in tables if 'notice' in t.lower() and ('file' in t.lower() or 'picture' in t.lower() or 'pic' in t.lower())]
        
        if pic_tables:
            print(f"找到图片关联表: {pic_tables}")
            for pic_table in pic_tables:
                print(f"\n--- {pic_table} 表结构 ---")
                df = pd.read_sql(f"DESCRIBE {pic_table}", conn)
                print(df.to_string(index=False))
                
                print(f"\n--- {pic_table} 数据样本 ---")
                df = pd.read_sql(f"SELECT * FROM {pic_table} LIMIT 20", conn)
                if not df.empty:
                    print(df.to_string(index=False))
                else:
                    print("(空表)")
        else:
            print("未找到专门的图片关联表")
        
        # 2. 检查打样通知表的图片字段
        print("\n【2】打样通知表图片字段检查:")
        print("-"*100)
        
        df = pd.read_sql("DESCRIBE t_erp_sample_notice", conn)
        pic_fields = df[df['Field'].str.contains('picture|pic|image|file', case=False)]
        print("图片相关字段:")
        print(pic_fields.to_string(index=False))
        
        if not pic_fields.empty:
            for _, row in pic_fields.iterrows():
                field = row['Field']
                df_count = pd.read_sql(f"SELECT COUNT(*) as cnt FROM t_erp_sample_notice WHERE {field} IS NOT NULL AND {field} != ''", conn)
                print(f"\n{field} 有数据: {df_count['cnt'].iloc[0]} 条")
                
                df_sample = pd.read_sql(f"SELECT id, sample_no, {field} FROM t_erp_sample_notice WHERE {field} IS NOT NULL AND {field} != '' LIMIT 10", conn)
                print("\n样本数据:")
                print(df_sample.to_string(index=False))
        
        # 3. 检查打样通知与图片的关联
        print("\n【3】打样通知数据统计:")
        print("-"*100)
        df_total = pd.read_sql("SELECT COUNT(*) as total FROM t_erp_sample_notice", conn)
        print(f"总打样通知数: {df_total['total'].iloc[0]}")
        
        print("\n" + "="*100)
        print("检查完成！")
        print("="*100)
        
    except Exception as e:
        print(f"检查失败: {e}")
        import traceback
        traceback.print_exc()
    finally:
        if conn:
            conn.close()

if __name__ == "__main__":
    check_picture_data()

