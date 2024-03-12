<template>
  <div class="app-container">
    <div class="flex-row-ver-center">
      <div class="flex-auto-item">
        <el-input v-model="tableAbout.listQuery.message" placeholder="请输入留言内容" clearable></el-input>
      </div>
      <div class="flex-fixed-item">
        <el-select v-model="tableAbout.listQuery.tag" clearable placeholder="标签分类">
          <el-option v-for="item in tagList" :label="item" :value="item" :key="item">{{ item }}</el-option>
        </el-select>
      </div>
      <div class="flex-fixed-item">
        <el-button class="filter-item" style="margin-left: 14px" type="primary" icon="el-icon-search" @click="search">
          搜索
        </el-button>
        <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit"
          @click="handleCreate">
          管理标签
        </el-button>
      </div>
    </div>
    <div class="table-container">
      <el-table ref="multipleTable" height="calc(100% - 10px)" :key="tableAbout.tableKey" :data="tableAbout.tableData"
        border fit highlight-current-row class="normal-table">
        <el-table-column label="编号" prop="id" width="80px" align="center" />
        <el-table-column label="昵称" prop="nick_name" width="140px" />
        <el-table-column label="标签" prop="tag" width="140px" />
        <el-table-column label="留言内容" align="center">
          <template slot-scope="{ row }">
            <div v-html="parseSpan(row.message)"></div>
          </template>
        </el-table-column>
        <el-table-column label="添加时间" prop="localDateTime" align="center" width="180px" />
        <el-table-column label="操作" align="center" width="160px">
          <template slot-scope="{ row }">
            <el-button type="primary" size="mini" @click="handleUpdate(row)">
              编辑
            </el-button>
            <el-button size="mini" type="danger" @click="handleDelete(row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="text-right">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="tableAbout.listQuery.page" background :page-size="tableAbout.listQuery.pageSize"
        layout="total, sizes, prev, pager, next, jumper" :page-sizes="[10, 15, 30, 50, 100]"
        :total="tableAbout.listQuery.total">
      </el-pagination>
    </div>

    <el-dialog :title="editDialog.title[editDialog.status]" width="500px" :visible.sync="editDialog.visible">
      <el-form ref="editInfoForm" :model="editDataModel" :rules="editDialog.rules" label-position="right"
        label-width="80px">
        <el-form-item label="留言内容" prop="message">
          <el-input v-model="editDataModel.message" autocomplete="off" maxlength="20" placeholder="请输入资源名称"></el-input>
        </el-form-item>
        <el-form-item label="标签分类" prop="tag">
          <el-select v-model="editDataModel.tag" filterable placeholder="标签分类">
            <el-option v-for="item in tagList" :label="item" :value="item" :key="item">{{ item }}</el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialog.visible = false">取 消</el-button>
        <el-button type="primary" @click="updateMessageInfo">确 定</el-button>
      </span>
    </el-dialog>

    <el-drawer class="drawerTag" title="标签管理" :visible.sync="drawer" :direction="direction">
      <el-button class="add-tag-button" type="primary" @click="handelCreateTag">添加标签</el-button>

      <el-table :data="tagList" border stripe>
        <el-table-column label="标签" align="center">
          <template slot-scope="{ row }">
            <el-tag type="success">{{ row }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="{ row }">
            <el-button type="danger" round size="mini" @click="handleDeleteTag(row)">删除标签</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-drawer>


    <el-dialog title="添加标签" :visible.sync="dialogVisibleTag" width="30%">
      <el-form :model="editDataModel" :rules="editDialog.rules" ref="ruleForm" label-width="100px"
        class="demo-ruleForm">
        <el-form-item label="标签名称" prop="tag">
          <el-input v-model="editDataModel.tag"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisibleTag = false">取 消</el-button>
        <el-button type="primary" @click="saveMessageTag">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getMessageList, deleteMsg, updateMsg, getTagList, saveTag, deleteTag } from '@/api/message'
import { emptyChecker } from '@/utils/validate'

export default {
  name: 'SourceManagement',
  data() {
    return {
      // 文章列表相关属性
      tableAbout: {
        listQuery: {
          current: 1,
          size: 5,
          total: 0,
          tag: '',
          message: ''
        },
        tableKey: 0,
        tableData: []
      },
      drawer: false,
      direction: "rtl",
      dialogVisibleTag: false,
      // 资源分类列表
      tagList: [],

      // 页面用来编辑的数据模型
      editDataModel: {
        id: 0,
        message: "",
        color: "",
        font_size: 16,
        font_weight: 500,
        bg_color: "",
        bg_url: "",
        user_id: 0,
        tag: "",
        nick_name: "",
        bgList: [],
      },

      // 编辑用户信息弹窗数据模型
      editDialog: {
        title: ['新增资源', '编辑留言'],
        visible: false,
        status: 0,
        rules: {
          tag: [{ required: true, validator: emptyChecker, message: '标签名称不能为空', trigger: 'blur' }]
        }
      }
    }
  },
  mounted() {
    this.getList()
    this.getAllTagList()
  },
  methods: {
    //保存标签信息
    saveMessageTag() {
      const tagName = this.editDataModel.tag
      saveTag(tagName).then((res) => {
        this.editDataModel.tag = ''
        this.dialogVisibleTag = false
        this.getAllTagList()
      })
    },
    //新增标签
    handelCreateTag() {
      this.dialogVisibleTag = true
    },
    //删除标签内容
    handleDeleteTag(row) {
      const confirmMes = '是否确认删除该标签？'
      this.$confirm(confirmMes, '系统提示', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteTag({ tagName: row }).then((res) => {
          this.getAllTagList()
        })
      })

    },
    // 解析留言内容
    parseSpan(message) {
      let parser = new DOMParser();
      let doc = parser.parseFromString(message, 'text/html');
      return doc.body.textContent || "";
    },
    //获取留言标签
    getAllTagList() {
      getTagList().then((res) => {
        this.tagList = res
      })
    },
    // 跳转资源分类页面
    gotoCategory() {
      this.$router.push('/system/source-categories')
    },

    // 处理页码改变事件
    handleSizeChange(val) {
      this.tableAbout.listQuery.size = val
      this.search()
    },

    // 处理页码改变事件
    handleCurrentChange(val) {
      this.tableAbout.listQuery.current = val
      this.getList()
    },

    // 文章搜索方法
    search() {
      this.tableAbout.listQuery.current = 1
      this.getList()
    },

    // 列表查询方法
    getList() {
      const param = JSON.stringify(this.tableAbout.listQuery)
      getMessageList(param).then((res) => {
        this.tableAbout.tableData = res
        this.tableAbout.listQuery.total = res.length
      })
    },

    // 查询资源分类列表方法，作为选项
    getCategoryList() {

    },

    // 保存编辑信息方法
    updateMessageInfo() {
      this.$refs['editInfoForm'].validate((valid) => {
        if (valid) {
          updateMsg(JSON.stringify(this.editDataModel)).then(() => {
            this.$notify({
              title: '成功',
              message: '保存成功',
              type: 'success',
              duration: 2000
            })
            this.getList()
            this.editDialog.visible = false
          })
        }
      })
    },

    // 新增的时候重置编辑数据模型
    getAddUserModel() {
      this.editDataModel = {
        tag: ''
      }
    },

    openEditDialog(dialogStatus) {
      this.editDialog.status = dialogStatus
      if (this.$refs['editInfoForm']) {
        this.$refs['editInfoForm'].clearValidate()
      }
      this.editDialog.visible = true
    },

    // 新增按钮点击方法
    handleCreate() {
      this.getAddUserModel()
      this.drawer = true
    },
    // 修改按钮点击方法
    handleUpdate(row) {
      this.editDataModel = row
      this.editDataModel.message = this.parseSpan(row.message)
      this.openEditDialog(1)
    },
    // 行删除按钮处理
    handleDelete(row, index) {
      const confirmMes = '是否确认删除该留言？'
      this.$confirm(confirmMes, '系统提示', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteMsg({ id: row.id }).then((res) => {
          this.getList()
        })
      })
    }
  }
}
</script>

<style scoped>
.drawerTag {
  text-align: center;
}

.add-tag-button {
  margin-bottom: 10px;
  float: right;
  margin-right: 30px;
}
</style>