import request from '@/utils/request'

// 查询计件工资汇总列表
export function listPiecewage(query) {
  return request({
    url: '/erp/piecewage/list',
    method: 'get',
    params: query
  })
}

// 查询计件工资汇总详细
export function getPiecewage(id) {
  return request({
    url: '/erp/piecewage/' + id,
    method: 'get'
  })
}

// 新增计件工资汇总
export function addPiecewage(data) {
  return request({
    url: '/erp/piecewage',
    method: 'post',
    data: data
  })
}

// 修改计件工资汇总
export function updatePiecewage(data) {
  return request({
    url: '/erp/piecewage',
    method: 'put',
    data: data
  })
}

// 删除计件工资汇总
export function delPiecewage(id) {
  return request({
    url: '/erp/piecewage/' + id,
    method: 'delete'
  })
}

// 导出计件工资汇总
export function exportPiecewage(query) {
  return request({
    url: '/erp/piecewage/export',
    method: 'post',
    params: query,
    responseType: 'blob'
  })
}
