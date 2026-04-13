import request from '@/utils/request'

// 查询已发任务列表
export function listMyProcess(query) {
  return request({
    url: '/flowable/task/myProcess/list',
    method: 'get',
    params: query
  })
}
