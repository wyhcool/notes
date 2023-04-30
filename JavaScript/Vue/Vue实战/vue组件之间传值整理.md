---
title: vue 组件传值整理
date: 2023-04-30 17:17:00
tags:
categories:
- Vue
---

## props 和 $emit 的用法
父组件 v-bind 绑定参数，v-on 接收子组件事件反馈
```html
<children :property="fatherProps" @sendVal="dataHandle"></children>
```
子组件 props 接收父组件参数，使用$emit 触发事件
```javascript
export default {
    props: [ "fatherProps" ],
    methods: {
        invoke() {
            this.$emit("sendVal",params)
        }
    }
}
```


## Event Bus（事件总线）：
Event Bus 实际上是一个不具备 Dom 的 Vue 组件。首先我们创建一个event-bus.js。
```javascript
// event-bus.js
import Vue from 'vue'
export const EventBus = new Vue()
```

此外，若是我们将 EventBus 直接在 main.js 中注册，它就成了一个全局事件总线。
```javascript
// main.js
Vue.prototype.$EventBus = new Vue()
```

执行上面的步骤了，我们就初始化了一个事件总线，下面我们如何使用它呢，很简单，若有A和B组件。
```javascript
// 首先我们在A和B页面都引入 event-bus.js
// 如果使用的是全局事件总线：则使用 this.$EventBus，不需要引入 event-bus.js 操作
import {  EventBus  } from "/utils/event-bus.js"

// A 页面 使用event-bus $emit触发事件，传递参数。
EventBus.$emit("sendMsg", "来自A页面的消息") 

// B 使用event-bus $on 注册接收事件。
EventBus.$on("sendMsg",(msg)=>{
    console.log(msg)
})
```

EventBus 主要是利用Vue 组件中 $emit 和 $on 触发、监听来传递参数，但是这个有个弊端：若 EventBus 使用在反复操作的组件上，$on 监听就会触发多次，且相关页面被刷新或者销毁，与之相关的 EventBus 就会被移除。

因此，我们可以在组件销毁前，使用 $off 去移除对应 $on 监听：
```javascript
EventBus.$off("sendMsg",null)
// 或移除所有事件
EventBus.$off()
```


## $refs
在 Vue 中，官方并不建议直接对 DOM 对象进行操作，但是有些情况下仍然需要直接访问底层 DOM 元素。要实现这点，Vue官方给我们提供了一个特殊的 ref attribute，它允许我们在一个特定的 DOM 元素或子组件实例被挂载后，获得对它的直接引用。
```
<input ref="inputRef" />

this.$refs.inputRef.focus()
```

若给子组件加上 ref 属性，则可以获取它的属性及方法等。
```
// children 子组件
data() {
    return {
      msg: "这是一个测试"
    };
  },
methods: {
  logMsg(){
     console.log("子组件的logMsg方法")
  }
}

// 父组件中
<children ref="childrenRef" />

this.$refs.childrenRef.msg
this.$refs.childrenRef.logMsg()
```


## Vuex

## .sync 修饰符与 $emit('update:xxx')
在对一个 prop 进行“双向绑定,单向修改”的场景下，因为子组件不能直接修改父组件，.sync 在 2.3 版本引入，作为一个事件绑定语法糖，利用 EventBus，当子组件触发事件时，父组件会响应事件并实现数据更新，避免了子组件直接修改父组件传过来的内容。

### .sync 修饰符之前的写法
父组件：
```html
<parent :myMessage=“bar” @update:myMessage=“func”>
```
父组件中定义函数:
```js
func（val）{
    this.bar = val;
}
```

子组件，事件触发函数：
```js
func2(){
    this.$emit(‘update:myMessage’,valc);
}
```

也就是说，父组件需要传一个绑定值（myMessage）同时需要设置一个更新触发函数（func）给子组件修改绑定值的时候调用。

### 使用 .sync 修饰符的写法
会简化上面的写法，父组件不需要定义更新触发函数。

父组件：
```html
<comp :myMessage.sync="bar"></comp>
```

子组件：
```
this.$emit('update:myMessage',valc);
```

sync 修饰符与 $emit(update:xxx)，驼峰法 和 - 写法的区别，使用 .sync 修饰符，即变量应该使用驼峰法：
```
// this.$emit('update:father-num',100);  //无效
this.$emit('update:fatherNum',100); //有效
//......
<father v-bind:father-num.sync="test"></father>
```

不使用 .sync 修饰符，变量应该使用 - ，即 father-num
```
this.$emit('update:father-num',100);  //有效
//this.$emit('update:fatherNum',100); // 无效
//......
<father v-bind:father-num="test" v-on:update:father-num="test=$event" ></father>
```

但从实践中发现，用 .sync 修饰符，这两种写法都是有效的。

在 vue 之中，当父组件向子组件传递属性的时候，如下使用驼峰法
```
<cpn :cMives="movies"></cpn>
```

在子组件中props:[‘cMives’],是接收不到属性的，应该使用 - 来绑定属性，接收依旧使用驼峰法。
```
<cpn :c-mives="movies"></cpn>
```

综上，绑定时候用 “-”，接收和使用的时候用驼峰法。


## 参考
[1] https://blog.csdn.net/weixin_42927679/article/details/128630362

[2] https://blog.csdn.net/u010017719/article/details/112979385