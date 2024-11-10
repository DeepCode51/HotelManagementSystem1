import request from '@/utils/request'

const URL = 'admin/operator'


export function add(form) {
  return request({
    url: URL + '/add',
    method: 'post',
    data: form
  })
}

export function getAll(searchData,role) {
  return request({
    url: URL + '',
    method: 'post',
    data: {
      searchData: searchData,
      role: role,
    }
  })
}

export function getById(workerId) {
  return request({
    url: URL + '/' + workerId,
    method: 'post',
    data: {

    }
  })
}

export function update(data) {
  return request({
    url: URL + '/update',
    method: 'post',
    data: data
  })
}

export function del(workerId) {
  return request({
    url: URL + '/' + workerId,
    method: 'post',
    data: {
    }
  })
}

export function dell(workerId) {
  return request({
    url: URL + '/delete/' + workerId,
    method: 'post',
    data: {
    }
  })
}

