import axios from 'axios'
import {
  Loading,
  MessageBox,
  Message
} from 'element-ui'
import {
  getToken,
  removeToken,
  getExpires,
  removeExpires,
  setToken,
  setExpires
} from '@/utils/auth'
import {
  refreshToken
} from '@/api/users'

import router from '../router'

// #region 处理ajax效果
// 标记页面加载对象是否存在
let loadingService = null
// 当前请求数量
let ajaxCount = 0
// 检查当前是否所有ajax都结束方法
let checkAllAjaxDone = () => {
  if (ajaxCount === 0) {
    // console.log('所有ajax结束。。。。', loadingService);
    if (loadingService) {
      loadingService.close()
      loadingService = null
    }
  }
}

// 是否正在刷新的标志
window.isRefreshing = false;
// 存储请求的数组
let cacheRequestArr = [];

// 将所有的请求都push到数组中,其实数组是[function(token){}, function(token){},...]
function cacheRequestArrHandle(cb) {
  cacheRequestArr.push(cb);
}
// 数组中的请求得到新的token之后自执行，用新的token去重新发起请求
function afreshRequest(token) {
  cacheRequestArr.map(cb => cb(token));
  cacheRequestArr = [];
}
// 判断token是否即将过期
function isTokenExpired() {
  let curTime = new Date().getTime(); //获取系统当前时间
  let expiresTime = Number(getExpires()) - curTime; //获取token的过期时间，并减去当前时间
  // 还差5分钟即将过期或者已经过期了，但过期时间在5分钟内
  if ((expiresTime >= 0 && expiresTime < 300000) || (expiresTime < 0 && Math.abs(expiresTime) <= 300000)) {
    return true
  }
  return false;
}

// #endregion

// 创建请求对象
const httpRequest = axios.create({
  // 统一的ajax请求前缀
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 5000 // 请求超时时间
})

// 请求封装
httpRequest.interceptors.request.use(
  config => {
    // do something before request is sent
    // console.log('request config data:', config.data)
    if (getToken()) {
      // 设置请求头token
      config.headers['Authorization'] = getToken()
      // 判断token是否即将过期
      if (isTokenExpired() && config.url !== '/users/refreshToken') {
        //标志正在刷新token
        window.isRefreshing = true;
        // 重新请求token
        refreshToken(getToken()).then(res => {
          // 先保存token到cookie
          setToken(`${res.tokenHead}${res.token}`, res.expiration)
          //设置过期时间
          setExpires(res.expiration)
          // 重新设置请求头token
          config.headers['Authorization'] = getToken()
          // 执行数组里的函数,重新发起被挂起的请求
          afreshRequest(getToken());
          // 重置标志
          window.isRefreshing = false;
        }).catch(() => {
          // 重置标志
          window.isRefreshing = false;
          // 删除本地缓存
          removeToken()
          removeExpires()
          // 跳转到登录页面
          router.push('/login')
        }).finally(() => {
          window.isRefreshing = false;
        })

        let retry = new Promise((resolve) => {
          cacheRequestArrHandle((token) => {
            config.headers['Authorization'] = token; // token为刷新完成后传入的token
            // 将请求挂起
            resolve(config)
          })
        })
        return retry;
      }
    }
    // #region 处理ajax效果
    // 如果loading对象不存在，当前又要发起请求，则创建loading对象，从而对所有ajax请求添加统一的请求等待效果
    if (!loadingService) {
      loadingService = Loading.service({
        fullscreen: true,
        lock: true,
        text: '加载中，请稍后...'
      })
    }
    // ajax数量+1
    ajaxCount++
    // #endregion
    return config
  },
  error => {
    // do something with request error
    return Promise.reject(error)
  }
)

// 响应处理封装
httpRequest.interceptors.response.use(
  response => {
    const {
      code,
      result,
      message
    } = response.data

    if ( // 请求正常的情况
      (code === 200 && response.status === 200) ||
      (response.data instanceof Blob && response.status === 200)
    ) {
      // #region 处理ajax效果
      ajaxCount--
      checkAllAjaxDone()
      // #endregion
      if (response.data instanceof Blob) {
        return response.data
      } else {
        return result
      }
    } else {
      // #region 处理ajax效果
      ajaxCount--
      checkAllAjaxDone()
      // #endregion
      if (code === 401) { // 无效 token
        removeToken() // 删除本地缓存
        removeExpires()
        //提示用户登录过期
        Message({
          message: '登录过期，请重新登录',
          type: 'error',
          duration: 3 * 1000,
          showClose: true
        })
        // 跳转到登录页面
        router.push('/login')
      } else {
        Message({
          message,
          type: 'error',
          duration: 3 * 1000,
          showClose: true
        })
        return Promise.reject(message)
      }
    }
  },
  error => {
    // #region 处理ajax效果
    ajaxCount--
    checkAllAjaxDone()
    // #endregion

    // 当用户登录过期，直接跳转登录页面
    if (error.response.status === 401 || error.response.data.code === 401) {
      removeToken()
      router.push('/login')
    }

    // 如果用户没定义异常处理，弹出统一友好提示
    MessageBox({
      type: 'error',
      message: '页面发生异常，请刷新页面重试或联系系统管理员', // error.response.data.message,
      title: '系统提示'
    })
    // if (error.response && error.response.data && error.response.data.message) {

    // }
    return Promise.reject(error.response)
  }
)

export default httpRequest
