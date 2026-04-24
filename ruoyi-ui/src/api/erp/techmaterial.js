import request from '@/utils/request'

// 查询工艺书面料信息列表
export function listTechMaterial(query) {
  return request({
    url: '/erp/techmaterial/list',
    method: 'get',
    params: query
  })
}

// 查询工艺书面料信息详细
export function getTechMaterial(id) {
  return request({
    url: '/erp/techmaterial/' + id,
    method: 'get'
  })
}

// 新增工艺书面料信息
export function addTechMaterial(data) {
  return request({
    url: '/erp/techmaterial',
    method: 'post',
    data: data
  })
}

// 修改工艺书面料信息
export function updateTechMaterial(data) {
  return request({
    url: '/erp/techmaterial',
    method: 'put',
    data: data
  })
}

// 删除工艺书面料信息
export function delTechMaterial(id) {
  return request({
    url: '/erp/techmaterial/' + id,
    method: 'delete'
  })
}

// Backward-compatible aliases for older imports
export const listTechmaterial = listTechMaterial
export const getTechmaterial = getTechMaterial
export const addTechmaterial = addTechMaterial
export const updateTechmaterial = updateTechMaterial
export const delTechmaterial = delTechMaterial
