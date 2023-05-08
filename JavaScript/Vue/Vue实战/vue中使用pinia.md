---
title: vue 中使用 pinia
date: 2023-05-08 22:07:00
tags:
categories:
- Vue
---


## 安装
```shell
$ npm install pinia
```


## 引入
在 Vue2 中引入：
```javascript
import { createPinia, PiniaVuePlugin } from 'pinia'

Vue.use(PiniaVuePlugin)
const pinia = createPinia()

new Vue({
  el: '#app',
  // 其他配置...
  // ...
  // 请注意，同一个`pinia'实例
  // 可以在同一个页面的多个 Vue 应用中使用。 
  pinia,
})
```


## 创建状态管理库
```javascript
import { defineStore } from 'pinia'

export const useCounterStore = defineStore('counter', {
  state:()=>{
    return {
        count: 0
    }
  },
  getters: {
    double: (state) => state.count * 2
  },
  actions: {
    increment() {
        this.count++
    }
  }
})
```
defineStore() 方法的第一个参数：相当于为容器起一个名字。注意：这里的名字必须唯一，不能重复。
defineStore() 方法的第二个参数：可以简单理解为一个配置对象，里边是对容器仓库的配置说明。当然这种说明是以对象的形式。
state 属性： 用来存储全局的状态的，这里边定义的，就可以是为SPA里全局的状态了。
getters属性： 用来监视或者说是计算状态的变化的，有缓存的功能。
actions属性： 对state里数据变化的业务逻辑，需求不同，编写逻辑不同。说白了就是修改state全局状态数据的。


## 使用状态管理库
可以使用 setup() 函数和组合式 API，也可以使用类似 Vuex 的映射 state 的辅助函数，通过 mapStores()、mapState()、mapActions() 的形式访问。
```javascript
export default {
  computed: {
    // 其他计算属性
    // ...
    // 允许访问 this.counterStore 和 this.userStore
    ...mapStores(useCounterStore, useUserStore)
    // 允许读取 this.count 和 this.double
    ...mapState(useCounterStore, ['count', 'double']),
  },
  methods: {
    // 允许读取 this.increment()
    ...mapActions(useCounterStore, ['increment']),
  },
}
```


## Store 是什么
Store 是一个保存状态和业务逻辑的实体，它并不与组件树绑定，它承载着全局状态。它有点像是一个永远存在的组件，每个组件都可以读取和写入它。它有三个概念，state、getter 和 action，类似于组件中的 data、computed 和 methods。


## 应该在什么时候使用 Store
一个 Store 应该包含可以在整个应用中访问的数据。这包括在许多地方的数据，比如显示在导航栏中的用户信息，以及需要通过页面保存的数据，比如一个非常复杂的多步骤表单。

同时，应该避免在 Store 中引入那些原本可以在组件中保存的数据，比如一个元素在页面中的可见性。

注意，并非所有的应用都需要访问全局状态。


## 参考
[1] https://zhuanlan.zhihu.com/p/619297883

[2] https://blog.csdn.net/weixin_42365757/article/details/12384827

[3] https://pinia.web3doc.top/introduction.html