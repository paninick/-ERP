
import os

erp_dir = r"d:\erp\RuoYi-Vue\ruoyi-admin\src\main\java\com\ruoyi\erp"

print("=" * 80)
print("清除 Java 文件中的零宽字符")
print("=" * 80)

count = 0
for root, dirs, files in os.walk(erp_dir):
    for fname in files:
        if not fname.endswith('.java'):
            continue
        fpath = os.path.join(root, fname)
        with open(fpath, 'r', encoding='utf-8') as f:
            content = f.read()
        original = content
        content = content.replace('\u200b', '')
        content = content.replace('\u200c', '')
        content = content.replace('\u200d', '')
        content = content.replace('\ufeff', '')
        if content != original:
            with open(fpath, 'w', encoding='utf-8') as f:
                f.write(content)
            rel_path = os.path.relpath(fpath, erp_dir)
            print(f"  修复: {rel_path}")
            count += 1

print(f"\n共修复 {count} 个文件")
print("=" * 80)

