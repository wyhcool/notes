---
title: 小程序scroll-view用法
date: 2023-02-18 11:18:00
tags:
categories:
- 小程序
---

## scroll-view
设置横向滚动（必须满足以下三点）：

1.给scroll-view添加scroll-x="{{true}}"属性（设置为允许横向滚动）

2.给scroll-view添加white-space:nowrap;属性（设置为不换行）

3.给scroll-view中的子元素设置为display:inline-block;（设置为子组件显示在一行）
