
import request from '@/utils/request'

// 查询打样总览列表
export function listOverview(query) {
  return request({
    url: '/erp/overview/list',
    method: 'get',
    params: query
  })
}

// 查询打样总览详细
export function getOverview(id) {
  return request({
    url: '/erp/overview/' + id,
    method: 'get'
  })
}

// 新增打样总览
export function addOverview(data) {
  return request({
    url: '/erp/overview',
    method: 'post',
    data: data
  })
}

// 修改打样总览
export function updateOverview(data) {
  return request({
    url: '/erp/overview',
    method: 'put',
    data: data
  })
}

// 删除打样总览
export function delOverview(id) {
  return request({
    url: '/erp/overview/' + id,
    method: 'delete'
  })
}

// 导出打样总览
export function exportOverview(query) {
  return request({
    url: '/erp/overview/export',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}

// 获取打样总览统计信息
export function getOverviewStatistics() {
  return request({
    url: '/erp/overview/statistics',
    method: 'get'
  })
}

