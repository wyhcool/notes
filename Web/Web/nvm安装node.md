---
title: nvm 安装 node
date: 2023-04-23 21:01:00
tags:
categories:
- web
---

## NVM 介绍
NVM：Node Version Manage，即 Node 的版本管理工具。使用 NVM，可以很方便地在多个 NodeJS 版本之间进行切换。

由于项目开发当中，不同的项目可能依赖不同版本的 NodeJS，这种情况下，NodeJS 版本的切换将会是一件非常麻烦的事情。因此，使用 NVM 管理 NodeJS 版本就显得尤为重要。

## 安装 NVM
### 下载安装包
在 GitHub 上下载 NVM 的安装包。下载地址：https://github.com/coreybutler/nvm-windows/releases，选择下载 nvm-setup.zip。

### 安装NVM
（注：安装NVM前，建议先卸载电脑上现有的NodeJS，避免冗余）

双击nvm-setup.exe文件，进行NVM的安装。

(1) 接受协议，下一步。

(2) 选择NVM的安装路径。可以按照个人习惯，自行选择安装路径。本例中，按照本人习惯，选择安装在D:\Program Files\nvm下。

(3) 选择NodeJS的Symlink (符号链接)文件夹的位置，用于生成NodeJS的映射目录。本例中，按照本人习惯，选择D:\Program Files\nodejs。注：安装前，需确保Symlink文件夹所在的路径不存在，否则可能安装失败。

(4) 点击安装，等待安装完成。

(5) 配置环境变量。打开高级系统设置->环境变量，可以发现，安装成功后，NVM已经自动帮我们配置了环境变量 NVM_HOME 和 NVM_SYMLINK，并且配置了Path的值。

### 验证NVM是否安装成功
安装完成后，打开命令行窗口，输入 `nvm -v`，出现版本号即表示安装成功。


## 在 NVM 中安装 NodeJS
首先，在使用NVM安装NodeJS前，需要以`管理员身份`打开命令行窗口。否则，将无法使用nvm use命令切换NPM版本。

### 在线安装NodeJS
(1) 首先，使用NVM命令 `nvm list available`，查看可安装的NodeJS版本。建议选择LTS中的版本进行安装（LTS，即Long Term Support，长期支持版本）

(2) 使用NVM命令 `nvm install`，进行对应版本NodeJS的在线安装。
```shell
$ nvm install 16.14.2
```

### 离线安装NodeJS
(1) 从官网下载指定版本NodeJS的免安装版离线安装包，下载地址：https://nodejs.org/en/download/releases/。比如 Windows 10 64位操作系统，因此选择的是node-v16.14.2-win-x64.zip。

(2) 将指定版本的NodeJS免安装版离线安装包，解压后放入到NVM安装路径的根目录下，即可完成离线安装。


## 版本切换
若已经成功安装了指定版本的NodeJS，只需要在命令行窗口执行npm use命令，切换对应版本的NodeJS即可。本例中，由于已经安装了16.14.2版本，因此可以执行以下命令：
```shell
$ nvm use 16.14.2
```

若操作成功，将出现提示：Now using node v16.14.2 (64-bit)

## NVM常用命令
NVM的常用命令如下：
```shell
# 查看可在线安装的NodeJS版本
$ nvm list available

# 列出所有已经在NVM中安装的NodeJS版本。
$ nvm ls

# 显示当前正在使用的Node版本
$ nvm current

# 在NVM中在线安装指定版本的NodeJS，xx.xx.xx为指定的版本号。
nvm install xx.xx.xx

# 在NVM中卸载指定版本的NodeJS，xx.xx.xx为指定的版本号。
nvm uninstall xx.xx.xx

# 切换NodeJS版本，xx.xx.xx为指定的版本号。
nvm use xx.xx.xx

# 显示当前所使用的NVM的版本号。
nvm version
```

## 参考
https://blog.csdn.net/weixin_42045719/article/details/129161299