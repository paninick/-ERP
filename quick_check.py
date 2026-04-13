
import os

print("=" * 80)
print("genCode (3) 导入完整性检查报告")
print("=" * 80)

base_system = r"d:\erp\RuoYi-Vue\ruoyi-system\src\main\java\com\ruoyi"
base_admin = r"d:\erp\RuoYi-Vue\ruoyi-admin\src\main\java\com\ruoyi"
base_mapper = r"d:\erp\RuoYi-Vue\ruoyi-admin\src\main\resources\mapper"

# 检查 1: common/wx 包
print("\n1. 冲突包检查")
for pkg in ["common", "wx"]:
    p = os.path.join(base_system, pkg)
    if os.path.exists(p):
        print("  [FAIL] " + pkg + " 包存在 (冲突!)")
    else:
        print("  [OK] " + pkg + " 包已删除")

# 检查 2: ERP 文件
print("\n2. ERP 文件检查")
erp_dirs = ["domain", "controller", "mapper", "service"]
erp_base = os.path.join(base_admin, "erp")
for d in erp_dirs:
    dp = os.path.join(erp_base, d)
    if os.path.exists(dp):
        cnt = len([f for f in os.listdir(dp) if f.endswith('.java')])
        print("  [OK] " + d + ": " + str(cnt) + " 个文件")
    else:
        print("  [FAIL] " + d + " 目录不存在")

# 检查 3: Mapper XML
print("\n3. Mapper XML 检查")
erp_mapper = os.path.join(base_mapper, "erp")
if os.path.exists(erp_mapper):
    xmls = [f for f in os.listdir(erp_mapper) if f.endswith('Mapper.xml')]
    print("  [OK] " + str(len(xmls)) + " 个 Mapper XML")
else:
    print("  [FAIL] Mapper XML 目录不存在")

# 检查 4: Vue 文件
print("\n4. 前端文件检查")
vue_views = r"d:\erp\RuoYi-Vue\ruoyi-ui\src\views\erp"
vue_api = r"d:\erp\RuoYi-Vue\ruoyi-ui\src\api\erp"
if os.path.exists(vue_views):
    vues = []
    for r, ds, fs in os.walk(vue_views):
        for f in fs:
            if f.endswith('.vue'):
                vues.append(f)
    print("  [OK] " + str(len(vues)) + " 个 Vue 页面")
if os.path.exists(vue_api):
    apis = [f for f in os.listdir(vue_api) if f.endswith('.js')]
    print("  [OK] " + str(len(apis)) + " 个 API 文件")

print("\n" + "=" * 80)
print("检查完成")
print("=" * 80)

