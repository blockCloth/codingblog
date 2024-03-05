import http from "@/config/request";

/** 获取所有的tag */
export const getAllTag = () => {
  return new Promise((resolve, reject) => {
    http.get("/api/front/tag/getAllTerm", {}).then((res) => {
      resolve(res);
    });
  });
};
