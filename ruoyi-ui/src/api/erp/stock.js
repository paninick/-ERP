import request from '@/utils/request'

// 查询库存列表
export function listStock(query) {
  return request({
    url: '/erp/inventory/list',
    method: 'get',
    params: query
  })
}

// 查询库存详细
export function getStock(id) {
  return request({
    url: '/erp/inventory/' + id,
    method: 'get'
  })
}
