<template>
  <div class="app-container">
    <div class="flex-row-ver-center">
      <div class="flex-auto-item">
      </div>
      <div class="flex-fixed-item">
        <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit"
          @click="handleCreate">
          新增
        </el-button>
      </div>
    </div>
    <div class="table-container">
      <el-table :data="tableAbout.tableData" stripe border style="width: 100%">
        <el-table-column prop="pictureId" label="序号" width="110" align="center">
        </el-table-column>
        <el-table-column prop="pictureName" label="路由名称" width="180" align="center">
        </el-table-column>
        <el-table-column label="背景图片" align="center" width="345">
          <template slot-scope="{row}">
            <el-popover v-if="isShowImage(row)" placement="right" trigger="hover">
              <el-image style="width: 360px; height: 240px" :src="showImage(row)" fit="cover"></el-image>
              <el-image slot="reference" style="width: 140px; height: 79px" :src="showImage(row)" fit="cover"></el-image>
            </el-popover>
            <el-image v-else :src="defaultArticleCoverSrc" fit="fill"></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" align="center" width="240">
        </el-table-column>
        <el-table-column prop="isVisible" label="是否可见" align="center" width="160">
          <template slot-scope="{ row }">
            <el-switch v-model="row.isVisible" :active-value="0" :inactive-value="1"
              @change="handleStatusChange($event, row.pictureId)">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="240px">
          <template slot-scope="{ row }">
            <el-button type="primary" size="mini" @click="handleUpdate(row)">
              编辑
            </el-button>
            <el-button v-if="row.status != 'deleted'" size="mini" type="danger" @click="handleDelete(row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 详情编辑对话框 -->
    <el-dialog :title="editDialog.title[editDialog.status]" width="500px" :visible.sync="editDialog.visible">
      <el-form ref="editInfoForm" :model="editDataModel" :rules="editDialog.rules" label-position="right"
        label-width="80px">
        <el-form-item label="路由名称">
          <el-input v-model="editDataModel.pictureName" placeholder="请输入路由名称"></el-input>
        </el-form-item>
        <el-form-item label="背景图片">
          <ImageUpload  v-if="editDataModel.pictureUrl" :imagePath="editDataModel.pictureUrl" @image-uploaded="headerImageUploaded" :width="320" :height="200"/>
          <ImageUpload v-else @image-uploaded="headerImageUploaded" :width="320" :height="200"/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialog.visible = false">取 消</el-button>
        <el-button type="primary" @click="saveBackgroundImgInfo">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getBackgroundList, createBackground,deleteBackground,updateBackground,setEnableBackground } from '@/api/background'
import qs from 'qs'
import { emptyChecker } from '@/utils/validate'
import ImageUpload from '@/components/image'
import { Image } from 'element-ui'

export default {
  components: {
    ImageUpload,
    Image
},
  data() {
    return {
      // 文章列表相关属性
      tableAbout: {
        tableKey: 0,
        tableData: []
      },
      defaultArticleCoverSrc: require('@/assets/default-article-cover.png'),
      // 页面用来编辑的数据模型
      editDataModel: {
        pictureUrl: '',
        pictureName: ''
      },

      editDialog: {
        title: ['新增友链', '编辑友链'],
        visible: false,
        status: 0,
        rules: {
          pictureName: [{ required: true, validator: emptyChecker, message: '路由名称不能为空', trigger: 'blur' }],
          pictureUrl: [{ required: true, validator: emptyChecker, message: '背景图片URL不能为空', trigger: 'blur' }],
        }
      }
    }
  },
  methods: {
    handleDelete(row) {
      this.$confirm('此操作将永久删除该图片, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteBackground({pictureId: row.pictureId}).then(res => {
          this.$notify({
            title: '成功',
            message: '删除成功',
            type: 'success',
            duration: 2000
          })
          this.getBackgroundImgList()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    handleUpdate(row) {
      this.editDataModel = row
      this.openEditDialog(1)
    },
    saveBackgroundImgInfo() {
      this.$refs['editInfoForm'].validate(valid => {
        if (valid) {
          const saveFunc = this.editDialog.status === 0 ? createBackground : updateBackground

          saveFunc(JSON.stringify(this.editDataModel)).then(res => {
            this.$notify({
              title: '成功',
              message: '保存成功',
              type: 'success',
              duration: 2000
            })
            this.getBackgroundImgList()
            //重置编辑数据模型
            this.getAddCategoryModel()
            this.editDialog.visible = false
          })
        }
      })
    },
    headerImageUploaded(imagePath) {
      this.editDataModel.pictureUrl = imagePath
    },
    handleCreate() {
      this.getAddCategoryModel()
      this.openEditDialog(0)
    },
    getBackgroundImgList() {
      getBackgroundList().then(res => {
        this.tableAbout.tableData = res
      })
    },
    isShowImage(row) {
      return row.pictureUrl !== ''
    },
    showImage(row) {
      return row.pictureUrl
    },
    // 启用/禁用用户事件处理方法
    handleStatusChange(status, pictureId) {
      const reqData = qs.stringify({ status: status, pictureId: pictureId })
      setEnableBackground(reqData).then(res => {
        this.$message.success('操作成功')
      })
    },
    // 打开编辑对话框方法
    openEditDialog(dialogStatus) {
      this.editDialog.status = dialogStatus
      if (this.$refs['editInfoForm']) {
        this.$refs['editInfoForm'].clearValidate()
      }
      this.editDialog.visible = true
    },
    // 新增的时候重置编辑数据模型
    getAddCategoryModel() {
      this.editDataModel = {
        pictureUrl: '',
        pictureName: ''
      }
    }
  },
  mounted() {
    this.getBackgroundImgList()
  }
}
</script>

<style></style>