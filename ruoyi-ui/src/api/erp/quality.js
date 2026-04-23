import request from '@/utils/request'

export function getQualityStats() {
  return request({ url: '/erp/check/stats', method: 'get' })
}

export function getDefectReasons() {
  return request({ url: '/erp/check/defectReasons', method: 'get' })
}

export function listRecentChecks(query) {
  return request({ url: '/erp/check/list', method: 'get', params: query })
}

export function rejectCheck(id, data) {
  return request({ url: '/erp/check/reject/' + id, method: 'post', data })
}

export function getCheckByBarcode(barcode) {
  return request({ url: '/erp/check/barcode/' + barcode, method: 'get' })
}
