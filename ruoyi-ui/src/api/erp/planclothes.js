import request from '@/utils/request'

// 查询生产计划衣服明细列表
export function listPlanClothes(query) {
  return request({
    url: '/erp/planclothes/list',
    method: 'get',
    params: query
  })
}

// 查询生产计划衣服明细详细
export function getPlanClothes(id) {
  return request({
    url: '/erp/planclothes/' + id,
    method: 'get'
  })
}

// 新增生产计划衣服明细
export function addPlanClothes(data) {
  return request({
    url: '/erp/planclothes',
    method: 'post',
    data: data
  })
}

// 修改生产计划衣服明细
export function updatePlanClothes(data) {
  return request({
    url: '/erp/planclothes',
    method: 'put',
    data: data
  })
}

// 删除生产计划衣服明细
export function delPlanClothes(id) {
  return request({
    url: '/erp/planclothes/' + id,
    method: 'delete'
  })
}

// Backward-compatible aliases for older imports
export const listPlanclothes = listPlanClothes
export const getPlanclothes = getPlanClothes
export const addPlanclothes = addPlanClothes
export const updatePlanclothes = updatePlanClothes
export const delPlanclothes = delPlanClothes
