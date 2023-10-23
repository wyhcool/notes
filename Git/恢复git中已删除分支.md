---
title: 恢复 git 中已删除分支
date: 2023-10-23 17:52:00
tags:
categories:
- Git
---

第一步 查看 Reflog
Reflog 记录了本地仓库中的引用更改历史，包括分支的删除。
首先，进入项目根目录，并打开终端或命令行。运行以下命令查看分支的 Reflog：
```shell
git reflog
```
在输出中，将看到提交号（commit hash）以及删除分支之前的引用号。记住这个引用号，它将帮助您恢复被删除的分支。

第二步 恢复分支
现在，已经有了删除分支之前的引用号，可以使用以下命令在本地仓库中恢复分支：
```shell
git checkout -b dev_xj d9244f1
```
dev_xj：你的分支名，可以和之前删除的一样，也可以重新命个名 
d9244f1： 最后一次commit 的提交号或者引用号

第三步 推送分支
如果您希望将恢复的分支同步到 GitLab 远程仓库，可以使用以下命令将分支推送到 GitLab：
```shell
git push origin dev_xj
```

注意事项
Git 会定期清理过期的 reflog 记录，以减少仓库大小。默认情况下，过期的 reflog 记录会在 90 天后被删除。如果需要修改过期时间，可以通过配置 gc.reflogExpire 和 gc.reflogExpireUnreachable 参数来调整
```sh
# 设置 reflog 记录的保留时间为 180 天
git config gc.reflogExpire 180.days

# 设置无法访问的 reflog 记录的保留时间为 180 天
git config gc.reflogExpireUnreachable 180.days
```

## 参考
[1] https://zhuanlan.zhihu.com/p/645286252