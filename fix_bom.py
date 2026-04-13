import os
import codecs

BASE = r"D:\erp\RuoYi-Vue\ruoyi-admin\src\main\java"

fixed = 0
for root, dirs, files in os.walk(BASE):
    for f in files:
        if not f.endswith(".java"):
            continue

        filepath = os.path.join(root, f)

        try:
            with open(filepath, 'rb') as fh:
                content_bytes = fh.read()

            # 检查 UTF-8 BOM (\xef\xbb\xbf)
            if content_bytes.startswith(codecs.BOM_UTF8):
                content_bytes = content_bytes[len(codecs.BOM_UTF8):]
                with open(filepath, 'wb') as fh:
                    fh.write(content_bytes)
                rel_path = os.path.relpath(filepath, r"D:\erp\RuoYi-Vue")
                print(f"[FIXED BOM] {rel_path}")
                fixed += 1
        except Exception as e:
            print(f"[ERROR] {filepath}: {e}")

print(f"\nFixed {fixed} files with BOM")
print("DONE!")
