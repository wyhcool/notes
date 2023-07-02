---
title: router-view 的 key 属性用法
date: 2023-07-02 16:45:00
tags:
categories:
- Vue
---

## 概念
router-view 作用，可以把 router-view 当做是一个容器，它渲染的组件是你使用 vue-router 指定的。

```html
<template>
  <section class="app-main">
    <transition name="fade-transform" mode="out-in">
      <router-view :key="key" />
    </transition>
  </section>
</template>

<script>
export default {
  name: 'AppMain',
  computed: {
    key() {
      return this.$route.fullPath
    }
  }
}
```
</script>

这里 router-view 有一个 key 的属性，这个key的属性作用是：

1. 不设置 router-view 的 key 属性
由于 Vue 会复用相同组件, 即 /page/1 => /page/2 或者 /page?id=1 => /page?id=2 这类链接跳转时, 将不在执行created, mounted之类的钩子, 这时候你需要在路由组件中, 添加 beforeRouteUpdate 钩子来执行相关方法拉去数据

相关钩子加载顺序为: beforeRouteUpdate


2. 设置 router-view 的 key 属性值为 $route.path
从/page/1 => /page/2, 由于这两个路由的 $route.path 并不一样, 所以组件被强制不复用, 相关钩子加载顺序为:
beforeRouteUpdate => created => mounted

从/page?id=1 => /page?id=2, 由于这两个路由的 $route.path 一样, 所以和没设置 key 属性一样, 会复用组件, 相关钩子加载顺序为:
beforeRouteUpdate


3. 设置 router-view 的 key 属性值为 $route.fullPath
从/page/1 => /page/2, 由于这两个路由的 $route.fullPath 并不一样, 所以组件被强制不复用, 相关钩子加载顺序为:
beforeRouteUpdate => created => mounted

从/page?id=1 => /page?id=2, 由于这两个路由的 $route.fullPath 并不一样, 所以组件被强制不复用, 相关钩子加载顺序为:
beforeRouteUpdate => created => mounted


## 参考
[1] https://www.cnblogs.com/yg_zhang/p/10867617.html