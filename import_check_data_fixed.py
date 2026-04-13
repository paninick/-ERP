import csv
import pymysql
from datetime import datetime
from db_config import get_connection

# 定义字典映射 - 这些应该与数据库中的sys_dict_data对应
audit_status_map = {
    '审批通过': '3',  # 假设审批通过对应字典值3
    '': None
}

sample_type_map = {
    '拼接': '1',
    '服饰': '2',
    '毛衫': '3'
}

style_type_map = {
    '拼接': '1',
    '针织': '2',
    '梭织': '3',
    '毛衣': '4'
}

sample_category_map = {
    '排料': '1',
    '摄影样': '2',
    '初样': '3',
    '产前样': '4',
    '大货量产': '5',
    '二次样': '6',
    '修正样': '7',
    '织片': '8',
    '三次样': '9',
    '检测样': '10',
    '四次样': '11'
}

def clean_value(value):
    if value is None:
        return None
    value = str(value).strip()
    if value == '' or value == 'nan':
        return None
    return value

def get_dict_value(value, value_map, field_name):
    """获取字典值，如果映射不存在则直接返回原始值"""
    if value is None:
        return None
    if value in value_map:
        return value_map[value]
    print(f"警告: {field_name} '{value}' 未在字典映射中找到，使用原始值")
    return value

def import_csv_to_db(csv_file):
    # 连接数据库
    conn = get_connection()
    cursor = conn.cursor()
        print("正在清空现有数据...")
        cursor.execute("DELETE FROM t_erp_check")
        conn.commit()
        print(f"已清空 {cursor.rowcount} 条数据")
        
        # 读取CSV文件
        with open(csv_file, 'r', encoding='utf-8') as f:
            reader = csv.reader(f)
            rows = list(reader)
        
        # 跳过表头
        data_rows = rows[1:]
        
        success_count = 0
        fail_count = 0
        
        print(f"开始导入 {len(data_rows)} 条数据...")
        
        for idx, row in enumerate(data_rows, 1):
            if len(row) < 13:
                fail_count += 1
                continue
                
            # CSV列: (空), 审批状态, 进行状态, 客户, 打样类型, 样品款式, 样品类型, 款号, 大货款号, 业务员, 要求交期, 工艺书编号, 打样编号
            audit_status_text = clean_value(row[1])
            progress_status = clean_value(row[2])
            customer_name = clean_value(row[3])
            sample_type_text = clean_value(row[4])
            style_type_text = clean_value(row[5])
            sample_category_text = clean_value(row[6])
            style_no = clean_value(row[7])
            bulk_order_no = clean_value(row[8])
            sales_name = clean_value(row[9])
            due_date = clean_value(row[10])
            tech_no = clean_value(row[11])
            sample_no = clean_value(row[12])
            
            # 映射字典值
            audit_status = get_dict_value(audit_status_text, audit_status_map, '审批状态')
            sample_type = get_dict_value(sample_type_text, sample_type_map, '打样类型')
            style_type = get_dict_value(style_type_text, style_type_map, '样品款式')
            sample_category = get_dict_value(sample_category_text, sample_category_map, '样品类型')
            
            # 生成check_no
            check_no = f"CHECK{idx:06d}"
            
            # 构建SQL
            sql = """INSERT INTO t_erp_check (check_no, audit_status, progress_status, customer_name, sample_type, style_type, sample_category_type, style_no, bulk_order_no, sales_name, due_date, tech_no, sample_no, create_by, create_time) 
VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, 'admin', NOW())"""
            
            try:
                cursor.execute(sql, (
                    check_no,
                    audit_status,
                    progress_status,
                    customer_name,
                    sample_type,
                    style_type,
                    sample_category,
                    style_no,
                    bulk_order_no,
                    sales_name,
                    due_date,
                    tech_no,
                    sample_no
                ))
                success_count += 1
                
                if success_count % 100 == 0:
                    conn.commit()
                    print(f"已导入 {success_count} 条数据...")
                    
            except Exception as e:
                fail_count += 1
                print(f"第 {idx} 条数据导入失败: {e}")
        
        # 提交剩余数据
        conn.commit()
        
        print(f"\n导入完成！")
        print(f"成功: {success_count} 条")
        print(f"失败: {fail_count} 条")
        
    except Exception as e:
        conn.rollback()
        print(f"导入出错: {e}")
        raise
    finally:
        cursor.close()
        conn.close()

if __name__ == "__main__":
    csv_file = r"C:\Users\91306\Downloads\合并数据_2026-04-11.csv"
    import_csv_to_db(csv_file)
