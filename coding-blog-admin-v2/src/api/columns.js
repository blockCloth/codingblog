import request from '../utils/request'

// 获得所有专栏
export function getAllColumns(params) {
  return request({
    url: `/termTaxonomy/queryAllTermTaxonomyTree`,
    method: 'get',
    params
  })
}

// 根据id查询专栏详情
export function getOneColumn(params) {
  return request({
    url: `/termTaxonomy/getById`,
    method: 'get',
    params
  })
}

// 更新专栏保存方法
export function updateColumn(data) {
  return request({
    url: '/termTaxonomy/update',
    method: 'put',
    data
  })
}

// 添加专栏保存方法
export function addColumn(data) {
  return request({
    url: '/termTaxonomy/save',
    method: 'post',
    data
  })
}

// 删除专栏方法
export function deleteColumn(id) {
  return request({
    url: `/termTaxonomy/delete?_=${Math.random()}`,
    method: 'delete',
    params: {
      termTaxonomyId: id
    }
  })
}


// 获取所有父专栏方法
export function queryTermTaxonmyTreeParent() {
  return request({
    url: `/termTaxonomy/queryTermTaxonmyTreeParent?_=${Math.random()}`,
    method: 'get'
  })
}