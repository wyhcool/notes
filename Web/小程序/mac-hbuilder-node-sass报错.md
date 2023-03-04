---
title: mac-hbuilder-node-sass报错
date: 2023-03-04 20:01:00
tags:
categories:
- 小程序
---

## 解决办法
更新下 hbuilder 的 node 配置
```
cd /Applications/HBuilderX.app/Contents/HBuilderX/plugins/node  
 
mv node node-bak
 
ln -s /usr/local/bin/node /Applications/HBuilderX.app/Contents/HBuilderX/plugins/node/node
```

## 参考
<https://blog.csdn.net/ys_healone/article/details/121491380>

