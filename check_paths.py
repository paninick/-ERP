
import os
import re

base_admin = r"d:\erp\RuoYi-Vue\ruoyi-admin\src\main\java\com\ruoyi"
controller_dir = os.path.join(base_admin, "erp", "controller")

paths = {}
conflicts = []

print("=" * 80)
print("ERP Controller 路径检查")
print("=" * 80)

for fname in sorted(os.listdir(controller_dir)):
    if not fname.endswith("Controller.java"):
        continue
    fpath = os.path.join(controller_dir, fname)
    with open(fpath, 'r', encoding='utf-8') as f:
        content = f.read()
    m = re.search(r'@RequestMapping\("([^"]+)"\)', content)
    if m:
        url_path = m.group(1)
        controller_name = fname.replace("Controller.java", "")
        if url_path in paths:
            conflicts.append((url_path, paths[url_path], controller_name))
            print("冲突: %s -&gt; %s 和 %s" % (url_path, paths[url_path], controller_name))
        else:
            paths[url_path] = controller_name

print("\n路径列表:")
for p, c in sorted(paths.items()):
    print("  %-35s -&gt; %s" % (c, p))

print("\n" + "=" * 80)
if conflicts:
    print("发现 %d 处冲突!" % len(conflicts))
else:
    print("无冲突，所有路径正常!")
print("=" * 80)

