import request from '@/utils/request'

// 查询自动化任务列表
export function listAutomation(query) {
  return request({
    url: '/erp/automation/list',
    method: 'get',
    params: query
  })
}

// 查询自动化任务详细
export function getAutomation(taskId) {
  return request({
    url: '/erp/automation/' + taskId,
    method: 'get'
  })
}

// 新增自动化任务
export function addAutomation(data) {
  return request({
    url: '/erp/automation',
    method: 'post',
    data: data
  })
}

// 修改自动化任务
export function updateAutomation(data) {
  return request({
    url: '/erp/automation',
    method: 'put',
    data: data
  })
}

// 删除自动化任务
export function delAutomation(taskId) {
  return request({
    url: '/erp/automation/' + taskId,
    method: 'delete'
  })
}

// 运行自动化任务
export function runAutomation(taskId) {
  return request({
    url: '/erp/automation/run/' + taskId,
    method: 'put'
  })
}

// 暂停自动化任务
export function pauseAutomation(taskId) {
  return request({
    url: '/erp/automation/pause/' + taskId,
    method: 'put'
  })
}

// 导出自动化任务
export function exportAutomation(query) {
  return request({
    url: '/erp/automation/export',
    method: 'get',
    params: query
  })
}
