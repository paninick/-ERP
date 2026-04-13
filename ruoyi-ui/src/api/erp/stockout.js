import request from '@/utils/request'

// 查询出库单列表
export function listStockout(query) {
  return request({
    url: '/erp/stockOut/list',
    method: 'get',
    params: query
  })
}

// 查询出库单详细
export function getStockout(id) {
  return request({
    url: '/erp/stockOut/' + id,
    method: 'get'
  })
}

// 新增出库单
export function addStockout(data) {
  return request({
    url: '/erp/stockOut',
    method: 'post',
    data: data
  })
}

// 修改出库单
export function updateStockout(data) {
  return request({
    url: '/erp/stockOut',
    method: 'put',
    data: data
  })
}

// 删除出库单
export function delStockout(id) {
  return request({
    url: '/erp/stockOut/' + id,
    method: 'delete'
  })
}

// 导出出库单
export function exportStockout(query) {
  return request({
    url: '/erp/stockOut/export',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
