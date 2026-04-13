# -*- coding: utf-8 -*-
import subprocess

MYSQL = r'C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe'

sqls = []
def A(sql): sqls.append(sql)

A("-- 流程管理 - 使用600+ ID范围避免冲突")

A("DELETE FROM sys_menu WHERE menu_id = 5;")

A("""INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, menu_type, visible, status, perms, icon, create_by, create_time)
VALUES (5, '流程管理', 0, 60, 'flowable', NULL, 'M', '0', '0', '', 'documentation', 'admin', NOW());""")

A("-- [C] 流程定义")
A("""INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, menu_type, visible, status, perms, icon, create_by, create_time)
VALUES (600, '流程定义', 5, 1, 'definition', 'flowable/definition/index', 'C', '0', '0', '', 'form', 'admin', NOW());""")

A("-- [M] 表单配置 + [C] 表单列表 + 按钮")
A("""INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, menu_type, visible, status, perms, icon, create_by, create_time)
VALUES (601, '表单配置', 5, 2, 'form', 'ParentView', 'M', '0', '0', '', 'build', 'admin', NOW());""")
A("""INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, menu_type, visible, status, perms, icon, create_by, create_time)
VALUES (602, '表单列表', 601, 1, 'list', 'flowable/form/list', 'C', '0', '0', '', 'table', 'admin', NOW());""")
A("""INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time)
VALUES (3000, '新增', 602, 1, '', '', 1, 'F', '0', '0', 'flowable.form:add', '#', 'admin', NOW());""")
A("""INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time)
VALUES (3001, '编辑', 602, 2, '', '', 1, 'F', '0', '0', 'flowable.form:edit', '#', 'admin', NOW());""")
A("""INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time)
VALUES (3002, '删除', 602, 3, '', '', 1, 'F', '0', '0', 'flowable.form:remove', '#', 'admin', NOW());""")
A("""INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time)
VALUES (3003, '查询', 602, 4, '', '', 1, 'F', '0', '0', 'flowable.form:query', '#', 'admin', NOW());""")

A("-- [M] 流程表达式 + [C] 流程表达式查询 + 按钮")
A("""INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, menu_type, visible, status, perms, icon, create_by, create_time)
VALUES (603, '流程表达式', 5, 3, 'expression', 'ParentView', 'M', '0', '0', '', 'cascader', 'admin', NOW());""")
A("""INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, menu_type, visible, status, perms, icon, create_by, create_time)
VALUES (604, '流程表达式查询', 603, 1, 'list', 'flowable/expression/index', 'C', '0', '0', '', 'search', 'admin', NOW());""")
A("""INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time)
VALUES (3010, '流程表达式查询', 604, 1, '', '', 1, 'F', '0', '0', 'system.expression:query', '#', 'admin', NOW());""")
A("""INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time)
VALUES (3011, '流程表达式新增', 604, 2, '', '', 1, 'F', '0', '0', 'system.expression:add', '#', 'admin', NOW());""")
A("""INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time)
VALUES (3012, '流程表达式修改', 604, 3, '', '', 1, 'F', '0', '0', 'system.expression:edit', '#', 'admin', NOW());""")
A("""INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time)
VALUES (3013, '流程表达式删除', 604, 4, '', '', 1, 'F', '0', '0', 'system.expression:remove', '#', 'admin', NOW());""")
A("""INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time)
VALUES (3014, '流程表达式导出', 604, 5, '', '', 1, 'F', '0', '0', 'system.expression:export', '#', 'admin', NOW());""")

A("-- [M] 流程监听 + [C] 流程监听查询 + 按钮")
A("""INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, menu_type, visible, status, perms, icon, create_by, create_time)
VALUES (605, '流程监听', 5, 4, 'listener', 'ParentView', 'M', '0', '0', '', 'monitor', 'admin', NOW());""")
A("""INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, menu_type, visible, status, perms, icon, create_by, create_time)
VALUES (606, '流程监听查询', 605, 1, 'list', 'flowable/listener/index', 'C', '0', '0', '', 'online', 'admin', NOW());""")
A("""INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time)
VALUES (3020, '流程监听查询', 606, 1, '', '', 1, 'F', '0', '0', 'system.listener:query', '#', 'admin', NOW());""")
A("""INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time)
VALUES (3021, '流程监听新增', 606, 2, '', '', 1, 'F', '0', '0', 'system.listener:add', '#', 'admin', NOW());""")
A("""INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time)
VALUES (3022, '流程监听修改', 606, 3, '', '', 1, 'F', '0', '0', 'system.listener:edit', '#', 'admin', NOW());""")
A("""INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time)
VALUES (3023, '流程监听删除', 606, 4, '', '', 1, 'F', '0', '0', 'system.listener:remove', '#', 'admin', NOW());""")
A("""INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time)
VALUES (3024, '流程监听导出', 606, 5, '', '', 1, 'F', '0', '0', 'system.listener:export', '#', 'admin', NOW());""")

A("-- 分配给管理员角色")
all_ids = [5, 600, 601, 602, 603, 604, 605, 606,
           3000,3001,3002,3003, 3010,3011,3012,3013,3014,
           3020,3021,3022,3023,3024]
A(f"DELETE FROM sys_role_menu WHERE role_id=1 AND menu_id IN ({','.join(map(str, all_ids))});")
for mid in all_ids:
    A(f"INSERT IGNORE INTO sys_role_menu (role_id, menu_id) VALUES (1, {mid});")


full_sql = "\n".join(sqls)
proc = subprocess.Popen([MYSQL, '-u', 'root', '--default-character-set=utf8mb4', 'ry_vue'],
                       stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
out, err = proc.communicate(input=full_sql.encode('utf-8'))
print("✅ 成功!" if proc.returncode == 0 else f"❌ {err.decode('utf-8')[:400]}")

verify = """
SELECT menu_id, menu_name, parent_id, order_num, 
       CASE menu_type WHEN 'M' THEN '[目录]' WHEN 'C' THEN '[菜单]' ELSE '[按钮]' END AS type,
       COALESCE(perms,'') AS perms
FROM sys_menu 
WHERE menu_id=5 OR parent_id=5 OR parent_id IN (601,603,605) OR parent_id IN (602,604,606)
ORDER BY 
  CASE WHEN menu_id=5 THEN 0 ELSE 1 END,
  parent_id, order_num;
"""
proc2 = subprocess.Popen([MYSQL, '-u', 'root', '--default-character-set=utf8mb4', 'ry_vue'],
                        stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
out2, _ = proc2.communicate(input=verify.encode('utf-8'))
lines = out2.decode('utf-8').strip().split('\n')
print(f"\n=== 流程管理完整菜单树 ({len(lines)-1} 条) ===")
for line in lines:
    print(line)

total_sql = "SELECT COUNT(*) FROM sys_menu WHERE menu_id=5 OR parent_id=5;"
proc3 = subprocess.Popen([MYSQL, '-u', 'root', '--default-character-set=utf8mb4', 'ry_vue'],
                        stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
out3, _ = proc3.communicate(input=total_sql.encode('utf-8'))
print(f"\n总计: {out3.decode('utf-8').strip()}")
