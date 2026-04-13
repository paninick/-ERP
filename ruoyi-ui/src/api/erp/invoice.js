import request from '@/utils/request'

// 查询公司开票信息列表
export function listInvoice(query) {
  return request({
    url: '/erp/invoice/list',
    method: 'get',
    params: query
  })
}

// 查询公司开票信息详细
export function getInvoice(id) {
  return request({
    url: '/erp/invoice/' + id,
    method: 'get'
  })
}

// 新增公司开票信息
export function addInvoice(data) {
  return request({
    url: '/erp/invoice',
    method: 'post',
    data: data
  })
}

// 修改公司开票信息
export function updateInvoice(data) {
  return request({
    url: '/erp/invoice',
    method: 'put',
    data: data
  })
}

// 删除公司开票信息
export function delInvoice(id) {
  return request({
    url: '/erp/invoice/' + id,
    method: 'delete'
  })
}
