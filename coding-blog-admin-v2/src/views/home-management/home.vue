<template>
  <div v-if="userInfo && userInfo.username === 'lixiaoying'">
    <welcome></welcome>
  </div>
  <div v-else class="centered-text">
    {{ msg }}
  </div>
</template>

<script>
import welcome from '@/components/welcome/welcome.vue'

export default {
  components: {
    welcome
    
  },
  data() {
    return {
      msg: 'Coding-Blog后台管理系统',
      userInfo: null
    }
  },
  methods: {
    // 获取用户信息
    getUserInfo() {
      this.userInfo = this.$store.state.userInfo
    }
  },
  mounted() {
    this.getUserInfo()
  }
}
</script>

<style>
.centered-text {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 60vh; /* 设置高度为视口的高度，使文本垂直居中 */
  font-size: 60px;
  color: #409EFF;
  font-family: 'Brush Script MT', cursive; /* 使用艺术字体 */
}
</style>