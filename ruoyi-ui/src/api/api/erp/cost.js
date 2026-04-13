import request from '@/utils/request'

// 查询工艺书单耗列表
export function listCost(query) {
  return request({
    url: '/erp/cost/list',
    method: 'get',
    params: query
  })
}

// 查询工艺书单耗详细
export function getCost(id) {
  return request({
    url: '/erp/cost/' + id,
    method: 'get'
  })
}

// 新增工艺书单耗
export function addCost(data) {
  return request({
    url: '/erp/cost',
    method: 'post',
    data: data
  })
}

// 修改工艺书单耗
export function updateCost(data) {
  return request({
    url: '/erp/cost',
    method: 'put',
    data: data
  })
}

// 删除工艺书单耗
export function delCost(id) {
  return request({
    url: '/erp/cost/' + id,
    method: 'delete'
  })
}
