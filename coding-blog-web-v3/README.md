<p align="center">
  <a href="https://blockcloth.cn/" target="_blank" rel="noopener Aquamarine">
    <img width="180" src="https://img.shields.io/badge/青的个人博客-v1.0.0-lightgrey" alt="Blog logo">
  </a>
</p>



<p align="center">
  <a href="https://nodejs.org/en/about/releases/">
    <img src="https://img.shields.io/badge/node-v18.17.0-green" alt="node compatibility">
  </a>
  <a href="https://element-plus.gitee.io/zh-CN/">
    <img src="https://img.shields.io/badge/ElementPlus-v2.2.17-blue" alt="Element Plus">
  </a>
  <a href="https://nodejs.org/en/about/releases/">
    <img src="https://img.shields.io/badge/vite-4.4.0-purple" alt="node compatibility">
  </a>
  <a href="https://cn.vuejs.org/">
    <img src="https://img.shields.io/badge/Vue-v3.3.4-brightgreen" alt="Vue.js">
  </a>
  <a href="https://imzbf.github.io/md-editor-v3/docs#%F0%9F%A7%B1%20toolbarsExclude">
    <img src="https://img.shields.io/badge/md--editor--v3-v2.7.2-lightgrey" alt="md-editor-v3">
  </a>
  <a href="https://pinia.web3doc.top/">
    <img src="https://img.shields.io/badge/pinia-v2.0.28-yellowgreen" alt="pinia">
  </a>
  <a href="https://router.vuejs.org/zh/guide/">
    <img src="https://img.shields.io/badge/vue--router-v4.0.3-green" alt="vue-router">
  </a>
  <a href="https://vueuse.org/">
    <img src="https://img.shields.io/badge/vueuse-v%5E9.10.0-red" alt="vue-use">
  </a>
  <a href="https://www.axios-http.cn/docs/intro">
    <img src="https://img.shields.io/badge/axios-v%5E1.2.0-blueviolet" alt="axios">
  </a>
  <a href="https://www.dowebok.com/demo/2014/98/">
    <img src="https://img.shields.io/badge/animate-v%5E4.1.1-orange" alt="animate">
  </a>
</p>


## ⚡ 简介 introduction

前后端分离的个人博客项目 博客前台

已适配移动端、PC端，适合新手用于学习。

#### 前台

博客前台基于 vue3、element plus、pinia、axios、vue-router、vite、vue-use、npm、scss、tailwind.css 等主流技术

- 前台线上预览地址：<https://blockcloth.cn/>

- GitHub 仓库地址：<https://github.com/blockCloth/codingblog/tree/master/coding-blog-web-v3>
- Gitee 仓库地址：<https://gitee.com/blockcloth/codingblog/tree/master/coding-blog-web-v3>
- 博客部署教程：<https://blockcloth.cn/#/article?id=103>

## 🚀 博客前台下载运行

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

（注：如果后端端口号有变更，需要前往vite.config.js中进行修改）
```

## ✔️ 预览

#### 页面预览

##### 首页

![](https://blockcloth.cn/codingblog/welcome.png)

##### 主页

![](https://blockcloth.cn/codingblog/home.png)

##### 分类

![](https://blockcloth.cn/codingblog/category.png)

##### 时间轴

![](https://blockcloth.cn/codingblog/active.png)

##### 留言

![](https://blockcloth.cn/codingblog/message.png)

#### 功能总览

| 模块     | 功能                                                         |
| -------- | ------------------------------------------------------------ |
| 首页     | 分页查看文章、展示博客网站基础信息、博客公告、博主信息等     |
| 文章详情 | 浏览文章详情，随机进行文章推送，排除本篇文章                 |
| 时间轴   | 根据文章发布时间展示文章                                     |
| 标签     | 展示博客所有的标签，点击标签会搜索出当前点击标签下的文章列表 |
| 分类     | 展示博客所有的分类，点击分类会搜索出当前点击分类下的文章列表 |
| 相册     | 展示博主的所有相册，目前暂未开放                             |
| 友链     | 展示友链                                                     |
| 说说     | 类似微信朋友圈的功能，支持发图片，目前暂未开放               |
| 留言     | 可以自定义要发布的类型、发布的内容样式、图片等，还允许自己编写html |
| 音乐     | 使用网易云项目的接口，自己写了一个音乐播放器，有分类排行榜、搜索音乐、播放音乐、显示歌词等功能 |

#### 功能开发

目前的功能是满足个人使用的，如果有更好玩的功能，可以推荐给我哦

## 🌈部署

博客文章会出一系列的部署教程

教程地址：<https://blockcloth.cn/#/article?id=103>

## 🥰感谢

我们都应该由衷感谢每一位为开源社区做出贡献的开发者和维护者。他们长期投入大量时间和精力,经历无数次踩坑和思考,才能为我们提供如此优秀的技术框架和库。我们使用的每一个框架、库背后,都凝聚着开发者们的智慧结晶和辛勤付出。

如果你喜欢这个项目,不妨为它点个星标(star)或fork一份,你们的支持和鼓励将成为我们持续努力的最大动力。让我们一起为开源事业贡献自己的一份力量,推动技术的不断进步。

