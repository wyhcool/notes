---
title: uniapp 获取子组件高度
date: 2023-02-18 10:47:00
tags:
categories:
- 小程序
---

### uniapp 获取子组件高度

一般 DOM 元素应用 CSS 选择器，但是有父子组件关系时，因为组件加载顺序的问题，可能会导致获取元素为 null

```javascript
export default {
  mounted() {
    console.log(document.querySelector("id").clientHeight);
  },
};
```

给子元素的根节点添加 ref ，在父组件中 `this.$refs['child']` 来获取子组件 DOM。

每个组件都有一个 $el 属性，表示其对应的 DOM，可以通过该属性获取元素各种参数。但这个属性其实是私有属性，理论上对开发者并不透明，但因为 JS 语法的缘故并没有真正的私有属性所以也可以直接用。
```javascript
<template>
  <son-component ref="ref"></son-component>
</template>
<script>
export default {
  mounted() {
    console.log(this.$refs.ref.$el.offsetHeight)
  }
}
</script>
```

关于 uniapp 中使用 ref 去获取 dom 元素时，抛出错误：`this.$refs.xxx is undefined`

关于 ref 注册时间的重要说明：因为 ref 本身是作为渲染结果被创建的，在初始渲染的时候你不能访问它们 ———— 它们还不存在！$refs 也不是响应式的，因此你不应该试图用它在模板中做数据绑定。

解释：如果该 ref 绑定的元素不是当前页面的元素，也就是当前页面调用的某个子组件，那么在 mounted 中是不能通过 $refs 来访问的，可以通过 this.$nextTick() 来访问它，而且得放在 methods 里面。

在小程序中，uniapp 的 ref 要绑定在子组件中才能被获取，如果绑定在 view，是获取不了的！！！

合理的方式：主动上报，在子组件中增加 $emit 上报，在组件渲染完毕后，进行自身信息的上报，由父页面来处理响应。