import request from '@/utils/request'

// 查询微信用户列表
export function listWxuser(query) {
  return request({
    url: '/wx/wxuser/list',
    method: 'get',
    params: query
  })
}

// 查询微信用户详细
export function getWxuser(id) {
  return request({
    url: '/wx/wxuser/' + id,
    method: 'get'
  })
}

// 新增微信用户
export function addWxuser(data) {
  return request({
    url: '/wx/wxuser',
    method: 'post',
    data: data
  })
}

// 修改微信用户
export function updateWxuser(data) {
  return request({
    url: '/wx/wxuser',
    method: 'put',
    data: data
  })
}

// 删除微信用户
export function delWxuser(id) {
  return request({
    url: '/wx/wxuser/' + id,
    method: 'delete'
  })
}
