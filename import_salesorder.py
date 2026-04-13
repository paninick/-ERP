import pandas as pd
import numpy as np

# 读取Excel文件
df = pd.read_excel(r'C:\Users\91306\Downloads\销售订单_全量导出_2026-04-11 (1).xlsx')

print(f"总行数: {len(df)}")
print(f"列名: {list(df.columns)}")

# Excel表头 -> 数据库字段映射
mapping = {
    '销售单号': 'sales_no',
    '销售类型': 'sales_type',
    '客户名称': 'customer_name',
    '销售日期': 'sales_date',
    '交货日期': 'due_date',
    '数量': 'quantity',
    '订单金额': 'amount',
    '订单状态': 'order_status',
    '审批状态': 'audit_status',
    '审批时间': 'audit_time',
    '创建时间': 'create_time',
}

# 需要插入的所有字段
all_fields = [
    'id', 
    'sales_type', 
    'customer_name', 
    'bulk_order_no', 
    'sample_style_no', 
    'style_category', 
    'sample_no', 
    'sales_name',
    'notice_id', 
    'tech_id', 
    'sales_no', 
    'sales_date', 
    'sales_id', 
    'customer_id', 
    'due_date', 
    'quantity', 
    'amount', 
    'pay_amount', 
    'order_status', 
    'audit_status', 
    'audit_by', 
    'audit_time', 
    'create_by', 
    'create_time', 
    'update_by', 
    'update_time', 
    'remark'
]

# 生成INSERT语句
output_file = r'd:\erp\import_salesorder_data.sql'

with open(output_file, 'w', encoding='utf-8') as f:
    f.write("-- 导入正式库销售订单数据到测试库\n")
    f.write("-- 生成时间: 2026-04-12\n")
    f.write(f"-- 总记录数: {len(df)}\n\n")
    f.write("-- 先清空原有数据\n")
    f.write("TRUNCATE TABLE t_erp_sales_order;\n\n")
    f.write("-- 插入数据\n")
    
    # 分批插入，每批100条
    batch_size = 100
    total_batches = (len(df) + batch_size - 1) // batch_size
    
    for batch_idx in range(total_batches):
        start_idx = batch_idx * batch_size
        end_idx = min((batch_idx + 1) * batch_size, len(df))
        batch_df = df.iloc[start_idx:end_idx]
        
        f.write(f"INSERT INTO t_erp_sales_order ({', '.join(all_fields)}) VALUES\n")
        
        values_list = []
        for idx, (_, row) in enumerate(batch_df.iterrows()):
            # 自增ID从1开始
            row_id = start_idx + idx + 1
            
            # 构建值列表
            values = []
            values.append(str(row_id))  # id
            
            # sales_type (销售类型)
            if pd.notna(row['销售类型']):
                val = str(row['销售类型']).replace("'", "''")
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # customer_name (客户名称)
            if pd.notna(row['客户名称']):
                val = str(row['客户名称']).replace("'", "''")
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # bulk_order_no (大货款号) - Excel没有这列，NULL
            values.append("NULL")
            
            # sample_style_no (打样款号) - Excel没有这列，NULL
            values.append("NULL")
            
            # style_category (款式品类) - Excel没有这列，NULL
            values.append("NULL")
            
            # sample_no (打样编号) - Excel没有这列，NULL
            values.append("NULL")
            
            # sales_name (业务员) - Excel没有这列，NULL
            values.append("NULL")
            
            # notice_id - NULL
            values.append("NULL")
            
            # tech_id - NULL
            values.append("NULL")
            
            # sales_no (销售单号)
            if pd.notna(row['销售单号']):
                val = str(row['销售单号']).replace("'", "''")
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # sales_date (销售日期)
            if pd.notna(row['销售日期']):
                val = str(row['销售日期']).split(' ')[0]
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # sales_id - NULL
            values.append("NULL")
            
            # customer_id - NULL
            values.append("NULL")
            
            # due_date (交货日期)
            if pd.notna(row['交货日期']):
                val = str(row['交货日期']).split(' ')[0]
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # quantity (数量)
            if pd.notna(row['数量']):
                values.append(str(row['数量']))
            else:
                values.append("NULL")
            
            # amount (订单金额)
            if pd.notna(row['订单金额']):
                values.append(str(row['订单金额']))
            else:
                values.append("NULL")
            
            # pay_amount - NULL
            values.append("NULL")
            
            # order_status (订单状态)
            if pd.notna(row['订单状态']):
                val = str(row['订单状态']).replace("'", "''")
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # audit_status (审批状态)
            if pd.notna(row['审批状态']):
                val = str(row['审批状态']).replace("'", "''")
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # audit_by - NULL
            values.append("NULL")
            
            # audit_time (审批时间)
            if pd.notna(row['审批时间']):
                if pd.notna(row['审批时间']):
                    val = str(row['审批时间']).split(' ')[0]
                    values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # create_by - NULL
            values.append("NULL")
            
            # create_time (创建时间) - NOW()
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
print("映射关系:")
for excel_col, db_col in mapping.items():
    print(f"  {excel_col} -> {db_col}")
