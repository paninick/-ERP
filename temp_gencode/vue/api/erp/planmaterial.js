import request from '@/utils/request'

// 查询生产计划物料明细列表
export function listPlanmaterial(query) {
  return request({
    url: '/erp/planmaterial/list',
    method: 'get',
    params: query
  })
}

// 查询生产计划物料明细详细
export function getPlanmaterial(id) {
  return request({
    url: '/erp/planmaterial/' + id,
    method: 'get'
  })
}

// 新增生产计划物料明细
export function addPlanmaterial(data) {
  return request({
    url: '/erp/planmaterial',
    method: 'post',
    data: data
  })
}

// 修改生产计划物料明细
export function updatePlanmaterial(data) {
  return request({
    url: '/erp/planmaterial',
    method: 'put',
    data: data
  })
}

// 删除生产计划物料明细
export function delPlanmaterial(id) {
  return request({
    url: '/erp/planmaterial/' + id,
    method: 'delete'
  })
}
