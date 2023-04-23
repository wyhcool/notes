---
title: git换行符问题解决方案
date: 2023-04-23 21:21:00
tags:
categories:
- web
---

## 相关参数
配置 git,先了解下 git 的相关参数：eol、autocrlf、safecrlf：
- eol: 设置工作目录中文件的换行符，有三个值 lf, crlf 和 native（默认，同操作系统）
- autocrlf:
  - true 表示检出是转换CRLF, 提交时转换为 LF
  - input 表示检出是不转换，提交时转换为 LF
  - false 表示不做转换
- safecrlf:
  - true 表示不允许提交时包含不同换行符
  - warn 则只在有不同换行符时警告
  - false 则允许提价时有不同换行符存在

autocrlf
```shell
#提交时转换为LF，检出时转换为CRLF
git config --global core.autocrlf true

#提交时转换为LF，检出时不转换
git config --global core.autocrlf input

#提交检出均不转换
git config --global core.autocrlf false
```

safecrlf
```shell
#拒绝提交包含混合换行符的文件
git config --global core.safecrlf true

#允许提交包含混合换行符的文件
git config --global core.safecrlf false

#提交包含混合换行符的文件时给出警告
git config --global core.safecrlf warn
```

## 解决办法　
命令行下配置方法如下：
```shell
# 统一换行符为 lf
git config --global core.eol lf

# 将自动转换关闭,避免转换失败不能不同进行提交
git config --global core.autocrlf false

# 禁止混用 lf 和 crlf 两种换行符
git config --global core.safecrlf true
```

或者在本地路径 `C:\ Users\ [用户名] \ .gitconfig` 下修改 git 配置 `[core]`，如果没有就直接添加上去：
```
[gui]
    encoding = utf-8  
    # 代码库统一使用utf-8  
[i18n]  
    commitencoding = utf-8  
    # log编码  
[svn]  
    pathnameencoding = utf-8  
    # 支持中文路径  
[core]
    eol = lf
    quotepath = false 
    autocrlf = false
    safecrlf = true
    # status引用路径不再是八进制（反过来说就是允许显示中文了）
[filter "lfs"]
    clean = git-lfs clean -- %f
    smudge = git-lfs smudge -- %f
    process = git-lfs filter-process
    required = true
```


多操作系统平台协同开发时，git的注意事项：不要使用git的换行符自动转换功能，尽可能保证代码的换行符都是lf！


## 参考
https://blog.51cto.com/u_15127509/3473766

https://blog.csdn.net/weixin_39973518/article/details/111538308