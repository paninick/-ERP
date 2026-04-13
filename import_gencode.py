import os
import shutil

SRC = r"C:\Users\91306\Downloads\genCode5_extracted"
DST_ROOT = r"D:\erp\RuoYi-Vue"

mappings = [
    (r"main\java\com\ruoyi\erp",     r"ruoyi-admin\src\main\java\com\ruoyi\erp"),
    (r"main\java\com\ruoyi\wx",      r"ruoyi-admin\src\main\java\com\ruoyi\wx"),
    (r"main\resources\mapper\erp",   r"ruoyi-admin\src\main\resources\mapper\erp"),
    (r"main\resources\mapper\wx",    r"ruoyi-admin\src\main\resources\mapper\wx"),
    (r"main\resources\mapper\common",r"ruoyi-admin\src\main\resources\mapper\common"),
    (r"vue\api\erp",                 r"ruoyi-ui\src\api\erp"),
    (r"vue\api\wx",                  r"ruoyi-ui\src\api\wx"),
    (r"vue\api\common",              r"ruoyi-ui\src\api\common"),
    (r"vue\views\erp",               r"ruoyi-ui\src\views\erp"),
    (r"vue\views\wx",                r"ruoyi-ui\src\views\wx"),
    (r"vue\views\common",            r"ruoyi-ui\src\views\common"),
]

total_files = 0
overwritten = 0

for src_rel, dst_rel in mappings:
    src_path = os.path.join(SRC, src_rel)
    dst_path = os.path.join(DST_ROOT, dst_rel)

    if not os.path.exists(src_path):
        print(f"[SKIP] {src_rel} - source not found")
        continue

    for root, dirs, files in os.walk(src_path):
        rel_root = os.path.relpath(root, src_path)
        dst_root_dir = os.path.join(dst_path, rel_root)

        if not os.path.exists(dst_root_dir):
            os.makedirs(dst_root_dir)

        for f in files:
            src_file = os.path.join(root, f)
            dst_file = os.path.join(dst_root_dir, f)

            if os.path.exists(dst_file):
                overwritten += 1
            total_files += 1
            shutil.copy2(src_file, dst_file)

    print(f"[OK] {src_rel}")

print(f"\n=== Import Summary ===")
print(f"  Total files: {total_files}")
print(f"  Overwritten: {overwritten}")
print("DONE!")
