---
title: 小程序中 setData 详解
date: 2021-05-30 14:35:00
tags:
categories:
- 小程序
---

## 缘起
19年底做原生支付宝小程序开发的时候，总是在调用 setData 之后，设置 setTimeout 之后再调用 this.data 获取最新数据。之后转 uniapp 开发，使用 vue 的模式开发后，这一茬也就不再关注了。最近又涉及到京东小程序开发，无法使用 uniapp 统一转换，只得又回归到原生开发，这时又遇到了这个问题，到底什么时候 this.data 中可以取到最新的 setData 中的数据？

## 解决
setData 函数用于将数据从逻辑层发送到视图层（异步），同时改变对应的 this.data 的值（同步）。

## demo
```javascript
Page({
  data: {
    title: 'demo'
  },
  changeData() {
    console.log('before seData: ', this.data.title)
    this.setData({
      title: 'newDemo'
    }, () => {
      console.log('setData callback: ', this.data.title)
    })
    console.log('after setData: ', this.data.title)
  },
  onLoad(query) {
    // 页面加载
    this.changeData();
    console.info(`Page onLoad with query: ${JSON.stringify(query)}`);
  }
});

// 执行结果
// before seData:  demo
// after setData:  newDemo
// Page onLoad with query: {}
// setData callback:  newDemo
```

## 参考
[1] https://blog.csdn.net/wzc_coder/article/details/106894104
[2] https://opendocs.alipay.com/mini/framework/page-detail#Page.prototype.setData(data%3A%20Object%2C%20callback%3A%20Function)
[3] https://developers.weixin.qq.com/miniprogram/dev/reference/api/Page.html