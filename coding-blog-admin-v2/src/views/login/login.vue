<template>
  <div class="outer">
    <el-card class="box-card">
      <div slot="header" class="clearfix login-img">
        <!-- <h2>CodingMore后台管理登陆</h2> -->
        <img :src="logoUrl" width="340" height="220" alight="center"/>
      </div>
      <el-form ref="form" :model="form" label-width="60px">
        <el-form-item label="用户名">
          <el-input v-model="form.userLogin" maxlength="30" @keydown.native.enter="btnLoginClick"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input type="password" v-model="form.userPass" show-password maxlength="50" @keydown.native.enter="btnLoginClick"></el-input>
        </el-form-item>
        <div class="text-right">
          <el-button type="primary" @click="btnLoginClick">登陆</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>
<script>
import { UserLogin } from '../../api/login'
import qs from 'qs'
import { setToken,setExpires } from '../../utils/auth'
export default {
  name: 'login',
  data() {
    return {
      form: {
        userLogin: 'admin',
        userPass: ''
      },
      // 添加标签对话框可见性
      qrDialogVisible: false,

      // 公众号二维码 URL
      // itwangerQrcodeUrl: require('@/assets/wanger-qrcode.jpg'),

      // logo图片url
      logoUrl: 'https://blockcloth.cn/codingblog/login-main-2.png'
    }
  },
  methods: {
    // 登陆按钮方法
    btnLoginClick() {
      const reqData = qs.stringify(this.form)
      UserLogin(reqData).then((res) => {
        // 先保存token到cookie
        setToken(`${res.tokenHead}${res.token}`,res.expiration)
        //设置过期时间
        setExpires(res.expiration)
        // 之后跳转页面到首页
        this.$router.push('/')
      })
    },

  }
}
</script>
<style scoped>
.box-card {
  width: 400px;
}
.box-card h2 {
  margin: 0;
  padding: 0;
}
.outer {
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background: url(https://pic4.zhimg.com/80/v2-8e470221c2ccb98da5eaac20c8e95e6f_720w.webp) no-repeat center center;
  /* background: url(https://cdn.tobebetterjavaer.com/codingmore-admin-web/login-bg.jpeg) no-repeat center center; */
  background-size: 100% 100%;
}
.color-blue {
  color: #409eff;
}

/* 二维码上方文字样式 */
.font-title-large {
  font-size: 18px;
  color: #303133;
  margin-bottom: 40px;
}
/* 二维码样式 */
.img-qrcode {
  width: 160px;
  height: 160px;
}
.login-img{
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
