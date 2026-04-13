# -*- coding: utf-8 -*-
import subprocess

MYSQL=r'C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe'

def M(menu_id,name,parent,order_num,path,component=None,**kw):
    mtype=kw.get("mtype","M");visible=kw.get("visible","0");status=kw.get("status","0")
    perms=kw.get("perms","''");icon=kw.get("icon","");is_frame=kw.get("is_frame",1)
    comp="NULL" if component is None else "'%s'"%component
    return "INSERT INTO sys_menu(menu_id,menu_name,parent_id,order_num,path,component,is_frame,menu_type,visible,status,perms,icon,create_by,create_time) VALUES(%d,'%s',%d,%d,'%s',%s,%d,'%s','%s','%s',%s,'%s','admin',NOW());"%(menu_id,name,parent,order_num,path,comp,is_frame,mtype,visible,status,perms,icon)

sqls=[]
def A(s): sqls.append(s)

A("-- 库存管理 order=120")
A(M(9,"库存管理",0,120,"stock", icon="table"))

# 入库单 + 按钮
A(M(910,"入库单",9,5,"stockin","erp/stockin/index", mtype="C", icon="upload"))
for i,(n,p) in enumerate([("库存单查询","erp.stock:query"),("库存单新增","erp.stock:add"),("库存单修改","erp.stock:edit"),("库存单删除","erp.stock:remove"),("库存单导出","erp.stock:export")]):
    A(M(4300+i,n,910,i+1,"", perms="'%s'"%p, mtype="F"))

# 出库单
A(M(911,"出库单",9,10,"stockout","erp/stockout/index", mtype="C", icon="download"))

# 库存查询
A(M(912,"库存查询",9,20,"stocklist","erp/stock/index", mtype="C", icon="chart"))

all_ids=[9,910,911,912]+list(range(4300,4305))
A("\nDELETE FROM sys_role_menu WHERE role_id=1 AND menu_id IN (%s);" % ",".join(map(str,all_ids)))
for mid in all_ids:
    A("INSERT IGNORE INTO sys_role_menu(role_id,menu_id) VALUES(1,%d);"%mid)

proc=subprocess.Popen([MYSQL,"-u","root","--default-character-set=utf8mb4","ry_vue"],stdin=subprocess.PIPE,stdout=subprocess.PIPE,stderr=subprocess.PIPE)
out,err=proc.communicate(input="\n".join(sqls).encode("utf-8"))
print("OK" if proc.returncode==0 else "ERR:%s"%err.decode("utf-8")[:500])

v="""SELECT menu_id,menu_name,parent_id,order_num,CASE menu_type WHEN 'M' THEN '[M]' WHEN 'C' THEN '[C]' ELSE '[F]' END AS T,COALESCE(perms,'') AS P FROM sys_menu WHERE menu_id=9 OR parent_id=9 OR parent_id IN(910) ORDER BY parent_id,order_num;"""
proc2=subprocess.Popen([MYSQL,"-u","root","--default-character-set=utf8mb4","ry_vue"],stdin=subprocess.PIPE,stdout=subprocess.PIPE)
out2,_=proc2.communicate(input=v.encode("utf-8"))
lines=out2.decode("utf-8").strip().split("\n")
print(f"\n=== {len(lines)-1} records ===")
for l in lines: print(l)
