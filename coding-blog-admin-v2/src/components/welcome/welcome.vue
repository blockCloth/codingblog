<template>
    <div class="welcome-class">
        <div style="height: 100px;text-align: center;">
            <span>欢迎小黎同学前来视察工作！</span>
        </div>
        <el-carousel class="half-width-carousel" :interval="4000" type="card" height="400px">
            <el-carousel-item v-for="item in imgList" :key="item">
                <img :src="item" class="carousel-image" />
            </el-carousel-item>
        </el-carousel>

        <el-collapse class="half-width-collapse" v-model="activeName" accordion>
            <el-collapse-item title="2020-10-24" name="1">
                <p>这是一个特别的的日子，在这一天我跟一个大憨憨发生一段长达一辈子的故事! 我依稀记得
                    在这一天，某个大憨憨嘎嘎能吃，小嘴就没停过。也是在这一天就暴漏了她色色的属性，
                    还有一些小憨憨的事情，我就不一一列举了，
                    总之，这是一个特别的日子，我会永远记得这一天
                </p>
            </el-collapse-item>
            <el-collapse-item title="2021-10-24" name="2">
                <p>
                    当到这一天的时候，已经来到了我们恋爱一周年了，但是在这一天我们没有出门。因为我去了长沙
                    回来需要隔离，所以我们只能在学校度过这一天，我们买了一个小蛋糕吃。虽然这一天我们没有做什么
                    特别的事情，但是我知道，这一天对于我们来说是特别的，因为这一天是我们恋爱一周年的日子。
                </p>
            </el-collapse-item>
            <el-collapse-item title="2022-10-24" name="3">
                <p>
                    这一天，我们已经在一起两年了，这个时候因为疫情封校了，我们仍然在学校里度过。
                    虽然这一天我们没有做什么特别的事情，但是我们我个爆发了矛盾。不知道大憨憨还记不记得
                    原因，如果记得的话请立马call轩哥哟！
                </p>
            </el-collapse-item>
            <el-collapse-item title="2023-10-24" name="4">
                <p>
                    已经来到我们的第三年啦，这个时候我们已经在异地恋啦！我跟你相约周末见面，这次周年庆
                    我们去了公园里游玩，拍了英妹妹做飞椅的照片，我们还在这个时候约定三章、拍照要有耐心不能
                    骂人（某些人还是没有履行꒰╬•᷅д•᷄╬꒱）。还吃了小蛋糕、拍了纪念视频！这次是一个完美的周年庆！
                </p>
            </el-collapse-item>
            <el-collapse-item title="2024-10-24" name="5">
                <p style="font-weight: bold;">
                    From the moment I met you, my love for you has only grown stronger.
                </p>
            </el-collapse-item>
        </el-collapse>
    </div>
</template>

<script>
export default {
    data() {
        return {
            activeName: '0',
            imgList: [
                require('@/assets/beautiful/lxy1.jpg'),
                require('@/assets/beautiful/lxy2.jpg'),
                require('@/assets/beautiful/lxy3.jpg'),
                require('@/assets/beautiful/lxy4.jpg')
            ]
        };
    }
}
</script>
<style>
.welcome-class span {
    font-family: "Zhi Mang Xing", cursive;
    font-weight: 400;
    font-style: normal;
    font-size: 45px;
}

.carousel-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
    /* 使图片自适应走马灯的大小 */
}

.el-divider--vertical {
    height: 600px;
}

.half-width-collapse {
    width: 40%;
    /* 占用右边一半 */
    float: right;
    /* 浮动到右边 */
    margin-right: 8%;
}

.half-width-carousel {
    margin-left: 8%;
    width: 40%;
    /* 占用左边一半 */
    float: left;
    /* 浮动到左边 */
}

.el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 400px;
    margin: 0;
}

.el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
}
</style>