import request from '@/utils/request'

// 查询客户尺寸细节列表
export function listCustomer(query) {
  return request({
    url: '/erp/customer/list',
    method: 'get',
    params: query
  })
}

// 查询客户尺寸细节详细
export function getCustomer(id) {
  return request({
    url: '/erp/customer/' + id,
    method: 'get'
  })
}

// 新增客户尺寸细节
export function addCustomer(data) {
  return request({
    url: '/erp/customer',
    method: 'post',
    data: data
  })
}

// 修改客户尺寸细节
export function updateCustomer(data) {
  return request({
    url: '/erp/customer',
    method: 'put',
    data: data
  })
}

// 删除客户尺寸细节
export function delCustomer(id) {
  return request({
    url: '/erp/customer/' + id,
    method: 'delete'
  })
}
