import request from '@/utils/request'

// 查询工艺书面料信息列表
export function listTechmaterial(query) {
  return request({
    url: '/erp/techmaterial/list',
    method: 'get',
    params: query
  })
}

// 查询工艺书面料信息详细
export function getTechmaterial(id) {
  return request({
    url: '/erp/techmaterial/' + id,
    method: 'get'
  })
}

// 新增工艺书面料信息
export function addTechmaterial(data) {
  return request({
    url: '/erp/techmaterial',
    method: 'post',
    data: data
  })
}

// 修改工艺书面料信息
export function updateTechmaterial(data) {
  return request({
    url: '/erp/techmaterial',
    method: 'put',
    data: data
  })
}

// 删除工艺书面料信息
export function delTechmaterial(id) {
  return request({
    url: '/erp/techmaterial/' + id,
    method: 'delete'
  })
}
