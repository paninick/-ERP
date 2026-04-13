import request from '@/utils/request'

// 查询外发加工订单列表
export function getList(query) {
  return request({
    url: '/demo/outsource/list',
    method: 'get',
    params: query
  })
}

// 查询外发加工订单详情
export function getById(id) {
  return request({
    url: `/demo/outsource/${id}`,
    method: 'get'
  })
}

// 新增外发加工订单
export function add(data) {
  return request({
    url: '/demo/outsource',
    method: 'post',
    data: data
  })
}

// 修改外发加工订单
export function update(data) {
  return request({
    url: '/demo/outsource',
    method: 'put',
    data: data
  })
}

// 删除外发加工订单
export function remove(id) {
  return request({
    url: `/demo/outsource/${id}`,
    method: 'delete'
  })
}

// 批量删除外发加工订单
export function removeBatch(ids) {
  return request({
    url: '/demo/outsource/batch',
    method: 'delete',
    data: ids
  })
}

// 查询外发加工补料记录列表
export function getExtraList(query) {
  return request({
    url: '/demo/outsource/extra/list',
    method: 'get',
    params: query
  })
}

// 查询外发加工补料记录详情
export function getExtraById(id) {
  return request({
    url: `/demo/outsource/extra/${id}`,
    method: 'get'
  })
}

// 新增外发加工补料记录
export function addExtra(data) {
  return request({
    url: '/demo/outsource/extra',
    method: 'post',
    data: data
  })
}

// 审批外发加工补料记录
export function approveExtra(id, approved) {
  return request({
    url: `/demo/outsource/extra/approve/${id}`,
    method: 'put',
    params: { approved }
  })
}
