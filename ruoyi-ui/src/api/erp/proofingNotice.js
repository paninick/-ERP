import request from '@/utils/request'

export function listProofingNotice(query) {
  return request({
    url: '/erp/proofingNotice/list',
    method: 'get',
    params: query
  })
}

export function getProofingNotice(noticeId) {
  return request({
    url: '/erp/proofingNotice/' + noticeId,
    method: 'get'
  })
}

export function addProofingNotice(data) {
  return request({
    url: '/erp/proofingNotice',
    method: 'post',
    data: data
  })
}

export function updateProofingNotice(data) {
  return request({
    url: '/erp/proofingNotice',
    method: 'put',
    data: data
  })
}

export function delProofingNotice(noticeId) {
  return request({
    url: '/erp/proofingNotice/' + noticeId,
    method: 'delete'
  })
}
