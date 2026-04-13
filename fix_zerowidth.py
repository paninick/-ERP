import os
import re

base = r"d:\erp\RuoYi-Vue\ruoyi-ui\src"

def fix_zerowidth():
    fixed = 0
    for root, dirs, files in os.walk(base):
        for f in files:
            if not (f.endswith('.vue') or f.endswith('.js')):
                continue
            fpath = os.path.join(root, f)
            try:
                with open(fpath, 'r', encoding='utf-8') as fh:
                    content = fh.read()
            except:
                continue

            original = content
            # Remove zero-width characters: space (U+200B), non-joiner (U+200C), joiner (U+200D)
            # Also remove other invisible Unicode chars that cause JS syntax errors
            content = re.sub(r'[\u200b\u200c\u200d\ufeff\u00ad]', '', content)

            if content != original:
                with open(fpath, 'w', encoding='utf-8') as fh:
                    fh.write(content)
                fixed += 1
                print(f"Fixed: {fpath}")

    print(f"\nTotal fixed: {fixed} files")

if __name__ == "__main__":
    fix_zerowidth()
