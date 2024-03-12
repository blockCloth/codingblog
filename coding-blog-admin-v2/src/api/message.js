import request from "../utils/request";

export function getMessageList(data) {
  return request({
    url: "/message/getAllList",
    method: "post",
    data,
    headers: {
      "Content-Type": "application/json"
    }
  });
}

export function deleteMsg(params) {
  return request({
    url: "/message/delete",
    method: "delete",
    params
  });
}

export function updateMsg(data) {
  return request({
    url: "/message/update",
    method: "put",
    data,
    headers: {
      "Content-Type": "application/json"
    }
  });
}

export function getTagList(params) {
  return request({
    url: "/message/getTagList",
    method: "get",
    params
  });
}

export function saveTag(data) {
  return request({
    url: "/message/saveTag",
    method: "post",
    data,
    headers: {
      "Content-Type": "application/json; charset=utf-8"
    }
  });
}

export function deleteTag(params) {
  return request({
    url: "/message/deleteTag",
    method: "delete",
    params
  });
}
