/* eslint-disable no-unused-vars */
import router, {
  pageRouters,
  systemRouters
} from './index'
import store from '../store'
import {
  getToken,
  removeToken,
  removeExpires
} from '../utils/auth'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css' // nprogress样式文件
import Cookies from 'js-cookie'

router.beforeEach(async (to, from, next) => {
  // 开启进度条
  NProgress.start()

  // 判断是否系统中支持跳转的路由
  if (!to.name) {
    next({
      name: 'error-page404',
      replace: true
    })
  }

  /*
   * 权限控制思路：
   * 1.判断是否有token
   * 2.没有token直接跳转登录页
   * 3.有token查看是否已经初始化用户权限，如果没有，则异步调用接口获得用户信息和权限信息
   * 4.判断用户是否有业务菜单权限，如果没有，直接跳转到没有任何权限页面
   * 5.判断特殊路由情况，login和刷新页面情况，两者都跳转到第一个有权限的路由名称即可
   * 6.判断当前要跳转的路由名称是否名称在用户允许访问的路由名称列表内
   * 7.不在可访问列表内，跳转无权限页面
   * 8.在可访问列表内，直接next()跳转目标路由
   */

  const tokenValue = getToken()
  if (!tokenValue && to.name !== 'login') {
    // 关闭进度条
    NProgress.done()
    next({
      name: 'login',
      replace: true
    })
  }

  if (tokenValue && !store.state.userInfo) {
    // 通过路由跳转动态请求并渲染可用菜单
    await initSysPower()
  }

  const sysFind = systemRouters.find(x => x.name === to.name)
  const businessFind = pageRouters.find(x => {
    if (x.name === to.name) {
      return x
    }
    if (x.children) {
      const child = x.children.find(y => y.name === to.name)
      if (child) {
        return child
      }
    }
    return null
  })
  if (tokenValue && store.state.userInfo) {
    if (store.state.userMenus.length == 0 && to.name != 'error-no-any-power') {
      NProgress.done()
      next({
        name: 'error-no-any-power',
        replace: true
      })
    }
    const powerFind = store.state.userMenus ? store.state.userMenus.find(x => x === to.name) : null
    // 业务路由匹配，权限没匹配，跳转无权限页面
    if (store.state.userMenus.length > 0 && businessFind && !powerFind && from.path !== '/') {
      NProgress.done()
      next({
        name: 'error-no-power'
      })
    }
    // 当有token还要跳转login的时候，或者直接跳转 / 的时候，跳转第一个有权限的菜单
    if (store.state.userMenus.length > 0 && !powerFind && (to.path === '/' || to.name === 'login' || from.path === '/')) {
      let targetName = store.state.userMenus[0]
      NProgress.done()
      next({
        name: targetName,
        replace: true
      })
    }
  }

  // 所有路由都没匹配，跳转404
  if (!sysFind && !businessFind) {
    NProgress.done()
    next({
      name: 'error-page404',
      replace: true
    })
  }

  // 其他所有情况，直接跳转
  next()
})

router.afterEach((to, from) => {
  // 关闭进度条
  NProgress.done()
})

async function initSysPower() {
  return store.dispatch('refleshUserInfo').then(menus => {
    if (menus && menus.length > 0) {
      // 存储最终要添加到客户端的菜单变量
      let userMenus = menus.map(item => item.name)
      store.dispatch('setUserPowers', userMenus)
    }else{
      removeToken(),
      removeExpires()
    }
  })
}
