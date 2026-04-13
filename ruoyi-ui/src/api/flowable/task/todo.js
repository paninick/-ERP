import request from '@/utils/request'

// 查询待办任务列表
export function listTodo(query) {
  return request({
    url: '/flowable/task/todo/list',
    method: 'get',
    params: query
  })
}
