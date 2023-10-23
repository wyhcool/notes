---
title: vue路由守卫死循环及next原理解释
date: 2023-10-23 18:06:00
tags:
categories:
- Vue
---


## 路由守卫
​在使用 vue 路由守卫的 beforeEach 方法时可能会出现无限递归，也就是死循环的问题，根本原因在于 next() 方法在不合适的地方错误调用所致。

每个守卫方法接收三个参数:
- to: 即将要进入的目标路由对象
- from: 当前导航正要离开的路由
- next: 一定要调用该方法来 resolve 这个钩子，执行效果依赖 next 方法的调用参数
  - next(): 进行管道中的下一个钩子。如果全部钩子执行完了，则导航的状态就是 confirmed (确认的)。
  - next(false): 中断当前的导航。如果浏览器的 URL 改变了(可能是用户手动或者浏览器后退按钮)，那么 URL 地址会重置到 from 路由对应的地址。
  - next('/')或者 next({ path: '/’}): 跳转到一个不同的地址。当前的导航被中断，然后进行个新的导航。可以向 next 传递任意位置对象，且允许设置诸如 `replace: true`、 `name: home` 之类的选项以及任何用在 router-link 的 to 的 prop 或 router.push 中的选项。
  - next(error): (2.4.0+)如果传入 next 的参数是一个 Error 实例，则导航会被终止且该错误会被传递给 router.onError() 注册过的回调。

```js
beforeEach((to, from, next) => {
    to // 要去的路由
    from // 当前路由
    next() // 放行的意思
}
```
在路由守卫中，只有 next() 是放行，其他的诸如：`next(‘/logon’)`、 `next(to)` 或者 `next({ …to, replace: true })` 都不是放行，而是：中断当前导航，执行新的导航。

VUE 中的中断就是此时不会执行 `router.afterEach(() => {})`这一次路由守卫的操作，而是又进入一次路由守卫，就像嵌套一样。


## 动态添加路由
在 addRoutes()之后第一次访问被添加的路由会白屏，这是因为刚刚 addRoutes() 就立刻访问被添加的路由，然而此时 addRoutes() 没有执行结束，因而找不到刚刚被添加的路由导致白屏。因此需要从新访问一次路由才行。

该如何解决这个问题?

此时就要使用 `next({ …to, replace: true })` 来确保 addRoutes() 时动态添加的路由已经被完全加载上去。

`replace: true` 只是一个设置信息，告诉 VUE 本次操作后，不能通过浏览器后退按钮，返回前一个路由。

`next({ …to })` 的执行过程：如果参数 to 不能找到对应的路由的话，就再执行一次 `beforeEach((to, from, next)` 直到其中的 `next({ …to})` 能找到对应的路由为止。也就是说此时 addRoutes() 已经完成啦，找到对应的路由之后，接下来将执行前往对应路由的`beforeEach((to, from, next)`，因此需要用代码来判断这一次是否就是前往对应路由的 `beforeEach((to, from, next)`，如果是，就执行 next() 放行。如果守卫中没有正确的放行出口的话，会一直 `next({ …to})` 进入死循环 !!!

因此你还需要确保在当 addRoutes() 已经完成时，所执行到的这一次 beforeEach((to, from, next) 中有一个正确的 next() 方向出口。

因此想实现动态添加路由的操作的话，代码应该是这样的：
```js
router.beforeEach((to, from, next) => {
 const token = sessionStorage.getItem('access_token')
 // 存在 token 说明已经登录
 if (token) {
   // 登录过就不能访问登录界面，需要中断这一次路由守卫，执行下一次路由守卫，并且下一次守卫的to是主页'
   if (to.path === '/login') {
     next({ path: '/' })
   }
   // 保存在store中路由不为空则放行 (如果执行了刷新操作，则 store 里的路由为空，此时需要重新添加路由)
   if (store.getters.getRoutes.length || to.name != null) {
     //放行
     next()
   } else {
     // 将路由添加到 store 中，用来标记已添加动态路由
     store.commit('ADD_ROUTER', '需要添加的路由')
     router.addRoutes('需要添加的路由')
     // 如果 addRoutes 并未完成，路由守卫会一层一层的执行执行，直到 addRoutes 完成，找到对应的路由
     next({ ...to, replace: true })
   }
 } else {
   // 未登录时，注意 ：在这里也许你的项目不只有 logon 不需要登录 ，register 等其他不需要登录的页面也需要处理
   if (to.path !== '/logon') {
     next({ path: '/logon' })
   } else {
     next()
   }
 }
})
```


## 参考
[1] https://blog.csdn.net/qq_36958916/article/details/122496369

[2] https://blog.csdn.net/qq_41912398/article/details/109231418