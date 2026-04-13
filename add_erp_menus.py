# -*- coding: utf-8 -*-
import subprocess

MYSQL = r'C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe'

def M(menu_id, name, parent, order_num, path, component=None, **kw):
    mtype   = kw.get("mtype",   "M")
    visible = kw.get("visible", "0")
    status  = kw.get("status",  "0")
    perms   = kw.get("perms",   "''")
    icon    = kw.get("icon",    "")
    is_frame= kw.get("is_frame",1)
    comp = "NULL" if component is None else "'%s'" % component
    return "INSERT INTO sys_menu(menu_id,menu_name,parent_id,order_num,path,component,is_frame,menu_type,visible,status,perms,icon,create_by,create_time) VALUES(%d,'%s',%d,%d,'%s',%s,%d,'%s','%s','%s',%s,'%s','admin',NOW());" % (menu_id,name,parent,order_num,path,comp,is_frame,mtype,visible,status,perms,icon)

sqls = []
def A(s): sqls.append(s)

def BTN(parent_id, start_id, btn_list):
    for i,(n,p) in enumerate(btn_list):
        A(M(start_id+i,n,parent_id,i+1,"", perms="'%s'"%p, mtype="F"))

A("-- 1. 任务管理 order=70")
A(M(6,"任务管理",0,70,"task", icon="list"))
A(M(700,"已发任务",6,1,"myProcess","flowable/task/myProcess/index", mtype="C"))
BTN(700,4000,[("新增","system.deployment:add"),("编辑","system.deployment:edit"),("删除","system.deployment:remove")])
A(M(701,"待办任务",6,2,"todo","flowable/task/todo/index", mtype="C"))
A(M(702,"已办任务",6,3,"finished","flowable/task/finished/index", mtype="C"))

A("\n-- 2. 基础管理 order=100")
A(M(7,"基础管理",0,100,"basic", icon="education"))
A(M(800,"客户管理",7,1,"customer","erp/customer/index", mtype="C", icon="peoples"))
BTN(800,4100,[("客户查询","erp.customer:query"),("客户新增","erp.customer:add"),("客户修改","erp.customer:edit"),("客户删除","erp.customer:remove"),("客户导出","erp.customer:export")])
A(M(801,"供应商管理",7,20,"supplier","erp/supplier/index", mtype="C", icon="tree-table"))
BTN(801,4110,[("供应商查询","erp.supplier:query"),("供应商新增","erp.supplier:add"),("供应商修改","erp.supplier:edit"),("供应商删除","erp.supplier:remove"),("供应商导出","erp.supplier:export")])
A(M(802,"主料管理",7,30,"material","erp/material/index", mtype="C", icon="cascader"))
BTN(802,4120,[("主料查询","erp.material:query"),("主料新增","erp.material:add"),("主料修改","erp.material:edit"),("主料删除","erp.material:remove"),("主料导出","erp.material:export")])
A(M(803,"辅料管理",7,40,"auxiliary","erp/auxiliary/index", mtype="C", icon="edit"))
BTN(803,4130,[("辅料查询","erp.auxiliary:query"),("辅料新增","erp.auxiliary:add"),("辅料修改","erp.auxiliary:edit"),("辅料删除","erp.auxiliary:remove"),("辅料导出","erp.auxiliary:export")])
A(M(804,"客户模板",7,50,"customerTemplate","erp/customerTemplate/index", mtype="C", icon="skill"))
BTN(804,4140,[("客户尺寸查询","erp.customer:query"),("客户尺寸新增","erp.customer:add"),("客户尺寸修改","erp.customer:edit"),("客户尺寸删除","erp.customer:remove"),("客户尺寸导出","erp.customer:export")])
A(M(805,"仓库维护",7,60,"warehouse","ParentView", icon="log"))
A(M(806,"仓库管理",805,1,"list","erp/warehouse/index", mtype="C", icon="form"))
BTN(806,4150,[("仓库查询","erp.warehouse:query"),("仓库新增","erp.warehouse:add"),("仓库修改","erp.warehouse:edit"),("仓库删除","erp.warehouse:remove"),("仓库导出","erp.warehouse:export")])
A(M(807,"库区管理",7,70,"warehousearea","erp/warehousearea/index", mtype="C", icon="table"))

A("\n-- 3. 业务系统 order=110")
A(M(8,"业务系统",0,110,"business", icon="shopping"))
A(M(900,"打样通知",8,10,"notice","erp/notice/index", mtype="C", icon="message"))
BTN(900,4200,[("打样通知查询","erp.notice:query"),("打样新增","erp.notice:add"),("打样修改","erp.notice:edit"),("打样删除","erp.notice:remove"),("打样导出","erp.notice:export")])
A(M(901,"样衣BOM",8,20,"bom","erp/bom/index", mtype="C", icon="documentation"))
A(M(902,"大货核版",8,25,"check","erp/check/index", mtype="C", icon="validCode"))
A(M(903,"工艺指示书",8,30,"tech","erp/tech/index", mtype="C", icon="cascader"))
tech_btns=[("工艺查询","erp.tech:query"),("工艺新增","erp.tech:add"),("工艺修改","erp.tech:edit"),("工艺删除","erp.tech:remove"),("工艺保存","erp.tech:save"),("工艺导出","erp.tech:export")]
tech_orders=[0,1,2,3,5,4]
for idx,(n,p) in enumerate(tech_btns):
    A(M(4210+idx,n,903,tech_orders[idx]+1,"", perms="'%s'"%p, mtype="F"))
A(M(904,"销售订单",8,40,"sales","erp/sales/index", mtype="C", icon="shopping-cart"))
BTN(904,4220,[("销售订单查询","erp.sales:query"),("销售订户新增","erp.sales:add"),("销售订单修改","erp.sales:edit"),("销售订单删除","erp.sales:remove"),("销售订单导出","erp.sales:export")])
A(M(905,"生产计划",8,50,"plan","erp/plan/index", mtype="C", icon="date"))
BTN(905,4230,[("生产计划查询","erp.plan:query"),("生产计划新增","erp.plan:add"),("生产计划修改","erp.plan:edit"),("生产计划删除","erp.plan:remove"),("生产计划导出","erp.plan:export")])
A(M(906,"后道生产1",8,55,"post","erp/post/index", mtype="C", icon="tool"))
A(M(907,"采购订单",8,60,"purchase","erp/purchase/index", mtype="C", icon="shopping"))
BTN(907,4240,[("采购单查询","erp.purchase:query"),("采购单新增","erp.purchase:add"),("采购单修改","erp.purchase:edit"),("采购单删除","erp.purchase:remove"),("采购单导出","erp.purchase:export")])
A(M(908,"打样总览",8,70,"overview","erp/overview/index", mtype="C", icon="eye"))

all_ids=[6,700,701,702,
         7,800,801,802,803,804,805,806,807,
         8,900,901,902,903,904,905,906,907,908,
         4000,4001,4002,
        ]+list(range(4100,4105))+list(range(4110,4115))+list(range(4120,4125))+list(range(4130,4135))+list(range(4140,4145)) \
        +list(range(4150,4155))+list(range(4200,4205))+list(range(4210,4216))+list(range(4220,4225)) \
        +list(range(4230,4235))+list(range(4240,4245))
A("\nDELETE FROM sys_role_menu WHERE role_id=1 AND menu_id IN (%s);" % ",".join(map(str,all_ids)))
for mid in all_ids:
    A("INSERT IGNORE INTO sys_role_menu(role_id,menu_id) VALUES(1,%d);" % mid)

full_sql="\n".join(sqls)
proc=subprocess.Popen([MYSQL,"-u","root","--default-character-set=utf8mb4","ry_vue"],stdin=subprocess.PIPE,stdout=subprocess.PIPE,stderr=subprocess.PIPE)
out,err=proc.communicate(input=full_sql.encode("utf-8"))
print("OK" if proc.returncode==0 else "ERR:%s"%err.decode("utf-8")[:500])

v="SELECT menu_id,menu_name,parent_id,order_num,CASE menu_type WHEN 'M' THEN '[M]' WHEN 'C' THEN '[C]' ELSE '[F]' END AS T,COALESCE(perms,'') AS P FROM sys_menu WHERE menu_id IN(6,7,8) OR parent_id IN(6,7,8) ORDER BY CASE WHEN parent_id=0 THEN menu_id*10 ELSE parent_id*100+order_num END;"
proc2=subprocess.Popen([MYSQL,"-u","root","--default-character-set=utf8mb4","ry_vue"],stdin=subprocess.PIPE,stdout=subprocess.PIPE,stderr=subprocess.PIPE)
out2,_=proc2.communicate(input=v.encode("utf-8"))
lines=out2.decode("utf-8").strip().split("\n")
print("\n=== %d records ===" % (len(lines)-1))
for l in lines: print(l)
