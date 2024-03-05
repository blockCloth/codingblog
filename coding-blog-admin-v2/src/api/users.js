import request from '../utils/request'

// 获取当前用户登录信息
export function getLoginUserInfo() {
  return request({
    url: '/users/info',
    method: 'get'
  })
}

// 用户退出登陆方法
export function userLogout() {
  return request({
    url: '/users/logout',
    method: 'post'
  })
}

// 登陆用户修改自己密码的方法
export function modifyPassword(data) {
  return request({
    url: '/users/updateUserPass',
    method: 'put',
    data
  })
}

// 根据用户id获取用户详细信息的方法
export function getUserInfoById(params) {
  return request({
    url: '/users/getUserDetail',
    method: 'get',
    params
  })
}

// 更新一个用户基本信息的方法
export function updateUserInfoById(data) {
  return request({
    url: '/users/update',
    method: 'put',
    data
  })
}

// 添加用户方法
export function addUser(data) {
  return request({
    url: '/users/register',
    method: 'post',
    data
  })
}

// 删除用户方法
export function deleteUser(params) {
  return request({
    url: '/users/delete',
    method: 'delete',
    params
  })
}

// 查询用户列表方法
export function getUserList(params) {
  return request({
    url: '/users/getAllUserDetail',
    method: 'get',
    params
  })
}

// 查询用户当前拥有的角色的方法
export function getUserRoles(params) {
  return request({
    url: '/users/role/query',
    method: 'get',
    params
  })
}

// 保存用户角色分配的设置方法
export function setUserRoles(data) {
  return request({
    url: '/users/role/save',
    method: 'post',
    data
  })
}

// 修改用户当前启用禁用状态方法
export function setUserStatus(data) {
  return request({
    url: '/users/enableOrDisable',
    method: 'put',
    data
  })
}

//刷新用户token
export function refreshToken(data) {
  return request({
    url: '/users/refreshToken',
    method: 'post',
    data
  })
}

export const uploadUrl = process.env.VUE_APP_BASE_API + '/imageController/upload'
