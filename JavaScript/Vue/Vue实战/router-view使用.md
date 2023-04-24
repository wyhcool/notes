---
title: router-view 使用
date: 2023-04-24 23:27:00
tags:
categories:
- Vue
---

## 介绍
router-view 组件作为 vue 最核心的路由管理组件，在项目中作为路由管理经常被使用到。vue 项目最核心的 App.vue 文件中即是通过 router-view 进行路由管理。
```html
<template>
  <div id="app">
      <router-view></router-view>
  </div>
</template>
```

我们在自己维护项目的时候，也可以使用 router-view 组件进行路由管理，对于页面局部刷新的场景，该组件能发挥关键作用。

## 代码
router.js 文件：
```javascript
import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
  {
    path: "/routerViewPractice",
    name: "routerViewPractice",
    component: () => import("@/views/routerView/index.vue"),
    redirect: "/messagePage", // 页面默认加载的路由
    children: [
      {
        path: "/messagePage",
        name: "messagePage",
        component: () =>
          import("@/views/routerView/childPages/messagePage.vue"),
      },
      {
        path: "/contactPage",
        name: "contactPage",
        component: () =>
          import("@/views/routerView/childPages/contactPage.vue"),
      },
      {
        path: "/dynamicPage",
        name: "dynamicPage",
        component: () =>
          import("@/views/routerView/childPages/dynamicPage.vue"),
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
```
文件说明：
- routerViewPractice：父路由 path；
- redirect：页面 router-view 组件默认加载的路由；
- children：用于父页面进行切换的子路由；

父页面代码：
```vue
<template>
  <div>
    <div class="top">
        <div class="top-item" @click="handleMsg">Message</div>
        <div class="top-item" @click="lookContact">Contact</div>
        <div class="top-item" @click="readDynamic">Dynamic</div>
    </div>
    <div class="main">
        <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {
  created() {
    console.log("routerView/index.vue created");
  },
   methods: {
    handleMsg() {
      this.$router.push({path: '/messagePage'})
    },
    lookContact() {
      this.$router.push({path: '/contactPage'})
    },
    readDynamic() {
      this.$router.push({path: '/dynamicPage'})
    }
  }
};
</script>

<style>
.top {
    position: fixed;
    top: 0;
    margin: 0 auto;
    width: 750px;
    height: 100px;
    display: flex;
    justify-content: center;
    align-items: center;
}
.top-item {
    width: 250px;
    cursor: pointer;
}
.top-item:hover {
    background: #cf0;
}
</style>
```
使用 `this.$router.push` 进行页面上 router-view 组件的路由替换。


## 参考
[1] https://blog.csdn.net/m0_46309087/article/details/119701254