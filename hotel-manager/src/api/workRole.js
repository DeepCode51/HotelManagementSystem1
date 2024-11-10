import request from '@/utils/request'

const URL = 'op/workRole'

export function addWorkRole(form) {
  return request({
    url: URL + '/insert',
    method: 'post',
    data: form
  })
}

export function editWorkRole(form) {
  return request({
    url: URL + '/update',
    method: 'post',
    data: form
  })
}

export function delWorkRole(id) {
  return request({
    url: URL + '/delete',
    method: 'post',
    data: {
      id: id
    }
  })
}

export function getAllWorkRole(searchData) {
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

