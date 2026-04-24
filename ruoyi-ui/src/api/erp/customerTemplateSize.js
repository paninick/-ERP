import request from '@/utils/request'

// 查询客户模板尺寸列表
export function listCustomerTemplateSize(query) {
  return request({
    url: '/erp/customerTemplate/size/list',
    method: 'get',
    params: query
  })
}

// 查询客户模板尺寸详细
export function getCustomerTemplateSize(id) {
  return request({
    url: '/erp/customerTemplate/size/' + id,
    method: 'get'
  })
}

// 新增客户模板尺寸
export function addCustomerTemplateSize(data) {
  return request({
    url: '/erp/customerTemplate/size',
    method: 'post',
    data: data
  })
}

// 修改客户模板尺寸
export function updateCustomerTemplateSize(data) {
  return request({
    url: '/erp/customerTemplate/size',
    method: 'put',
    data: data
  })
}

// 删除客户模板尺寸
export function delCustomerTemplateSize(id) {
  return request({
    url: '/erp/customerTemplate/size/' + id,
    method: 'delete'
  })
}

// 导出客户模板尺寸
export function exportCustomerTemplateSize(query) {
  return request({
    url: '/erp/customerTemplate/size/export',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
