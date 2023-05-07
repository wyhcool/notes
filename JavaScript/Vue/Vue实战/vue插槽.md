---
title: vue 插槽
date: 2023-05-07 20:53:00
tags:
categories:
- Vue
---


## 概念
父组件传给子组件 html 结构（html 结构在子组件标签内书写）

子组件在适当位置放置插槽出口 `<slot></slot>`。

插槽出口分为匿名插槽、具名插槽、作用域插槽、动态插槽。


## 匿名插槽
```html
<template>
  <div>
    <p>
      <!-- 
            匿名插槽
            <slot name='default'></slot>
            或
            <slot ></slot>
         -->
      <slot>父组件没有传入时，插槽的默认内容</slot>
    </p>
  </div>
</template>
```


## 具名插槽
`v-slot:header` 简写为 `#header`
```html
<template>
  <div>
    <p>
      <!-- 
            具名插槽
         -->
      <slot name="header">header插槽默认内容</slot>
      <slot name="main">main插槽默认内容</slot>
      <slot name="footer">footer插槽默认内容</slot>
    </p>
  </div>
</template>
```


## 作用域插槽
使父组件能够访问子组件中插槽的数据 (插槽提供数据)
```html
<!-- 父组件 -->
<template>
  <div id="app">
    <Son>
      <!-- slotProps接收到子组件中插槽的数据 -->
      <template v-slot:header="slotProps">
        <p>App指定header插槽内容</p>
        <p>{{ slotProps.user }}</p>
      </template>
    </Son>

  </div>
</template>
<!-- 子组件 -->
<template>
  <div>
    <p>
      <!-- 
            作用域插槽：
            在slot标签上绑定自定义属性user
         -->
      <slot name="header" :user="user"></slot>
    </p>
  </div>
</template>
```

## 动态插槽
动态切换插槽名
```html
<template>
  <div>
    <p>
      <!-- 
            动态插槽
         -->
      <slot #[slotName]>
        <h2>我是具名插槽-{{slotName}}</h2>
      </slot>
    </p>
  </div>
</template>
```


## 参考
[1] https://blog.csdn.net/qq_43551056/article/details/130367628