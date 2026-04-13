
import os

ctrl_dir = r"d:\erp\RuoYi-Vue\ruoyi-admin\src\main\java\com\ruoyi\erp\controller"

print("=" * 80)
print("批量修复所有 Controller 路径冲突")
print("=" * 80)

# 修复 PurchaseItemController
fpath = os.path.join(ctrl_dir, "PurchaseItemController.java")
if os.path.exists(fpath):
    with open(fpath, 'r', encoding='utf-8') as f:
        content = f.read()
    content = content.replace('@RequestMapping("/erp/purchase")', '@RequestMapping("/erp/purchaseItem")')
    with open(fpath, 'w', encoding='utf-8') as f:
        f.write(content)
    print("  [OK] PurchaseItemController -> /erp/purchaseItem")

# 修复 StockInItemController
fpath = os.path.join(ctrl_dir, "StockInItemController.java")
if os.path.exists(fpath):
    with open(fpath, 'r', encoding='utf-8') as f:
        content = f.read()
    content = content.replace('@RequestMapping("/erp/stockIn")', '@RequestMapping("/erp/stockInItem")')
    with open(fpath, 'w', encoding='utf-8') as f:
        f.write(content)
    print("  [OK] StockInItemController -> /erp/stockInItem")

# 修复 StockOutItemController
fpath = os.path.join(ctrl_dir, "StockOutItemController.java")
if os.path.exists(fpath):
    with open(fpath, 'r', encoding='utf-8') as f:
        content = f.read()
    content = content.replace('@RequestMapping("/erp/stockOut")', '@RequestMapping("/erp/stockOutItem")')
    with open(fpath, 'w', encoding='utf-8') as f:
        f.write(content)
    print("  [OK] StockOutItemController -> /erp/stockOutItem")

# 修复 SalesOrderItemController
fpath = os.path.join(ctrl_dir, "SalesOrderItemController.java")
if os.path.exists(fpath):
    with open(fpath, 'r', encoding='utf-8') as f:
        content = f.read()
    content = content.replace('@RequestMapping("/erp/salesOrder")', '@RequestMapping("/erp/salesOrderItem")')
    with open(fpath, 'w', encoding='utf-8') as f:
        f.write(content)
    print("  [OK] SalesOrderItemController -> /erp/salesOrderItem")

# 修复 SampleNoticeDetailController
fpath = os.path.join(ctrl_dir, "SampleNoticeDetailController.java")
if os.path.exists(fpath):
    with open(fpath, 'r', encoding='utf-8') as f:
        content = f.read()
    content = content.replace('@RequestMapping("/erp/sampleNotice")', '@RequestMapping("/erp/sampleNoticeDetail")')
    with open(fpath, 'w', encoding='utf-8') as f:
        f.write(content)
    print("  [OK] SampleNoticeDetailController -> /erp/sampleNoticeDetail")

# 修复 SampleTechMaterialController
fpath = os.path.join(ctrl_dir, "SampleTechMaterialController.java")
if os.path.exists(fpath):
    with open(fpath, 'r', encoding='utf-8') as f:
        content = f.read()
    content = content.replace('@RequestMapping("/erp/sampleTech")', '@RequestMapping("/erp/sampleTechMaterial")')
    with open(fpath, 'w', encoding='utf-8') as f:
        f.write(content)
    print("  [OK] SampleTechMaterialController -> /erp/sampleTechMaterial")

# 修复 SalesOrderMaterialController
fpath = os.path.join(ctrl_dir, "SalesOrderMaterialController.java")
if os.path.exists(fpath):
    with open(fpath, 'r', encoding='utf-8') as f:
        content = f.read()
    content = content.replace('@RequestMapping("/erp/salesOrder")', '@RequestMapping("/erp/salesOrderMaterial")')
    with open(fpath, 'w', encoding='utf-8') as f:
        f.write(content)
    print("  [OK] SalesOrderMaterialController -> /erp/salesOrderMaterial")

# 修复 ProducePlanMaterialController
fpath = os.path.join(ctrl_dir, "ProducePlanMaterialController.java")
if os.path.exists(fpath):
    with open(fpath, 'r', encoding='utf-8') as f:
        content = f.read()
    content = content.replace('@RequestMapping("/erp/producePlan")', '@RequestMapping("/erp/producePlanMaterial")')
    with open(fpath, 'w', encoding='utf-8') as f:
        f.write(content)
    print("  [OK] ProducePlanMaterialController -> /erp/producePlanMaterial")

# 修复 TErpSampleNoticeMaterialController
fpath = os.path.join(ctrl_dir, "TErpSampleNoticeMaterialController.java")
if os.path.exists(fpath):
    with open(fpath, 'r', encoding='utf-8') as f:
        content = f.read()
    content = content.replace('@RequestMapping("/erp/sampleNotice")', '@RequestMapping("/erp/sampleNoticeMaterial")')
    with open(fpath, 'w', encoding='utf-8') as f:
        f.write(content)
    print("  [OK] TErpSampleNoticeMaterialController -> /erp/sampleNoticeMaterial")

print("\n" + "=" * 80)
print("路径修复完成!")
print("=" * 80)
