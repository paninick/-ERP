import request from '@/utils/request'

// 查询工序工价列表
export function listProcessPrice(query) {
  return request({
    url: '/erp/processPrice/list',
    method: 'get',
    params: query
  })
}

// 查询工序工价详细
export function getProcessPrice(id) {
  return request({
    url: '/erp/processPrice/' + id,
    method: 'get'
  })
}

// 新增工序工价
export function addProcessPrice(data) {
  return request({
    url: '/erp/processPrice',
    method: 'post',
    data: data
  })
}

// 修改工序工价
export function updateProcessPrice(data) {
  return request({
    url: '/erp/processPrice',
    method: 'put',
    data: data
  })
}

// 删除工序工价
export function delProcessPrice(id) {
  return request({
    url: '/erp/processPrice/' + id,
    method: 'delete'
  })
}

// 导出工序工价
export function exportProcessPrice(query) {
  return request({
    url: '/erp/processPrice/export',
    method: 'post',
    params: query
  })
}
