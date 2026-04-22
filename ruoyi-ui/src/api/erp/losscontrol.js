import request from '@/utils/request'

export function listLossControl(query) {
  return request({ url: '/erp/materialconsume/list', method: 'get', params: query })
}

export function getLossStats(query) {
  return request({ url: '/erp/materialconsume/lossStats', method: 'get', params: query })
}

export function approveLoss(id, approved, remark) {
  return request({
    url: '/erp/materialconsume/approve/' + id,
    method: 'put',
    data: { approved, remark }
  })
}

export function exportLossControl(query) {
  return request({ url: '/erp/materialconsume/export', method: 'post', params: query, responseType: 'blob' })
}
