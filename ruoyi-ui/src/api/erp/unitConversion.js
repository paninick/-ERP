import request from '@/utils/request'

// 查询单位换算列表
export function listUnitConversion(query) {
  return request({
    url: '/erp/unitConversion/list',
    method: 'get',
    params: query
  })
}

// 查询单位换算详细
export function getUnitConversion(id) {
  return request({
    url: '/erp/unitConversion/' + id,
    method: 'get'
  })
}

// 根据物料ID获取单位换算
export function getUnitConversionByMaterial(materialId) {
  return request({
    url: '/erp/unitConversion/material/' + materialId,
    method: 'get'
  })
}

// 新增单位换算
export function addUnitConversion(data) {
  return request({
    url: '/erp/unitConversion',
    method: 'post',
    data: data
  })
}

// 修改单位换算
export function updateUnitConversion(data) {
  return request({
    url: '/erp/unitConversion',
    method: 'put',
    data: data
  })
}

// 删除单位换算
export function delUnitConversion(id) {
  return request({
    url: '/erp/unitConversion/' + id,
    method: 'delete'
  })
}

// 导出单位换算
export function exportUnitConversion(query) {
  return request({
    url: '/erp/unitConversion/export',
    method: 'post',
    params: query
  })
}
