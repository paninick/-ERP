import os
import re

# 检查所有ERP Controller的@RequestMapping路径
base_dir = r"D:\erp\RuoYi-Vue\ruoyi-admin\src\main\java\com\ruoyi\erp\controller"

paths = {}
conflicts = []

for filename in os.listdir(base_dir):
    if not filename.endswith("Controller.java"):
        continue
    
    filepath = os.path.join(base_dir, filename)
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()
    
    # 查找 @RequestMapping
    match = re.search(r'@RequestMapping\s*\(\s*"([^"]+)"\s*\)', content)
    if match:
        path = match.group(1)
        if path in paths:
            conflicts.append((path, filename, paths[path]))
        else:
            paths[path] = filename

print("=== 所有ERP Controller路径 ===")
for path, name in sorted(paths.items()):
    print(f"  {path:30} -> {name}")

print(f"\n=== 冲突检测 ===")
if conflicts:
    print(f"发现 {len(conflicts)} 个路径冲突:")
    for path, f1, f2 in conflicts:
        print(f"  PATH='{path}'  -> {f1} 冲突于 {f2}")
else:
    print("没有路径冲突!")

print("\n=== 微信模块Controller路径 ===")
wx_dir = r"D:\erp\RuoYi-Vue\ruoyi-admin\src\main\java\com\ruoyi\wx\controller"
wx_paths = {}
wx_conflicts = []
for filename in os.listdir(wx_dir):
    if not filename.endswith("Controller.java"):
        continue
    filepath = os.path.join(wx_dir, filename)
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()
    match = re.search(r'@RequestMapping\s*\(\s*"([^"]+)"\s*\)', content)
    if match:
        path = match.group(1)
        if path in wx_paths:
            wx_conflicts.append((path, filename, wx_paths[path]))
        else:
            wx_paths[path] = filename
for path, name in sorted(wx_paths.items()):
    print(f"  {path:30} -> {name}")
if wx_conflicts:
    print(f"\n发现 {len(wx_conflicts)} 个微信路径冲突:")
    for path, f1, f2 in wx_conflicts:
        print(f"  PATH='{path}'  -> {f1} 冲突于 {f2}")
else:
    print("没有路径冲突!")

print("\nDONE!")
