import request from '@/utils/request'

// 查询计件工资明细列表
export function listPiecewagedetail(query) {
  return request({
    url: '/erp/piecewagedetail/list',
    method: 'get',
    params: query
  })
}

// 查询计件工资明细详细
export function getPiecewagedetail(id) {
  return request({
    url: '/erp/piecewagedetail/' + id,
    method: 'get'
  })
}

// 根据工资汇总ID查询明细
export function listPiecewagedetailByWage(wageId) {
  return request({
    url: '/erp/piecewagedetail/listByWage/' + wageId,
    method: 'get'
  })
}

// 新增计件工资明细
export function addPiecewagedetail(data) {
  return request({
    url: '/erp/piecewagedetail',
    method: 'post',
    data: data
  })
}

// 修改计件工资明细
export function updatePiecewagedetail(data) {
  return request({
    url: '/erp/piecewagedetail',
    method: 'put',
    data: data
  })
}

// 删除计件工资明细
export function delPiecewagedetail(id) {
  return request({
    url: '/erp/piecewagedetail/' + id,
    method: 'delete'
  })
}

// 导出计件工资明细
export function exportPiecewagedetail(query) {
  return request({
    url: '/erp/piecewagedetail/export',
    method: 'post',
    params: query,
    responseType: 'blob'
  })
}
