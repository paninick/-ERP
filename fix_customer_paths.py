
import os

ctrl_dir = r"d:\erp\RuoYi-Vue\ruoyi-admin\src\main\java\com\ruoyi\erp\controller"

print("=" * 80)
print("批量修复 Controller 路径冲突")
print("=" * 80)

# 修复 CustomerTemplateSizeController
fpath = os.path.join(ctrl_dir, "CustomerTemplateSizeController.java")
if os.path.exists(fpath):
    with open(fpath, 'r', encoding='utf-8') as f:
        content = f.read()
    content = content.replace('@RequestMapping("/erp/customer")', '@RequestMapping("/erp/customerTemplateSize")')
    with open(fpath, 'w', encoding='utf-8') as f:
        f.write(content)
    print("  [OK] CustomerTemplateSizeController -> /erp/customerTemplateSize")

# 修复 CustomerTemplateMaterialController
fpath = os.path.join(ctrl_dir, "CustomerTemplateMaterialController.java")
if os.path.exists(fpath):
    with open(fpath, 'r', encoding='utf-8') as f:
        content = f.read()
    content = content.replace('@RequestMapping("/erp/customer")', '@RequestMapping("/erp/customerTemplateMaterial")')
    with open(fpath, 'w', encoding='utf-8') as f:
        f.write(content)
    print("  [OK] CustomerTemplateMaterialController -> /erp/customerTemplateMaterial")

print("\n" + "=" * 80)
print("路径修复完成!")
print("=" * 80)
