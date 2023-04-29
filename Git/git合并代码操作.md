---
title: git合并代码操作
date: 2023-04-29 14:45:00
tags:
categories:
- Git
---

## 引子
如果在 Rquest to merge `my_branch` into `main_branch` 下方看到 "The source branch is 11 commits behind the target branch" 类似的字样，则表示 你当前分支中的代码不是目标分支代码中的最新版本。

解决办法：在本地的窗口命令行中拉取 `main_branch` 分支中的代码，并且重新提交到自己的远程分支 `my_branch`，gitlab 会自动将新的内容合并到原来的分支 `my_branch` 中。合并完成后，警告会消除。

具体命令：
```shell
# 切换到本地分支 my_branch
$ git checkout my_branch

# 拉取 main_branch 分支最新代码
$ git pull origin main_branch

# 提交到之前的远程分支 my_branch
$ git push
```


## merge 和 rebase
git 中的 merge 和 rebase 都有合并的功能，但是他们之间存在区别，有不同的使用场景。

比如当前在 master 分支，master 中有 A,B,C,D 四个提交，dev 分支中有两个提交 M 和 N，现在需要将 dev 分支合并过来，那么可以通过命令：

`git merge dev`:
```
dev:                       M -------------- N           
                          /                  \
master:           A ---- B ---- C ---- D ---- E ----
```

`git rebase dev`:
```
after rebase:

master:           A ---- B ---- M ---- C ---- D ---- N ----   线性
```

从上面可以看出他们之间的区别，merge 会创建一个新的节点，之前的提交分开显示，而 rebase 操作不会生成新的节点，是将两个分支融合成一个线性的分支，顺序为 commit 的时间顺序。

冲突处理：

merge 手动修改冲突，然后add，commit

rebase 有冲突的话会中断rebase，同时会提示去解决冲突，解决冲突之后，将修改add之后执行git rebase -continue继续操作，或者git rebase -skip忽略冲突。


## 使用 rebase 和 merge 的基本原则
下游分支更新上游分支内容的时候使用 rebase(同步公共分支)
上游分支合并下游分支内容的时候使用 merge(合入公共分支)


## 参考
[1] https://blog.csdn.net/lovely_yoshino/article/details/120515346