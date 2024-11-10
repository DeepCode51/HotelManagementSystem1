import request from '@/utils/request'

const URL = 'op/room-type'

export function addRoomType(form) {
  return request({
    url: URL + '/add',
    method: 'post',
    data: form
  })
}

export function editRoomType(form) {
  return request({
    url: URL + '/update',
    method: 'post',
    data: form
  })
}

export function delRoomType(typeId) {
  return request({
    url: URL + '/delete/'+typeId,
    method: 'post',
    data: {
    }
  })
}

export function getRoomTypeById(typeId) {
  return request({
    url: URL + '/'+typeId,
    method: 'post',
    data: {
    }
  })
}

export function getAllRoomType(searchData) {
  return request({
    url: URL,
    method: 'post',
    data: {
      searchData: searchData
    }
  })
}

export function roomTypeDtatistics() {
  return request({
    url: '/service/statistics/roomTypeDtatistics',
    method: 'get'
  })
}

export function orderVolumeStatistics() {
  return request({
    url: '/service/statistics/orderVolumeStatistics',
    method: 'get'
  })
}

export function roomTypeOrderStatistics() {
  return request({
    url: '/service/statistics/roomTypeOrderStatistics',
    method: 'get'
  })
}

export function roomAccommodationDtatistics() {
  return request({
    url: '/service/statistics/roomAccommodationDtatistics',
    method: 'get'
  })
}
