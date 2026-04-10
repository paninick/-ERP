import request from '@/utils/request'

// 查询微信账户列表
export function listAccount(query) {
  return request({
    url: '/wx/account/list',
    method: 'get',
    params: query
  })
}

// 查询微信账户详细
export function getAccount(id) {
  return request({
    url: '/wx/account/' + id,
    method: 'get'
  })
}

// 新增微信账户
export function addAccount(data) {
  return request({
    url: '/wx/account',
    method: 'post',
    data: data
  })
}

// 修改微信账户
export function updateAccount(data) {
  return request({
    url: '/wx/account',
    method: 'put',
    data: data
  })
}

// 删除微信账户
export function delAccount(id) {
  return request({
    url: '/wx/account/' + id,
    method: 'delete'
  })
}
