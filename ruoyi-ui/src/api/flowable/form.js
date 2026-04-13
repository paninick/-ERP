import request from '@/utils/request'

// 查询表单配置列表
export function listForm(query) {
  return request({
    url: '/flowable/form/list',
    method: 'get',
    params: query
  })
}

// 查询表单配置详细
export function getForm(id) {
  return request({
    url: '/flowable/form/' + id,
    method: 'get'
  })
}

// 新增表单配置
export function addForm(data) {
  return request({
    url: '/flowable/form',
    method: 'post',
    data: data
  })
}

// 修改表单配置
export function updateForm(data) {
  return request({
    url: '/flowable/form',
    method: 'put',
    data: data
  })
}

// 删除表单配置
export function delForm(id) {
  return request({
    url: '/flowable/form/' + id,
    method: 'delete'
  })
}

// 导出表单配置
export function exportForm(query) {
  return request({
    url: '/flowable/form/export',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
