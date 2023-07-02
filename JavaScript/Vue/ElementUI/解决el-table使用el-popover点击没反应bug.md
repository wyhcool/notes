---
title: 解决 el-table 使用 el-popover 点击没反应 bug
date: 2023-07-02 16:58:00
tags:
categories:
- ElementUI
---

## 解决方案：
1、不能在 el-popover 上⾯使⽤ v-if 进⾏显⽰隐藏，应该⽤ v-show；
2、在每⼀个 el-popover 上都增加⼀个 ref 确定每个 el-popover 都是唯⼀的，
```js
:ref="`node-popover-${scope.row.appversionId}`"
```
3、需要使⽤ slot="reference" 定义由哪个元素触发事件。


## 参考
[1] https://blog.csdn.net/weixin_63443983/article/details/130381626

[2] https://blog.csdn.net/qq_45881166/article/details/128398533