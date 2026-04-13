import request from '@/utils/request'

// 查询订单列表
export function getList(query) {
  return request({
    url: '/demo/order/list',
    method: 'get',
    params: query
  })
}

// 查询订单详情
export function getById(id) {
  return request({
    url: `/demo/order/${id}`,
    method: 'get'
  })
}

// 新增订单
export function add(data) {
  return request({
    url: '/demo/order',
    method: 'post',
    data: data
  })
}

// 修改订单
export function update(data) {
  return request({
    url: '/demo/order',
    method: 'put',
    data: data
  })
}

// 删除订单
export function remove(id) {
  return request({
    url: `/demo/order/${id}`,
    method: 'delete'
  })
}

// 批量删除订单
export function removeBatch(ids) {
  return request({
    url: '/demo/order/batch',
    method: 'delete',
    data: ids
  })
}
