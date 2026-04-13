import request from '@/utils/request'

// 查询打样通知列表
export function listNotice(query) {
  return request({
    url: '/erp/notice/list',
    method: 'get',
    params: query
  })
}

// 查询打样通知详细
export function getNotice(id) {
  return request({
    url: '/erp/notice/' + id,
    method: 'get'
  })
}

// 新增打样通知
export function addNotice(data) {
  return request({
    url: '/erp/notice',
    method: 'post',
    data: data
  })
}

// 修改打样通知
export function updateNotice(data) {
  return request({
    url: '/erp/notice',
    method: 'put',
    data: data
  })
}

// 删除打样通知
export function delNotice(id) {
  return request({
    url: '/erp/notice/' + id,
    method: 'delete'
  })
}
