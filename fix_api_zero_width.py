
import os

api_dir = r"d:\erp\RuoYi-Vue\ruoyi-ui\src\api\erp"

print("=" * 80)
print("清除 API 文件中的零宽字符")
print("=" * 80)

count = 0
for fname in os.listdir(api_dir):
    if not fname.endswith('.js'):
        continue
    fpath = os.path.join(api_dir, fname)
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
        print(f"  修复: {fname}")
        count += 1

print(f"\n共修复 {count} 个文件")
print("=" * 80)

