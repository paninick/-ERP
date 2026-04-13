import os
import re

BASE = r"D:\erp\RuoYi-Vue\ruoyi-admin\src\main\java\com\ruoyi\erp\service\impl"

fixed = 0
for f in os.listdir(BASE):
    if not f.endswith("ServiceImpl.java"):
        continue

    filepath = os.path.join(BASE, f)
    with open(filepath, 'r', encoding='utf-8') as fh:
        content = fh.read()

    if "lombok" not in content:
        continue

    # 1. Remove lombok import line
    content = re.sub(r'import lombok\.RequiredArgsConstructor;\s*\n?', '', content)

    # 2. Remove @RequiredArgsConstructor annotation line
    content = re.sub(r'@RequiredArgsConstructor\s*\n?', '', content)

    # 3. Change "private final XxxMapper xxxMapper;" to "@Autowired\n    private XxxMapper xxxMapper;"
    content = re.sub(r'private final (\w+Mapper) (\w+);', r'@Autowired\n    private \1 \2;', content)

    # Also fix controllers that might have similar patterns
    content = re.sub(r'import lombok\.RequiredArgsConstructor;\s*\n?', '', content)
    content = re.sub(r'@RequiredArgsConstructor\s*\n?', '', content)
    content = re.sub(r'private final (\w+Service|I\w+Service) (\w+);', r'@Autowired\n    private \1 \2;', content)

    with open(filepath, 'w', encoding='utf-8') as fh:
        fh.write(content)

    fixed += 1

print(f"Fixed {fixed} files - removed Lombok, added @Autowired")
print("DONE!")
