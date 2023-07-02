---
title: Vue.component 解析
date: 2023-07-02 17:25:00
tags:
categories:
- Vue
---

## 概念
使用 Vue.component 创建组件：
```js
// html部分
<element-block></element-block>

// js部分
Vue.component('element-block', {
    template: '<div>子组件</div>'
});
```


Vue 提供的创建组件API：Vue.component 和 Vue.extend，通过上面的梳理分析，可以得到它们之间的联系如下：
- 都用于创建组件
- Vue.component 内部实际上还是调用了 Vue.extend

主要区别如下：
- 创建组件的方式，Vue.extend 需要手动执行 new 运算创建组件，而 Vue.component 是在 $mount 阶段自动执行 new 运算的


## 参考
[1] https://blog.csdn.net/s1879046/article/details/84677223