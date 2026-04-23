import request from '@/utils/request'

// 路由对接 Model C: FinInvoiceController (@RequestMapping("/erp/finInvoice"))
export function getInvoiceBoardStats() {
  return request({ url: '/erp/finInvoice/boardStats', method: 'get' })
}

export function listInvoiceCards(query) {
  return request({ url: '/erp/finInvoice/list', method: 'get', params: query })
}

export function reconcileInvoice(invoiceId, data) {
  return request({ url: '/erp/finInvoice/reconcile', method: 'post', data: { invoiceId, ...data } })
}

export function redIssueInvoice(id, data) {
  return request({ url: '/erp/finInvoice/redIssue/' + id, method: 'post', data })
}
