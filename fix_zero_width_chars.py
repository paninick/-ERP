
import os

vue_dir = r"d:\erp\RuoYi-Vue\ruoyi-ui\src\views\erp"

print("=" * 80)
print("清除 Vue 文件中的零宽字符")
print("=" * 80)

count = 0
for root, dirs, files in os.walk(vue_dir):
    for fname in files:
        if not fname.endswith('.vue'):
            continue
        fpath = os.path.join(root, fname)
        with open(fpath, 'r', encoding='utf-8') as f:
            content = f.read()
        # 清除零宽字符
        original = content
        content = content.replace('\u200b', '')  # 零宽空格
        content = content.replace('\u200c', '')  # 零宽非连接符
        content = content.replace('\u200d', '')  # 零宽连接符
        content = content.replace('\ufeff', '')  # BOM
        if content != original:
            with open(fpath, 'w', encoding='utf-8') as f:
                f.write(content)
            print(f"  修复: {os.path.relpath(fpath, vue_dir)}")
            count += 1

print(f"\n共修复 {count} 个文件")
print("=" * 80)

