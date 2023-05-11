---
title: Vue 中 .native 修饰符的作用
date: 2023-05-11 22:22:00
tags:
categories:
- Vue
---


一般情况下，父组件要监听子组件的事件，可以通过 `$emit` 的方式。但是如果父组件要监听子组件的原生事件，比如：input 的 focus 事件。此时可以通过使用 v-on 的 `.native` 修饰符达到目的。

`.native` 可以在某组件的根元素上监听一个原生事件。 

给 vue 组件绑定事件时候，必须加上 native ，否则会认为监听的是来自组件自定义的事件，等同于在子组件内部处理 click 事件然后向外发送 click 事件：`$emit("click", fn)`

`.native` 作用于组件的根元素，如果需要监听的元素作为根元素，此时 `.native` 完全适用。

但如果目标监听的元素不是根元素，那么 `.native` 可能会失效，此时可以利用 `$emit` 方法，通过使用 `$listeners` 来获得父组件中不含 `.native` 修饰符的事件。子组件监听这些事件，当这些事件发生时通知父组件，这个时候就需要使用 `.native` 修饰符就可以监听原生事件了。

父组件：
```html
<son @focus="sonFocus"></son>
```

子组件：
```html
<template>
  <div>
      <input placeholder="this is a input." v-on="inputListeners"/>
  </div>
</template>
<script>
export default {
  computed: {
    inputListeners() {
      const vm = this
      // 将所有对象整合在一起
      return Object.assign({},
        // 从父组件添加的所有不含 .native 监听器
        this.$listeners,
        // 添加自定义监听器
        {
          // 确保v-model正常工作
          input(event) {
            vm.$emit('input', event.target.value)
          }
        }
      )
    }
  }
}
</script>
```

## 参考
[1] https://blog.csdn.net/chen__cheng/article/details/119105879