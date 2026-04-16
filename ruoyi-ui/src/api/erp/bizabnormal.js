import request from '@/utils/request'

// 查询业务异常池列表
export function listBizabnormal(query) {
  return request({
    url: '/erp/bizabnormal/list',
    method: 'get',
    params: query
  })
}

// 查询业务异常池详细
export function getBizabnormal(id) {
  return request({
    url: '/erp/bizabnormal/' + id,
    method: 'get'
  })
}

// 新增业务异常池
export function addBizabnormal(data) {
  return request({
    url: '/erp/bizabnormal',
    method: 'post',
    data: data
  })
}

// 修改业务异常池
export function updateBizabnormal(data) {
  return request({
    url: '/erp/bizabnormal',
    method: 'put',
    data: data
  })
}

// 删除业务异常池
export function delBizabnormal(id) {
  return request({
    url: '/erp/bizabnormal/' + id,
    method: 'delete'
  })
}

// 导出业务异常池
export function exportBizabnormal(query) {
  return request({
    url: '/erp/bizabnormal/export',
    method: 'post',
    params: query,
    responseType: 'blob'
  })
}

// 检查业务是否存在未处理异常
export function checkUnhandled(bizType, bizId) {
  return request({
    url: '/erp/bizabnormal/check/' + bizType + '/' + bizId,
    method: 'get'
  })
}
