import request from '@/utils/request'

// 查询报工列表
export function getList(query) {
  return request({
    url: '/demo/report/list',
    method: 'get',
    params: query
  })
}

// 查询报工详情
export function getById(id) {
  return request({
    url: `/demo/report/${id}`,
    method: 'get'
  })
}

// 新增报工
export function add(data) {
  return request({
    url: '/demo/report',
    method: 'post',
    data: data
  })
}

// 修改报工
export function update(data) {
  return request({
    url: '/demo/report',
    method: 'put',
    data: data
  })
}

// 删除报工
export function remove(id) {
  return request({
    url: `/demo/report/${id}`,
    method: 'delete'
  })
}

// 批量删除报工
export function removeBatch(ids) {
  return request({
    url: '/demo/report/batch',
    method: 'delete',
    data: ids
  })
}
