import subprocess

MYSQL=r'C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe'

sqls = []
sqls.append("-- 1. 给 sys_dict_data 添加 parent_id 列 (支持级联)")
sqls.append("ALTER TABLE sys_dict_data ADD COLUMN parent_id bigint DEFAULT 0 AFTER dict_code;")

sqls.append("")
sqls.append("-- 2. 插入级联字典类型")
dict_types = [
    ("erp_sample_style", "样衣款式", "Y", "0"),
    ("erp_nation", "国家地区", "Y", "0"), 
    ("erp_unit", "计量单位", "Y", "0"),
    ("erp_color", "颜色", "Y", "0"),
    ("erp_size", "尺码", "Y", "0"),
    ("sys_normal_disable", "正常/停用", "N", "0"),
]
for dt, dn, status, css in dict_types:
    sqls.append(f"INSERT IGNORE INTO sys_dict_type(dict_type,dict_name,status,create_by,create_time) VALUES('{dt}','{dn}','{status}','admin',NOW());")

sqls.append("")
sqls.append("-- 3. 插入级联字典数据 - 样衣款式(示例)")

sample_styles = [
    ("1", "针织", "1", "erp_sample_style", "", "", "N", "0", "0"),
    ("2", "梭织", "2", "erp_sample_style", "", "", "N", "0", "0"),
    ("3", "毛衣", "3", "erp_sample_style", "", "", "N", "0", "0"),
    ("11", "圆领", "11", "erp_sample_style", "primary", "", "N", "0", "1"),
    ("12", "V领", "12", "erp_sample_style", "success", "", "N", "0", "1"),
    ("13", " Polo领", "13", "erp_sample_style", "warning", "", "N", "0", "1"),
    ("21", "平纹", "21", "erp_sample_style", "", "", "N", "0", "2"),
    ("22", "斜纹", "22", "erp_sample_style", "", "", "N", "0", "2"),
    ("31", "套头", "31", "erp_sample_style", "", "", "N", "0", "3"),
    ("32", "开衫", "32", "erp_sample_style", "", "", "N", "0", "3"),
]

for item in sample_styles:
    code, label, value, dtype, css, lst, isdef, status, parent = item
    sqls.append(f"INSERT INTO sys_dict_data(dict_sort,dict_label,dict_value,dict_type,css_class,list_class,is_default,status,parent_id) VALUES({code},'{label}','{value}','{dtype}','{css}','{lst}','{isdef}','{status}',{parent});")

sqls.append("")
sqls.append("-- 4. 计量单位数据")
units = [
    ("1", "重量-公斤(KG)", "kg_erp", "erp_unit", "", "", "N", "0", "0"),
    ("2", "重量-克(g)", "g_erp", "erp_unit", "", "", "N", "0", "0"),
    ("3", "长度-米(m)", "m_erp", "erp_unit", "", "", "N", "0", "0"),
    ("4", "长度-厘米(cm)", "cm_erp", "erp_unit", "", "", "N", "0", "0"),
    ("5", "数量-件(PC)", "pc_erp", "erp_unit", "", "", "N", "0", "0"),
    ("6", "数量-打(DZ)", "dz_erp", "erp_unit", "", "", "N", "0", "0"),
    ("7", "数量-箱(CT)", "ct_erp", "erp_unit", "", "", "N", "0", "0"),
]
for item in units:
    sort, label, val, dtype, css, lst, idef, sta, pid = item
    sqls.append(f"INSERT INTO sys_dict_data(dict_sort,dict_label,dict_value,dict_type,css_class,list_class,is_default,status,parent_id) VALUES({sort},'{label}','{val}','{dtype}','{css}','{lst}','{idef}','{sta}',{pid});")

sqls.append("")
sqls.append("-- 5. 国家地区数据")
nations = [
    ("1", "中国", "CN", "erp_nation", "", "", "N", "0", "0"),
    ("2", "日本", "JP", "erp_nation", "", "", "N", "0", "0"),
    ("3", "韩国", "KR", "erp_nation", "", "", "N", "0", "0"),
    ("4", "美国", "US", "erp_nation", "", "", "N", "0", "0"),
    ("5", "欧盟", "EU", "erp_nation", "", "", "N", "0", "0"),
    ("11", "广东", "GD", "erp_nation", "", "", "N", "0", "1"),
    ("12", "浙江", "ZJ", "erp_nation", "", "", "N", "0", "1"),
    ("13", "江苏", "JS", "erp_nation", "", "", "N", "0", "1"),
    ("14", "上海", "SH", "erp_nation", "", "", "N", "0", "1"),
]
for item in nations:
    sort, label, val, dtype, css, lst, idef, sta, pid = item
    sqls.append(f"INSERT INTO sys_dict_data(dict_sort,dict_label,dict_value,dict_type,css_class,list_class,is_default,status,parent_id) VALUES({sort},'{label}','{val}','{dtype}','{css}','{lst}','{idef}','{sta}',{pid});")


full_sql = "\n".join(sqls)
proc = subprocess.Popen([MYSQL,"-u","root","--default-character-set=utf8mb4","ry_vue"],
                       stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
out,err=proc.communicate(input=full_sql.encode("utf-8"))
if proc.returncode == 0:
    print("✅ 级联字典修复成功!")
else:
    err_text = err.decode('utf-8')
    if 'Duplicate column' in err_text:
        print("✅ parent_id列已存在，跳过")
        # 执行剩余SQL
        remaining = "\n".join(sqls[2:])
        proc2 = subprocess.Popen([MYSQL,"-u","root","--default-character-set=utf8mb4","ry_vue"],
                               stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        out2,err2 = proc2.communicate(input=remaining.encode("utf-8"))
        print(f"✅ 字典数据导入完成!" if proc2.returncode==0 else f"⚠️ {err2.decode('utf-8')[:300]}")
    else:
        print(f"❌ {err_text[:500]}")

v="SELECT d.dict_type,d.dict_name,COUNT(dd.dict_code) as items FROM sys_dict_type d LEFT JOIN sys_dict_data dd ON d.dict_type=dd.dict_type GROUP BY d.dict_type ORDER BY d.dict_id;"
p=subprocess.Popen([MYSQL,"-u","root","--default-character-set=utf8mb4","ry_vue"],stdin=subprocess.PIPE,stdout=subprocess.PIPE)
out3,_=p.communicate(input=v.encode("utf-8"))
print(f"\n=== 字典统计 ===\n{out3.decode('utf-8').strip()}")

v2="SELECT dict_code,dict_label,dict_value,parent_id FROM sys_dict_data WHERE dict_type='erp_sample_style' ORDER BY parent_id,dict_sort;"
p2=subprocess.Popen([MYSQL,"-u","root","--default-character-set=utf8mb4","ry_vue"],stdin=subprocess.PIPE,stdout=subprocess.PIPE)
out4,_=p2.communicate(input=v2.encode("utf-8"))
print(f"\n=== 样衣款式(级联) ===\n{out4.decode('utf-8').strip()}")
