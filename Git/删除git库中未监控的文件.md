---
title: 删除 git 库中 untracked files（未监控）的文件
date: 2023-07-02 16:49:00
tags:
categories:
- Git
---

在编译 git 库拉下来的代码时，往往会产生一些中间文件，这些文件我们根本不需要，尤其是在成产环节做预编译，检查代码提交是否能编译通过这种 case 时，我们往往需要编译完成后不管正确与否，还原现场，以方便下次同步代码时不受上一次的编译影响。
sssss
# 删除 untracked files
```sh
$ git clean -f  
```

# 连 untracked 的目录也一起删掉
```sh
$ git clean -fd 
```

# 连 gitignore 的 untrack 文件/目录也一起删掉 （慎用，一般这个是用来删掉编译出来的 .o之类的文件用的）
```sh
$ git clean -xfd 
```

# 在用上述 git clean 前，强烈建议加上 -n 参数来先看看会删掉哪些文件，防止重要文件被误删
```she
git clean -nxfd  
git clean -nf  
git clean -nfd  
```
