import request from "../utils/request";

// 查询背景图片列表的方法
export function getBackgroundList(params) {
  return request({
    url: `/picture/getAll`,
    method: "get",
    params
  });
}

// 保存背景图片方法
export function createBackground(data) {
  return request({
    url: "/picture/save",
    method: "post",
    data,
    headers: {
      "Content-Type": "application/json"
    }
  });
}

// 删除背景图片方法
export function deleteBackground(params) {
  return request({
    url: `/picture/delete`,
    method: "delete",
    params
  });
}

// 更新背景图片方法
export function updateBackground(data) {
  return request({
    url: "/picture/update",
    method: "put",
    data,
    headers: {
      "Content-Type": "application/json"
    }
  });
}

// 设置背景图片是否启用
export function setEnableBackground(data) {
  return request({
    url: "/picture/setVisible",
    method: "put",
    data
  });
}
