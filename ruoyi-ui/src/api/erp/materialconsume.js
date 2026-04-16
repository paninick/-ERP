import request from '@/utils/request'

// 查询物料消耗记录列表
export function listMaterialconsume(query) {
  return request({
    url: '/erp/materialconsume/list',
    method: 'get',
    params: query
  })
}

// 查询物料消耗记录详细
export function getMaterialconsume(id) {
  return request({
    url: '/erp/materialconsume/' + id,
    method: 'get'
  })
}

// 新增物料消耗记录
export function addMaterialconsume(data) {
  return request({
    url: '/erp/materialconsume',
    method: 'post',
    data: data
  })
}

// 修改物料消耗记录
export function updateMaterialconsume(data) {
  return request({
    url: '/erp/materialconsume',
    method: 'put',
    data: data
  })
}

// 删除物料消耗记录
export function delMaterialconsume(id) {
  return request({
    url: '/erp/materialconsume/' + id,
    method: 'delete'
  })
}

// 导出物料消耗记录
export function exportMaterialconsume(query) {
  return request({
    url: '/erp/materialconsume/export',
    method: 'post',
    params: query,
    responseType: 'blob'
  })
}

// 计算限额
export function calculateLimit(bomQty, standardLossRate) {
  return request({
    url: '/erp/materialconsume/calculateLimit',
    method: 'get',
    params: { bomQty, standardLossRate }
  })
}

// 根据生产计划查询
export function listByProducePlan(producePlanId) {
  return request({
    url: '/erp/materialconsume/listByPlan/' + producePlanId,
    method: 'get'
  })
}
