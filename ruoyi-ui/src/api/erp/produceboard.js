import request from '@/utils/request'

// 获取生产看板统计数据
export function getBoardStats() {
  return request({
    url: '/erp/produceboard/stats',
    method: 'get'
  })
}

// 获取工序WIP统计
export function getWipStats() {
  return request({
    url: '/erp/produceboard/wipStats',
    method: 'get'
  })
}

// 获取员工产量排名
export function getEmployeeRank() {
  return request({
    url: '/erp/produceboard/employeeRank',
    method: 'get'
  })
}

// 获取瓶颈预警
export function getBottleneckWarnings() {
  return request({
    url: '/erp/produceboard/bottleneckWarnings',
    method: 'get'
  })
}
