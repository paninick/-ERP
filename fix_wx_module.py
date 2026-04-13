import os
import re

# 1. 修复微信模块的 Lombok 和 getUserIdStr()
BASE1 = r"D:\erp\RuoYi-Vue\ruoyi-admin\src\main\java\com\ruoyi\wx"

fixed = 0
for root, dirs, files in os.walk(BASE1):
    for f in files:
        if not f.endswith(".java"):
            continue

        filepath = os.path.join(root, f)
        with open(filepath, 'r', encoding='utf-8') as fh:
            content = fh.read()

        changed = False
        
        # 移除 Lombok
        if "lombok" in content:
            content = re.sub(r'import lombok\.RequiredArgsConstructor;\s*\n?', '', content)
            content = re.sub(r'@RequiredArgsConstructor\s*\n?', '', content)
            content = re.sub(r'private final (\w+Mapper) (\w+);', r'@Autowired\n    private \1 \2;', content)
            content = re.sub(r'private final (\w+Service|I\w+Service) (\w+);', r'@Autowired\n    private \1 \2;', content)
            changed = True
        
        # 修复 getUserIdStr()
        if "getUserIdStr()" in content:
            content = content.replace("SecurityUtils.getUserIdStr()", "SecurityUtils.getUserId().toString()")
            content = content.replace("getUserIdStr()", "getUserId().toString()")
            changed = True

        if changed:
            with open(filepath, 'w', encoding='utf-8') as fh:
                fh.write(content)
            fixed += 1
            rel_path = os.path.relpath(filepath, r"D:\erp\RuoYi-Vue")
            print(f"[FIXED-WX] {rel_path}")

print(f"Fixed {fixed} wx files")

# 2. 修复 TemplateMessage 的 getParams() 冲突
template_file = r"D:\erp\RuoYi-Vue\ruoyi-admin\src\main\java\com\ruoyi\wx\domain\TemplateMessage.java"
if os.path.exists(template_file):
    with open(template_file, 'r', encoding='utf-8') as fh:
        content = fh.read()

    if "public String getParams()" in content:
        content = content.replace("public String getParams()", "public String getParamsString()")
        content = content.replace("private String params;", "private String paramsString;")
        content = content.replace("public void setParams(String params) {", "public void setParamsString(String paramsString) {")
        content = content.replace("this.params = params;", "this.paramsString = paramsString;")
        content = content.replace(".append(\"params\", getParams())", ".append(\"params\", getParamsString())")
        
        with open(template_file, 'w', encoding='utf-8') as fh:
            fh.write(content)
        
        print(f"[FIXED-TEMPLATE] getParams() -> getParamsString()")

print("\nDONE!")
