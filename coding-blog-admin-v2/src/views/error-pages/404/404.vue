<template>
  <el-empty :image="picUrl" :description="discriptionText">
    <el-button type="primary" @click="fnReturnToFirst">返回首页</el-button>
  </el-empty>
</template>
<script>
export default {
  name: 'NoPower',
  data() {
    return {
      picUrl: require('@/assets/404.png'),
      discriptionText: '对不起，您访问的页面不存在'
    }
  },
  methods: {
    fnReturnToFirst() {
      this.$router.push({ name: this.$store.state.userMenus[0] })
    }
  }
}
</script>
