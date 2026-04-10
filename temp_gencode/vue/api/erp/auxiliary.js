import request from '@/utils/request'

// 查询辅料列表
export function listAuxiliary(query) {
  return request({
    url: '/erp/auxiliary/list',
    method: 'get',
    params: query
  })
}

// 查询辅料详细
export function getAuxiliary(id) {
  return request({
    url: '/erp/auxiliary/' + id,
    method: 'get'
  })
}

// 新增辅料
export function addAuxiliary(data) {
  return request({
    url: '/erp/auxiliary',
    method: 'post',
    data: data
  })
}

// 修改辅料
export function updateAuxiliary(data) {
  return request({
    url: '/erp/auxiliary',
    method: 'put',
    data: data
  })
}

// 删除辅料
export function delAuxiliary(id) {
  return request({
    url: '/erp/auxiliary/' + id,
    method: 'delete'
  })
}
