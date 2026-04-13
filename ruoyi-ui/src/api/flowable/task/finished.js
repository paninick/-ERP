import request from '@/utils/request'

// 查询已办任务列表
export function listFinished(query) {
  return request({
    url: '/flowable/task/finished/list',
    method: 'get',
    params: query
  })
}
