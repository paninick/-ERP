import request from '@/utils/request'

// 查询工艺路线明细列表
export function listProcessRouteItem(query) {
  return request({
    url: '/erp/processRouteItem/list',
    method: 'get',
    params: query
  })
}

// 查询工艺路线明细详细
export function getProcessRouteItem(id) {
  return request({
    url: '/erp/processRouteItem/' + id,
    method: 'get'
  })
}

// 新增工艺路线明细
export function addProcessRouteItem(data) {
  return request({
    url: '/erp/processRouteItem',
    method: 'post',
    data: data
  })
}

// 修改工艺路线明细
export function updateProcessRouteItem(data) {
  return request({
    url: '/erp/processRouteItem',
    method: 'put',
    data: data
  })
}

// 删除工艺路线明细
export function delProcessRouteItem(id) {
  return request({
    url: '/erp/processRouteItem/' + id,
    method: 'delete'
  })
}

// 导出工艺路线明细
export function exportProcessRouteItem(query) {
  return request({
    url: '/erp/processRouteItem/export',
    method: 'post',
    params: query
  })
}
