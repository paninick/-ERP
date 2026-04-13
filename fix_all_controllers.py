import os

base = r"d:\erp\RuoYi-Vue\ruoyi-admin\src\main\java\com\ruoyi\erp\controller"
exclude_prefixes = ["Customer", "Sales", "Purchase", "Plan", "ProducePlan", "SampleTech", "Tech", "StockIn", "StockOut"]

for fname in sorted(os.listdir(base)):
    if not fname.endswith("Controller.java"):
        continue
    fpath = os.path.join(base, fname)
    with open(fpath, 'r', encoding='utf-8') as f:
        content = f.read()
    
    # Check if it's a conflicting path
    has_conflict = False
    for prefix in exclude_prefixes:
        # Check pattern: @RequestMapping("/erp/prefix") and there's a shorter prefix
        if '@RequestMapping("/erp/' in content:
            m = __import__('re').search(r'@RequestMapping\("/erp/([a-zA-Z]+)"\)', content)
            if m:
                path_name = m.group(1)
                if fname.startswith(prefix + "Controller.java"):
                    if path_name != prefix.lower() + "Item" and path_name != prefix.lower() + "Material" and path_name != prefix.lower() + "Size" and path_name != prefix.lower() + "Clothes":
                        pass  # No conflict
                    else:
                        # Found a conflicting one!
                        new_name = fname.replace("Controller.java", "").lower()
                        new_path = f'/erp/{new_name}'
                        old_line = f'@RequestMapping("/erp/{path_name}")'
                        new_line = f'@RequestMapping("{new_path}")'
                        if old_line in content:
                            content = content.replace(old_line, new_line)
                            with open(fpath, 'w', encoding='utf-8') as f:
                                f.write(content)
                            print(f"Fixed: {fname} → {new_path}")
                            has_conflict = True

print("\nAll fixed!")
