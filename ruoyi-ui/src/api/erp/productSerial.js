import request from '@/utils/request'

// 查询单件流水号列表
export function listProductSerial(query) {
  return request({
    url: '/erp/productSerial/list',
    method: 'get',
    params: query
  })
}

// 查询单件流水号详细
export function getProductSerial(id) {
  return request({
    url: '/erp/productSerial/' + id,
    method: 'get'
  })
}

// 根据工票ID查询单件流水号列表
export function listProductSerialByJob(jobId) {
  return request({
    url: '/erp/productSerial/listByJob/' + jobId,
    method: 'get'
  })
}

// 扫码查询单件流水号
export function scanProductSerial(serialNo) {
  return request({
    url: '/erp/productSerial/scan/' + serialNo,
    method: 'get'
  })
}

// 新增单件流水号
export function addProductSerial(data) {
  return request({
    url: '/erp/productSerial',
    method: 'post',
    data: data
  })
}

// 批量新增单件流水号
export function batchAddProductSerial(data) {
  return request({
    url: '/erp/productSerial/batchAdd',
    method: 'post',
    data: data
  })
}

// 修改单件流水号
export function updateProductSerial(data) {
  return request({
    url: '/erp/productSerial',
    method: 'put',
    data: data
  })
}

// 删除单件流水号
export function delProductSerial(id) {
  return request({
    url: '/erp/productSerial/' + id,
    method: 'delete'
  })
}

// 导出单件流水号
export function exportProductSerial(query) {
  return request({
    url: '/erp/productSerial/export',
    method: 'post',
    params: query
  })
}
