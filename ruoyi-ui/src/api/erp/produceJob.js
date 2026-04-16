import request from '@/utils/request'

// 查询生产工票列表
export function listProduceJob(query) {
  return request({
    url: '/erp/produceJob/list',
    method: 'get',
    params: query
  })
}

// 查询生产工票详细
export function getProduceJob(id) {
  return request({
    url: '/erp/produceJob/' + id,
    method: 'get'
  })
}

// 根据生产计划ID查询工票列表
export function listProduceJobByProducePlan(producePlanId) {
  return request({
    url: '/erp/produceJob/listByProducePlan/' + producePlanId,
    method: 'get'
  })
}

// 获取生产计划统计
export function getProduceJobStatistics(producePlanId) {
  return request({
    url: '/erp/produceJob/statistics/' + producePlanId,
    method: 'get'
  })
}

// 新增生产工票
export function addProduceJob(data) {
  return request({
    url: '/erp/produceJob',
    method: 'post',
    data: data
  })
}

// 批量新增生产工票
export function batchAddProduceJob(data) {
  return request({
    url: '/erp/produceJob/batchAdd',
    method: 'post',
    data: data
  })
}

// 修改生产工票
export function updateProduceJob(data) {
  return request({
    url: '/erp/produceJob',
    method: 'put',
    data: data
  })
}

// 删除生产工票
export function delProduceJob(id) {
  return request({
    url: '/erp/produceJob/' + id,
    method: 'delete'
  })
}

// 导出生产工票
export function exportProduceJob(query) {
  return request({
    url: '/erp/produceJob/export',
    method: 'post',
    params: query
  })
}
