import request from '@/utils/request'

// 查询生产排程列表
export function getList(query) {
  return request({
    url: '/demo/schedule/list',
    method: 'get',
    params: query
  })
}

// 查询生产排程详情
export function getById(id) {
  return request({
    url: `/demo/schedule/${id}`,
    method: 'get'
  })
}

// 新增生产排程
export function add(data) {
  return request({
    url: '/demo/schedule',
    method: 'post',
    data: data
  })
}

// 修改生产排程
export function update(data) {
  return request({
    url: '/demo/schedule',
    method: 'put',
    data: data
  })
}

// 删除生产排程
export function remove(id) {
  return request({
    url: `/demo/schedule/${id}`,
    method: 'delete'
  })
}

// 批量删除生产排程
export function removeBatch(ids) {
  return request({
    url: '/demo/schedule/batch',
    method: 'delete',
    data: ids
  })
}

// 判断生产排程是否爆满
export function isFullLoad(id) {
  return request({
    url: `/demo/schedule/isFullLoad/${id}`,
    method: 'get'
  })
}

// 判断生产排程是否空闲
export function isIdleLoad(id) {
  return request({
    url: `/demo/schedule/isIdleLoad/${id}`,
    method: 'get'
  })
}
