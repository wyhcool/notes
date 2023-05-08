---
title: vue 中的 provide 和 inject
date: 2023-05-08 22:54:00
tags:
categories:
- Vue
---


## 介绍
provide 和 inject 主要为高阶插件/组件库提供用例。并不推荐直接用于应用程序代码中。

定义说明：这对选项是一起使用的。以允许一个祖先组件向其所有子孙后代注入一个依赖，不论组件层次有多深，并在起上下游关系成立的时间里始终生效。

通俗的说就是：组件得引入层次过多，我们的子孙组件想要获取祖先组件得资源，那么怎么办呢，总不能一直取父级往上吧，而且这样代码结构容易混乱。这个就是这对选项要干的事情。

provide：是一个对象，或者是一个返回对象的函数。里面呢就包含要给子孙后代的东西，也就是属性和属性值。
```javascript
const Parent = {
    provide() {
        return {
            bar: 'baz'
        }
    }
}
// 或者
const Parent = {
    provide: {
        bar: 'baz'
    }
}
```

inject：一个字符串数组，或者是一个对象。属性值可以是一个对象，包含 from 和 default 默认值。把注入的属性当做当前 vue 实例的属性，不用使用 $parent 一级一级的访问。
```javascript
const Child = {
    inject: ['bar']
}
// 或者
// from 表示在可用的注入内容中搜索用的 key
// default 是默认值。
const Child = {
  inject: {
    foo: {
      from: 'bar',
      default: 'foo'
    }
  }
}
```

我们可以把依赖注入看做一个大范围有效的 prop，除了：
- 祖先组件不需要知道哪些后代组件使用它提供的属性
- 后台组件不需要知道被注入的属性来自哪里

vue 有 $parent 属性可以让子组件访问父组件。但子孙组件想要访问祖先组件就比较困难。这时候可以通过 provide 和 inject 来实现跨级访问祖先组件的数据。


## 注意
provide 和 inject绑定并不是可响应的。这是刻意为之的。然而，如果你传入了一个可监听的对象，那么其对象的 property 还是可响应的。

第一种解决方案是把值转为函数，记得要用箭头函数，不然不能正确获取this
```javascript
const Parent = {
    provide() {
        return {
            color: () => {
                return this.color;
            },
        };
    }
}
```
然后使用时就要变成了函数的调用
```html
<template>
  <div :style="{'color':color()}">传下来的颜色{{color()}}</div>
</template>
```
这样子就带来一个很明显的缺点就是由于不是响应式，这个函数将会被调用多次，比如上面模板里有两个 color()，可以在函数里打个断点，会发现进来两次。

更好一些的解决方案是把 provide 所在的 Vue 实例给传递下去，再来改造一下
```javascript
const Parent = {
    provide() {
        return {
            color: this,
        };
    }
}
```

在孙组件里获得的其实是实例了，所以要多取一层属性
```html
<template>
  <div :style="{'color':color.color}">传下来的颜色{{color.color}}</div>
</template>
```

可以看到很多 UI 组件库就是通过这个方式来传递属性的，因为有可能在不确定层级的子组件里要获得祖组件里的值。


## 总结
慎用 provide / inject

既然 provide/inject 如此好用，那么，为什么 Vue 官方还要推荐我们使用 Vuex，而不是用原生的 API 呢？

Vuex 和 provide/inject 最大的区别：Vuex 中的全局状态的每次修改是可以追踪回溯的，而 provide/inject 中变量的修改是无法控制的。换句话说，不知道是哪个组件修改了这个全局状态。

Vue 的设计理念借鉴了 React 中的单向数据流原则（虽然有 sync 这种破坏单向数据流的家伙），而 provide/inject 明显破坏了单向数据流原则。试想，如果有多个后代组件同时依赖于一个祖先组件提供的状态，那么只要有一个组件修改了该状态，那么所有组件都会受到影响。这一方面增加了耦合度，另一方面，使得数据变化不可控。如果在多人协作开发中，这将成为一个噩梦。

在这里，总结了使用 provide/inject 做全局状态管理的原则：
- 多人协作时，做好作用域隔离；
- 尽量使用一次性数据作为全局状态

看起来，使用 provide / inject 做全局状态管理好像很危险，那么有没有 provide / inject 更好的使用方式呢？当然有，那就是使用 provide / inject 编写组件。

使用 provide / inject 编写组件

使用 provide/inject 做组件开发，是 Vue 官方文档中提倡的一种做法。

以我们比较熟悉的 elementUI 来举例：

在 elementUI 中有 Button（按钮）组件，当在 Form（表单）组件中使用时，它的尺寸会同时受到外层的 FormItem 组件以及更外层的 Form 组件中的 size 属性的影响。

如果是常规方案，我们可以通过 props 从 Form 开始，一层层往下传递属性值。看起来只需要传递传递两层即可，还可以接受。但是，Form 的下一层组件不一定是 FormItem，FormItem 的下一层组件不一定是 Button，它们之间还可以嵌套其他组件，也就是说，层级关系不确定。如果使用 props，我们写的组件会出现强耦合的情况。

provide/inject 可以完美的解决这个问题，只需要向后代注入组件本身（上下文），后代组件中可以无视层级任意访问祖先组件中的状态。


## 参考
[1] https://blog.csdn.net/ZYS10000/article/details/123243486

[2] https://zhuanlan.zhihu.com/p/184967263
