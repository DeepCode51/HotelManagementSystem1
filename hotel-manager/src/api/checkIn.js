import request from '@/utils/request'

const URL = 'op/checkIn'

export function checkIn(data) {
  return request({
    url: URL + '/in',
    method: 'post',
    data: data
  })
}

export function checkOut(data) {
  return request({
    url: URL + '/out',
    method: 'post',
    data: data
  })
}
