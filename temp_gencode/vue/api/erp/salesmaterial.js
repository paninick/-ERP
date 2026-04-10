import request from '@/utils/request'

// 查询大货订单物料明细列表
export function listSalesmaterial(query) {
  return request({
    url: '/erp/salesmaterial/list',
    method: 'get',
    params: query
  })
}

// 查询大货订单物料明细详细
export function getSalesmaterial(id) {
  return request({
    url: '/erp/salesmaterial/' + id,
    method: 'get'
  })
}

// 新增大货订单物料明细
export function addSalesmaterial(data) {
  return request({
    url: '/erp/salesmaterial',
    method: 'post',
    data: data
  })
}

// 修改大货订单物料明细
export function updateSalesmaterial(data) {
  return request({
    url: '/erp/salesmaterial',
    method: 'put',
    data: data
  })
}

// 删除大货订单物料明细
export function delSalesmaterial(id) {
  return request({
    url: '/erp/salesmaterial/' + id,
    method: 'delete'
  })
}
