
import os

ctrl_dir = r"d:\erp\RuoYi-Vue\ruoyi-admin\src\main\java\com\ruoyi\erp\controller"

print("=" * 80)
print("直接修复已知的路径冲突")
print("=" * 80)

conflicts = [
    ("MainMaterialController.java", "/erp/material", "/erp/mainMaterial"),
    ("TErpSampleNoticeMaterialController.java", "/erp/material", "/erp/sampleNoticeMaterial"),
    ("SalesOrderPackController.java", "/erp/sales", "/erp/salesOrderPack"),
    ("SampleNoticeDetailController.java", "/erp/notice", "/erp/sampleNoticeDetail"),
    ("SampleNoticeFileController.java", "/erp/notice", "/erp/sampleNoticeFile"),
    ("SampleNoticeHisController.java", "/erp/notice", "/erp/sampleNoticeHis"),
    ("StockInItemController.java", "/erp/stock", "/erp/stockInItem"),
    ("StockLogController.java", "/erp/stock", "/erp/stockLog"),
    ("StockOutItemController.java", "/erp/stock", "/erp/stockOutItem"),
]

for fname, old_path, new_path in conflicts:
    fpath = os.path.join(ctrl_dir, fname)
    if os.path.exists(fpath):
        print(f"  修改 {fname}: {old_path} -&gt; {new_path}")
        with open(fpath, 'r', encoding='utf-8') as f:
            content = f.read()
        old_str = f'@RequestMapping("{old_path}")'
        new_str = f'@RequestMapping("{new_path}")'
        content = content.replace(old_str, new_str)
        with open(fpath, 'w', encoding='utf-8') as f:
            f.write(content)

print("\n" + "=" * 80)
print("路径冲突修复完成!")
print("=" * 80)
