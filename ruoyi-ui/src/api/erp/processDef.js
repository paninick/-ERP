import request from '@/utils/request'

// 查询工序定义列表
export function listProcessDef(query) {
  return request({
    url: '/erp/processDef/list',
    method: 'get',
    params: query
  })
}

// 查询工序定义详细
export function getProcessDef(id) {
  return request({
    url: '/erp/processDef/' + id,
    method: 'get'
  })
}

// 新增工序定义
export function addProcessDef(data) {
  return request({
    url: '/erp/processDef',
    method: 'post',
    data: data
  })
}

// 修改工序定义
export function updateProcessDef(data) {
  return request({
    url: '/erp/processDef',
    method: 'put',
    data: data
  })
}

// 删除工序定义
export function delProcessDef(id) {
  return request({
    url: '/erp/processDef/' + id,
    method: 'delete'
  })
}

// 导出工序定义
export function exportProcessDef(query) {
  return request({
    url: '/erp/processDef/export',
    method: 'post',
    params: query
  })
}
