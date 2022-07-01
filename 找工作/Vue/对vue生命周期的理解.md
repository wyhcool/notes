---
title: vue生命周期
date: 2022-07-01 14:53:00
tags:
categories:
- 找工作
---

生命周期的基本含义是从开始到结束的整个过程，在 vue 中实例从创建到销毁的过程就是生命周期，主要分为五个阶段：创建、初始化、渲染、运⾏、销毁。也就是从创建、初始化数据、编译模板、挂载DOM，更新&渲染，卸载等一系列过程。

beforeCreated（创建前）vue 实例的挂载元素 $el 和数据对象 data 都为 undefined，还未初始化。

created（创建后） vue 实例的数据对象 data 有了，$el 还没有。

beforeMount（模板载入前） vue 实例的 $el 和 data 都初始化了，但还是挂载之前为虚拟的 DOM 节点，data.message 还未替换

mounted（模板载入后） vue 实例挂载完成，data.message 成功渲染。

beforeUpdate（更新前） 更新之前调用

updated（更新后） 更新之后调用

beforeDestroy（销毁前） 调用 $destroy 方法后，立即执行 beforeDestroy

pdestroyed（销毁后） 组件销毁后调用，此时只剩下 DOM 空壳

activated  keep-alive 缓存的组件停用时调用

deactivated keep-alive 缓存的组件停用时调用


创建前/后：在beforeCreated阶段，vue实例的挂载完el还没有。
挂载前/后：在beforeMount阶段，vue实例的$el和data都初始化了，但还是挂载之前为虚拟的dom节点，data.message还未替换。在mounted阶段，vue实例挂载完成，data.message成功渲染。
更新前/后：当data变化时，会触发beforeUpdate和updated方法。
销毁前/后：在执行destroy方法后，对data的改变不会在触发周期函数，说明此时vue实例已经解除了事件监听以及和dom的绑定，但是dom结构依然存在。