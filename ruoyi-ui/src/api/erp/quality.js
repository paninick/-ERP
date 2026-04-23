import request from '@/utils/request'

// 路由对接 Model C: QcInspectionController (@RequestMapping("/erp/qc"))
export function getQualityStats() {
  return request({ url: '/erp/qc/stats', method: 'get' })
}

export function getDefectReasons() {
  return request({ url: '/erp/qc/defectReasons', method: 'get' })
}

export function listRecentChecks(query) {
  return request({ url: '/erp/qc/list', method: 'get', params: query })
}

export function rejectCheck(id, data) {
  return request({ url: '/erp/qc/reject/' + id, method: 'post', data })
}

export function getCheckByBarcode(barcode) {
  return request({ url: '/erp/qc/barcode/' + barcode, method: 'get' })
}
