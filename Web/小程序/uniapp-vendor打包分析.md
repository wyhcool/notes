---
title: uniapp vendor 打包分析
date: 2023-03-14 23:05:00
tags:
categories:
- 小程序
---


引入 webpack-bundle-analyzer 包分析
```javascript
const path = require('path')
const BundleAnalyzerPlugin = require('webpack-bundle-analyzer')
  .BundleAnalyzerPlugin;

module.exports = {
  configureWebpack (config) {
    config.node = {},
    config.plugins.push(new BundleAnalyzerPlugin())
  }
}
```

<https://github.com/dcloudio/uni-app/issues/1405>