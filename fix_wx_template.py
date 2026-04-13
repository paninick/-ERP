import os
import mysql.connector

# 1. 修复 TemplateMessage getParams() 冲突
file_path = r"D:\erp\RuoYi-Vue\ruoyi-admin\src\main\java\com\ruoyi\wx\domain\TemplateMessage.java"
if os.path.exists(file_path):
    with open(file_path, 'r', encoding='utf-8') as fh:
        content = fh.read()
    
    # 重命名 params 为 paramsJson 避免冲突
    if "public String getParams()" in content and "private String params;" in content:
        content = content.replace("private String params;", "private String paramsJson;")
        content = content.replace("public String getParams()", "public String getParamsJson()")
        content = content.replace("public void setParams(String params)", "public void setParamsJson(String paramsJson)")
        content = content.replace(".append(\"params\", getParams())", ".append(\"params\", getParamsJson())")
        
        with open(file_path, 'w', encoding='utf-8') as fh:
            fh.write(content)
        print("[FIXED] TemplateMessage.java: params -> paramsJson (avoid name conflict)")
    else:
        print("[OK] TemplateMessage.java already fixed")
else:
    print("[NOT FOUND] TemplateMessage.java")

# 2. 修复 Lombok 和 getUserIdStr
print("\n=== Fixing Lombok and getUserIdStr for wx module ===")
base = r"D:\erp\RuoYi-Vue\ruoyi-admin\src\main\java\com\ruoyi\wx"

fixed_lombok = 0
fixed_userid = 0

for root, dirs, files in os.walk(base):
    for f in files:
        if not f.endswith(".java"):
            continue
        
        filepath = os.path.join(root, f)
        with open(filepath, 'r', encoding='utf-8') as fh:
            content = fh.read()
        
        changed = False
        
        if "lombok" in content:
            content = content.replace("import lombok.RequiredArgsConstructor;\n", "")
            content = content.replace("@RequiredArgsConstructor\n", "")
            content = content.replace("private final (\\w+Mapper) (\\w+);", r"@Autowired\n    private \1 \2;", 0)
            content = content.replace("private final (\\w+Service|I\\w+Service) (\\w+);", r"@Autowired\n    private \1 \2;", 0)
            changed = True
            fixed_lombok += 1
        
        if "getUserIdStr()" in content:
            content = content.replace("SecurityUtils.getUserIdStr()", "SecurityUtils.getUserId().toString()")
            content = content.replace("getUserIdStr()", "getUserId().toString()")
            changed = True
            fixed_userid += 1
        
        if changed:
            with open(filepath, 'w', encoding='utf-8') as fh:
                fh.write(content)
            rel = os.path.relpath(filepath, r"D:\erp\RuoYi-Vue")
            print(f"  [FIXED] {rel}")

print(f"\nFixed: {fixed_lombok} lombok, {fixed_userid} getUserIdStr")

# 3. 检查 BOM
print("\n=== Checking BOM ===")
import codecs
fixed_bom = 0
for root, dirs, files in os.walk(base):
    for f in files:
        if not f.endswith(".java"):
            continue
        filepath = os.path.join(root, f)
        try:
            with open(filepath, 'rb') as fh:
                bytes = fh.read()
            if bytes.startswith(codecs.BOM_UTF8):
                bytes = bytes[len(codecs.BOM_UTF8):]
                with open(filepath, 'wb') as fh:
                    fh.write(bytes)
                fixed_bom += 1
                rel = os.path.relpath(filepath, r"D:\erp\RuoYi-Vue")
                print(f"  [FIXED BOM] {rel}")
        except:
            pass

print(f"Fixed BOM: {fixed_bom} files")

print("\nDONE!")
