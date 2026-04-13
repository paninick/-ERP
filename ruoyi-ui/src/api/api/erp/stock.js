import request from '@/utils/request'

// 查询入库明细列表
export function listStock(query) {
  return request({
    url: '/erp/stock/list',
    method: 'get',
    params: query
  })
}

// 查询入库明细详细
export function getStock(id) {
  return request({
    url: '/erp/stock/' + id,
    method: 'get'
  })
}

// 新增入库明细
export function addStock(data) {
  return request({
    url: '/erp/stock',
    method: 'post',
    data: data
  })
}

// 修改入库明细
export function updateStock(data) {
  return request({
    url: '/erp/stock',
    method: 'put',
    data: data
  })
}

// 删除入库明细
export function delStock(id) {
  return request({
    url: '/erp/stock/' + id,
    method: 'delete'
  })
}
