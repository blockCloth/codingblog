import http from "@/config/request";

/** 首页获取文章列表 */
export const homeGetArticleList = (current, size) => {
  return new Promise((resolve, reject) => {
    http.get(`/api/front/post/introduction?pageNum=${current}&pageSize=${size}`, {}).then((res) => {
      resolve(res);
    });
  });
};

/** 时间轴 */
export const blogTimelineGetArticleList = (current, size) => {
  return new Promise((resolve, reject) => {
    http.get(`/api/front/post/postsTime?pageNum=${current}&pageSize=${size}`, {}).then((res) => {
      resolve(res);
    });
  });
};

/** 根据标签id获取该标签下的文章*/
export const getArticleListByTagId = (postTagId) => {
  return new Promise((resolve, reject) => {
    http.get(`/api/front/post/getPostsByTagId?postTagId=${postTagId}`).then((res) => {
      resolve(res);
    });
  });
};

/** 根据分类id获取该标签下的文章*/
export const getArticleListByCategoryId = (termTaxonomyId) => {
  return new Promise((resolve, reject) => {
    http.get( `/api/front/post/getPostsByTermId?termTaxonomyId=${termTaxonomyId}`).then((res) => {
      resolve(res);
    });
  });
};

/** 根据文章id获取推荐文章*/
export const getRecommendArticles = (id) => {
  return new Promise((resolve, reject) => {
    http.get(`/api/front/post/recommendPosts/${id}`, {}).then((res) => {
      resolve(res);
    });
  });
};

/** 根据文章id获取文章详情*/
export const getArticleById = (id) => {
  return new Promise((resolve, reject) => {
    http.get(`/api/front/post/getDetailById?postId=${id}`, {}).then((res) => {
      resolve(res);
    });
  });
};

/** 获取热门文章*/
export const getHotArticle = () => {
  return new Promise((resolve, reject) => {
    http.get("/api/front/post/getHotPosts", {}).then((res) => {
      resolve(res);
    });
  });
};

/** 根据文章内容搜索文章*/
export const getArticleByContent = (content) => {
  return new Promise((resolve, reject) => {
    http.get("/api/front/post/getPostsByContent?content=" + content, {}).then((res) => {
      resolve(res);
    });
  });
};

/** 文章点赞 */
export const articleLike = (id) => {
  return new Promise((resolve, reject) => {
    http.get("/api/front/post/praise?postId=" + id , {}).then((res) => {
      resolve(res);
    });
  });
};

/** 取消文章点赞 */
export const cancelArticleLike = (id) => {
  return new Promise((resolve, reject) => {
    http.get("/api/front/post/cancelPraise?postId=" + id, {}).then((res) => {
      resolve(res);
    });
  });
};

/** 文章增加阅读时长 */
export const readingDuration = (id, duration) => {
  return new Promise((resolve, reject) => {
    http.put(`/api/front/article/addReadingDuration/${id}/${duration}`, {}).then((res) => {
      resolve(res);
    });
  });
};
