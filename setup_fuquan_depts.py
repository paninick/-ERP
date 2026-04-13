# -*- coding: utf-8 -*-
import subprocess

MYSQL = r'C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe'

sql_statements = []

sql_statements.append("-- 清空旧部门数据")
sql_statements.append("DELETE FROM sys_dept;")
sql_statements.append("-- 清空用户-部门关联（保留admin的默认部门）")
sql_statements.append("UPDATE sys_user SET dept_id = 100 WHERE user_id = 1;")

sql_statements.append("")
sql_statements.append("-- ===============================")
sql_statements.append("-- 富泉工贸 - 部门管理体系")
sql_statements.append("-- ===============================")

sql_statements.append("")
sql_statements.append("-- [Level 1] 根节点: 富泉工贸")
sql_statements.append("""INSERT INTO sys_dept (dept_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_by, create_time)
VALUES (100, 0, '0', '富泉工贸', 0, NULL, NULL, NULL, '0', '0', 'admin', NOW());""")

sql_statements.append("")
sql_statements.append("-- [Level 2] 一级部门")

sql_statements.append("""INSERT INTO sys_dept (dept_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_by, create_time)
VALUES (101, 100, '0,100', '技术支持', 1, NULL, NULL, NULL, '0', '0', 'admin', NOW());""")

sql_statements.append("""INSERT INTO sys_dept (dept_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_by, create_time)
VALUES (102, 100, '0,100', '公司领导', 1, NULL, NULL, NULL, '0', '0', 'admin', NOW());""")

sql_statements.append("""INSERT INTO sys_dept (dept_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_by, create_time)
VALUES (107, 100, '0,100', '生产部', 5, NULL, NULL, NULL, '0', '0', 'admin', NOW());""")

sql_statements.append("")
sql_statements.append("-- [Level 3] 技术支持 下属")
sql_statements.append("""INSERT INTO sys_dept (dept_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_by, create_time)
VALUES (103, 101, '0,100,101', '研发部门', 1, NULL, NULL, NULL, '0', '0', 'admin', NOW());""")

sql_statements.append("""INSERT INTO sys_dept (dept_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_by, create_time)
VALUES (105, 101, '0,100,101', '测试部门', 3, NULL, NULL, NULL, '0', '0', 'admin', NOW());""")

sql_statements.append("")
sql_statements.append("-- [Level 3] 公司领导 下属")
sql_statements.append("""INSERT INTO sys_dept (dept_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_by, create_time)
VALUES (104, 102, '0,100,102', '行政部', 2, NULL, NULL, NULL, '0', '0', 'admin', NOW());""")

sql_statements.append("""INSERT INTO sys_dept (dept_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_by, create_time)
VALUES (106, 102, '0,100,102', '业务部', 3, NULL, NULL, NULL, '0', '0', 'admin', NOW());""")

sql_statements.append("""INSERT INTO sys_dept (dept_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_by, create_time)
VALUES (116, 102, '0,100,102', '采购部', 4, NULL, NULL, NULL, '0', '0', 'admin', NOW());""")

sql_statements.append("")
sql_statements.append("-- [Level 3] 生产部 下属")
sql_statements.append("""INSERT INTO sys_dept (dept_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_by, create_time)
VALUES (108, 107, '0,100,107', '服装生产部', 1, NULL, NULL, NULL, '0', '0', 'admin', NOW());""")

sql_statements.append("""INSERT INTO sys_dept (dept_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_by, create_time)
VALUES (110, 107, '0,100,107', '技术部', 6, NULL, NULL, NULL, '0', '0', 'admin', NOW());""")

sql_statements.append("""INSERT INTO sys_dept (dept_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_by, create_time)
VALUES (113, 107, '0,100,107', '前道车间', 7, NULL, NULL, NULL, '0', '0', 'admin', NOW());""")

sql_statements.append("""INSERT INTO sys_dept (dept_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_by, create_time)
VALUES (114, 107, '0,100,107', '后道车间', 8, NULL, NULL, NULL, '0', '0', 'admin', NOW());""")

sql_statements.append("""INSERT INTO sys_dept (dept_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_by, create_time)
VALUES (115, 107, '0,100,107', '仓库', 9, NULL, NULL, NULL, '0', '0', 'admin', NOW());""")

sql_statements.append("""INSERT INTO sys_dept (dept_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_by, create_time)
VALUES (117, 107, '0,100,107', '财务部', 10, NULL, NULL, NULL, '0', '0', 'admin', NOW());""")

sql_statements.append("""INSERT INTO sys_dept (dept_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_by, create_time)
VALUES (118, 107, '0,100,107', '单证', 11, NULL, NULL, NULL, '0', '0', 'admin', NOW());""")

sql_statements.append("")
sql_statements.append("-- [Level 4] 服装生产部 下属")
sql_statements.append("""INSERT INTO sys_dept (dept_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_by, create_time)
VALUES (109, 108, '0,100,107,108', '毛衣生产部', 2, NULL, NULL, NULL, '0', '0', 'admin', NOW());""")

sql_statements.append("")
sql_statements.append("-- [Level 4] 技术部 下属")
sql_statements.append("""INSERT INTO sys_dept (dept_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_by, create_time)
VALUES (111, 110, '0,100,107,110', '服装技术部', 1, NULL, NULL, NULL, '0', '0', 'admin', NOW());""")

sql_statements.append("""INSERT INTO sys_dept (dept_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_by, create_time)
VALUES (112, 110, '0,100,107,110', '毛衣技术部', 2, NULL, NULL, NULL, '0', '0', 'admin', NOW());""")


full_sql = "\n".join(sql_statements)

print("=" * 50)
print("执行富泉工贸部门数据初始化...")
print("=" * 50)

proc = subprocess.Popen([MYSQL, '-u', 'root', '--default-character-set=utf8mb4', 'ry_vue'],
                       stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
out, err = proc.communicate(input=full_sql.encode('utf-8'))

if proc.returncode == 0:
    print("✅ SQL执行成功!")
else:
    print(f"❌ 执行错误: {err.decode('utf-8')}")

print("\n" + out.decode('utf-8'))

print("\n" + "=" * 50)
print("验证结果:")
print("=" * 50)

verify_sql = """SELECT dept_id, parent_id, ancestors, dept_name, order_num 
FROM sys_dept 
ORDER BY CAST(SUBSTRING_INDEX(ancestors,',',1) AS UNSIGNED),
         CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(ancestors,',',2),',',-1) AS UNSIGNED),
         CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(ancestors,',',3),',',-1) AS UNSIGNED),
         CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(ancestors,',',4),',',-1) AS UNSIGNED),
         order_num;"""

proc2 = subprocess.Popen([MYSQL, '-u', 'root', '--default-character-set=utf8mb4', 'ry_vue'],
                        stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
out2, _ = proc2.communicate(input=verify_sql.encode('utf-8'))
print(out2.decode('utf-8'))
