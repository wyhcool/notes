---
title: v-if 和 v-show 的区别
date: 2023-07-02 17:01:00
tags:
categories:
- Vue
---

## 共同点
在 vue 中 v-show 与 v-if 的作用效果是相同的，都能控制元素在页面是否显示 。
- 当表达式都为 false 时，都不会占据页面位置
- 当表达式结果为 true 时，都会占据页面的位置

## 区别
### 控制手段
v-show 隐藏则是为该元素添加 css `display:none`，dom 元素依旧还在。v-if 显示隐藏是将 dom 元素整个添加或删除。

### 编译过程
v-if 切换有一个局部编译/卸载的过程，切换过程中合适地销毁和重建内部的事件监听和子组件；v-show 只是简单的基于 css 切换。

### 编译条件
v-if 是真正的条件渲染，它会确保在切换过程中条件块内的事件监听器和子组件适当地被销毁和重建。只有渲染条件为假时，并不做操作，直到为真才渲染。

v-show 由 false 变为 true 的时候不会触发组件的生命周期

v-if 由 false 变为 true 的时候，触发组件的 beforeCreate、create、beforeMount、mounted 钩子，由 true 变为 false 的时候触发组件的 beforeDestory、destoryed 方法。

### 性能消耗
v-if有更高的切换消耗；v-show有更高的初始渲染消耗；


## 使用场景
v-if 与 v-show 都能控制 dom 元素在页面的显示

v-if 相比 v-show 开销更大的（直接操作 dom 节点增加与删除）

如果需要非常频繁地切换，则使用 v-show 较好

如果在运行时条件很少改变，则使用 v-if 较好


## 参考
[1] https://blog.csdn.net/weixin_57519185/article/details/121168426