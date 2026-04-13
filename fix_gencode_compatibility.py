
import os
import re

base_admin = r"d:\erp\RuoYi-Vue\ruoyi-admin\src\main\java\com\ruoyi\erp"

print("=" * 80)
print("修复 genCode (3) 兼容性问题")
print("=" * 80)

# 1. 修复 Controller 中的 javax.servlet -&gt; jakarta.servlet
controller_dir = os.path.join(base_admin, "controller")
count = 0
for fname in os.listdir(controller_dir):
    if not fname.endswith("Controller.java"):
        continue
    fpath = os.path.join(controller_dir, fname)
    with open(fpath, 'r', encoding='utf-8') as f:
        content = f.read()
    original = content
    content = content.replace('javax.servlet.http.HttpServletResponse', 'jakarta.servlet.http.HttpServletResponse')
    if content != original:
        with open(fpath, 'w', encoding='utf-8') as f:
            f.write(content)
        count += 1
print(f"  修复 Controller javax.servlet: {count} 个文件")

# 2. 修复 ServiceImpl 中的 @RequiredArgsConstructor 和 SecurityUtils.getUserIdStr()
service_impl_dir = os.path.join(base_admin, "service", "impl")
count = 0
for fname in os.listdir(service_impl_dir):
    if not fname.endswith("ServiceImpl.java"):
        continue
    fpath = os.path.join(service_impl_dir, fname)
    with open(fpath, 'r', encoding='utf-8') as f:
        content = f.read()
    original = content
    
    # 移除 @RequiredArgsConstructor
    content = content.replace('import lombok.RequiredArgsConstructor;\n', '')
    content = content.replace('@RequiredArgsConstructor\n', '')
    
    # 替换 private final 为 @Autowired private
    # 先找到所有 private final Xxx xxx; 的行
    lines = content.split('\n')
    new_lines = []
    in_class = False
    for line in lines:
        if '@Service' in line:
            in_class = True
        if in_class and 'private final' in line and 'Mapper' in line:
            # 替换为 @Autowired + private
            line = line.replace('private final', '@Autowired\n    private')
        new_lines.append(line)
    content = '\n'.join(new_lines)
    
    # 修复 SecurityUtils.getUserIdStr()
    content = content.replace('SecurityUtils.getUserIdStr()', 'String.valueOf(SecurityUtils.getUserId())')
    
    if content != original:
        with open(fpath, 'w', encoding='utf-8') as f:
            f.write(content)
        count += 1
print(f"  修复 ServiceImpl: {count} 个文件")

print("\n" + "=" * 80)
print("兼容性修复完成!")
print("=" * 80)

