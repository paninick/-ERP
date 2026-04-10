import request from '@/utils/request'

// 查询工艺书尺寸细节列表
export function listTechsize(query) {
  return request({
    url: '/erp/techsize/list',
    method: 'get',
    params: query
  })
}

// 查询工艺书尺寸细节详细
export function getTechsize(id) {
  return request({
    url: '/erp/techsize/' + id,
    method: 'get'
  })
}

// 新增工艺书尺寸细节
export function addTechsize(data) {
  return request({
    url: '/erp/techsize',
    method: 'post',
    data: data
  })
}

// 修改工艺书尺寸细节
export function updateTechsize(data) {
  return request({
    url: '/erp/techsize',
    method: 'put',
    data: data
  })
}

// 删除工艺书尺寸细节
export function delTechsize(id) {
  return request({
    url: '/erp/techsize/' + id,
    method: 'delete'
  })
}
