import Cookies from 'js-cookie'
// import { createUuid } from './common'

// 随机存储cookie的token key
// 2022-02-11 改为不随机了，因为每次刷新页面会重新创建uuid，所以每次刷新页面会导致无法获取到本地已有缓存
const TokenKey = '1D596CD8-8A20-4CEC-98DD-CDC12282D65C' // createUuid()

export function getToken () {
  return Cookies.get(TokenKey)
}

export function setToken (token,expires) {
  return Cookies.set(TokenKey, token,{'expires':expires}) //并设置超时时间
}

export function removeToken () {
  return Cookies.remove(TokenKey)
}

// 将expiresAt过期时间写入cookie
const expiresAtKey = 'ExpiresAt'

export function getExpires() {
    return Cookies.get(expiresAtKey)
}

export function setExpires(expiresAt, expires) {
    return Cookies.set(expiresAtKey, expiresAt, { expires: expires })
}

export function removeExpires() {
    return Cookies.remove(expiresAtKey)
}
