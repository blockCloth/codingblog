<template>
  <div class="app-container">
    <div class="flex-row-ver-center">
      <div class="flex-auto-item">
        <el-input v-model="tableAbout.listQuery.postsTitle" placeholder="请输入标题"></el-input>
      </div>
      <div class="flex-fixed-item">
        <el-select v-model="tableAbout.listQuery.postStatus" clearable placeholder="文章状态">
          <el-option v-for="item in statusList" :label="item.label" :value="item.value" :key="item.value">{{ item.label
          }}</el-option>
        </el-select>
      </div>
      <div class="flex-fixed-item">
        <el-button class="filter-item" style="margin-left:14px;" type="primary" icon="el-icon-search" @click="search">
          搜索
        </el-button>
        <!-- <el-button class="filter-item" type="primary" @click="setArticleColumns">
          分配文章
        </el-button> -->
        <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit"
          @click="handleCreate">
          新增
        </el-button>
      </div>
    </div>

    <div class="table-container">
      <el-table ref="multipleTable" height="calc(100% - 10px)" :key="tableAbout.tableKey" :data="tableAbout.tableData"
        border fit highlight-current-row class="normal-table" @selection-change="handleSelectionChange">
        <!-- <el-table-column align="center" class-name="recorrect-center" type="selection" width="55px" /> -->
        <el-table-column label="编号" prop="postsId" width="80px" align="center" />
        <el-table-column label="封面图" width="80px" align="center" class-name="article-cover-col">
          <template slot-scope="{row}">
            <el-popover v-if="isShowImage(row)" placement="right" trigger="hover">
              <el-image style="width: 360px; height: 240px" :src="showImage(row)" fit="cover"></el-image>
              <el-image slot="reference" style="width: 69px; height: 46px" :src="showImage(row)" fit="cover"></el-image>
            </el-popover>
            <el-image v-else :src="defaultArticleCoverSrc" fit="fill"></el-image>
          </template>
        </el-table-column>
        <el-table-column label="标题" prop="postTitle">
          <template slot-scope="{row}">
            <img :src="onTopImageSrc" class="icon-ontop" v-if="row.menuOrder !== 0" />
            <el-link type="primary" @click="handleUpdate(row)">{{ row.postTitle }}</el-link>
          </template>
        </el-table-column>
        <!-- <el-table-column label="摘要" prop="postExcerpt" width="200px" show-overflow-tooltip /> -->
        <el-table-column label="详情" width="100px" align="center">
          <template slot-scope="{row}">
            <el-button type="primary" icon="el-icon-reading" @click="articleDetail(row)" />
          </template>
        </el-table-column>
        <el-table-column label="操作/发布时间" prop="postDate" width="155px" align="center">
          <template slot-scope="{row}">
            {{ row.postDate ? row.postDate.substr(0, 16) : row.postModified.substr(0, 16) }}
          </template>
        </el-table-column>
        <el-table-column label="状态" prop="postStatus" width="80px" :formatter="statusFilter" align="center">
          <template slot-scope="{row}">
            <el-tag :type="row.postStatus == 'DRAFT' ? 'info' : 'success'">
              {{ statusFilter(row) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="置顶" prop="menuOrder" width="80px" align="center">
          <template slot-scope="{row}">
            <el-switch v-model="row.menuOrder" :active-value="1" :inactive-value="0"
              @change="handleSettingOnTop($event, row)">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="180px">
          <template slot-scope="{row,$index}">
            <!-- <el-button type="primary" size="mini" @click="handleSettingOnTop(row)">
              {{ row.menuOrder === 0 ? '置顶' : '取消置顶'}}
            </el-button> -->
            <el-button type="primary" size="mini" @click="handleUpdate(row)">
              编辑
            </el-button>
            <el-button v-if="row.status != 'deleted'" size="mini" type="danger" @click="handleDelete(row, $index)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="text-right">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="tableAbout.listQuery.page" background :page-size="tableAbout.listQuery.pageSize"
        layout="total, sizes, prev, pager, next, jumper" :page-sizes="[1, 10, 15, 30, 50, 100]"
        :total="tableAbout.listQuery.total">
      </el-pagination>
    </div>

    <el-dialog class="dialogHtml" :title="articleDetailData.postTitle" :visible.sync="centerDialogVisible" width="70%"
      center>
      <div class="articleHeader">
        <hr>
        <el-descriptions class="margin-top" :column="2" border>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user"></i>
              作者名
            </template>
            <el-tag type="success" size="small">{{ articleDetailData.postAuthor }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-s-ticket"></i>
              所属专栏
            </template>
            <el-tag type="success" size="small">{{ articleDetailData.termTaxonomy }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-collection-tag"></i>
              标签
            </template>
            <el-tag type="warning" style="margin-left: 5px;" size="small" v-for="item in tags" :key="item">
              {{ item }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-date"></i>
              操作或发布时间
            </template>
            {{ articleDetailData.postDate }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-tickets"></i>
              文章状态
            </template>
            <el-tag type="success" size="small">{{ articleDetailData.postStatus }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-view"></i>
              浏览量
            </template>
            {{ articleDetailData.pageView }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-reading"></i>
              摘要
            </template>
            {{ articleDetailData.postExcerpt }}
          </el-descriptions-item>
        </el-descriptions>

        <div class="htmlContentImg" v-html="parsedMarkdown"></div>

      </div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="centerDialogVisible = false">关闭</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import { getArticlePagedList, deleteArticle, getArticleById, setArticleOnTop, cancelArticleOnTop, getAuthorName } from '@/api/articles'
import { loopExpendTree } from '@/utils/common'
import { getAllColumns } from '@/api/columns'
import MarkdownIt from 'markdown-it';
import qs from 'qs'
import './page.css'

export default {
  name: 'ArticlesManagement',
  data() {
    return {
      // 默认文章图片路径
      defaultArticleCoverSrc: require('@/assets/default-article-cover.png'),

      statusList: [{ value: 'DRAFT', label: '草稿' }, { value: 'PUBLISHED', label: '发布' }], // { value: 'DELETED', label: '删除' },

      // 文章列表相关属性
      tableAbout: {
        listQuery: {
          pageNum: 1,
          pageSize: 10,
          total: 0,
          // orderBy: 'menu_order,post_modified',
          // asc: false,
          postsTitle: '',
          postStatus: ''
        },
        tableKey: 0,
        tableData: [],
        selectedRowList: [] // 被选中行集合
      },

      // 页面用来编辑的数据模型
      editDataModel: {
        postsId: undefined,
        postTitle: '', // 标题
        postDate: '', // 发布时间
        postContent: '', // 正文
        postExcerpt: '', // 摘要
        menuOrder: '', // 排序号
        postType: 'POST', // 文章类型
        postStatus: 'DRAFT', // 文章状态
        termTaxonomyId: '', // 所属栏目id
        attribute: '', // 属性
        tags: '', // 标签
      },

      // 置顶图标路径
      onTopImageSrc: require('@/assets/icon-ontop.png'),

      //文章详情弹窗
      centerDialogVisible: false,
      //文章详细信息
      articleDetailData: {
        postTitle: '',
        postDate: '', // 发布时间
        htmlContent: '', // html正文
        postContent: '', // 正文
        postExcerpt: '', // 摘要
        postType: '', // 文章类型
        postStatus: '', // 文章状态
        termTaxonomy: '', // 所属栏目
        pageView: '', // 浏览量
        postAuthor: '' // 作者
      },
      tags: [], // 标签
      md: null
    }
  },
  mounted() {
    // 获取文章列表
    this.getList()
    // 获取专栏树数据
    // this.getTreeData()

    let _this = this
    window.refleshTable = (requestColumnId) => {
      if (_this.$route.name === 'article-management') {
        if (_this.tableAbout.listQuery.termTaxonomyId == requestColumnId ||
          _this.tableAbout.listQuery.termTaxonomyId == '') {
          _this.search()
        }
      }
    }
  },
  created() {
    this.md = new MarkdownIt();
  },
  computed: {
    parsedMarkdown() {
      return this.md.render(this.articleDetailData.postContent);
    }
  },
  methods: {
    //展示文章详情
    articleDetail(res) {
      getArticleById({ postId: res.postsId }).then(res => {
        this.articleDetailData = res.posts
        this.articleDetailData.termTaxonomy = res.termTaxonomy.name
        res.postTagList.forEach(item => {
          this.tags.push(item.description)
        })
      })

      //获取作者
      getAuthorName({ userId: res.postAuthor }).then(res => {
        this.articleDetailData.postAuthor = res.userLogin
      })

      this.centerDialogVisible = true
    },

    // 显示图片
    showImage(row) {
      return JSON.parse(row.attribute).articleCoverUrl
    },
    // 是否显示图片
    isShowImage(row) {
      return row.attribute && JSON.parse(row.attribute).articleCoverUrl
    },

    // 处理页码改变事件
    handleSizeChange(val) {
      this.tableAbout.listQuery.pageSize = val
      this.search()
    },

    // 处理页码改变事件
    handleCurrentChange(val) {
      this.tableAbout.listQuery.pageNum = val
      this.getList()
    },

    // 状态翻译
    statusFilter(row) {
      let statusText = ''
      const filterArr = this.statusList.filter(item => item.value === row.postStatus)
      if (filterArr.length > 0) {
        statusText = filterArr[0].label
      }
      return statusText
    },

    // 文章搜索方法
    search() {
      this.tableAbout.listQuery.page = 1
      this.getList()
    },

    // 文章列表查询方法
    getList() {
      getArticlePagedList(this.tableAbout.listQuery).then(res => {

        this.tableAbout.tableData = res.records
        this.tableAbout.listQuery.total = res.total
      })
    },

    // 处理table选中行改变方法
    handleSelectionChange(val) {
      this.tableAbout.selectedRowList = val
    },

    // 新增按钮点击方法
    handleCreate() {
      // this.openEditPage(null)
      this.$router.push('/content/article-editing')
    },
    // 修改按钮点击方法
    handleUpdate(row) {
      // this.openEditPage(row.postsId)
      // this.$router.push(`/content/article-editing?aid=${row.postsId}`)

      this.$router.push(`/content/article-modify?aid=${row.postsId}`)
    },
    // 行删除按钮处理
    handleDelete(row, index) {
      const confirmMes = '是否确认删除该文章？'
      this.$confirm(confirmMes, '系统提示', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteArticle({ postId: row.postsId }).then(() => {
          this.$notify({
            title: '成功',
            message: '删除成功',
            type: 'success',
            duration: 2000
          })
          this.getList()
        })
      })
    },

    // 置顶/取消置顶方法
    handleSettingOnTop(status, row) {
      const reqFunc = status === 0 ? cancelArticleOnTop : setArticleOnTop
      let reqData = qs.stringify({ postsId: row.postsId })
      reqFunc(reqData).then(() => {
        this.$notify({
          title: '成功',
          message: '操作成功',
          type: 'success',
          duration: 2000
        })
        this.getList()
      })
    },

    // 打开文章编辑页面
    openEditPage(aid) {
      let url = '/#/content/article-editing'
      if (aid) {
        url = `${url}?aid=${aid}`
      }
      window.open(url)
    }
  }
}
</script>

<style>
.htmlContentImg img {
  max-width: 100%;
}
</style>