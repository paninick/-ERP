import request from '@/utils/request'

// 查询生产计划衣服明细列表
export function listPlanclothes(query) {
  return request({
    url: '/erp/planclothes/list',
    method: 'get',
    params: query
  })
}

// 查询生产计划衣服明细详细
export function getPlanclothes(id) {
  return request({
    url: '/erp/planclothes/' + id,
    method: 'get'
  })
}

// 新增生产计划衣服明细
export function addPlanclothes(data) {
  return request({
    url: '/erp/planclothes',
    method: 'post',
    data: data
  })
}

// 修改生产计划衣服明细
export function updatePlanclothes(data) {
  return request({
    url: '/erp/planclothes',
    method: 'put',
    data: data
  })
}

// 删除生产计划衣服明细
export function delPlanclothes(id) {
  return request({
    url: '/erp/planclothes/' + id,
    method: 'delete'
  })
}
