import request from '@/utils/request'

// 查询样衣BOM列表
export function listBom(query) {
  return request({
    url: '/erp/bom/list',
    method: 'get',
    params: query
  })
}

// 查询样衣BOM详细
export function getBom(id) {
  return request({
    url: '/erp/bom/' + id,
    method: 'get'
  })
}

// 新增样衣BOM
export function addBom(data) {
  return request({
    url: '/erp/bom',
    method: 'post',
    data: data
  })
}

// 修改样衣BOM
export function updateBom(data) {
  return request({
    url: '/erp/bom',
    method: 'put',
    data: data
  })
}

// 删除样衣BOM
export function delBom(id) {
  return request({
    url: '/erp/bom/' + id,
    method: 'delete'
  })
}

// 导出样衣BOM
export function exportBom(query) {
  return request({
    url: '/erp/bom/export',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
