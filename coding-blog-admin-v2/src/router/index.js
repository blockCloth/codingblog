import Vue from 'vue'
import Router from 'vue-router'
import page404 from '../views/error-pages/404'
import page500 from '../views/error-pages/500'
import pageNoAnyPower from '../views/error-pages/no-any-power'
import pageNoPower from '../views/error-pages/no-power'
import pageLogin from '../views/login'
import mainFrame from '@/layout'
import articles from '../views/content-management/article-management'
import tags from '../views/system-management/tag-management'
import articleEdit from '../views/content-management/article-editing'
import users from '../views/system-management/users-management'
import roles from '../views/system-management/roles-management'
import menus from '../views/system-management/menu-management'
import sourceCategories from '../views/system-management/source-category-management'
import sources from '../views/system-management/source-management'
import siteConfig from '../views/system-management/site-configuration'
import columns from '../views/system-management/column-management'
import link from '../views/system-management/link-management'
import background from '../views/system-management/background-management'
import home from '../views/home-management'

Vue.use(Router)

// 定义路由数组，暂时前端写死，之后加入权限管理之后，读取权限之后要进行过滤

// 不展示在菜单栏上，但是系统要使用到的数组
export const systemRouters = [
  {
    path: '/error/404',
    name: 'error-page404',
    component: page404
  },
  {
    path: '/error/500',
    name: 'error-page500',
    component: page500
  },
  {
    path: '/error/no-power',
    name: 'error-no-power',
    component: pageNoPower
  },
  {
    path: '/error/no-any-power',
    name: 'error-no-any-power',
    component: pageNoAnyPower
  },
  {
    path: '/login',
    name: 'login',
    component: pageLogin
  },
  {
    path: '/',
    name: 'root',
    // redirect: '/content/articles'
    component: mainFrame
  }
]

export const pageRouters = [
  {
    path: '/home',
    name: 'home-management',
    redirect: '/home/main',
    component: mainFrame,
    icon: 'el-icon-s-home', // el-icon-s-help
    meta: {
      title: '首页'
    },
    children: [
      {
        path: 'main',
        name: 'home-main',
        component: home,
        icon: 'el-icon-s-home',
        meta: {
          title: '主页'
        }
      }
    ]
  },
  {
    
    path: '/content',
    redirect: '/content/articles',
    name: 'content-management',
    component: mainFrame,
    icon: 'el-icon-s-help', // el-icon-s-help
    meta: {
      title: '内容管理'
    },
    children: [
      {
        path: 'article-editing',
        name: 'article-editing',
        icon: 'el-icon-edit-outline',
        component: articleEdit,
        meta: {
          title: '创建文章'
        }
      },
      {
        path: 'article-modify',
        name: 'article-modify',
        icon: 'el-icon-edit-outline',
        component: articleEdit,
        meta: {
          title: '编辑文章'
        }
      },
      {
        path: 'articles',
        name: 'article-management',
        icon: 'iconpark-list-two',
        component: articles,
        meta: {
          title: '文章列表'
        }
      }
      
    ]
  },
  {
    path: '/system',
    redirect: '/system/site',
    name: 'system-management',
    component: mainFrame,
    icon: 'el-icon-s-operation',
    meta: {
      title: '系统管理'
    },
    children: [
      {
        path: 'site',
        name: 'site-configuration',
        component: siteConfig,
        icon: 'iconpark-config',
        meta: {
          title: '站点配置'
        }
      },
      {
        path: 'users',
        name: 'users-management',
        component: users,
        icon: 'el-icon-user',
        meta: {
          title: '用户管理'
        }
      },
      {
        path: 'roles',
        name: 'roles-management',
        component: roles,
        icon: 'iconfont-role-management',
        meta: {
          title: '角色管理'
        }
      },
      {
        path: 'menus',
        name: 'menus-management',
        component: menus,
        icon: 'iconfont-menu-management',
        meta: {
          title: '菜单管理'
        }
      },
      {
        path: 'sources',
        name: 'sources-management',
        component: sources,
        icon: 'iconfont-source-management',
        meta: {
          title: '资源管理'
        }
      },
      {
        path: 'source-categories',
        name: 'source-categories-management',
        component: sourceCategories,
        icon: 'el-icon-coin',
        meta: {
          title: '资源分类管理'
        }
      },
      {
        path: 'tags',
        name: 'tags-management',
        icon: 'iconfont-tag-management',
        component: tags,
        meta: {
          title: '标签管理'
        }
      },
      {
        path: 'columns',
        name: 'column-management',
        icon: 'el-icon-s-grid',
        component: columns,
        meta: {
          title: '专栏管理'
        }
      },
      {
        path: 'links',
        name: 'links-management',
        icon: 'el-icon-link',
        component: link,
        meta: {
          title: '友链管理'
        }
      },
      {
        path: 'background',
        name: 'background-management',
        icon: 'el-icon-picture-outline',
        component: background,
        meta: {
          title: '背景管理'
        }
      }
      // {
      //   path: 'log',
      //   name: 'log-management',
      //   component: log,
      //   meta: {
      //     title: '日志管理'
      //   }
      // }
    ]
  }
]

export default new Router({
  routes: [...pageRouters, ...systemRouters]
  // routes: systemRouters
})
