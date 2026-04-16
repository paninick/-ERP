import request from '@/utils/request'

// 查询次品记录列表
export function listDefect(query) {
  return request({
    url: '/erp/defect/list',
    method: 'get',
    params: query
  })
}

// 查询次品记录详细
export function getDefect(id) {
  return request({
    url: '/erp/defect/' + id,
    method: 'get'
  })
}

// 新增次品记录
export function addDefect(data) {
  return request({
    url: '/erp/defect',
    method: 'post',
    data: data
  })
}

// 修改次品记录
export function updateDefect(data) {
  return request({
    url: '/erp/defect',
    method: 'put',
    data: data
  })
}

// 删除次品记录
export function delDefect(id) {
  return request({
    url: '/erp/defect/' + id,
    method: 'delete'
  })
}

// 导出次品记录
export function exportDefect(query) {
  return request({
    url: '/erp/defect/export',
    method: 'post',
    params: query,
    responseType: 'blob'
  })
}
