import request from '@/utils/request'

// 查询模板消息列表
export function listTemplatemessage(query) {
  return request({
    url: '/wx/templatemessage/list',
    method: 'get',
    params: query
  })
}

// 查询模板消息详细
export function getTemplatemessage(id) {
  return request({
    url: '/wx/templatemessage/' + id,
    method: 'get'
  })
}

// 新增模板消息
export function addTemplatemessage(data) {
  return request({
    url: '/wx/templatemessage',
    method: 'post',
    data: data
  })
}

// 修改模板消息
export function updateTemplatemessage(data) {
  return request({
    url: '/wx/templatemessage',
    method: 'put',
    data: data
  })
}

// 删除模板消息
export function delTemplatemessage(id) {
  return request({
    url: '/wx/templatemessage/' + id,
    method: 'delete'
  })
}
