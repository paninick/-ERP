import request from '@/utils/request'

// 获取甘特图数据
export function getGanttData() {
  return request({
    url: '/erp/producegantt/list',
    method: 'get'
  })
}

// 更新计划日期
export function updatePlanDate(id, startDate, dueDate) {
  return request({
    url: '/erp/producegantt/updateDate/' + id,
    method: 'put',
    params: { startDate, dueDate }
  })
}
