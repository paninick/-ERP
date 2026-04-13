import request from '@/utils/request'

// 查询工艺指示书列表
export function listTech(query) {
  return request({
    url: '/erp/tech/list',
    method: 'get',
    params: query
  })
}

// 查询工艺指示书详细
export function getTech(id) {
  return request({
    url: '/erp/tech/' + id,
    method: 'get'
  })
}

// 新增工艺指示书
export function addTech(data) {
  return request({
    url: '/erp/tech',
    method: 'post',
    data: data
  })
}

// 修改工艺指示书
export function updateTech(data) {
  return request({
    url: '/erp/tech',
    method: 'put',
    data: data
  })
}

// 删除工艺指示书
export function delTech(id) {
  return request({
    url: '/erp/tech/' + id,
    method: 'delete'
  })
}
