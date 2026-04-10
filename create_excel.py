import pandas as pd

# 创建测试数据
data = {
    'BOM编号': ['BOM001', 'BOM002', 'BOM003'],
    '款号': ['STYLE001', 'STYLE002', 'STYLE003'],
    '版本': ['V1', 'V1', 'V1'],
    '主料数量': [5, 8, 10],
    '辅料数量': [3, 5, 7],
    '描述': ['测试BOM1', '测试BOM2', '测试BOM3'],
    '状态': ['0', '0', '0'],
    '备注': ['测试备注1', '测试备注2', '测试备注3']
}

# 创建DataFrame
df = pd.DataFrame(data)

# 保存为Excel文件
df.to_excel('test_bom_excel.xlsx', index=False)

print('Excel文件创建成功！')