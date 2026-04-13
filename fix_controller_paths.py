import os
import re

BASE = r"D:\erp\RuoYi-Vue\ruoyi-admin\src\main\java\com\ruoyi\erp\controller"

conflicts = {
    "/erp/customer": [
        ("CustomerController.java",              "/erp/customer"),
        ("CustomerTemplateController.java",      "/erp/customerTemplate"),
        ("CustomerTemplateMaterialController.java", "/erp/customerTemplate/material"),
        ("CustomerTemplateSizeController.java",  "/erp/customerTemplate/size"),
    ],
    "/erp/purchase": [
        ("PurchaseController.java",      "/erp/purchase"),
        ("PurchaseItemController.java",  "/erp/purchase/item"),
    ],
    "/erp/sales": [
        ("SalesOrderController.java",       "/erp/sales/order"),
        ("SalesOrderPackController.java",   "/erp/sales/pack"),
    ],
    "/erp/notice": [
        ("SampleNoticeController.java",     "/erp/notice"),
        ("SampleNoticeDetailController.java", "/erp/notice/detail"),
        ("SampleNoticeFileController.java",  "/erp/notice/file"),
        ("SampleNoticeHisController.java",   "/erp/notice/his"),
    ],
    "/erp/stock": [
        ("StockInController.java",      "/erp/stockIn"),
        ("StockInItemController.java",  "/erp/stockIn/item"),
        ("StockOutController.java",     "/erp/stockOut"),
        ("StockOutItemController.java", "/erp/stockOut/item"),
        ("StockLogController.java",     "/erp/stock/log"),
    ],
    "/erp/material": [
        ("MainMaterialController.java",                "/erp/material/main"),
        ("TErpSampleNoticeMaterialController.java",   "/erp/sampleNotice/material"),
    ],
}

fixed_count = 0
for old_path, files in conflicts.items():
    for filename, new_path in files:
        filepath = os.path.join(BASE, filename)
        if not os.path.exists(filepath):
            print(f"[MISSING] {filename}")
            continue

        with open(filepath, 'r', encoding='utf-8') as f:
            content = f.read()

        old_pattern = f'@RequestMapping("{old_path}")'
        new_pattern = f'@RequestMapping("{new_path}")'

        if old_pattern in content:
            content = content.replace(old_pattern, new_pattern)
            with open(filepath, 'w', encoding='utf-8') as f:
                f.write(content)
            print(f"  [FIXED] {filename}: {old_path} -> {new_path}")
            fixed_count += 1
        else:
            print(f"  [SKIP] {filename}: pattern not found")

print(f"\nTotal fixed: {fixed_count} controllers")
print("DONE!")
