import request from '../utils/request'

// 分页查询菜单列表方法
export function getMenuList(params) {
  return request({
    url: `/menu/queryAllMenus`,
    method: 'get',
    params
  })
}

// 查询全部菜单，按照树形结构返回的方法
export function getAllMenusTree(params) {
  return request({
    url: `/menu/queryAllMenus`,
    method: 'get',
    params
  })
}

// 根据id查询菜单详细信息的方法
export function getMenuInfo(params) {
  return request({
    url: `/menu/queryMenuById`,
    method: 'get',
    params
  })
}

// 新增保存菜单方法
export function addMenu(data) {
  return request({
    url: `/menu/saveMenu`,
    method: 'post',
    data
  })
}

// 更新保存菜单方法
export function updateMenu(data) {
  return request({
    url: `/menu/updateMenu`,
    method: 'put',
    data
  })
}

// 删除菜单方法
export function deleteMenu(params) {
  return request({
    url: `/menu/deleteMenu`,
    method: 'delete',
    params
  })
}

// 设置菜单显示状态方法
export function setMenuVisible(data) {
  return request({
    url: `/menu/setMenuHidden`,
    method: 'put',
    data
  })
}
