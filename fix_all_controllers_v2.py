import os
import re

base = r"d:\erp\RuoYi-Vue\ruoyi-admin\src\main\java\com\ruoyi\erp\controller"

for fname in sorted(os.listdir(base)):
    if not fname.endswith("Controller.java"):
        continue
    fpath = os.path.join(base, fname)
    with open(fpath, 'r', encoding='utf-8') as f:
        content = f.read()
    
    controller_name = fname.replace("Controller.java", "")
    
    # Convert to camelCase path: CustomerTemplate → customerTemplate
    path_name = controller_name[0].lower() + controller_name[1:]
    
    # Find current @RequestMapping
    match = re.search(r'@RequestMapping\("([^"]+)"\)', content)
    if match:
        current_path = match.group(1)
        desired_path = f"/erp/{path_name}"
        if current_path != desired_path:
            # Fix it
            content = re.sub(r'@RequestMapping\("[^"]+"\)', f'@RequestMapping("{desired_path}")', content)
            with open(fpath, 'w', encoding='utf-8') as f:
                f.write(content)
            print(f"Fixed: {fname} → {desired_path}")

print("\nAll paths fixed!")
