import request from '@/utils/request'

// 查询大货核版列表
export function listCheck(query) {
  return request({
    url: '/erp/check/list',
    method: 'get',
    params: query
  })
}

// 查询大货核版详细
export function getCheck(id) {
  return request({
    url: '/erp/check/' + id,
    method: 'get'
  })
}

// 新增大货核版
export function addCheck(data) {
  return request({
    url: '/erp/check',
    method: 'post',
    data: data
  })
}

// 修改大货核版
export function updateCheck(data) {
  return request({
    url: '/erp/check',
    method: 'put',
    data: data
  })
}

// 删除大货核版
export function delCheck(id) {
  return request({
    url: '/erp/check/' + id,
    method: 'delete'
  })
}

// 导出大货核版
export function exportCheck(query) {
  return request({
    url: '/erp/check/export',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}

// 审批大货核版
export function approveCheck(data) {
  return request({
    url: '/erp/check/approve',
    method: 'post',
    data: data
  })
}
