import os

API_BASE = r"D:\erp\RuoYi-Vue\ruoyi-ui\src\api\erp"

fixes = {
    "material.js":        ("/erp/material",       "/erp/material/main"),
    "sales.js":           ("/erp/sales",          "/erp/sales/order"),
    "salesitem.js":       ("/erp/salesitem",      "/erp/sales/order/item"),
    "salesmaterial.js":   ("/erp/salesmaterial",  "/erp/sales/order/material"),
    "stock.js":           ("/erp/stock",          "/erp/stockIn"),
    "stockin.js":         ("/erp/stockin",        "/erp/stockIn/item"),
    "stockout.js":        ("/erp/stockout",       "/erp/stockOut"),
}

for filename, (old_url, new_url) in fixes.items():
    filepath = os.path.join(API_BASE, filename)
    if not os.path.exists(filepath):
        print(f"[SKIP] {filename} not found")
        continue

    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()

    if old_url in content:
        content = content.replace(old_url, new_url)
        with open(filepath, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f"[FIXED] {filename}: {old_url} -> {new_url}")
    else:
        print(f"[NO CHANGE] {filename}")

print("\nDONE!")
