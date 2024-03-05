package com.coding.blog.service.service.image.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @User Administrator
 * @CreateTime 2023/12/29 17:21
 * @className com.coding.blog.service.service.image.service.ImageService
 */
public interface ImageService {
    /**
     * 保存图片
     * @param request
     * @return
     */
    String saveImg(HttpServletRequest request);

    /**
     * 外网图片转存
     * @param img
     * @return
     */
    String saveImg(String img);

    /**
     * 获取文件名
     * @param pathName
     * @return
     */
    String getFileName(String pathName);

    /**
     * 删除图片
     *
     * @param imageName 文件名
     * @return
     */
    boolean deleteImage(String imageName);

    /**
     * 图片转存
     * @param content
     * @return
     */
    String mdImgReplace(String content);

    /**
     * 上传本地图片至服务器
     * @param localImagePath
     * @return
     */
    String uploadLocalImage(String localImagePath);
}
