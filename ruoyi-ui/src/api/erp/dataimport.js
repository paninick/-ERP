import request from '@/utils/request'

const apiName = '/erp/dataimport'

export function listDataImport(query) {
  return request({
    url: apiName + '/list',
    method: 'get',
    params: query
  })
}

export function getDataImport(importId) {
  return request({
    url: apiName + '/' + importId,
    method: 'get'
  })
}

export function addDataImport(data) {
  return request({
    url: apiName,
    method: 'post',
    data: data
  })
}

export function updateDataImport(data) {
  return request({
    url: apiName,
    method: 'put',
    data: data
  })
}

export function delDataImport(importIds) {
  return request({
    url: apiName + '/' + importIds,
    method: 'delete'
  })
}
