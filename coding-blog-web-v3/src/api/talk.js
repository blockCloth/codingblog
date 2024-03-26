import http from "@/config/request";

/** 获取说说列表 */
export const getTalkList = (current,size) => {
  return new Promise((resolve, reject) => {
    http.post(`/api/front/talk/listTalks?current=${current}&size=${size}`).then((res) => {
      resolve(res);
    });
  });
};

/** 说说点赞 */
export const talkLike = (id) => {
  return new Promise((resolve, reject) => {
    http.get("/api/front/talk/talkLike?talkId=" + id, {}).then((res) => {
      resolve(res);
    });
  });
};

/** 取消说说点赞 */
export const cancelTalkLike = (id) => {
  return new Promise((resolve, reject) => {
    http.get("/api/front/talk/cancelTalkLike?talkId=" + id, {}).then((res) => {
      resolve(res);
    });
  });
};
