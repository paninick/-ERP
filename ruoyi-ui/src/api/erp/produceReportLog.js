import request from '@/utils/request'

export function listProduceReportLog(query) {
  return request({ url: '/erp/produceReportLog/list', method: 'get', params: query })
}

export function getProduceReportLog(id) {
  return request({ url: '/erp/produceReportLog/' + id, method: 'get' })
}

export function addProduceReportLog(data) {
  return request({ url: '/erp/produceReportLog', method: 'post', data })
}

export function updateProduceReportLog(data) {
  return request({ url: '/erp/produceReportLog', method: 'put', data })
}

export function delProduceReportLog(id) {
  return request({ url: '/erp/produceReportLog/' + id, method: 'delete' })
}

export function exportProduceReportLog(query) {
  return request({ url: '/erp/produceReportLog/export', method: 'post', params: query })
}
