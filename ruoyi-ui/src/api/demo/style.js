import request from '@/utils/request'

// 查询款式列表
export function getList(query) {
  return request({
    url: '/demo/style/list',
    method: 'get',
    params: query
  })
}

// 查询款式详情
export function getById(id) {
  return request({
    url: `/demo/style/${id}`,
    method: 'get'
  })
}

// 新增款式
export function add(data) {
  return request({
    url: '/demo/style',
    method: 'post',
    data: data
  })
}

// 修改款式
export function update(data) {
  return request({
    url: '/demo/style',
    method: 'put',
    data: data
  })
}

// 删除款式
export function remove(id) {
  return request({
    url: `/demo/style/${id}`,
    method: 'delete'
  })
}

// 批量删除款式
export function removeBatch(ids) {
  return request({
    url: '/demo/style/batch',
    method: 'delete',
    data: ids
  })
}
