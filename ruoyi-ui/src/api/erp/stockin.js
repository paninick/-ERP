import request from '@/utils/request'

// 查询入库单列表
export function listStockin(query) {
  return request({
    url: '/erp/stockIn/list',
    method: 'get',
    params: query
  })
}

// 查询入库单详细
export function getStockin(id) {
  return request({
    url: '/erp/stockIn/' + id,
    method: 'get'
  })
}

// 新增入库单
export function addStockin(data) {
  return request({
    url: '/erp/stockIn',
    method: 'post',
    data: data
  })
}

// 修改入库单
export function updateStockin(data) {
  return request({
    url: '/erp/stockIn',
    method: 'put',
    data: data
  })
}

// 删除入库单
export function delStockin(id) {
  return request({
    url: '/erp/stockIn/' + id,
    method: 'delete'
  })
}

// 导出入库单
export function exportStockin(query) {
  return request({
    url: '/erp/stockIn/export',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
