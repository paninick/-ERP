import os
import re

base = r"d:\erp\RuoYi-Vue\ruoyi-admin\src\main\java\com\ruoyi\erp\controller"

keep_simple = {
    "CustomerController.java": "/erp/customer",
    "SupplierController.java": "/erp/supplier",
    "MainMaterialController.java": "/erp/material",  # 保持 material，主菜单
    "AuxiliaryMaterialController.java": "/erp/auxiliary",
    "WarehouseController.java": "/erp/warehouse",
    "WarehouseAreaController.java": "/erp/warehousearea",
    "WarehouseLocationController.java": "/erp/warehouselocation",
    "SampleNoticeController.java": "/erp/notice",
    "SampleTechController.java": "/erp/tech",
    "SalesOrderController.java": "/erp/sales",
    "ProducePlanController.java": "/erp/plan",
    "PurchaseController.java": "/erp/purchase",
    "StockInController.java": "/erp/stockin",
    "StockOutController.java": "/erp/stockout",
    "StockLogController.java": "/erp/stock",
}

for fname, desired_path in keep_simple.items():
    fpath = os.path.join(base, fname)
    if not os.path.exists(fpath):
        continue
    with open(fpath, 'r', encoding='utf-8') as f:
        content = f.read()
    
    # Replace
    new_content = re.sub(r'@RequestMapping\("[^"]+"\)', f'@RequestMapping("{desired_path}")', content)
    if new_content != content:
        with open(fpath, 'w', encoding='utf-8') as f:
            f.write(new_content)
        print(f"Restored: {fname} → {desired_path}")

print("\nDone!")
