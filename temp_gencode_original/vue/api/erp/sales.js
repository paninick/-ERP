import request from '@/utils/request'

// 查询销售订单列表
export function listSales(query) {
  return request({
    url: '/erp/sales/list',
    method: 'get',
    params: query
  })
}

// 查询销售订单详细
export function getSales(id) {
  return request({
    url: '/erp/sales/' + id,
    method: 'get'
  })
}

// 新增销售订单
export function addSales(data) {
  return request({
    url: '/erp/sales',
    method: 'post',
    data: data
  })
}

// 修改销售订单
export function updateSales(data) {
  return request({
    url: '/erp/sales',
    method: 'put',
    data: data
  })
}

// 删除销售订单
export function delSales(id) {
  return request({
    url: '/erp/sales/' + id,
    method: 'delete'
  })
}
