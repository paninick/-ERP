import request from '@/utils/request'

export function listProofingNotice(query) {
  return request({
    url: '/erp/notice/list',
    method: 'get',
    params: query
  })
}

export function getProofingNotice(noticeId) {
  return request({
    url: '/erp/notice/' + noticeId,
    method: 'get'
  })
}

export function addProofingNotice(data) {
  return request({
    url: '/erp/notice',
    method: 'post',
    data: data
  })
}

export function updateProofingNotice(data) {
  return request({
    url: '/erp/notice',
    method: 'put',
    data: data
  })
}

export function delProofingNotice(noticeId) {
  return request({
    url: '/erp/notice/' + noticeId,
    method: 'delete'
  })
}
