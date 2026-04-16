import request from '@/utils/request'

// 查询员工列表
export function listEmployee(query) {
  return request({
    url: '/erp/employee/list',
    method: 'get',
    params: query
  })
}

// 查询员工详细
export function getEmployee(id) {
  return request({
    url: '/erp/employee/' + id,
    method: 'get'
  })
}

// 新增员工
export function addEmployee(data) {
  return request({
    url: '/erp/employee',
    method: 'post',
    data: data
  })
}

// 修改员工
export function updateEmployee(data) {
  return request({
    url: '/erp/employee',
    method: 'put',
    data: data
  })
}

// 删除员工
export function delEmployee(id) {
  return request({
    url: '/erp/employee/' + id,
    method: 'delete'
  })
}

// 导出员工
export function exportEmployee(query) {
  return request({
    url: '/erp/employee/export',
    method: 'post',
    params: query
  })
}
