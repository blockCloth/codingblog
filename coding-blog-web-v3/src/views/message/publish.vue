<script setup>
import { ref, reactive, onMounted, h } from "vue";
import { useRouter, useRoute } from "vue-router";
import { ElNotification } from "element-plus";
import { user } from "@/store/index";
import { storeToRefs } from "pinia";

import { addMessage, updateMessage, getMessageTag } from "@/api/message";
import { _getLocalItem, _removeLocalItem, _setLocalItem } from "@/utils/tool";
import { debounce } from "@/utils/tool";
import { fontSizeList, fontWeightList, predefineColors, opTabList } from "./useMessage";
import { imgUpload } from "@/api/user";

import Upload from "@/components/Upload/upload.vue";

const router = useRouter();
const route = useRoute();
const userStore = user();
const { getUserInfo } = storeToRefs(userStore);
const loading = ref(false);

const tabList = ref([]);

function keepLastIndex(dom) {
  var range;
  if (window.getSelection) {
    //ie11 10 9 ff safari
    dom.focus(); //解决ff不获取焦点无法定位问题
    range = window.getSelection(); //创建range
    range.selectAllChildren(dom); //range 选择obj下所有子内容
    range.collapseToEnd(); //光标移至最后
  } else if (document.selection) {
    range = document.selection.createRange(); //创建选择对象
    range.moveToElementText(dom); //range定位到obj
    range.collapse(false); //光标移至最后
    range.select();
  }
}

const form = reactive({
  id: 0,
  message: "",
  color: "#676767",
  font_size: 16,
  font_weight: 500,
  bg_color: "transparent",
  bg_url: "",
  user_id: 0,
  tag: "",
  nick_name: "",
  bgList: [],
});

const primaryForm = Object.assign({ ...form });

const activeTab = ref(0);

const inputCommentRef = ref("");

const inputComment = debounce(() => {
  form.message = inputCommentRef.value.innerHTML;
}, 100);

// 当鼠标点入输入框做的事情 光标得在最后一位
const focusCommentInput = () => {
  if (inputCommentRef.value.innerHTML == "留下点什么再走吧~") {
    inputCommentRef.value.innerHTML = "";
  }
  keepLastIndex(inputCommentRef.value);
};

const leaveMessage = async () => {
  if (!form.message) {
    ElNotification({
      offset: 60,
      title: "温馨提示",
      message: h("div", { style: "color: #e6c081; font-weight: 600;" }, "留言内容不能为空"),
    });
    return;
  }
  if (!form.tag) {
    ElNotification({
      offset: 60,
      title: "温馨提示",
      message: h(
        "div",
        { style: "color: #e6c081; font-weight: 600;" },
        "请选择标签，没有标签可以自行创建"
      ),
    });
    return;
  }
  // 新增
  if (!form.id) {
    form.user_id = getUserInfo.value.id;
  }
  // 上传背景图片
  loading.value = true;
  if (form.bgList.length && !form.bgList[0].id) {
    const img = await imgUpload(form.bgList[0]);
    if (img.code == 200) {
      const { imagePath } = img.result;
      form.bg_url = imagePath;
    }
  }
  //将数据转成JSON字符串
  const messageJson = JSON.parse(JSON.stringify(form));
  let res;
  if (form.id) {
    res = await updateMessage(messageJson);
  } else {
    res = await addMessage(messageJson);
  }
  if (res && res.code == 200) {
    ElNotification({
      offset: 60,
      title: "提示",
      message: h(
        "div",
        { style: "color: #7ec050; font-weight: 600;" },
        form.id ? "修改成功" : "留言成功"
      ),
    });
    Object.assign(form, primaryForm);
    loading.value = false;
    _removeLocalItem("blog-message-item");
    _setLocalItem("message-refresh", true);
    _setLocalItem("message-need-scroll", true);
    router.go(-1);
  } else {
    ElNotification({
      offset: 60,
      title: "错误提示",
      message: h("div", { style: "color: #f56c6c; font-weight: 600;" }, res.message),
    });
    loading.value = false;
  }
};

const getHotMessageTag = async () => {
  const res = await getMessageTag();
  if (res.code == 200) {
    tabList.value = Array.isArray(res.result)
      ? res.result.map((v, i) => {
        return {
          key: i + 1,
          label: v,
        };
      })
      : [];
  }
};

const changeTab = (key) => {
  activeTab.value = key;
};

onMounted(async () => {
  await getHotMessageTag();

  if (route.query.type == "edit") {
    const item = _getLocalItem("blog-message-item");
    if (item) {
      Object.assign(form, item);
      if (item.bg_url) {
        form.bgList = [
          {
            id: 1,
            name: item.bg_url.split("/").slice(-1),
            url: item.bg_url,
          },
        ];
      }
    }
    if (inputCommentRef.value) {
      inputCommentRef.value.innerHTML = form.message;
    }
  } else {
    if (inputCommentRef.value) {
      inputCommentRef.value.innerHTML = "留下点什么再走吧~";
    }
    form.nick_name = getUserInfo.value.nick_name || "游客";
    form.avatar = getUserInfo.value.avatar || "游客";
    if (Array.isArray(tabList.value) && tabList.value.length > 0) {
      form.tag = tabList.value[0].label;
    }
  }
});
</script>
<template>
  <div class="message">
    <div class="center_box !pt-[80px]">
      <div class="flex items-center justify-center !h-[1rem]">
        <TypeWriter size="1.2rem" :typeList="['世间真假，皆我所求，苦与乐，都可奉酒。']"></TypeWriter>
      </div>

      <el-card class="!mt-[2rem]">
        <div class="!h-[22rem]" :style="{ backgroundColor: form.bg_color }">
          <div class="top" :style="{ backgroundImage: form.bg_url ? `url(${form.bg_url})` : '' }">
            <div class="top-header">
              <div class="flex items-center">
                <el-avatar class="left-avatar" :src="form.avatar">{{ form.nick_name }} </el-avatar>
                <span class="nick-name"> {{ form.nick_name }}</span>
              </div>
            </div>
            <div class="content" :style="{
          color: form.color,
          fontSize: form.font_size + 'px',
          fontWeight: form.font_weight,
        }" ref="inputCommentRef" contenteditable="true" @input="inputComment(val)" @focus="focusCommentInput">
            </div>
          </div>
          <div class="bottom">
            <div class="tag">{{ form.tag }}</div>
          </div>
        </div>
      </el-card>
      <div class="!mt-[10px] !h-[20rem]">
        <ul class="tab">
          <li v-for="item in opTabList" :key="item.key" @click="changeTab(item.key)">
            <div :class="[item.key == activeTab ? 'active-tab' : '', 'tab-li']">
              {{ item.label }}
            </div>
          </li>
        </ul>
        <div class="!h-[12rem] !p-[15px]">
          <div v-if="activeTab == 0">
            <el-color-picker v-model="form.bg_color" show-alpha :predefine="predefineColors" />
          </div>
          <div v-else-if="activeTab == 1" class="flex items-center">
            <el-select v-model="form.font_size" class="!w-[160px] !mr-[20px]" placeholder="请选择字体大小" size="large">
              <el-option v-for="item in fontSizeList" :key="item.key" :label="item.key" :value="item.key" />
            </el-select>
            <el-color-picker v-model="form.color" show-alpha :predefine="predefineColors" />
            <el-select v-model="form.font_weight" class="!w-[160px] !ml-[20px]" placeholder="请选择字体宽度" size="large">
              <el-option v-for="item in fontWeightList" :key="item.key" :label="item.key" :value="item.key" />
            </el-select>
          </div>
          <div v-else-if="activeTab == 2">
            <Upload v-model:file-list="form.bgList" :limit="1" :width="280" :height="140" :preview="false" />
          </div>
          <div v-else>
            <el-select v-model="form.tag" class="!w-[180px]" placeholder="请选择或输入标签" size="large" filterable allow-create
              clearable>
              <el-option v-for="item in tabList" :key="item.key" :label="item.label" :value="item.label" />
            </el-select>
          </div>
        </div>
        <div class="!h-[4rem] !p-[15px] flex justify-center items-center">
          <el-button :disabled="loading" :loading="loading" class="leave-message" @click="leaveMessage">{{
          loading ? "努力上传中..." : route.query.type == "edit" ? "保存" : "发布"
        }}</el-button>
        </div>
      </div>
    </div>
    <div v-if="loading" class="loading">
      <div class="coffee_cup"></div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.message {
  .top {
    height: 19rem;
    padding: 8px;
    background-position: center center;
    background-size: cover;
    background-repeat: no-repeat;
  }

  .top-header {
    height: 4rem;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .nick-name {
    color: var(--global-white);
    margin-left: 1rem;
    letter-spacing: 1px;
    padding: 3px 8px;
    background-color: rgba(0, 0, 0, 0.2);
    border-radius: 8px;
  }

  .content {
    height: 15rem;
    width: 100%;
    overflow: auto;
  }

  .bottom {
    height: 3rem;
    display: flex;
    justify-content: flex-end;
    align-items: center;
    padding: 8px;
  }

  .tag {
    font-size: 12px;
    color: var(--global-white);
    background-color: rgba(0, 0, 0, 0.2);
    padding: 3px 8px;
    border-radius: 8px;
    margin-right: 10px;
  }

  &-input {
    width: 100%;
  }

  :deep(.el-textarea__inner) {
    background-color: transparent;
  }

  .tab {
    width: 100%;
    min-height: 3rem;
    padding: 0.5rem;
    display: flex;
    justify-content: flex-start;
    align-items: center;
    cursor: pointer;
    flex-wrap: wrap;
    font-size: 1.2rem;
    font-weight: 600;
    margin-bottom: 1rem;
    background-color: rgba(0, 0, 0, 0.2);
    border-radius: 2rem;

    li {
      margin-right: 1rem;
    }

    .tab-li {
      width: 6rem;
      height: 2rem;
      line-height: 2rem;
      text-align: center;
      border-radius: 1rem;
    }

    .active-tab {
      color: var(--global-white);
      background-color: var(--primary);
    }
  }
}

.center_box {
  min-height: calc(100vh - 128px);
}

.loading {
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  display: flex;
  justify-content: center;
  align-items: center;
}

.coffee_cup {
  transform: scale(2);
}

.type-writer {
  color: var(--global-black) !important;
}

.leave-message {
  color: var(--font-color);
  width: 200px;
  height: 40px;
  border-radius: 20px;
  background-color: var(--shadow-button-bg);
  transition: all 0.3s;

  &:hover {
    transform: translateY(-3px);
  }
}

:deep(.el-upload--picture-card) {
  width: 280px !important;
  height: 140px !important;
  border-radius: 8px !important;
}

:deep(.el-upload-list__item) {
  width: 280px !important;
  height: 140px !important;
  border-radius: 8px !important;
  margin: 0;
}

:deep(.el-upload-list--picture-card) {
  width: 280px !important;
  height: 140px !important;
  border-radius: 8px !important;
}

@media screen and (min-width: 768px) {
  .center-top {
    .left-avatar {
      width: 48px;
      height: 48px;
    }
  }

  .center_box {
    max-width: 600px !important;
  }
}

@media screen and (mxn-width: 768px) {
  .center-top {
    .left-avatar {
      width: 40px;
      height: 40px;
    }
  }
}
</style>
