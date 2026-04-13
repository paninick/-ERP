import request from '@/utils/request'

// 查询客户模板列表
export function listCustomerTemplate(query) {
  return request({
    url: '/erp/customerTemplate/list',
    method: 'get',
    params: query
  })
}

// 查询客户模板详细
export function getCustomerTemplate(id) {
  return request({
    url: '/erp/customerTemplate/' + id,
    method: 'get'
  })
}

// 新增客户模板
export function addCustomerTemplate(data) {
  return request({
    url: '/erp/customerTemplate',
    method: 'post',
    data: data
  })
}

// 修改客户模板
export function updateCustomerTemplate(data) {
  return request({
    url: '/erp/customerTemplate',
    method: 'put',
    data: data
  })
}

// 删除客户模板
export function delCustomerTemplate(id) {
  return request({
    url: '/erp/customerTemplate/' + id,
    method: 'delete'
  })
}

// 导出客户模板
export function exportCustomerTemplate(query) {
  return request({
    url: '/erp/customerTemplate/export',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
