<p align="center">
  <a href="https://blockcloth.cn/" target="_blank" rel="noopener noreferrer">
    <img width="180" src="https://img.shields.io/badge/青的个人博客-v1.0.0-lightgrey" alt="Blog logo">
  </a>
</p>

<p align="center">
  <a href="https://nodejs.org/en/about/releases/">
    <img src="https://img.shields.io/badge/node-v20.9.0-green" alt="node compatibility">
  </a>
  <a href="https://element.eleme.io/#/zh-CN">
    <img src="https://img.shields.io/badge/Element UI-v2.15.6-blue" alt="Element UI">
  </a>
  <a href="https://cn.vuejs.org/">
    <img src="https://img.shields.io/badge/Vue-v2.5.2-brightgreen" alt="Vue.js">
  </a>
  <a href="https://vueuse.org/">
    <img src="https://img.shields.io/badge/vue--router-v3.0.1-green" alt="vue-router">
  </a>
  <a href="https://vueuse.org/">
    <img src="https://img.shields.io/badge/vuex-v3.6.2-red" alt="vue-use">
  </a>
  <a href="https://www.axios-http.cn/docs/intro">
    <img src="https://img.shields.io/badge/axios-v0.25.0-blueviolet" alt="axios">
  </a>
</p>

## ⚡ 简介 introduction

前后端分离的个人博客项目 博客后台

目前仅支持PC端，适合新手用于学习。

#### 后台

博客前台基于 vue2、element ui、axios、vue-router、vite、vuex、npm等主流技术

- 前台线上预览地址：<https://blockcloth.cn/admin>**（用户名：guest  密码：123456）**

- GitHub 仓库地址：<https://github.com/blockCloth/codingblog/tree/master/coding-blog-web-v3>
- Gitee 仓库地址：<https://gitee.com/blockcloth/codingblog/tree/master/coding-blog-admin-v2>
- 部署教程：<https://blockcloth.cn/#/article?id=103>

## 🚀 博客后台下载运行

```git
# pnpm 版本 v10.1.0
# node 版本 v20.9.0

1、下载项目
git clone https://github.com/blockCloth/codingblog.git
tips: 也可以下载zip打开
2、打开项目，安装依赖（安装依赖报错可以降低自己的npm版本或者网上百度解决方法，一般情况下不会有问题）
npm install
3、运行项目
npm run serve（将博客的后端代码和前端代码拉到本地后，先启动 Redis 和服务端端。然后再启动前台，可以通过 VSCode 来进行开发。）
4、项目打包
npm run build

（注：如果后端端口号有变更，需要前往 config/dev.env.js 中进行修改）
```

## ✔️ 预览

### 页面总览

##### 主页

![](https://blockcloth.cn/codingblog/main.png)

##### 文章列表

![](https://blockcloth.cn/codingblog/article.png)

##### 文章创建

![](https://blockcloth.cn/codingblog/createArticle.png)

##### 菜单

![](https://blockcloth.cn/codingblog/menu.png)

##### 资源

![](https://blockcloth.cn/codingblog/resource.png)

### 功能总览

| 模块         | 功能                                                         |
| ------------ | ------------------------------------------------------------ |
| 文章模块     | 分页展示文章，查看文章新信息；新增文章时绑定专栏、标签和上传封面； |
| 用户模块     | 分页展示用户列表，给用户分配角色；                           |
| 角色模块     | 分页模糊查询角色，给角色分配菜单、资源，做权限校验           |
| 菜单模块     | 展示菜单树，新增菜单时需要需要分配父级菜单                   |
| 资源模块     | 管理接口地址，用作权限校验，给角色分配相关的资源信息         |
| 资源分类模块 | 添加资源时更好管理                                           |
| 标签管理     | 展示博客所有标签，文章添加时需要选择标签                     |
| 专栏管理     | 展示博客所有专栏，文章添加时需要选择专栏                     |
| 友链管理     | 增加友链                                                     |
| 背景管理     | 展示前台路由对应的背景图片，每个路由都可使用不同图片         |
| 留言板管理   | 修改留言信息，增加留言标签                                   |
| 站点配置     | 配置博主信息，以及常见配置                                   |

## 🌈部署

博客文章会出一系列的部署教程

教程地址：<https://blockcloth.cn/#/article?id=103>

## 🥰感谢

我们都应该由衷感谢每一位为开源社区做出贡献的开发者和维护者。他们长期投入大量时间和精力,经历无数次踩坑和思考,才能为我们提供如此优秀的技术框架和库。我们使用的每一个框架、库背后,都凝聚着开发者们的智慧结晶和辛勤付出。

如果你喜欢这个项目,不妨为它点个星标(star)或fork一份,你们的支持和鼓励将成为我们持续努力的最大动力。让我们一起为开源事业贡献自己的一份力量,推动技术的不断进步。
