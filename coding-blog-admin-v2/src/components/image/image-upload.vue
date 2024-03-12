<template>
    <div>
        <div class="styleof-inlineblock" @mouseover="articleCoverOpLayerShow = true"
            @mouseleave="articleCoverOpLayerShow = false" :style="{ height: height + 'px', width: width + 'px' }">
            <el-upload class="article-cover" name="image" :action="uploadUrl" :headers="{ Authorization: getToken() }"
                :show-file-list="false" :on-success="handleArticleCoverSuccess" :before-upload="beforeArticleCoverUpload"
                :style="{ height: height + 'px', width: width + 'px' }">
                <el-image v-if="imageUrl" :src="imageUrl" fit="cover" class="article-cover"
                    :style="{ height: height + 'px', width: width + 'px' }"></el-image>
                <i v-else class="el-icon-plus cover-uploader-icon" :style="{ height: height + 'px', width: width + 'px' }"></i>
                <div v-show="imageUrl && articleCoverOpLayerShow" class="op-layer">
                    <el-button type="danger" size="mini"><i class="el-icon-refresh"></i>删除</el-button>
                </div>
            </el-upload>
        </div>
    </div>
</template>

<script>
import { getToken } from '@/utils/auth'
import { uploadUrl } from '@/api/image';
export default {
    props: {
        imagePath: {
            type: String,
            default: ''
        },
        width: {
            type: Number,
            default: 160
        },
        height: {
            type: Number,
            default: 160
        }
    },
    data() {
        return {
            uploadUrl,
            getToken,
            articleCoverOpLayerShow: false,
            imageUrl: ''
        };

    },
    methods: {
        // 上传文章封面图成功后回调函数
        handleArticleCoverSuccess(res, file) {
            if (res && res.code === 200) {
                console.log('图片上传信息',res)
                this.imageUrl = res.result.imagePath
                this.$emit('image-uploaded', res.result.imagePath);
            } else {
                this.$message.error('上传失败，请重试或联系管理员')
            }
        },
        // 上传文章封面图之前验证的方法
        beforeArticleCoverUpload(file) {
            const isLt1M = file.size / 1024 / 1024 < 10
            if (!isLt1M) {
                this.$message.error('封面图片大小不能超过10MB!')
            }
            return isLt1M
        }
    },
    mounted() {
        if (this.imagePath) {
            this.imageUrl = this.imagePath
        }
    },
    watch: {
        imagePath(newVal) {
            this.imageUrl = newVal
        }
    }
}
</script>

<style scoped></style>