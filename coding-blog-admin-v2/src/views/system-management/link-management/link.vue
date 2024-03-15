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
        <el-table-column prop="linkId" label="序号" width="80" align="center">
        </el-table-column>
        <el-table-column prop="linkName" label="姓名" width="180">
        </el-table-column>
        <el-table-column prop="linkUrl" label="链接地址" width="350">
        </el-table-column>
        <el-table-column prop="linkDescription" label="链接描述" width="288">
        </el-table-column>
        <el-table-column label="链接背景" align="center" width="120">
          <template slot-scope="{row}">
            <el-popover v-if="isShowImage(row)" placement="right" trigger="hover">
              <el-image style="width: 360px; height: 240px" :src="showImage(row)" fit="cover"></el-image>
              <el-image slot="reference" style="width: 69px; height: 46px" :src="showImage(row)" fit="cover"></el-image>
            </el-popover>
            <el-image v-else :src="defaultArticleCoverSrc" fit="fill"></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="linkVisible" label="是否可见" align="center" width="100">
          <template slot-scope="{ row }">
            <el-switch v-model="row.linkVisible" active-value="Y" inactive-value="N"
              @change="handleStatusChange($event, row.linkId)">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="160px">
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
        <el-form-item label="友链名称" prop="linkName">
          <el-input v-model="editDataModel.linkName" autocomplete="off" maxlength="20" placeholder="请输入友链名称"></el-input>
        </el-form-item>
        <el-form-item label="友链URL" prop="linkUrl">
          <el-input v-model="editDataModel.linkUrl" autocomplete="off" maxlength="50" placeholder="请输入友链URL"></el-input>
        </el-form-item>
        <el-form-item label="友链描述" prop="linkDescription">
          <el-input v-model="editDataModel.linkDescription" autocomplete="off" maxlength="50"
            placeholder="请输入友链描述"></el-input>
        </el-form-item>
        <el-form-item label="友链图片">
          <ImageUpload  v-if="editDataModel.linkImage" :imagePath="editDataModel.linkImage" @image-uploaded="headerImageUploaded"/>
          <ImageUpload v-else @image-uploaded="headerImageUploaded" :width="160" :height="160"/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialog.visible = false">取 消</el-button>
        <el-button type="primary" @click="saveLinkInfo">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getLinks, setVisibility,saveLink,updateLink,deleteLink } from '@/api/link'
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
        linkName: '', // 友链名称
        linkUrl: '', // 友链URL
        linkDescription: '', // 友链描述
        linkImage: '' // 友链图片
      },

      editDialog: {
        title: ['新增友链', '编辑友链'],
        visible: false,
        status: 0,
        rules: {
          linkName: [{ required: true, validator: emptyChecker, message: '友链名称不能为空', trigger: 'blur' }],
          linkUrl: [{ required: true, validator: emptyChecker, message: '友链URL不能为空', trigger: 'blur' }],
          linkDescription: [{ required: true, validator: emptyChecker, message: '友链描述不能为空', trigger: 'blur' }]
        }
      }
    }
  },
  methods: {
    handleDelete(row) {
      this.$confirm('此操作将永久删除该友链, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteLink({linkId: row.linkId}).then(res => {
          this.$notify({
            title: '成功',
            message: '删除成功',
            type: 'success',
            duration: 2000
          })
          this.getLinkList()
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
    saveLinkInfo() {
      this.$refs['editInfoForm'].validate(valid => {
        if (valid) {
          const saveFunc = this.editDialog.status === 0 ? saveLink : updateLink

          saveFunc(JSON.stringify(this.editDataModel)).then(res => {
            this.$notify({
              title: '成功',
              message: '保存成功',
              type: 'success',
              duration: 2000
            })
            this.getLinkList()
            this.editDialog.visible = false
          })
        }
      })
    },
    headerImageUploaded(imagePath) {
      this.editDataModel.linkImage = imagePath
    },
    handleCreate() {
      this.getAddCategoryModel()
      this.openEditDialog(0)
    },
    getLinkList() {
      getLinks().then(res => {
        this.tableAbout.tableData = res
      })
    },
    isShowImage(row) {
      return row.linkImage !== ''
    },
    showImage(row) {
      return row.linkImage
    },
    // 启用/禁用用户事件处理方法
    handleStatusChange(status, linkId) {
      const reqData = qs.stringify({ hidden: status, linkId: linkId })
      setVisibility(reqData).then(res => {
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
        linkName: '', // 友链名称
        linkUrl: '', // 友链URL
        linkDescription: '', // 友链描述
        linkImage: '' // 友链图片
      }
    }
  },
  mounted() {
    this.getLinkList()
  }
}
</script>

<style></style>