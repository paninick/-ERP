import request from '@/utils/request'

// 查询销售订单明细列表
export function listSalesitem(query) {
  return request({
    url: '/erp/salesitem/list',
    method: 'get',
    params: query
  })
}

// 查询销售订单明细详细
export function getSalesitem(id) {
  return request({
    url: '/erp/salesitem/' + id,
    method: 'get'
  })
}

// 新增销售订单明细
export function addSalesitem(data) {
  return request({
    url: '/erp/salesitem',
    method: 'post',
    data: data
  })
}

// 修改销售订单明细
export function updateSalesitem(data) {
  return request({
    url: '/erp/salesitem',
    method: 'put',
    data: data
  })
}

// 删除销售订单明细
export function delSalesitem(id) {
  return request({
    url: '/erp/salesitem/' + id,
    method: 'delete'
  })
}
