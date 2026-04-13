import pandas as pd
import numpy as np

# 读取Excel文件
df = pd.read_excel(r'C:\Users\91306\Downloads\工艺指示书_全量导出_2026-04-11 (3).xlsx')

print(f"总行数: {len(df)}")
print(f"列名: {list(df.columns)}")

# 拆分打样类型中的空格
# 只要打样类型包含空格，就拆分，第一个词给打样类型，第二个词给样品款式
# 即使样品款式原来有值，也覆盖
def split_always(row):
    sample_type = row['打样类型']
    if isinstance(sample_type, str) and ' ' in sample_type:
        parts = sample_type.split(maxsplit=1)  # 只拆分第一个空格
        if len(parts) == 2:
            row['打样类型'] = parts[0]
            row['样品款式'] = parts[1]
    return row

df = df.apply(split_always, axis=1)

print("\n拆分后前10行:")
print(df[['审批状态', '进行状态', '客户', '打样类型', '样品款式', '样品类型', '款号', '大货款号', '业务员', '创建日期']].head(10).to_string())

# 统计拆分了多少行
original = pd.read_excel(r'C:\Users\91306\Downloads\工艺指示书_全量导出_2026-04-11 (3).xlsx')
split_count = sum([1 for x in original['打样类型'] if isinstance(x, str) and ' ' in x])
print(f"\n需要拆分的总行数: {split_count}")

# 表头映射关系
# Excel表头 -> 数据库字段
mapping = {
    '审批状态': 'audit_status',
    '进行状态': 'progress_status',
    '客户': 'customer_name',
    '打样类型': 'sample_type_display',
    '样品款式': 'style_type',
    '样品类型': 'sample_category_type',
    '款号': 'style_no',
    '大货款号': 'bulk_order_no',
    '业务员': 'sales_name',
    '创建日期': 'due_date',
}

# 需要插入的所有字段
all_fields = [
    'id', 'audit_status', 'progress_status', 'customer_name', 'sample_type_display', 
    'style_type', 'sample_category_type', 'style_no', 'bulk_order_no', 'sales_name',
    'due_date',
    'sample_type', 'sample_id', 'style_pic', 'tag_pic', 'tag_pic_remark', 
    'patten_marker', 'patten_checker', 'cutting_tip', 'lining_tip', 'thread_tip', 'needle_tip', 
    'sewing_tip', 'back_garment_tip', 'tag_hanging_tip', 'ironing_tip', 'fabric_tip', 
    'seam_sealing_tip', 'hand_stitching_tip', 'hand_stitching_inspection', 'washing_tip', 
    'audit_by', 'audit_time', 'create_by', 'create_time', 'update_by', 
    'update_time', 'remark'
]

# 生成INSERT语句
output_file = r'd:\erp\import_sampletech_data_split.sql'

with open(output_file, 'w', encoding='utf-8') as f:
    f.write("-- 导入正式库工艺指示书数据(完全拆分版)\n")
    f.write("-- 生成时间: 2026-04-12\n")
    f.write(f"-- 总记录数: {len(df)}\n")
    f.write("-- 修复: 只要打样类型包含空格就拆分，第一个词->打样类型，第二个词->样品款式\n\n")
    f.write("-- 先清空原有数据\n")
    f.write("TRUNCATE TABLE t_erp_sample_tech;\n\n")
    f.write("-- 插入数据\n")
    
    # 分批插入，每批100条
    batch_size = 100
    total_batches = (len(df) + batch_size - 1) // batch_size
    
    for batch_idx in range(total_batches):
        start_idx = batch_idx * batch_size
        end_idx = min((batch_idx + 1) * batch_size, len(df))
        batch_df = df.iloc[start_idx:end_idx]
        
        f.write(f"INSERT INTO t_erp_sample_tech ({', '.join(all_fields)}) VALUES\n")
        
        values_list = []
        for idx, (_, row) in enumerate(batch_df.iterrows()):
            # 自增ID从1开始
            row_id = start_idx + idx + 1
            
            # 构建值列表
            values = []
            values.append(str(row_id))  # id
            
            # audit_status (审批状态)
            if pd.notna(row['审批状态']):
                val = str(row['审批状态']).replace("'", "''")
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # progress_status (进行状态)
            if pd.notna(row['进行状态']):
                val = str(row['进行状态']).replace("'", "''")
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # customer_name (客户)
            if pd.notna(row['客户']):
                val = str(row['客户']).replace("'", "''")
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # sample_type_display (打样类型)
            if pd.notna(row['打样类型']):
                val = str(row['打样类型']).replace("'", "''")
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # style_type (样品款式)
            if pd.notna(row['样品款式']):
                val = str(row['样品款式']).replace("'", "''")
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # sample_category_type (样品类型)
            if pd.notna(row['样品类型']):
                val = str(row['样品类型']).replace("'", "''")
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # style_no (款号)
            if pd.notna(row['款号']):
                val = str(row['款号']).replace("'", "''")
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # bulk_order_no (大货款号)
            if pd.notna(row['大货款号']):
                val = str(row['大货款号']).replace("'", "''")
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # sales_name (业务员)
            if pd.notna(row['业务员']):
                val = str(row['业务员']).replace("'", "''")
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # due_date (要求交期 - 来自创建日期)
            if pd.notna(row['创建日期']):
                val = str(row['创建日期']).split(' ')[0]
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # sample_type - NULL
            values.append("NULL")
            
            # sample_id - NULL
            values.append("NULL")
            
            # style_pic - NULL
            values.append("NULL")
            
            # tag_pic - NULL
            values.append("NULL")
            
            # tag_pic_remark - NULL
            values.append("NULL")
            
            # patten_marker - NULL
            values.append("NULL")
            
            # patten_checker - NULL
            values.append("NULL")
            
            # cutting_tip - NULL
            values.append("NULL")
            
            # lining_tip - NULL
            values.append("NULL")
            
            # thread_tip - NULL
            values.append("NULL")
            
            # needle_tip - NULL
            values.append("NULL")
            
            # sewing_tip - NULL
            values.append("NULL")
            
            # back_garment_tip - NULL
            values.append("NULL")
            
            # tag_hanging_tip - NULL
            values.append("NULL")
            
            # ironing_tip - NULL
            values.append("NULL")
            
            # fabric_tip - NULL
            values.append("NULL")
            
            # seam_sealing_tip - NULL
            values.append("NULL")
            
            # hand_stitching_tip - NULL
            values.append("NULL")
            
            # hand_stitching_inspection - NULL
            values.append("NULL")
            
            # washing_tip - NULL
            values.append("NULL")
            
            # audit_by - NULL
            values.append("NULL")
            
            # audit_time - NULL
            values.append("NULL")
            
            # create_by - NULL
            values.append("NULL")
            
            # create_time - NOW()
            values.append("NOW()")
            
            # update_by - NULL
            values.append("NULL")
            
            # update_time - NOW()
            values.append("NOW()")
            
            # remark - NULL
            values.append("NULL")
            
            values_list.append(f"({', '.join(values)})")
        
        f.write(',\n'.join(values_list) + ";\n\n")
        print(f"已生成批次 {batch_idx + 1}/{total_batches} ({len(batch_df)} 条记录)")

print(f"\nSQL文件已生成: {output_file}")
print(f"总记录数: {len(df)}")
print(f"需要拆分的行数: {split_count}")
