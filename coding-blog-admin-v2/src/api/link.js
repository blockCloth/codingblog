import request from "../utils/request";

// 获得所有link
export function getLinks(params) {
  return request({
    url: `/links/getLinks`,
    method: "get",
    params
  });
}

// 获得link详情
export function getLinkById(params) {
  return request({
    url: `/links/getLinkById`,
    method: "get",
    params
  });
}

// 保存link
export function saveLink(data) {
  return request({
    url: `/links/save`,
    method: "post",
    data,
    headers: {
      'Content-Type': 'application/json',
    }
  });
}

// 修改link
export function updateLink(data) {
  return request({
    url: `/links/update`,
    method: "put",
    data,
    headers: {
      'Content-Type': 'application/json',
    }
  });
}

// 删除link
export function deleteLink(params) {
  return request({
    url: `/links/delete`,
    method: "delete",
    params
  });
}

// 设置link可见状态
export function setVisibility(data) {
  return request({
    url: `/links/setVisibility`,
    method: "put",
    data
  });
}

// 设置link可不见状态
export function setNotVisibility(data) {
  return request({
    url: `/links/setNotVisibility`,
    method: "put",
    data
  });
}
