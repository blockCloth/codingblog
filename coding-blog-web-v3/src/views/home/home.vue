<script setup>
import { ref, reactive, onMounted, nextTick } from "vue";
import { user } from "@/store/index.js";

import { homeGetArticleList } from "@/api/article";
import { homeGetConfig } from "@/api/config";
import { getAllTag } from "@/api/tag";
import { randomFontColor, numberFormate } from "@/utils/tool";

import HomeArticleList from "@/components/HomeArticle/home-article-list.vue";
import RightSide from "@/components/RightSide/right-side.vue";
import MobileTopSkeleton from "@/components/RightSide/components/skeleton/mobile-top-skeleton.vue";
import RightSideItem from "@/components/RightSide/components/item/right-side-item.vue";
import RightSideTop from "@/components/RightSide/components/item/right-side-top.vue";
import RightSideSkeletonItem from "@/components/RightSide/components/skeleton/right-side-skeleton-item.vue";
import { gsapTransY } from "@/utils/transform";

const userStore = user();

/** 文章 */
const param = reactive({
  current: 1, // 当前页
  size: 5, // 每页条目数
  loading: true, // 加载
});
const articleList = ref([]);
const articleTotal = ref();

const getHomeArticleList = async (type) => {
  param.loading = true;
  type == "init" ? "" : (param.loading = true);
  let res = await homeGetArticleList(param.current, param.size);

  if (res.code == 200) {
    type == "init" ? "" : (param.loading = false);
    const { records, total } = res.result;
    records.map(record => {
      try {
        record.attribute = JSON.parse(record.attribute);
      } catch (error) {
        return record.attribute; // 如果解析失败，返回原始值
      }
    });

    articleList.value = records;
    articleTotal.value = total;
  }
  param.loading = false;
};

const pagination = (page) => {
  param.current = page.current;
  getHomeArticleList();
};

/** 网站右侧 */
const rightSizeLoading = ref(true);
const runtime = ref(0);
let configDetail = ref({
  site: {}
});
let tags = ref([]);

// 获取网站详细信息
const getConfigDetail = async () => {
  rightSizeLoading.value = true;
  let res = await homeGetConfig();

  console.log(res);
  if (res.code == 200 && typeof res.result != "string") {
    res.result.site = transformSite(res.result.site);

    configDetail.value = res.result;
    userStore.setBlogAvatar(configDetail.value.site.siteIntroduce.headerImage);
    calcRuntimeDays(configDetail.value.site.siteConsult.createTime);
  }
  rightSizeLoading.value = false;
};
// 转换site数据
const transformSite = (site) => {
  if (site.siteIntroduce) {
    site.siteIntroduce = JSON.parse(site.siteIntroduce);
  }
  if (site.siteDesc) {
    site.siteDesc = JSON.parse(site.siteDesc);
  }
  if (site.siteConsult) {
    site.siteConsult = JSON.parse(site.siteConsult);
  }
  return site;
};

// 获取所有的标签
const getAllTags = async () => {
  let res = await getAllTag();

  console.log(res);
  if (res.code == 200) {
    tags.value = res.result.map((r) => {
      r.color = randomFontColor();
      return r;
    });
  }
};
// 计算出网站运行天数
const calcRuntimeDays = (time) => {
  if (time) {
    // eslint-disable-next-line
    time = time.replace(/\-/g, "/"); // 解决ios系统上格式化时间出现NAN的bug
    const now = new Date().getTime();
    const created = new Date(time).getTime();
    const days = Math.floor((now - created) / 8.64e7);
    runtime.value = days;
  }
};

const init = async () => {
  await getHomeArticleList("init");
  await getConfigDetail();
  // await getStatistic();
  await getAllTags();
};

const observeMobileBox = () => {
  nextTick(() => {
    gsapTransY([".mobile-top-card", ".mobile-bottom-card"], -30, 0.5, "bounce.in");
    gsapTransY([".mobile-bottom-card"], 50, 0.6, "none");
  });
};

onMounted(async () => {
  await init();
  await observeMobileBox();
});
</script>

<template>
  <div class="home_center_box">
    <el-row>
      <el-col :xs="24" :sm="18">
        <el-card class="mobile-top-card mobile-card info-card animate__animated animate__fadeIn" shadow="hover">
          <el-skeleton :loading="rightSizeLoading" animated>
            <template #template>
              <MobileTopSkeleton />
            </template>
            <template #default>
              <RightSideTop :configDetail="configDetail" />
            </template>
          </el-skeleton>
        </el-card> 
        <!-- 博客文章 -->
        <HomeArticleList :articleList="articleList" :param="param" :articleTotal="articleTotal" @pageChange="pagination">
        </HomeArticleList>
        <el-card class="mobile-bottom-card card-hover mobile-card info-card animate__animated animate__fadeIn"
          shadow="hover">
          <el-skeleton :loading="rightSizeLoading" animated>
            <template #template>
              <RightSideSkeletonItem />
            </template>
            <template #default>
              <RightSideItem icon="icon-zixun" size="1.4rem" title="网站资讯">
                <div class="site-info">
                  <div class="flex_r_between">
                    <span>文章数目：</span>
                    <span class="value">{{ configDetail.postSize }}</span>
                  </div>
                  <div class="flex_r_between">
                    <span>运行时间：</span>
                    <span class="value">{{ runtime }} 天</span>
                  </div>
                  <div class="flex_r_between">
                    <span>博客访问次数：</span>
                    <span class="value">{{ numberFormate(configDetail.blogView) }}</span>
                  </div>
                </div>
              </RightSideItem>
            </template>
          </el-skeleton>
        </el-card>
      </el-col>
      <el-col :xs="0" :sm="6">
        <!-- 博客我的信息 -->
        <RightSide :configDetail="configDetail" :tags="tags" :runtime="runtime" :loading="rightSizeLoading" />
      </el-col>
    </el-row>
  </div>
</template>

<style lang="scss" scoped>
.mobile-top-card {
  height: 31rem;
  margin: 4px;

  :deep(.info-avatar) {
    padding: 0 2rem;
  }

  :deep(.personal-say) {
    padding-left: 1rem;
  }

  :deep(.info-background) {
    height: 12rem;
    width: 100%;
  }

  :deep(.common-menu) {
    padding: 1rem 5.5rem;
  }

  :deep(.git-ee) {
    padding: 0 4rem;
  }

  :deep(.personal-link) {
    padding: 1rem 6rem;
  }
}

.mobile-bottom-card {
  margin: 4px;
  padding: 1rem;

  .icon-localoffer {
    font-weight: 900;
  }

  span {
    margin-left: 0.3rem;
  }

  .site-info {
    padding: 0.3rem 1rem;
    line-height: 2;
    font-size: 1rem;

    .value {
      font-weight: 600;
    }
  }
}

.group {
  margin-left: 0.3rem;
  width: 100%;
  height: auto;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;

  .img {
    width: 80px;
    height: 80px;
  }
}
</style>
