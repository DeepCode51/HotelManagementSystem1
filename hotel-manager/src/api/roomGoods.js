import request from '@/utils/request'

const URL = 'op/roomGoods'

export function addRoomGoods(form) {
  return request({
    url: URL + '/insert',
    method: 'post',
    data: form
  })
}

export function editRoomGoods(form) {
  return request({
    url: URL + '/update',
    method: 'post',
    data: form
  })
}

export function delRoomGoods(id) {
  return request({
    url: URL + '/delete',
    method: 'post',
    data: {
      id: id
    }
  })
}

export function getAllRoomGoods(searchData) {
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

