<template>
  <div class="app-container config-container">
    <el-form ref="form" :model="siteInfo" :rules="rules" label-position="right" label-width="120px">
      <el-form-item label="站点名称" prop="siteName">
        <el-input v-model="siteInfo.siteName" placeholder="请填写站点信息" maxlength="100"></el-input>
      </el-form-item>
      <el-form-item label="公告" prop="announcement">
        <el-input v-model="siteInfo.announcement" placeholder="请填写公告信息，不超过1000字" maxlength="1000" :rows="3" type="textarea"></el-input>
      </el-form-item>
      <el-form-item label="简介" prop="siteIntroduce.introduce">
        <el-input v-model="siteInfo.siteIntroduce.introduce" placeholder="请填写简介信息" :rows="3" type="textarea"></el-input>
      </el-form-item>
      <el-form-item label="GitHub" prop="siteIntroduce.githubLink">
        <el-input v-model="siteInfo.siteIntroduce.githubLink" placeholder="请填写github链接" :rows="3" type="textarea"></el-input>
      </el-form-item>
      <el-form-item label="站点描述" prop="siteDesc.message">
        <el-input v-model="siteInfo.siteDesc.message" placeholder="请填写站点信息" :rows="3" type="textarea"></el-input>
      </el-form-item>
      <el-form-item label="备案信息" prop="siteDesc.psInfo">
        <el-input v-model="siteInfo.siteDesc.psInfo" placeholder="请填写公安备案信息" :rows="3" type="textarea"></el-input>
      </el-form-item>
      <el-form-item label="头像">
        <ImageUpload v-if="siteInfo.siteIntroduce.headerImage && siteInfo.siteIntroduce"
         @image-uploaded="headerImageUploaded" 
         :imagePath="siteInfo.siteIntroduce.headerImage" 
         :width="160" :height="160"/>
      </el-form-item>
      <el-form-item label="背景">
        <ImageUpload v-if="siteInfo.siteIntroduce.background && siteInfo.siteIntroduce" 
        @image-uploaded="backgroundImageUploaded" 
        :imagePath="siteInfo.siteIntroduce.background"
        :width="160" :height="230"/>
      </el-form-item>
      <el-form-item label="WX">
        <ImageUpload v-if="siteInfo.siteIntroduce.wxImage && siteInfo.siteIntroduce" 
        @image-uploaded="wxImageUploaded" 
        :imagePath="siteInfo.siteIntroduce.wxImage"
        :width="160" :height="230"/>
      </el-form-item>
      <el-form-item label="QQ">
        <ImageUpload v-if="siteInfo.siteIntroduce.qqImage && siteInfo.siteIntroduce" 
        @image-uploaded="qqImageUploaded" 
        :imagePath="siteInfo.siteIntroduce.qqImage"
        :width="160" :height="230"/>
      </el-form-item>    
    </el-form>
    <div class="text-center">
      <el-button type="primary" @click="saveData">保存</el-button>
    </div>
  </div>
</template>
<script>
import { emptyChecker } from '@/utils/validate'
import ImageUpload from '@/components/image'
import { assignSameProperty } from '@/utils/common'
import qs from 'qs'
import { getSiteConfigInfo, updateSiteConfig } from '@/api/site'
export default {
  name: 'siteConfig',
  components: {
    ImageUpload
  },
  data() {
    return {
      siteInfo: {
        siteName: '', // 站点名称
        announcement: '', // 公告
        // 站点简介
        siteIntroduce: {
          headerImage: '', // 头像
          background: '', // 背景
          introduce: '',// 简介
          githubLink: '', // github链接
          wxImage: '', // 微信二维码
          qqImage: '', // qq二维码
        },
        // 站点咨询
        siteConsult: {

        },
        //站点备案信息
        siteDesc:{
          message: '', // 站点描述
          psInfo: '', // 公安网备案信息
        }
      },

      rules: {
        siteName: [{ required: true, validator: emptyChecker, message: '站点名称不能为空', trigger: 'blur' }],
        // keywords: [{ required: true, validator: emptyChecker, message: '关键字不能为空', trigger: 'blur' }]
      }
    }
  },
  methods: {
    // 加载数据方法
    loadData() {
      getSiteConfigInfo().then(res => {
        this.siteInfo.siteName = res.siteName
        this.siteInfo.announcement = res.announcement
        if (res.siteIntroduce) {
          this.siteInfo.siteIntroduce = JSON.parse(res.siteIntroduce);
        }
        if (res.siteDesc) {
          this.siteInfo.siteDesc = JSON.parse(res.siteDesc);
        }
        if (res.siteConsult) {
          this.siteInfo.siteConsult = JSON.parse(res.siteConsult);
        }
      })
    },

    headerImageUploaded(imagePath) {
      this.siteInfo.siteIntroduce.headerImage = imagePath
    },
    backgroundImageUploaded(imagePath) {
      this.siteInfo.siteIntroduce.background = imagePath
    },
    wxImageUploaded(imagePath) {
      this.siteInfo.siteIntroduce.wxImage = imagePath
    },
    qqImageUploaded(imagePath) {
      this.siteInfo.siteIntroduce.qqImage = imagePath
    },

    // 保存站点设置方法
    saveData() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          let { siteName, announcement } = this.siteInfo
          let reqData = {
            siteName, // 站点名称
            announcement, // 公告
            siteIntroduce: '', // 站点描述
            siteDesc: '' ,// 备案信息
            siteConsult: '', // 站点咨询
          }
          reqData.siteIntroduce = JSON.stringify(this.siteInfo.siteIntroduce)
          reqData.siteDesc = JSON.stringify(this.siteInfo.siteDesc)
          reqData.siteConsult = JSON.stringify(this.siteInfo.siteConsult)

          reqData = JSON.stringify(reqData)
          updateSiteConfig(reqData).then(() => {
            this.$notify({
              title: '成功',
              message: '保存成功',
              type: 'success',
              duration: 2000
            })
            this.loadData()
          })
        }
      })
    }
  },
  mounted() {
    this.loadData()
  }
}
</script>
<style scoped>
.config-container {
  width: 800px;
  margin: 0 auto;
}
</style>
