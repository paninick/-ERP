import request from '@/utils/request'

// 查询外协加工单列表
export function listOutsource(query) {
  return request({
    url: '/erp/outsource/list',
    method: 'get',
    params: query
  })
}

// 查询外协加工单详细
export function getOutsource(id) {
  return request({
    url: '/erp/outsource/' + id,
    method: 'get'
  })
}

// 新增外协加工单
export function addOutsource(data) {
  return request({
    url: '/erp/outsource',
    method: 'post',
    data: data
  })
}

// 修改外协加工单
export function updateOutsource(data) {
  return request({
    url: '/erp/outsource',
    method: 'put',
    data: data
  })
}

// 删除外协加工单
export function delOutsource(id) {
  return request({
    url: '/erp/outsource/' + id,
    method: 'delete'
  })
}

// 导出外协加工单
export function exportOutsource(query) {
  return request({
    url: '/erp/outsource/export',
    method: 'post',
    params: query,
    responseType: 'blob'
  })
}
