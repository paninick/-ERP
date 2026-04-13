import os
import re

BASE = r"D:\erp\RuoYi-Vue\ruoyi-admin\src\main\java\com\ruoyi\erp"

fixed = 0
for root, dirs, files in os.walk(BASE):
    for f in files:
        if not f.endswith(".java"):
            continue

        filepath = os.path.join(root, f)
        with open(filepath, 'r', encoding='utf-8') as fh:
            content = fh.read()

        if "getUserIdStr()" in content:
            content = content.replace("SecurityUtils.getUserIdStr()", "SecurityUtils.getUserId().toString()")
            content = content.replace("getUserIdStr()", "getUserId().toString()")
            with open(filepath, 'w', encoding='utf-8') as fh:
                fh.write(content)
            fixed += 1
            rel_path = os.path.relpath(filepath, r"D:\erp\RuoYi-Vue")
            print(f"[FIXED] {rel_path}")

print(f"\nFixed {fixed} files - getUserIdStr() -> getUserId().toString()")
print("DONE!")
