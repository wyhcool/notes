---
title: ps
date: 2023-05-14 11:37:00
tags:
categories:
- Linux
---

## 前言
ps 命令是 process status 的缩写。

用来列出系统当前运行的那些进程。ps 命令列出的是当前那些进程的快照，就是执行 ps 命令的那个时刻的那些进程，如果想要动态的显示进程信息，就可以使用 top 命令。

Linux 上进程有 5 种状态：
- 运行(正在运行或在运行队列中等待)
- 中断(休眠中, 受阻, 在等待某个条件的形成或接受到信号)
- 不可中断(收到信号不唤醒和不可运行, 进程必须等待直到有中断发生)
- 僵死(进程已终止, 但进程描述符存在, 直到父进程调用wait4()系统调用后释放)
- 停止(进程收到SIGSTOP, SIGSTP, SIGTIN, SIGTOU信号后停止运行运行)

ps 工具标识进程的 5 种状态码：
- R 运行 runnable (on run queue)
- S 中断 sleeping
- D 不可中断 uninterruptible sleep (usually IO)
- Z 僵死 a defunct (”zombie”) process
- T 停止 traced or stopped


## 用法
### ps -ef
`ps -ef` 显示所有进程信息，连同命令行。

显示出的结果：
- UID 用户ID
- PID 进程ID
- PPID 父进程ID
- C CPU占用率
- STIME 开始时间
- TTY 开始此进程的终端设备
- TIME 此进程运行的总时间
- CMD 命令名

### ps aux
`ps aux` 列出目前所有的正在内存当中的程序。

显示出的结果：
- USER：该 process 属于哪个使用者账号的
- PID ：该 process 的号码
- %CPU：该 process 使用掉的 CPU 资源百分比
- %MEM：该 process 所占用的物理内存百分比
- VSZ ：该 process 使用掉的虚拟内存量 (Kbytes)
- RSS ：该 process 占用的固定的内存量 (Kbytes)
- TTY ：该 process 是在哪个终端机上面运作，若与终端机无关，则显示 ?，另外， tty1-tty6 是本机上面的登入者程序，若为 pts/0 等等的，则表示为由网络连接进主机的程序。
- STAT：该程序目前的状态，主要的状态有
  - R ：该程序目前正在运作，或者是可被运作
  - S ：该程序目前正在睡眠当中 (可说是 idle 状态)，但可被某些讯号 (signal) 唤醒。
  - T ：该程序目前正在侦测或者是停止了
  - Z ：该程序应该已经终止，但是其父程序却无法正常的终止他，造成 zombie (疆尸) 程序的状态
- START：该 process 被触发启动的时间
- TIME ：该 process 实际使用 CPU 运作的时间
- COMMAND：该程序的实际指令



## 参考
[1] https://blog.csdn.net/lanlangaogao/article/details/125561850