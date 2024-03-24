<template>
    <div>
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <span>说说列表</span>
                <el-button style="float: right; padding: 3px 0" type="text" @click="handlerCreate">添加说说</el-button>
            </div>
            <div class="text item">
                <div class="card-container">
                    <el-card class="box-card card-split parent" v-for="talk in talksList" :key="talk.index">
                        <div slot="header" class="clearfix"
                            style="display: flex; justify-content: right; align-items: center;">
                            <el-dropdown>
                                <span class="el-dropdown-link">
                                    <i class="el-icon-arrow-down el-icon--right"></i>
                                </span>
                                <el-dropdown-menu slot="dropdown">
                                    <el-dropdown-item @click="alert('111')">
                                        <el-button type="text" @click="deleteTalkById(talk.talkId)">删除</el-button>
                                    </el-dropdown-item>
                                    <el-dropdown-item v-if="talk.talkOrder == 1">
                                        <el-button type="text" @click="setTalkOrder(talk.talkId, 0)">取消置顶</el-button>
                                    </el-dropdown-item>
                                    <el-dropdown-item v-else>
                                        <el-button type="text" @click="setTalkOrder(talk.talkId, 1)">置顶</el-button>
                                    </el-dropdown-item>
                                    <el-dropdown-item v-if="talk.talkStatus == 1">
                                        <el-button type="text" @click="setTalkStatus(talk.talkId, 0)">隐藏</el-button>
                                    </el-dropdown-item>
                                    <el-dropdown-item v-else>
                                        <el-button type="text" @click="setTalkStatus(talk.talkId, 1)">显示</el-button>
                                    </el-dropdown-item>
                                </el-dropdown-menu>
                            </el-dropdown>
                        </div>
                        <div class="talk-header">
                            <img :src="userAvatar" alt="User Avatar" class="talk-avatar">
                            <div class="talk-user">
                                <div class="talk-username">{{ user }}</div>
                                <p>{{ talk.talkContent }}</p>
                            </div>
                        </div>
                        <div class="talk-content">
                            <template>
                                <div v-if="talk.talkImages">
                                    <el-image v-for="photo in talkImages(talk.talkImages)" :src="photo" :key="photo"
                                        :preview-src-list="talkImages(talk.talkImages)" class="photo" />
                                </div>
                            </template>

                        </div>
                        <div class="talk-days">
                            <span>{{ calculateDays(talk.createDate) + '前' }}</span>
                        </div>

                    </el-card>
                </div>
            </div>
        </el-card>


        <el-dialog :title="editDialog.title[editDialog.status]" width="500px" :visible.sync="editDialog.visible">
            <el-form ref="editInfoForm" :model="editDataModel" :rules="editDialog.rules" label-position="right"
                label-width="80px">
                <el-form-item label="说说内容" prop="talkContent">
                    <el-input v-model="editDataModel.talkContent" autocomplete="off" maxlength="20"
                        placeholder="请输入说说内容"></el-input>
                </el-form-item>
                <el-form-item label="说说图片" prop="talkImages">
                    <div class="photo-wall">
                        <div v-if="editDataModel.talkImages">
                            <el-image v-for="photo in editDataModel.talkImages" :src="photo" :key="photo"
                                :preview-src-list="editDataModel.talkImages" class="photo" />
                        </div>
                        <!-- 上传图片 -->
                        <el-upload class="avatar-uploader" name="image" :action="uploadUrl"
                            :headers="{ Authorization: getToken() }" :on-success="handleAvatarSuccess"
                            :before-upload="beforeAvatarUpload" :show-file-list="false">
                            <i class="el-icon-plus avatar-uploader-icon"></i>
                        </el-upload>
                    </div>
                </el-form-item>

            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editDialog.visible = false">取 消</el-button>
                <el-button type="primary" @click="saveTalkInfo">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import { getTalkList, saveTalk, deleteTalk, setTalkStatus, setTalkOrder } from '@/api/talk'
import { uploadUrl } from '@/api/image'
import { getToken } from '@/utils/auth'
import { emptyChecker } from '@/utils/validate'

export default {
    data() {
        return {
            uploadUrl,
            getToken,
            talksList: [],
            userAvatar: '',
            uses: '',
            photos: [],
            // 编辑说说信息弹窗数据模型
            editDialog: {
                title: ['新增说说', '编辑说说'],
                visible: false,
                status: 0,
                rules: {
                    talkContent: [{ required: true, validator: emptyChecker, message: '说说内容不能为空', trigger: 'blur' }],
                }
            },
            editDataModel: {
                talkId: null,
                talkContent: '',
                talkImages: []
            }
        }
    },
    methods: {
        calculateDays(time) {
            time = time.replace(/-/g, "/"); // 解决ios系统上格式化时间出现NAN的bug
            const times = new Date().getTime() - new Date(time).getTime();
            let res;
            if (times < 6e4) {
                res = Math.trunc(times / 1000) + "秒";
            } else if (times >= 6e4 && times < 3.6e6) {
                res = Math.trunc(times / 6e4) + "分钟";
            } else if (times >= 3.6e6 && times < 8.64e7) {
                res = Math.trunc(times / 3.6e6) + "小时";
            } else {
                res = Math.trunc(times / 8.64e7) + "天";
            }
            return res;
        },
        // 修改说说状态
        setTalkStatus(talkId, talkStatus) {
            setTalkStatus({ talkId: talkId, talkStatus: talkStatus }).then(res => {
                this.$message.success('操作成功')
                this.getAllTalksList()
            })
        },
        // 置顶说说
        setTalkOrder(talkId, talkOrder) {
            setTalkOrder({ talkId: talkId, talkOrder: talkOrder }).then(res => {
                this.$message.success('操作成功')
                this.getAllTalksList()
            })
        },
        // 删除说说
        deleteTalkById(talkId) {
            this.$confirm('此操作将永久删除该说说, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                deleteTalk({ talkId: talkId }).then(res => {
                    this.$message.success('删除成功')
                    this.getAllTalksList()

                })
            }).catch(() => {
                this.$message.info('已取消删除')
            })
        },
        // 保存说说信息
        saveTalkInfo() {
            this.$refs['editInfoForm'].validate((valid) => {
                if (valid) {
                    saveTalk(JSON.stringify(this.editDataModel)).then(res => {
                        if (res) {
                            this.$message.success('保存成功')
                            this.editDialog.visible = false
                            this.getAllTalksList()
                            this.editDataModel = {
                                talkId: null,
                                talkContent: '',
                                talkImages: []
                            }
                        }
                    })
                }
            })
        },
        handleAvatarSuccess(res, file) {
            if (res && res.code === 200) {
                this.editDataModel.talkImages.push(res.result.imagePath)
            } else {
                this.$message.error('上传失败，请重试或联系管理员')
            }
        },
        beforeAvatarUpload(file) {
            const isLt2M = file.size / 1024 / 1024 < 10;

            if (!isLt2M) {
                this.$message.error('上传图片大小不能超过 10MB!');
            }
            return isLt2M;
        },
        getAllTalksList() {
            getTalkList().then(res => {
                console.log(res)
                this.talksList = res
            })
        },
        userAvatarUrl() {
            this.userAvatar = this.$store.state.userInfo.userDetail.userUrl
            this.user = this.$store.state.userInfo.userDetail.userLogin
        },
        talkImages(talkImage) {
            return talkImage.split(',')
        },

        // 打开编辑对话框方法
        openEditDialog(dialogStatus) {
            this.editDialog.status = dialogStatus
            if (this.$refs['editInfoForm']) {
                this.$refs['editInfoForm'].clearValidate()
            }
            this.editDialog.visible = true
        },
        // 保存说说信息
        handlerCreate() {
            this.openEditDialog(0)
        },
    },
    mounted() {
        this.getAllTalksList()
        this.userAvatarUrl()
    },

}
</script>

<style scoped>
.parent {
    position: relative;
    /* make the parent a positioning context */
    min-height: 100%;
    /* ensure the parent takes up at least the full height */
}

.talk-days {
    position: absolute;
    /* position talk-days relative to the parent */
    bottom: 20;
    /* align talk-days to the bottom of the parent */
    display: flex;
    justify-content: flex-end;
    color: #888;
    font-size: 0.8em;
}

.card-container {
    display: flex;
    flex-wrap: wrap;
}

.box-card {
    flex: 0 0 35%;
    box-sizing: border-box;
    padding: 10px;
}

.card-split {
    margin-left: 10%;
    margin-bottom: 5%;
}


.talk-header {
    display: flex;
    align-items: flex-start;
}

.talk-avatar {
    width: 50px;
    height: 50px;
    margin-right: 10px;
}

.talk-user {
    display: flex;
    flex-direction: column;
}

.talk-username {
    font-weight: bold;
}

.talk-date {
    font-size: 0.8em;
    color: #888;
}

.talk-content {
    margin-top: 10px;
}

.photo {
    width: 100px;
    height: 100px;
    object-fit: cover;
    margin: 5px;
}

.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}

.avatar-uploader .el-upload:hover {
    border-color: #409EFF;
}

.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    height: 100px;
    width: 100px;
    line-height: 100px;
    text-align: center;
}

.avatar {
    width: 100px;
    height: 100px;
    display: block;
}

.photo-wall {
    display: flex;
    flex-wrap: wrap;
    justify-content: start;
}

.photo,
.avatar-uploader {
    flex: 1 0 30%;
    margin: 10px;
}

@media (max-width: 600px) {

    .photo,
    .avatar-uploader {
        flex: 1 0 100%;
    }
}
</style>