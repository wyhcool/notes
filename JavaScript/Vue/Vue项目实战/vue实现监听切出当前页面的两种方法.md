---
title: vue实现监听切出当前页面的两种方法
date: 2023-10-23 19:26:00
tags:
categories:
- Vue
---


## visibilitychange 事件
visibilitychange 是浏览器新添加的一个事件，当浏览器当前页面被最小化，或者切换到浏览器的其他标签页，或者从其他页面或应用返回到当前标签页，都会触发这个事件。

document.visibilityState 共有四个值:
- hidden：文档处于背景标签页或者窗口处于最小化状态 或者操作系统正处于 ‘锁屏状态’
- visible：只要页面可见，哪怕只露出了一个角 document.visibilityState也会返回visible
- prerender：页面在屏幕外执行预渲染处理 document.hidden 的值为 true
- unloaded(已废弃)：表示页面即将卸载

另外 当 document.visibilityState 属性返回 visible 时，document.hidden属性返回 false；其他情况下，都返回true。
```js
mounted() {  
  document.addEventListener('visibilitychange', this.handleVisiable)  
},  
destroyed() {  
  document.removeEventListener('visibilitychange', this.handleVisiable)  
},  
methods: {  
  handleVisiable(e) {  
    switch(e.target.visibilityState) {
      case 'prerender':
        console.log('网页预渲染，内容不可见')
        break;
      case 'hidden':
        console.log('内容不可见，处理后台、最小化、锁屏状态')
        alert('警告！你已经离开当前答题页面');
        break;
      case 'visible':
        console.log('处于正常打开')
        break;
    }
  }
}
```


## onblur事件
onblur 事件会在对象失去焦点时发生，将事件绑定在dom元素上，当失去焦点时触发。

模拟一个场景，像在线考试功能，会监听学生鼠标移出考试页面，我们给body元素绑定一个函数，用于监听鼠标离开了当前页面并进行一个记录当触发后会提醒学生。
```js
mounted() {
    document.body.onblur = () => {
        this .outNumber++;
        console.log(this.outNumber)
        alert(`~请回到考试页面，您已离开${this.outNumber}次~`);
    }
}
```

## 参考
[1] https://blog.csdn.net/weixin_47082552/article/details/123698903