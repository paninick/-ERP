import request from '@/utils/request'

// 查询主料列表
export function listMaterial(query) {
  return request({
    url: '/erp/material/main/list',
    method: 'get',
    params: query
  })
}

// 查询主料详细
export function getMaterial(id) {
  return request({
    url: '/erp/material/main/' + id,
    method: 'get'
  })
}

// 新增主料
export function addMaterial(data) {
  return request({
    url: '/erp/material/main',
    method: 'post',
    data: data
  })
}

// 修改主料
export function updateMaterial(data) {
  return request({
    url: '/erp/material/main',
    method: 'put',
    data: data
  })
}

// 删除主料
export function delMaterial(id) {
  return request({
    url: '/erp/material/main/' + id,
    method: 'delete'
  })
}
