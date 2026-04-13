import os

BASE = r'd:\erp\RuoYi-Vue\ruoyi-ui\src\api'

def make_api(module, name, url_prefix):
    path = os.path.join(BASE, module)
    os.makedirs(path, exist_ok=True)
    content = f"""import request from '@/utils/request'

// 查询{name}列表
export function list{module.capitalize()}(query) {{
  return request({{
    url: '/{url_prefix}/list',
    method: 'get',
    params: query
  }})
}}

// 查询{name}详细
export function get{module.capitalize()}(id) {{
  return request({{
    url: '/{url_prefix}/' + id,
    method: 'get'
  }})
}}

// 新增{name}
export function add{module.capitalize()}(data) {{
  return request({{
    url: '/{url_prefix}',
    method: 'post',
    data: data
  }})
}}

// 修改{name}
export function update{module.capitalize()}(data) {{
  return request({{
    url: '/{url_prefix}',
    method: 'put',
    data: data
  }})
}}

// 删除{name}
export function del{module.capitalize()}(id) {{
  return request({{
    url: '/{url_prefix}/' + id,
    method: 'delete'
  }})
}}

// 导出{name}
export function export{module.capitalize()}(query) {{
  return request({{
    url: '/{url_prefix}/export',
    method: 'get',
    params: query,
    responseType: 'blob'
  }})
}}
"""
    filepath = os.path.join(path, f'{module}.js')
    with open(filepath, 'w', encoding='utf-8') as f:
        f.write(content)
    print(f'  {filepath}')

print('=== 创建 ERP API 文件 ===')
erp_modules = [
    ('customer','客户管理','erp/customer'),
    ('supplier','供应商管理','erp/supplier'),
    ('material','主料管理','erp/material'),
    ('auxiliary','辅料管理','erp/auxiliary'),
    ('customerTemplate','客户模板','erp/customerTemplate'),
    ('warehouse','仓库管理','erp/warehouse'),
    ('warehousearea','库区管理','erp/warehousearea'),
    ('notice','打样通知','erp/notice'),
    ('bom','样衣BOM','erp/bom'),
    ('check','大货核版','erp/check'),
    ('tech','工艺指示书','erp/tech'),
    ('sales','销售订单','erp/sales'),
    ('plan','生产计划','erp/plan'),
    ('post','后道生产1','erp/post'),
    ('purchase','采购订单','erp/purchase'),
    ('stockin','入库单','erp/stockin'),
    ('stockout','出库单','erp/stockout'),
    ('stock','库存查询','erp/stock'),
]
for m,n,u in erp_modules:
    make_api(m, n, u)

print('\n=== 创建 Flowable API 文件 ===')
flowable_modules = [
    ('definition','流程定义','flowable/definition'),
    ('form','表单配置','flowable/form'),
    ('expression','流程表达式','flowable/expression'),
    ('listener','流程监听','flowable/listener'),
    ('task','任务管理','flowable/task'),
    ('myProcess','已发任务','flowable/task/myProcess'),
    ('todo','待办任务','flowable/task/todo'),
    ('finished','已办任务','flowable/task/finished'),
]

for m,n,u in flowable_modules:
    if m in ('myProcess','todo','finished'):
        path = os.path.join(BASE, 'flowable', 'task')
        os.makedirs(path, exist_ok=True)
        content = f"""import request from '@/utils/request'

// 查询{n}列表
export function list{m.capitalize()}(query) {{
  return request({{
    url: '/{u}/list',
    method: 'get',
    params: query
  }})
}}
"""
        filepath = os.path.join(path, f'{m}.js')
        with open(filepath, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f'  {filepath}')
    else:
        make_api(m, n, u)

# overview is special - just a dashboard
path = os.path.join(BASE, 'erp')
with open(os.path.join(path, 'overview.js'), 'w', encoding='utf-8') as f:
    f.write("""import request from '@/utils/request'

// 获取打样总览统计
export function getOverviewStats() {
  return request({
    url: '/erp/overview/stats',
    method: 'get'
  })
}
""")
print(f'  {os.path.join(path, "overview.js")}')

print('\n✅ 所有API文件创建完成!')
