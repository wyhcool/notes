---
title: vue响应式原理
date: 2021-10-06 19:17
tags:
categories:
- 找工作
---

## Object.defineProperty
通过设定对象属性 getter/setter 方法来监听数据的变化，同时 getter 也用于依赖收集，而 setter 在数据变更时通知订阅者更新视图。
```javascript
function defineReactive(obj, key, value) {
    observe(value) // 递归子属性
    Object.defineProperty(obj, key, {
        enumerable: true,
        configurable: true,
        get() {
            collectDeps() // 收集依赖
            return value
        },
        set(newVal) {
            observe(newVal); // 若是对象需要递归子属性
            if (newVal !== value) {
                notifyRender() // 通知订阅者更新
                value = newVal;
            }
        }
    })
}
function observe(obj) {
    if (!obj || typeof obj! === 'object') {
        return
    }
    Object.keys(obj).forEach(key => {
        defineReactive(obj, key, obj[key]);
    })
}
```

Object.defineProperty 的缺陷：
- 1、无法检测到对象属性的新增或删除。由于 js 的动态性，可以为对象追加新的属性或者删除其中某个属性，这点对经过 Object.defineProperty 方法建立的响应式对象来说，只能追踪对象已有数据是否被修改，无法追踪新增属性和删除属性，这就需要另外处理。
- 2、不能监听数组的变化。vue在实现数组的响应式时，它使用了一些 hack，把无法监听数组的情况通过重写数组的部分方法来实现响应式，这也只限制在数组的push/pop/shift/unshift/splice/sort/reverse七个方法，其他数组方法及数组的使用则无法检测到。

## Proxy
Proxy 的代理是针对整个对象的，而不是对象的某个属性，因此不同于 Object.defineProperty 的必须遍历对象每个属性，Proxy 只需要做一层代理就可以监听同级结构下的所有属性变化，当然对于深层结构，递归还是需要进行的。此外Proxy支持代理数组的变化。
```javascript
let handler = {
 get(target, key){
   if (target[key] === 'object' && target[key]!== null) {
     // 嵌套子对象也需要进行数据代理
     return new Proxy(target[key], hanlder)
   }
   collectDeps() // 收集依赖
   return Reflect.get(target, key)
 },
 set(target, key, value) {
   if (key === 'length') return true
   notifyRender() // 通知订阅者更新
   return Reflect.set(target, key, value);
 }
}
let proxy = new Proxy(data, handler);
proxy.age = 18 // 支持新增属性
let proxy1 = new Proxy({arr: []}, handler);
proxy1.arr[0] = 'proxy' 
```
