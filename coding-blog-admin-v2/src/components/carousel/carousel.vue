<template>
    <el-carousel :height="carouselHeight" class="carousel-with-background">
        <el-carousel-item v-for="(image, index) in images" :key="index">
            <div class="carousel-item-content">
                <div v-if="image.url">
                    <img :src="image.url" class="carousel-image" />
                </div>

                <div v-else slot="error" class="image-slot">
                    <i class="el-icon-picture-outline"></i>
                </div>
            </div>
        </el-carousel-item>
        <!-- 添加增加按钮 -->
        <el-button @click="addImage">Add</el-button>
        <!-- 添加删除按钮 -->
        <el-button @click="deleteImage(index)">Delete</el-button>
    </el-carousel>
</template>
  
<script>
export default {
    data() {
        return {
            carouselHeight: '300px',
            images: [
                { url: 'image1.jpg' },
                { url: 'image2.jpg' },
                { url: 'image3.jpg' },
                // 添加更多的图片对象
            ]
        };
    }
};
</script>
  
<style>
.carousel-with-background {
    /* background-image: url('background.jpg'); */
    /* 设置背景图片的路径 */
    background-size: cover;
    /* 控制背景图片的适应方式 */
    background-position: center;
    /* 控制背景图片的位置 */
}

.carousel-item-content {
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
}

.carousel-image {
    max-width: 100%;
    max-height: 100%;
    object-fit: contain;
    /* 根据需要设置图片的适应方式 */
}
</style>