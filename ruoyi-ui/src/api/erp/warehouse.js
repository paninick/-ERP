import request from '@/utils/request'

// 查询库区管理列表
export function listWarehouse(query) {
  return request({
    url: '/erp/warehouse/list',
    method: 'get',
    params: query
  })
}

// 查询库区管理详细
export function getWarehouse(id) {
  return request({
    url: '/erp/warehouse/' + id,
    method: 'get'
  })
}

// 新增库区管理
export function addWarehouse(data) {
  return request({
    url: '/erp/warehouse',
    method: 'post',
    data: data
  })
}

// 修改库区管理
export function updateWarehouse(data) {
  return request({
    url: '/erp/warehouse',
    method: 'put',
    data: data
  })
}

// 删除库区管理
export function delWarehouse(id) {
  return request({
    url: '/erp/warehouse/' + id,
    method: 'delete'
  })
}
