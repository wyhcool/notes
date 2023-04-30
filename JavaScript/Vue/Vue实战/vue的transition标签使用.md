---
title: 【Vue】transition 标签的使用
date: 2023-04-30 16:53:00
tags:
categories:
- Vue
---

## 介绍
当插入或删除包含在 transition 组件中的元素时，Vue 将会做以下处理：

自动嗅探目标元素是否应用了 CSS 过渡或动画，如果是，在恰当的时机添加/删除 CSS 类名。

注意事项
- transition 标签只能包含 1 个元素；如果里面写了多个元素，则只生效第一个
- transition 包裹的标签需要设置 v-show / v-if 属性控制元素的显示

动画 CSS 样式对应的类名
- 进入：.v-enter 始状态、.v-enter-to 末状态、.v-enter-active 进入动画（Vue2）
- 离开：.v-leave 始状态、.v-leave-to 末状态、.v-leave-active 离开动画（Vue2）
- 进入：.v-enter-from 始状态、.v-enter-to 末状态、.v-enter-active 进入动画（Vue3）
- 离开：.v-leave-from 始状态、.v-leave-to 末状态、.v-leave-active 离开动画（Vue3）

v-enter: 定义进入过渡的开始状态。在元素被插入时生效，在下一个帧移除。
v-enter-active: 定义过渡的状态。在元素整个过渡过程中作用，在元素被插入时生效，在 transition/animation 完成之后移除。 这个类可以被用来定义过渡的过程时间，延迟和曲线函数。
v-enter-to: 2.1.8版及以上 定义进入过渡的结束状态。在元素被插入一帧后生效（于此同时 v-enter 被删除），在 transition/animation 完成之后移除。
v-leave: 定义离开过渡的开始状态。在离开过渡被触发时生效，在下一个帧移除。
v-leave-active: 定义过渡的状态。在元素整个过渡过程中作用，在离开过渡被触发后立即生效，在 transition/animation 完成之后移除。 这个类可以被用来定义过渡的过程时间，延迟和曲线函数。
v-leave-to: 2.1.8版及以上 定义离开过渡的结束状态。在离开过渡被触发一帧后生效（于此同时 v-leave 被删除），在 transition/animation 完成之后移除。

如果过渡组件提供了 JavaScript 钩子函数，这些钩子函数将在恰当的时机会被自动调用。
如果没有找到 JavaScript 钩子并且也没有检测到 CSS 过渡/动画，DOM 操作（插入/删除）在下一帧中立即执行。 

v-on:before-enter="beforeEnter"
v-on:enter="enter"
v-on:after-enter="afterEnter"
v-on:enter-cancelled="enterCancelled"
v-on:before-leave="beforeLeave"
v-on:leave="leave"
v-on:after-leave="afterLeave"
v-on:leave-cancelled="leaveCancelled">

transition 标签的属性
- name 属性：用于自动生成 CSS 动画类名。如果 transition 标签元素没有设置 name 属性，则对应的动画类名为 v-XXX；如果设置了 name 属性，则对应的动画类名为 属性值-XXX
- appear 属性：一开始就生效显示动画
- 当存在多个 tansition 标签时，我们可以通过 name 属性给各个 transition 标签指定不同的 CSS 动画效果


## 参考
[1] https://blog.csdn.net/Superman_H/article/details/122851610

[2] https://www.cnblogs.com/evaling/p/7289820.html

