import request from '@/utils/request'

// 查询字典数据列表
export function listSysdict(query) {
  return request({
    url: '/common/sysdict/list',
    method: 'get',
    params: query
  })
}

// 查询字典数据详细
export function getSysdict(id) {
  return request({
    url: '/common/sysdict/' + id,
    method: 'get'
  })
}

// 新增字典数据
export function addSysdict(data) {
  return request({
    url: '/common/sysdict',
    method: 'post',
    data: data
  })
}

// 修改字典数据
export function updateSysdict(data) {
  return request({
    url: '/common/sysdict',
    method: 'put',
    data: data
  })
}

// 删除字典数据
export function delSysdict(id) {
  return request({
    url: '/common/sysdict/' + id,
    method: 'delete'
  })
}
