
import os
import re

ctrl_dir = r"d:\erp\RuoYi-Vue\ruoyi-admin\src\main\java\com\ruoyi\erp\controller"

print("=" * 80)
print("查找并修复所有 Controller 路径冲突")
print("=" * 80)

# 先扫描所有 Controller 文件，找出路径映射
controllers = []
for f in os.listdir(ctrl_dir):
    if f.endswith("Controller.java") and not f.startswith("."):
        fpath = os.path.join(ctrl_dir, f)
        with open(fpath, 'r', encoding='utf-8') as f:
            content = f.read()
            match = re.search(r'@RequestMapping\s*\(\s*["\']([^"\']+)["\']', content)
            if match:
                path = match.group(1)
                controllers.append((f, path, fpath))

# 按路径分组，找出有多个 Controller 使用相同路径的
path_map = {}
for fname, path, fpath in controllers:
    if path not in path_map:
        path_map[path] = []
    path_map[path].append((fname, fpath))

print("\n冲突的路径:")
conflicts = []
for path, items in path_map.items():
    if len(items) &gt; 1:
        print(f"\n  路径: {path}")
        for fname, fpath in items:
            print(f"    - {fname}")
        conflicts.append((path, items))

print("\n" + "=" * 80)
print("开始修复路径冲突")
print("=" * 80)

for path, items in conflicts:
    # 找出"主"Controller（文件名最短的），其他都重命名
    items.sort(key=lambda x: len(x[0]))
    main_fname, main_fpath = items[0]
    others = items[1:]
    
    print(f"\n主Controller: {main_fname} (保留路径: {path})")
    for fname, fpath in others:
        # 从文件名构造新路径（去掉Controller后缀，转驼峰为kebab-case）
        base_name = fname.replace("Controller", "")
        # 转驼峰为kebab-case
        new_path = '/erp/' + re.sub(r'(?&lt;!^)(?=[A-Z])', '-', base_name).lower()
        # 如果新路径已经存在，则加数字后缀
        existing_paths = [p for _, p, _ in controllers]
        if new_path in existing_paths:
            i = 1
            while f"{new_path}{i}" in existing_paths:
                i += 1
            new_path = f"{new_path}{i}"
        
        print(f"  修改 {fname}: {path} -&gt; {new_path}")
        
        # 修改文件
        with open(fpath, 'r', encoding='utf-8') as f:
            content = f.read()
        old_str = f'@RequestMapping("{path}")'
        new_str = f'@RequestMapping("{new_path}")'
        content = content.replace(old_str, new_str)
        
        # 也检查单引号
        old_str2 = f"@RequestMapping('{path}')"
        new_str2 = f"@RequestMapping('{new_path}')"
        content = content.replace(old_str2, new_str2)
        
        with open(fpath, 'w', encoding='utf-8') as f:
            f.write(content)

print("\n" + "=" * 80)
print("路径冲突修复完成!")
print("=" * 80)
