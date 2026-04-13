
import os
import re

base_admin = r"d:\erp\RuoYi-Vue\ruoyi-admin\src\main\java\com\ruoyi"

print("=" * 80)
print("修复 common 和 wx 模块兼容性问题")
print("=" * 80)

# 1. 修复 Controller 中的 javax.servlet -&gt; jakarta.servlet
controller_dirs = [
    os.path.join(base_admin, "common", "controller"),
    os.path.join(base_admin, "wx", "controller")
]
count = 0
for ctrl_dir in controller_dirs:
    if not os.path.exists(ctrl_dir):
        continue
    for fname in os.listdir(ctrl_dir):
        if not fname.endswith("Controller.java"):
            continue
        fpath = os.path.join(ctrl_dir, fname)
        with open(fpath, 'r', encoding='utf-8') as f:
            content = f.read()
        original = content
        content = content.replace('javax.servlet.http.HttpServletResponse', 'jakarta.servlet.http.HttpServletResponse')
        if content != original:
            with open(fpath, 'w', encoding='utf-8') as f:
                f.write(content)
            print(f"  修复 Controller: {fname}")
            count += 1
print(f"  修复 Controller: {count} 个文件")

# 2. 修复 ServiceImpl 中的 @RequiredArgsConstructor 和 SecurityUtils.getUserIdStr()
service_impl_dirs = [
    os.path.join(base_admin, "common", "service", "impl"),
    os.path.join(base_admin, "wx", "service", "impl")
]
count = 0
for service_dir in service_impl_dirs:
    if not os.path.exists(service_dir):
        continue
    for fname in os.listdir(service_dir):
        if not fname.endswith("ServiceImpl.java"):
            continue
        fpath = os.path.join(service_dir, fname)
        with open(fpath, 'r', encoding='utf-8') as f:
            content = f.read()
        original = content
        
        # 移除 @RequiredArgsConstructor
        content = content.replace('import lombok.RequiredArgsConstructor;\n', '')
        content = content.replace('@RequiredArgsConstructor\n', '')
        
        # 替换 private final 为 @Autowired private
        lines = content.split('\n')
        new_lines = []
        in_class = False
        for line in lines:
            if '@Service' in line:
                in_class = True
            if in_class and 'private final' in line and ('Mapper' in line or 'Service' in line):
                line = line.replace('private final', '@Autowired\n    private')
            new_lines.append(line)
        content = '\n'.join(new_lines)
        
        # 修复 SecurityUtils.getUserIdStr()
        content = content.replace('SecurityUtils.getUserIdStr()', 'String.valueOf(SecurityUtils.getUserId())')
        
        if content != original:
            with open(fpath, 'w', encoding='utf-8') as f:
                f.write(content)
            print(f"  修复 ServiceImpl: {fname}")
            count += 1
print(f"  修复 ServiceImpl: {count} 个文件")

# 3. 修复 Domain 中的 TemplateMessage 字段冲突
wx_domain_dir = os.path.join(base_admin, "wx", "domain")
if os.path.exists(wx_domain_dir):
    template_path = os.path.join(wx_domain_dir, "TemplateMessage.java")
    if os.path.exists(template_path):
        with open(template_path, 'r', encoding='utf-8') as f:
            content = f.read()
        original = content
        # 重命名 params 字段避免冲突
        content = content.replace('private String params;', 'private String templateParams;')
        content = content.replace('setParams(String params)', 'setTemplateParams(String templateParams)')
        content = content.replace('getParams()', 'getTemplateParams()')
        content = content.replace('this.params = params;', 'this.templateParams = templateParams;')
        content = content.replace('return params;', 'return templateParams;')
        if content != original:
            with open(template_path, 'w', encoding='utf-8') as f:
                f.write(content)
            print("  修复 Domain: TemplateMessage.java (重命名字段避免冲突)")

print("\n" + "=" * 80)
print("兼容性修复完成!")
print("=" * 80)

