import request from '@/utils/request'

// 查询标准色卡列表
export function listStandardColor(query) {
  return request({
    url: '/erp/standardColor/list',
    method: 'get',
    params: query
  })
}

// 查询标准色卡详细
export function getStandardColor(id) {
  return request({
    url: '/erp/standardColor/' + id,
    method: 'get'
  })
}

// 新增标准色卡
export function addStandardColor(data) {
  return request({
    url: '/erp/standardColor',
    method: 'post',
    data: data
  })
}

// 修改标准色卡
export function updateStandardColor(data) {
  return request({
    url: '/erp/standardColor',
    method: 'put',
    data: data
  })
}

// 删除标准色卡
export function delStandardColor(id) {
  return request({
    url: '/erp/standardColor/' + id,
    method: 'delete'
  })
}

// 导出标准色卡
export function exportStandardColor(query) {
  return request({
    url: '/erp/standardColor/export',
    method: 'post',
    params: query
  })
}
