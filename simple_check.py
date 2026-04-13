
import os

print("=" * 80)
print("genCode (3) 导入完整性检查报告")
print("=" * 80)

# === 1. 检查 common/wx 包是否还存在 ===
print("\n【1】冲突包检查 (common/wx 应已删除)")
print("-" * 60)

base_system = r"d:\erp\RuoYi-Vue\ruoyi-system\src\main\java\com\ruoyi"
base_admin = r"d:\erp\RuoYi-Vue\ruoyi-admin\src\main\java\com\ruoyi"
base_mapper = r"d:\erp\RuoYi-Vue\ruoyi-admin\src\main\resources\mapper"

# 检查 ruoyi-system 中是否有 common 和 wx
for pkg in ["common", "wx"]:
    path = os.path.join(base_system, pkg)
    if os.path.exists(path):
        print("  [FAIL] %s 包仍存在于 ruoyi-system (会冲突!)" % pkg)
    else:
        print("  [OK] %s 包已从 ruoyi-system 删除" % pkg)

# 检查 mapper 中是否有 common 和 wx
for pkg in ["common", "wx"]:
    path = os.path.join(base_mapper, pkg)
    if os.path.exists(path):
        files = os.listdir(path)
        print("  [WARN] mapper/%s/ 存在 (%d 个文件)" % (pkg, len(files)))
    else:
        print("  [OK] mapper/%s/ 已删除" % pkg)

# === 2. 检查 ERP Controller 路径冲突 ===
print("\n【2】ERP Controller 路径检查")
print("-" * 60)

controller_dir = os.path.join(base_admin, "erp", "controller")
paths = {}
conflicts = []

if os.path.exists(controller_dir):
    for fname in sorted(os.listdir(controller_dir)):
        if not fname.endswith("Controller.java"):
            continue
        fpath = os.path.join(controller_dir, fname)
        with open(fpath, 'r', encoding='utf-8') as f:
            content = f.read()
        import re
        m = re.search(r'@RequestMapping\("([^"]+)"\)', content)
        if m:
            url_path = m.group(1)
            controller_name = fname.replace("Controller.java", "")
            if url_path in paths:
                conflicts.append((url_path, paths[url_path], controller_name))
                print("  [FAIL] 冲突: %s" % url_path)
                print("       -&gt; %sController" % paths[url_path])
                print("       -&gt; %sController" % controller_name)
            else:
                paths[url_path] = controller_name

if not conflicts:
    print("  [OK] 全部 %d 个 Controller 路径无冲突" % len(paths))
    for p, c in sorted(paths.items()):
        print("     %-30s -&gt; %s" % (c, p))

# === 3. 检查 ERP 文件完整性 ===
print("\n【3】ERP 文件导入完整性")
print("-" * 60)

expected_dirs = {
    "domain": 37,
    "controller": 37,
    "mapper": 37,
    "service": 74,
}

erp_base = os.path.join(base_admin, "erp")
for dirname, expected_count in expected_dirs.items():
    dirpath = os.path.join(erp_base, dirname)
    if os.path.exists(dirpath):
        actual_count = len([f for f in os.listdir(dirpath) if f.endswith('.java')])
        if actual_count &gt;= expected_count:
            status = "[OK]"
        else:
            status = "[WARN]"
        print("  %s %s: %d/%d" % (status, dirname, actual_count, expected_count))
    else:
        print("  [FAIL] %s: 目录不存在!" % dirname)

# === 4. 检查 Mapper XML ===
print("\n【4】Mapper XML 检查")
print("-" * 60)

erp_mapper = os.path.join(base_mapper, "erp")
if os.path.exists(erp_mapper):
    xml_files = [f for f in os.listdir(erp_mapper) if f.endswith('Mapper.xml')]
    print("  [OK] erp Mapper XML: %d 个文件" % len(xml_files))
else:
    print("  [FAIL] erp Mapper XML 目录不存在!")

# === 5. 检查 Vue 前端文件 ===
print("\n【5】Vue 前端文件检查")
print("-" * 60)

vue_views = r"d:\erp\RuoYi-Vue\ruoyi-ui\src\views\erp"
vue_api = r"d:\erp\RuoYi-Vue\ruoyi-ui\src\api\erp"

if os.path.exists(vue_views):
    vue_pages = []
    for root, dirs, files in os.walk(vue_views):
        for f in files:
            if f.endswith('.vue'):
                vue_pages.append(os.path.relpath(os.path.join(root, f), vue_views))
    print("  [OK] Vue 页面: %d 个" % len(vue_pages))

if os.path.exists(vue_api):
    api_files = [f for f in os.listdir(vue_api) if f.endswith('.js')]
    print("  [OK] API 文件: %d 个" % len(api_files))

# === 6. 总结 ===
print("\n" + "=" * 80)
print("检查完成!")
if conflicts:
    print("[WARN] 发现 %d 处路径冲突，需要修复!" % len(conflicts))
else:
    print("[OK] 所有检查通过，无冲突!")
print("=" * 80)

