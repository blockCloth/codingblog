import request from '../utils/request'

// 查询说说列表方法
export function getTalkList(params) {
  return request({
    url: '/talk/listTalks',
    method: 'get',
    params
  })
}

// 保存说说方法
export function saveTalk(data) {
  return request({
    url: '/talk/save',
    method: 'post',
    data,
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

// 删除说说方法
export function deleteTalk(params) {
  return request({
    url: '/talk/delete',
    method: 'delete',
    params
  })
}

// 设置说说是否指定方法
export function setTalkOrder(params) {
  return request({
    url: '/talk/setTalkOrder',
    method: 'put',
    params
  })
}

// 设置说说可见状态方法
export function setTalkStatus(params) {
  return request({
    url: '/talk/setTalkStatus',
    method: 'put',
    params
  })
}

// 查询说说详情方法
export function getTalkDetail(params) {
  return request({
    url: '/talk/getTalkById',
    method: 'get',
    params
  })
}

// 编辑说说方法
export function updateTalk(data) {
  return request({
    url: '/talk/update',
    method: 'put',
    data,
    headers: {
      'Content-Type': 'application/json'
    }
  })
}
