import request from '../utils/request'//删除文章封面图片

export function deleteArticleCover(data) {
  return request({
    url: '/imageController/deleteImage',
    method: 'delete',
    params: data
  })
}

export const uploadUrl = process.env.VUE_APP_BASE_API + '/imageController/upload'

export const importMdUrl = process.env.VUE_APP_BASE_API + '/posts/uploadMd'