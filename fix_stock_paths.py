
import os

ctrl_dir = r"d:\erp\RuoYi-Vue\ruoyi-admin\src\main\java\com\ruoyi\erp\controller"

print("=" * 80)
print("修复 StockIn/StockOut 路径冲突")
print("=" * 80)

# 修复 StockInController
fpath = os.path.join(ctrl_dir, "StockInController.java")
if os.path.exists(fpath):
    with open(fpath, 'r', encoding='utf-8') as f:
        content = f.read()
    content = content.replace('@RequestMapping("/erp/stock")', '@RequestMapping("/erp/stockIn")')
    with open(fpath, 'w', encoding='utf-8') as f:
        f.write(content)
    print("  [OK] StockInController -> /erp/stockIn")

# 修复 StockOutController
fpath = os.path.join(ctrl_dir, "StockOutController.java")
if os.path.exists(fpath):
    with open(fpath, 'r', encoding='utf-8') as f:
        content = f.read()
    content = content.replace('@RequestMapping("/erp/stock")', '@RequestMapping("/erp/stockOut")')
    with open(fpath, 'w', encoding='utf-8') as f:
        f.write(content)
    print("  [OK] StockOutController -> /erp/stockOut")

print("\n" + "=" * 80)
print("路径修复完成!")
print("=" * 80)
