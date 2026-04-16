import request from '@/utils/request'

// 查询工序损耗矩阵列表
export function listProcessLossMatrix(query) {
  return request({
    url: '/erp/processLossMatrix/list',
    method: 'get',
    params: query
  })
}

// 查询工序损耗矩阵详细
export function getProcessLossMatrix(id) {
  return request({
    url: '/erp/processLossMatrix/' + id,
    method: 'get'
  })
}

// 新增工序损耗矩阵
export function addProcessLossMatrix(data) {
  return request({
    url: '/erp/processLossMatrix',
    method: 'post',
    data: data
  })
}

// 修改工序损耗矩阵
export function updateProcessLossMatrix(data) {
  return request({
    url: '/erp/processLossMatrix',
    method: 'put',
    data: data
  })
}

// 删除工序损耗矩阵
export function delProcessLossMatrix(id) {
  return request({
    url: '/erp/processLossMatrix/' + id,
    method: 'delete'
  })
}

// 导出工序损耗矩阵
export function exportProcessLossMatrix(query) {
  return request({
    url: '/erp/processLossMatrix/export',
    method: 'post',
    params: query
  })
}
