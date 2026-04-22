import request from '@/utils/request'

export function getGanttData(params) {
  return request({ url: '/erp/producegantt/list', method: 'get', params })
}

export function updatePlanDate(id, newStartDate, newDueDate) {
  return request({
    url: '/erp/producegantt/reschedule/' + id,
    method: 'put',
    params: { newStartDate, newDueDate }
  })
}

export function detectConflicts() {
  return request({ url: '/erp/producegantt/detectConflicts', method: 'post' })
}
