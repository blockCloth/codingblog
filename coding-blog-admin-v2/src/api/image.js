import request from '../utils/request'//删除文章封面图片

export function deleteArticleCover(data) {
  return request({
    url: '/imageController/deleteImage',
    method: 'delete',
    params: data
  })
}

export const uploadUrl = '/api/imageController/upload'

export const importMdUrl = '/api/posts/uploadMd'