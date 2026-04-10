import request from '@/utils/request'

// 查询仓位管理列表
export function listWarehouselocation(query) {
  return request({
    url: '/erp/warehouselocation/list',
    method: 'get',
    params: query
  })
}

// 查询仓位管理详细
export function getWarehouselocation(id) {
  return request({
    url: '/erp/warehouselocation/' + id,
    method: 'get'
  })
}

// 新增仓位管理
export function addWarehouselocation(data) {
  return request({
    url: '/erp/warehouselocation',
    method: 'post',
    data: data
  })
}

// 修改仓位管理
export function updateWarehouselocation(data) {
  return request({
    url: '/erp/warehouselocation',
    method: 'put',
    data: data
  })
}

// 删除仓位管理
export function delWarehouselocation(id) {
  return request({
    url: '/erp/warehouselocation/' + id,
    method: 'delete'
  })
}
