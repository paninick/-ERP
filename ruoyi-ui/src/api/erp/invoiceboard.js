import request from '@/utils/request'

export function getInvoiceBoardStats() {
  return request({ url: '/erp/invoice/boardStats', method: 'get' })
}

export function listInvoiceCards(query) {
  return request({ url: '/erp/invoice/list', method: 'get', params: query })
}
