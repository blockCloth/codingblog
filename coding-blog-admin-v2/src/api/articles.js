import request from '../utils/request'

// 查询文章分页列表的方法
export function getArticlePagedList(params) {
  return request({
    url: `/posts/queryPostsList`,
    method: 'get',
    params
  })
}

// 根据id查询文章详情的方法
export function getArticleById(params) {
  return request({
    url: `/posts/queryPostDetailById?_=${Math.random()}`,
    method: 'get',
    params
  })
}

// 删除文章方法
export function deleteArticle(params) {
  return request({
    url: `/posts/delete?_=${Math.random()}`,
    method: 'delete',
    params
  })
}

// 添加文章保存方法
export function createArticle(data) {
  return request({
    url: '/posts/save',
    method: 'post',
    data
  })
}

// 更新文章保存方法
export function updateArticle(data) {
  return request({
    url: '/posts/update',
    method: 'put',
    data
  })
}

//获取作者名称
export function getAuthorName(params) {
  return request({
    url: `/users/getUserDetail?_=${Math.random()}`,
    method: 'get',
    params
  })
}
// 查询文章标签方法
export function getTagList(params) {
  return request({
    url: `/postTag/listAll?_=${Math.random()}`,
    method: 'get',
    params
  })
}

// 对接后台上传接口的方法
export function mdEditorUploadImage(data) {
  return request({
    url: '/imageController/upload',
    method: 'post',
    data,
    headers: { 'Content-Type': 'multipart/form-data' }
  })
}

// 绑定文章到栏目上
export function bindArticleToColumns(data) {
  return request({
    url: '/posts/insertPostTermTaxonomy',
    method: 'post',
    data
  })
}

// 文章置顶方法
export function setArticleOnTop(data) {
  return request({
    url: '/posts/setArticleOnTop',
    method: 'post',
    data
  })
}

// 取消文章置顶方法
export function cancelArticleOnTop(data) {
  return request({
    url: '/posts/cancelArticleOnTop',
    method: 'post',
    data
  })
}

// 设置文章是否展示
export function setArticleVisible(data) {
  return request({
    url: '/posts/setVisible',
    method: 'put',
    data
  })
}