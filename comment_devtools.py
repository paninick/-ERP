
pom_path = r"d:\erp\RuoYi-Vue\ruoyi-admin\pom.xml"

with open(pom_path, 'r', encoding='utf-8') as f:
    lines = f.readlines()

result = []
in_devtools = False
for line in lines:
    if 'spring-boot-devtools' in line and '&lt;!--' in line:
        # 开始注释
        result.append(line)
        in_devtools = True
    elif in_devtools and '&lt;/dependency&gt;' in line:
        # 结束注释
        result.append('        &lt;/dependency&gt; --&gt;\n')
        in_devtools = False
    elif in_devtools:
        result.append('        ' + line)
    else:
        result.append(line)

with open(pom_path, 'w', encoding='utf-8') as f:
    f.writelines(result)

print("=" * 80)
print("spring-boot-devtools 已注释掉!")
print("=" * 80)
