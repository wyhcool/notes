---
title: 关于 component 属性 is 的使用方法
date: 2023-07-02 15:02:00
tags:
categories:
- Vue
---

## 概念
有的时候，在不同组件之间进行动态切换是非常有用的，可以通过 Vue 的 `<component>` 元素加一个 is 属性来实现。也就是多个组件使用同一个挂载点，并动态切换，并且 is 属性就是这个组件的 name 值。

## 参考
[1] https://www.cnblogs.com/gong-ping/p/13995400.html