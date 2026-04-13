import request from '@/utils/request'

// 查询后道生产1列表
export function listPost(query) {
  return request({
    url: '/erp/post/list',
    method: 'get',
    params: query
  })
}

// 查询后道生产1详细
export function getPost(id) {
  return request({
    url: '/erp/post/' + id,
    method: 'get'
  })
}

// 新增后道生产1
export function addPost(data) {
  return request({
    url: '/erp/post',
    method: 'post',
    data: data
  })
}

// 修改后道生产1
export function updatePost(data) {
  return request({
    url: '/erp/post',
    method: 'put',
    data: data
  })
}

// 删除后道生产1
export function delPost(id) {
  return request({
    url: '/erp/post/' + id,
    method: 'delete'
  })
}

// 导出后道生产1
export function exportPost(query) {
  return request({
    url: '/erp/post/export',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
