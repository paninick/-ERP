import request from '@/utils/request'

// 查询待办任务列表
export function listTodo(query) {
  return request({
    url: '/flowable/task/todo/list',
    method: 'get',
    params: query
  })
}

export function approveTask(data) {
  return request({
    url: '/flowable/task/todo/approve',
    method: 'post',
    data: data
  })
}

export function rejectTask(data) {
  return request({
    url: '/flowable/task/todo/reject',
    method: 'post',
    data: data
  })
}
