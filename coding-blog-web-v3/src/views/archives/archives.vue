<script setup>
import { ref, reactive, onMounted } from "vue";

import { blogTimelineGetArticleList } from "@/api/article";

import PageHeader from "@/components/Header/PageHeader.vue";
import TimeLine from "@/components/TimeLine/time-line.vue";

const archives = ref([]);
let param = reactive({
  // 放置页码及相关数据
  current: 1, //当前页
  size: 5, //每页条目数
});
let archivesTotal = ref(0); // 记录总数
const loading = ref(false);

const pagination = (page) => {
  param.current = page.current;
  getArchives();
};

// 获取时间轴
const getArchives = async () => {
  loading.value = true;
  let res = await blogTimelineGetArticleList(param.current, param.size);

  console.log(res);
  if (res.code == 200) {
    const { total, records } = res.result;
    records.map(record => {
      record.postVoList.map(item => {
        // 在这里处理每个item
        try {
          item.attribute = JSON.parse(item.attribute);
        } catch (error) {
          // 如果解析失败，返回原始值
          return item.attribute;
        }
      });

    });

    archives.value = records;

    console.log(archives.value);
    archivesTotal.value = total;
    loading.value = false;
  }
};

onMounted(() => {
  getArchives();
});
</script>

<template>
  <PageHeader :loading="loading" />
  <div class="archives">
    <el-row class="center_box">
      <el-col :span="24">
        <el-card class="archives-card">
          <TimeLine :archives="archives" :total="archivesTotal" :loading="loading" :param="param"
            @pagination="pagination"></TimeLine>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<style lang="scss" scoped>
.archives {
  &-card {
    padding: 40px 50px;
  }
}
</style>
