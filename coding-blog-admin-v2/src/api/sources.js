import request from '../utils/request'

// 查询全部资源列表方法
export function getSourceAllList(params) {
  return request({
    url: '/resource/listAll',
    method: 'get',
    params
  })
}

// 分页查询资源列表方法
export function getSourceList(params) {
  return request({
    url: '/resource/queryListAll',
    method: 'get',
    params
  })
}

// 根据id获取资源详情方法
export function getSourceInfo(params) {
  return request({
    url: '/resource/queryDetail',
    method: 'get',
    params
  })
}

// 添加资源方法
export function addSource(data) {
  return request({
    url: '/resource/saveResource',
    method: 'post',
    data
  })
}

// 更新资源方法
export function updateSource(data) {
  return request({
    url: '/resource/updateResource',
    method: 'put',
    data
  })
}

// 删除资源方法
export function deleteSource(params) {
  return request({
    url: '/resource/deleteResource',
    method: 'delete',
    params
  })
}
