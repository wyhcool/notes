---
title: Object.freeze() 详解
date: 2023-07-02 15:02:00
tags:
categories:
- Vue
---

## 概念
const 定义基本数据类型，这个值是不可以修改的。而用 const 定义对象，这个对象的属性是可以被修改的。那么如果想定义一个不可被修改的对象，应该怎么办呢！

那就要用到 Object.freeze() 了。

它的作用是冻结一个对象，被冻结的对象有以下几个特性：
- 不能添加新属性
- 不能删除已有属性
- 不能修改已有属性的值
- 不能修改原型
- 不能修改已有属性的可枚举性、可配置性、可写性

注意：Object.freeze() 只支持浅冻结。

实现一个深冻结：
```js
function deepFreeze(obj) {
    // 获取所有属性
    var propNames = Object.getOwnPropertyNames(obj)
    // 遍历
    propNames.forEach(item => {
        var prop = obj[item]
        // 如果某个属性的属性值是对象，则递归调用
        if (prop instanceof Object && prop !== null) {
            deepFreeze(prop)
        }
    })
    // 冻结自身
    return Object.freeze(obj)
}
```


## 应用场景
Object.freeze() 可以提高性能，如果有一个对象，里面的内容特别特别多，而且都是一些静态数据，确保不会修改它们，那其实可以用 Object.freeze() 冻结起来，这样可以让性能大幅度提升，提升的效果随着数据量的递增而递增。

一般什么时候用呢？对于纯展示的大数据，都可以使用 Object.freeze() 提升性能。

在 vue 项目中，data 初始化里面一般会有很多变量，后续如果不想使用它，可以使用 Object.freeze()。这样可以避免 vue 初始化时候，做一些无用的操作，从而提高性能。


## Object.freeze() 原理
模拟 Object.freeze() 原理主要用到两个关键方法，Object.definedProperty() 和 Object.seal()。

Object.definedProperty() 方法可以定义对象的属性的特性。如可不可以删除、可不可以修改等等
```js
Object.defineProperty(person, 'name', {
    configurable: false,// 表示能否通过delete删除属性，能否修改属性的特性...
    enumerable: false,// 表示是否可以枚举。直接在对象上定义的属性，基本默认true
    writable: false,// 表示能否修改属性的值。直接在对象上定义的属性，基本默认true
    value: 'xm'// 表示属性的值。访问属性时从这里读取，修改属性时，也保存在这里。
})
```

通过上述配置，就能实现不能修改已有属性的值、不能修改已有属性的可枚举性、可配置性、可写性等等功能。

Object.seal() 静态方法密封一个对象。密封一个对象会阻止其扩展并且使得现有属性不可配置。密封对象有一组固定的属性：不能添加新属性、不能删除现有属性或更改其可枚举性和可配置性、不能重新分配其原型。只要现有属性的值是可写的，它们仍然可以更改。seal() 返回传入的同一对象。

通过这两个方法就可以实现一个简单的 freeze 方法了：
```js
function myFreeze(obj) {
    if (obj instanceof Object) {
        Object.seal(obj);
        let p;
        for (p in obj) {
            if (obj.hasOwnProperty(p)) {
                Object.defineProperty(obj, p, {
                    writable: false
                });
                myFreeze(obj[p]);
            }
        }
    }
}
```


## 参考
[1] https://blog.csdn.net/cai_niao5623/article/details/121095017