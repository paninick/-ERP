
import os
import pymysql
from dotenv import load_dotenv

load_dotenv()

def get_db_config():
    return {
        'host': os.environ.get('DB_HOST', 'localhost'),
        'port': int(os.environ.get('DB_PORT', '3306')),
        'user': os.environ.get('DB_USER', 'root'),
        'password': os.environ.get('DB_PASSWORD', ''),
        'database': os.environ.get('DB_NAME', 'ry_vue'),
        'charset': os.environ.get('DB_CHARSET', 'utf8mb4')
    }

config = get_db_config()

def adjust_dicts():
    """调整字典"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        cursor = conn.cursor()
        
        print("="*100)
        print("调整字典数据")
        print("="*100)
        
        # 1. 调整样品种类字典
        print("\n【1】调整样品种类字典 (erp_sample_category)...")
        
        sample_category_data = [
            ('1', '初样', 1),
            ('2', '二次样', 2),
            ('3', '三次样', 3),
            ('6', '摄影样', 6),
            ('8', '大货量产', 8),
            ('9', '产前样', 9),
            ('10', '织片', 10),
            ('11', '修正样', 11),
            ('12', '排料', 12),
            ('14', '四次样', 14)
        ]
        
        for value, label, sort in sample_category_data:
            cursor.execute(
                "SELECT dict_code FROM sys_dict_data WHERE dict_type = 'erp_sample_category' AND dict_value = %s",
                (value,)
            )
            if not cursor.fetchone():
                sql = """
                    INSERT INTO sys_dict_data 
                    (dict_label, dict_value, dict_type, dict_sort, status, create_by, create_time)
                    VALUES (%s, %s, 'erp_sample_category', %s, '0', 'admin', NOW())
                """
                cursor.execute(sql, (label, value, sort))
                print(f"  已添加: 样品种类 {value} = {label}")
            else:
                sql = """
                    UPDATE sys_dict_data 
                    SET dict_label = %s, dict_sort = %s
                    WHERE dict_type = 'erp_sample_category' AND dict_value = %s
                """
                cursor.execute(sql, (label, sort, value))
                print(f"  已更新: 样品种类 {value} = {label}")
        
        # 2. 调整样品款式字典
        print("\n【2】调整样品款式字典 (erp_sample_style)...")
        
        sample_style_data = [
            ('1', '针织', 1),
            ('2', '梭织', 2),
            ('3', '毛衣', 3),
            ('4', '拼接', 4),
            ('11', '圆领', 11),
            ('12', 'V领', 12),
            ('13', 'Polo领', 13),
            ('21', '平纹', 21),
            ('22', '斜纹', 22),
            ('31', '套头', 31),
            ('32', '开衫', 32)
        ]
        
        for value, label, sort in sample_style_data:
            cursor.execute(
                "SELECT dict_code FROM sys_dict_data WHERE dict_type = 'erp_sample_style' AND dict_value = %s",
                (value,)
            )
            if not cursor.fetchone():
                sql = """
                    INSERT INTO sys_dict_data 
                    (dict_label, dict_value, dict_type, dict_sort, status, create_by, create_time)
                    VALUES (%s, %s, 'erp_sample_style', %s, '0', 'admin', NOW())
                """
                cursor.execute(sql, (label, value, sort))
                print(f"  已添加: 样品款式 {value} = {label}")
            else:
                sql = """
                    UPDATE sys_dict_data 
                    SET dict_label = %s, dict_sort = %s
                    WHERE dict_type = 'erp_sample_style' AND dict_value = %s
                """
                cursor.execute(sql, (label, sort, value))
                print(f"  已更新: 样品款式 {value} = {label}")
        
        conn.commit()
        
        # 3. 验证结果
        print("\n【3】验证结果...")
        print("\n样品种类:")
        cursor.execute("SELECT dict_value, dict_label FROM sys_dict_data WHERE dict_type = 'erp_sample_category' ORDER BY dict_sort")
        for row in cursor.fetchall():
            print(f"  {row[0]} = {row[1]}")
        
        print("\n样品款式:")
        cursor.execute("SELECT dict_value, dict_label FROM sys_dict_data WHERE dict_type = 'erp_sample_style' ORDER BY dict_sort")
        for row in cursor.fetchall():
            print(f"  {row[0]} = {row[1]}")
        
        print("\n" + "="*100)
        print("字典调整完成！")
        print("="*100)
        print("\n现在请硬刷新浏览器 (Ctrl + F5) 查看效果！")
        
    except Exception as e:
        print(f"调整失败: {e}")
        import traceback
        traceback.print_exc()
        if conn:
            conn.rollback()
    finally:
        if conn:
            conn.close()

if __name__ == "__main__":
    adjust_dicts()

