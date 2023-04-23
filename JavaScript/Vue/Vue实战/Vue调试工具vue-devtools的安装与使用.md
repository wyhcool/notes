---
title: Vue调试工具vue-devtools的安装与使用
date: 2023-04-23 23:06:00
tags:
categories:
- Vue
---

首先，创建一个空文件夹，进入这个文件的终端。

输入 `npm init -y`

输入 `npm install vue-devtools`

安装完成后，进入该文件夹下的 node_modules 目录，找到 vue-devtools 目录，将 vender 文件下的 `manifest.json`，修改 persistent 为 true。

最后，将vender 文件拖至谷歌浏览器的扩展程序中即可。重启浏览器扩展程序即可使用。

开启 vue 项目可以看到图标高亮。


## 参考
[1] https://blog.csdn.net/xm1037782843/article/details/127708733