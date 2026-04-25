import request from '@/utils/request'

// 查询工序流转记录列表
export function listProduceJobProcess(query) {
  return request({
    url: '/erp/produceJobProcess/list',
    method: 'get',
    params: query
  })
}

// 查询工序流转记录详细
export function getProduceJobProcess(id) {
  return request({
    url: '/erp/produceJobProcess/' + id,
    method: 'get'
  })
}

// 根据工票ID查询工序流转记录列表
export function listProduceJobProcessByJob(jobId) {
  return request({
    url: '/erp/produceJobProcess/listByJob/' + jobId,
    method: 'get'
  })
}

// 获取工票当前工序
export function getCurrentProcess(jobId) {
  return request({
    url: '/erp/produceJobProcess/currentProcess/' + jobId,
    method: 'get'
  })
}

// 新增工序流转记录
export function addProduceJobProcess(data) {
  return request({
    url: '/erp/produceJobProcess',
    method: 'post',
    data: data
  })
}

// 修改工序流转记录
export function updateProduceJobProcess(data) {
  return request({
    url: '/erp/produceJobProcess',
    method: 'put',
    data: data
  })
}

// 删除工序流转记录
export function delProduceJobProcess(id) {
  return request({
    url: '/erp/produceJobProcess/' + id,
    method: 'delete'
  })
}

// 导出工序流转记录
export function exportProduceJobProcess(query) {
  return request({
    url: '/erp/produceJobProcess/export',
    method: 'post',
    params: query
  })
}

// 插入临时工序（印花/绣花/灯检等）
export function insertCustomProcess(data) {
  return request({
    url: '/erp/produceJobProcess/insertCustom',
    method: 'post',
    data: data
  })
}

// 跳过工序
export function skipProcess(data) {
  return request({
    url: '/erp/produceJobProcess/skip',
    method: 'put',
    data: data
  })
}

// 插入返修工序
export function insertReworkProcess(data) {
  return request({
    url: '/erp/produceJobProcess/insertRework',
    method: 'post',
    data: data
  })
}
