---
title: vue2 中使用 pinia
date: 2023-07-02 17:29:00
tags:
categories:
- Vue
---

## pinia 核心概念
核心概念：
- state: 状态
- actions: 修改状态（包括同步和异步，pinia 中没有 mutations）
- getters: 计算属性


## 定义 pinia
```js
import {
  defineStore
} from 'pinia'

// 创建 store,命名规则： useXxxxStore
// 参数1：store 的唯一表示
// 参数2：对象，可以提供 state actions getters
export const useCounterStore = defineStore('counter', {
  // data里中的数据
  state: () => ({
    count: 1
  }),
  // 计算属性
  getters: {
    double: state => state.count * 2
  },
  // 相当于 vue中的 methods 既可以写同步代码也可以写异步
  actions: {
    addCount () {
      this.count++
    }
  }
})
```


## 使用 pinia
```js
<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
    <div> pinia中的count值 {{count}}</div>
    <div>pinia中的计算属性 {{double}}</div>
    <button @click="addCount">count +1 </button>
    <button @click="getCount">拿到count的值</button>
  </div>
</template>
 
<script>
 
// @ is an alias to /src
import { mapState, mapActions } from 'pinia'
import { useCounterStore } from '@/store'
export default {
  name: 'Home',
  computed: {
    ...mapState(useCounterStore, ['count', 'double'])
  },
  methods: {
    ...mapActions(useCounterStore, ['addCount']),
    getCount () {
      console.log(this.count)
    }
  }
}
</script>
```


## 参考
[1] https://blog.csdn.net/m0_46846526/article/details/126591095