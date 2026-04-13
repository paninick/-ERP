import request from '@/utils/request'

// 查询仓库管理列表
export function listWarehousearea(query) {
  return request({
    url: '/erp/warehousearea/list',
    method: 'get',
    params: query
  })
}

// 查询仓库管理详细
export function getWarehousearea(id) {
  return request({
    url: '/erp/warehousearea/' + id,
    method: 'get'
  })
}

// 新增仓库管理
export function addWarehousearea(data) {
  return request({
    url: '/erp/warehousearea',
    method: 'post',
    data: data
  })
}

// 修改仓库管理
export function updateWarehousearea(data) {
  return request({
    url: '/erp/warehousearea',
    method: 'put',
    data: data
  })
}

// 删除仓库管理
export function delWarehousearea(id) {
  return request({
    url: '/erp/warehousearea/' + id,
    method: 'delete'
  })
}
