import request from '@/utils/request'

const URL = 'op/department'

export function addDepartment(form) {
  return request({
    url: URL + '/insert',
    method: 'post',
    data: form
  })
}

export function editDepartment(form) {
  return request({
    url: URL + '/update',
    method: 'post',
    data: form
  })
}

export function delDepartment(id) {
  return request({
    url: URL + '/delete',
    method: 'post',
    data: {
      id: id
    }
  })
}

export function getAllDepartment(searchData) {
  return request({
    url: URL + '/selectAll',
    method: 'post',
    data: {
      searchData: searchData
    }
  })
}

export function getById(id) {
  return request({
    url: URL + '/getById?id=' + id,
    method: 'get'
  })
}

