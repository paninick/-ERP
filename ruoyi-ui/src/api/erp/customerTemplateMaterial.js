import request from '@/utils/request'

// 查询客户模板材料列表
export function listCustomerTemplateMaterial(query) {
  return request({
    url: '/erp/customerTemplateMaterial/list',
    method: 'get',
    params: query
  })
}

// 查询客户模板材料详细
export function getCustomerTemplateMaterial(id) {
  return request({
    url: '/erp/customerTemplateMaterial/' + id,
    method: 'get'
  })
}

// 新增客户模板材料
export function addCustomerTemplateMaterial(data) {
  return request({
    url: '/erp/customerTemplateMaterial',
    method: 'post',
    data: data
  })
}

// 修改客户模板材料
export function updateCustomerTemplateMaterial(data) {
  return request({
    url: '/erp/customerTemplateMaterial',
    method: 'put',
    data: data
  })
}

// 删除客户模板材料
export function delCustomerTemplateMaterial(id) {
  return request({
    url: '/erp/customerTemplateMaterial/' + id,
    method: 'delete'
  })
}

// 导出客户模板材料
export function exportCustomerTemplateMaterial(query) {
  return request({
    url: '/erp/customerTemplateMaterial/export',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
