import request from '@/utils/request'

// 查询任务管理列表
export function listTask(query) {
  return request({
    url: '/flowable/task/list',
    method: 'get',
    params: query
  })
}

// 查询任务管理详细
export function getTask(id) {
  return request({
    url: '/flowable/task/' + id,
    method: 'get'
  })
}

// 新增任务管理
export function addTask(data) {
  return request({
    url: '/flowable/task',
    method: 'post',
    data: data
  })
}

// 修改任务管理
export function updateTask(data) {
  return request({
    url: '/flowable/task',
    method: 'put',
    data: data
  })
}

// 删除任务管理
export function delTask(id) {
  return request({
    url: '/flowable/task/' + id,
    method: 'delete'
  })
}

// 导出任务管理
export function exportTask(query) {
  return request({
    url: '/flowable/task/export',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
