---
title: nrm 管理 registry
date: 2023-05-05 20:57:00
tags:
categories:
- web
---

## nrm 介绍
nrm(npm registry manager)是 npm 的镜像源管理工具，有时候国外资源太慢，使用这个就可以快速地在 npm 源间切换。


## nrm 安装
nrm 安装后报错 require() of ES modules is not supported.
原因：应该使用 open 的 CommonJs 规范的包，现在 open v9.0.0 是 ES Module 版本的包
解决方法：
```shell
# https://github.com/Pana/nrm/issues/155
$ npm install -g nrm open@8.4.2 --save
```

nrm 当前源不显示标记 *
```shell
# 使用替代包 https://github.com/Pana/nrm/issues/157
$ npm i nrm-sunny1011111 -g
```

默认情况下，我们在执行 `npm install -g XXXX` 时，下载了一个全局包，这个包的默认存放路径位`C:\Users\xxx\AppData\Roaming\npm\node_modules` 下，可以通过 CMD 指令 `npm root -g` 查看


## nrm 使用
```shell
# 查看可选源
$ nrm ls
# 查看当前源
$ nrm current
# 切换源
$ nrm use [registry]
# 添加源
$ nrm add [registry] [url]
# 删除源
$ nrm del [registry]
# 测试源速度
$ nrm test [registry]
```


## 参考
[1] https://blog.csdn.net/qq_36958916/article/details/113653457
