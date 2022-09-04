---
title: vue生命周期
date: 2022-07-01 14:53:00
tags:
categories:
- 找工作
---

## 生命周期

一个组件从创建到销毁的整个过程就是生命周期。

## 生命周期函数（钩子函数）：
vue 框架内置函数，随着组件的生命周期，自动按次序执行。
- beforeCreate: 创建实例之前执行，元素和数据都是 undefined。
- created: 实例初始化完成后执行，可以访问数据或方法，不能操作 DOM。可以做一些初始数据的获取，发送 ajax。
- beforeMount: 挂载前执行，虚拟 DOM 创建完成，即将开始渲染。
- mounted: 页面渲染完成后执行，可以对 DOM 进行操作。
- beforeUpdate：数据发生更新时执行。此时 vue 实例中的数据是最新的，但是页面数据仍是旧值。
- updated：数据发生更新导致的 DOM 重新渲染完成时执行。
- beforeDestroy：实例销毁前执行。实例仍然可以使用，一般在这个时候清除定时器或者解除事件绑定等。
- destroyed：实例销毁完成。

注意：在使用keep-alive组件后，会多出生命周期，即 activated 激活状态，deactivated 失活状态。

四个阶段八个函数：
- 初始阶段 => 创建组件 === beforeCreate created
- 挂载阶段 => 渲染显示组件 === beforeMount mounted
- 更新阶段 => 修改了数据 === beforeUpdate updated
- 销毁阶段 => 删除组件对象 === beforeDestory destoryed

## 父子组件生命周期
1.初始、挂载阶段

父组件beforeCreate => 父组件created => 父组件beforeMount => 子组件beforeCreate => 子组件created => 子组件beforeMount => 子组件mounted => 父组件mounted

2.更新阶段

父组件beforeUpdate => 子组件beforeUpdate => 子组件updated => 父组件updated

3.销毁阶段

父组件beforeDestroy => 子组件beforeDestroy => 子组件destroyed => 父组件destroyed


## 参考
[1] https://blog.csdn.net/weixin_59731784/article/details/124654521