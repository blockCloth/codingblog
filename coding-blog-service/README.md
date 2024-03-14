<p align="center">
  <a href="https://blockcloth.cn/" target="_blank" rel="noopener noreferrer">
    <img width="180" src="https://img.shields.io/badge/青的个人博客-v1.0.0-important" alt="Blog logo">
  </a>
</p>
<p align="center">
    <a href="https://spring.io/projects/spring-boot">
        <img src="https://img.shields.io/badge/SpringBoot-v2.7.17-blue" alt="springboot version">
    </a>
  <a href="https://baomidou.com/">
    <img src="https://img.shields.io/badge/MybatisPlus-v3.5.4.1-green" alt="mybatis-plus">
  </a>
  <a href="https://dev.mysql.com/downloads/mysql/">
    <img src="https://img.shields.io/badge/mysql-v8.0.26-pink" alt="mysql">
  </a>
  <a href="https://spring.io/projects/spring-security">
    <img src="https://img.shields.io/badge/SpringSecurity-v2.7.17-ff69b4" alt="security">
  </a>
    <a href="https://github.com/alibaba/druid">
    <img src="https://img.shields.io/badge/druid-v1.2.6-red" alt="druid">
  </a>
  <a href="https://redis.io/">
    <img src="https://img.shields.io/badge/redis-v2.7.17-blue" alt="redis">
  </a>
</p>


## 友情提示

> [前台体验地址](https://blockcloth.cn/)
>
> [后台体验地址](https://blockcloth.cn/admin)

## 项目介绍       

- 文章模块：
  - 用于后台管理员管理文章，包括创建、编辑、删除和发布文章等操作
- 角色模块：
  - 角色模块允许管理员为每个角色分配特定的权限。权限可以是系统功能的访问权限，例如创建、编辑、删除等，也可以是资源的访问权限，例如文章管理、用户管理等。管理员可以根据角色的职责和权限需求，灵活地配置角色的权限。
  - 角色模块允许管理员将角色与用户进行关联。通过将角色与用户关联，可以为用户分配相应的角色和权限。一个用户可以关联多个角色，而一个角色也可以被多个用户所关联。
- 菜单模块：
  - 可以通过菜单管理功能来创建、编辑和删除菜单项。可以根据需要创建多级菜单，并设置菜单的显示顺序。支持根据用户的权限动态生成菜单，即根据用户的权限配置来决定哪些菜单项对用户可见
- 资源模块：
  - 管理员可以通过资源管理功能来创建、编辑和删除系统中的各种资源，每个资源通常包括一个唯一的标识符和相关的属性信息。
  - 资源模块可以支持根据用户的权限动态显示和控制资源的访问。根据用户的权限配置，系统可以动态隐藏或显示特定的资源，或者限制用户对资源的操作权限
- 标签、专栏模块：
  - 通过将标签与内容关联，可以实现按照标签进行内容的分类和检索。一个内容可以关联多个标签，而一个标签也可以被多个内容所关联。
  - 专栏模块允许管理员将文章或其他内容归属到特定的专栏中。通过将内容与专栏关联，可以实现按照专栏进行内容组织和展示。

## 技术选项

| 技术                 | 说明             | 官网                                           |
| -------------------- | ---------------- | ---------------------------------------------- |
| Spring Boot          | 容器+MVC 框架    | https://spring.io/projects/spring-boot         |
| SpringSecurity       | 认证和授权框架   | https://spring.io/projects/spring-security     |
| MyBatis              | ORM 框架         | http://www.mybatis.org/mybatis-3/zh/index.html |
| MyBatis-Plus         | MyBatis 增强工具 | https://baomidou.com/                          |
| Nginx                | 静态资源服务器   | https://www.nginx.com/                         |
| Druid                | 数据库连接池     | https://github.com/alibaba/druid               |
| Lombok               | 简化对象封装工具 | https://github.com/rzwitserloot/lombok         |
| Swagger-UI           | 文档生成工具     | https://github.com/swagger-api/swagger-ui      |                |
| Hutool               | Java工具类库     | https://github.com/looly/hutool                |
| OSS                  | 对象存储         | https://github.com/aliyun/aliyun-oss-java-sdk  |

### Swagger-UI

![](https://coding-blog-oss01.oss-cn-hangzhou.aliyuncs.com/codingblog/web.png)

![](https://coding-blog-oss01.oss-cn-hangzhou.aliyuncs.com/codingblog/admin.png)

## 环境搭建

### 开发工具

| 工具          | 说明             | 官网                                    |
| ------------- | ---------------- | --------------------------------------- |
| Intellij IDEA | 开发环境         | https://www.jetbrains.com/idea/download |
| Navicat       | 数据库连接工具   | http://www.formysql.com/xiazai.html     |
| 语雀          | 开发文档设计工具 | https://www.yuque.com/download          |

### 开发环境

| 工具  | 版本号 | 下载                                                         |
| ----- | ------ | ------------------------------------------------------------ |
| JDK   | 1.8    | https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html |
| MySQL | 8.0.26 | https://www.mysql.com/                                       |
| Redis | 7.2.1  | https://redis.io/download                                    |
| Nginx | 1.20.2 | http://nginx.org/en/download.html                            |



