import request from '@/utils/request'

// 查询流程定义列表
export function listDefinition(query) {
  return request({
    url: '/flowable/definition/list',
    method: 'get',
    params: query
  })
}

// 查询流程定义详细
export function getDefinition(id) {
  return request({
    url: '/flowable/definition/' + id,
    method: 'get'
  })
}

// 新增流程定义
export function addDefinition(data) {
  return request({
    url: '/flowable/definition',
    method: 'post',
    data: data
  })
}

// 修改流程定义
export function updateDefinition(data) {
  return request({
    url: '/flowable/definition',
    method: 'put',
    data: data
  })
}

// 删除流程定义
export function delDefinition(id) {
  return request({
    url: '/flowable/definition/' + id,
    method: 'delete'
  })
}

// 激活流程定义
export function activateDefinition(id) {
  return request({
    url: '/flowable/definition/activate/' + id,
    method: 'put'
  })
}

// 挂起流程定义
export function suspendDefinition(id) {
  return request({
    url: '/flowable/definition/suspend/' + id,
    method: 'put'
  })
}

// 导出流程定义
export function exportDefinition(query) {
  return request({
    url: '/flowable/definition/export',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
