import request from '@/utils/request'

// 查询业务系统-客户列表
export function listCustomer(query) {
  return request({
    url: '/erp/customer/list',
    method: 'get',
    params: query
  })
}

// 查询业务系统-客户详细
export function getCustomer(id) {
  return request({
    url: '/erp/customer/' + id,
    method: 'get'
  })
}

// 新增业务系统-客户
export function addCustomer(data) {
  return request({
    url: '/erp/customer',
    method: 'post',
    data: data
  })
}

// 修改业务系统-客户
export function updateCustomer(data) {
  return request({
    url: '/erp/customer',
    method: 'put',
    data: data
  })
}

// 删除业务系统-客户
export function delCustomer(id) {
  return request({
    url: '/erp/customer/' + id,
    method: 'delete'
  })
}
