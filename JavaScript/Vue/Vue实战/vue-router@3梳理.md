---
title: vue-router@3 梳理
date: 2023-04-28 21:10:00
tags:
categories:
- Vue
---

vue-router 是基于路由和组件的：
- 路由是设定的访问路径，将路径和组件映射起来
- 在单页面应用中，页面的路径改变就是组件的切换。

使用模块化机制编程，导入 Vue 和 VueRouter，通过 Vue.use(VueRouter) 明确地安装路由功能。

创建 router 实例，通过 Vue 的 router 配置参数注入路由，从而让整个应用都有路由功能。我们可以在任何组件内通过 `this.$router` 访问路由器，也可以通过 `this.$route` 访问当前路由。


## 动态路由匹配
一个路径参数使用冒号 `:` 标记，当匹配到一个路由时，参数值会被设置到 `this.$route.params`，此外，`this.$route.query` 获取查询参数。

当路由参数改变后，原来的组件实例会被复用，组件的生命周期钩子不会再次被调用。

想要对路由参数的变化作出响应的话，可以监听 `$route` 对象，或者使用 beforeRouteUpdate 导航守卫：
```javascript
export default {
    created() {
        console.log('component user created')
    },
    watch: {
        $route(to, from) {
            console.log('watch $route activated')
        }
    },
    beforeRouteUpdate(to, from, next) {
        // react to route changes...
        // don't forget to call next()
        console.log('beforeRouteUpdate activated') // 
        next()
    }
}
```

常规参数只会匹配 `/` 分隔的 URL 片段中的字符，如果要匹配任意路径，可以使用通配符 `*`。注意：**含有通配符的路由应该放在最后**。

当使用通配符时，`$route.params` 内会自动添加一个名为 pathMatch 参数，它包含了 URL 被匹配的部分。

匹配的优先级按照路由的定义顺序，路由定义得越早，优先级就越高。


## 嵌套路由
`<router-view>` 是最顶层的出口，渲染最高级路由匹配到的组件。同样地，一个被渲染组件同样可以包含自己的嵌套 `<router-view>`。


## 编程式导航
声明式：
`<router-link :to="...">`
编程式：
`router.push(location, onComplete?, onAbort?)` 向 history 栈添加一个新的记录

该方法的参数可以是一个字符串路径，或者一个描述地址的对象：
```javascript
// 字符串 /home
router.push('home')

// 对象 /home
router.push({ path: 'home' })

// 命名的路由 /user/123
router.push({ name: 'user', params: { userId: '123' }})

// 带查询参数，变成 /register?plan=private
router.push({ path: 'register', query: { plan: 'private' }})
```
注意：**如果提供了 path，params 会被忽略**

`router.replace(location, onComplete?, onAbort?)` 替换掉当前 history 记录

`router.go(n)` 在 history 记录中向前或者后退多少步


## 命名路由
可以在创建 Router 实例时，在 routes 配置中给某个路由设置名称 name。


## 命名视图
如果想要同级展示多个视图，而不是嵌套展示，如果 router-view 没有设置名字，那么默认为 default。

一个视图对应一个组件渲染，对于同个路由，多个视图就需要多个组件 components。


## 重定向和别名
重定向 redirect 当用户访问 `/a` 时，URL 将会被替换成 `/b`，然后匹配路由为 `/b`

注意：导航守卫并没有应用在跳转路由上，而仅仅应用在其目标上。

别名 alias

`/a` 的别名是 `/b`，意味着，当用户访问 `/b` 时，URL 会保持为 `/b`，但是路由匹配则为 `/a`，就像用户访问 `/a` 一样。


## 导航守卫
“导航” 表示路由正在发生改变。

导航守卫通过跳转或取消的方式守卫导航。

注意：params 或 query 的改变并不会触发进入/离开的导航守卫。可以通过观察 `$route` 对象，或者使用 beforeRouteUpdate 的组件内守卫来应对这些变化。

### 全局前置守卫 beforeEach
当一个导航触发时，全局前置守卫按照创建顺序调用。守卫是异步解析执行，此时导航在所有守卫 resolve 完之前都一直处在等待中。

每个守卫方法接受三个参数：
- to 即将要进入的目标的路由对象
- from 当前导航正要离开的路由对象
- next 一定要调用该方法来 resolve 这个钩子，执行结果依赖 next 方法的调用参数。
  - next() 进入管道中的下一个钩子。如果全部钩子执行完了，则导航的状态就是 confirmed(确认的)。
  - next(false) 中断当前导航。
  - next('/') 或 next({ path: '/' }) 跳转到一个不同的地址。当前的导航被中断，然后进行一个新的导航。
  - next(error) 导航被终止，且该错误会被传递给 router.onError() 注册过的回调。

确保 next 函数在任何给定的导航守卫中都被严格调用一次。它可以出现多于一次，但是只能在所有的逻辑路径都不重叠的情况下。

### 全局解析守卫 beforeResolve
在导航被确认之前，同时在所有组件内守卫和异步路由组件被解析之后，解析守卫就被调用。

### 全局后置守卫 afterEach
不会接受 next 函数，也不会改变导航本身。

### 路由独享的守卫 beforeEnter
在路由配置上直接定义。

### 组件内的守卫
beforeRouteEnter 在渲染该组件的对应路由被 confirm 前调用，不能获取组件实例 this，组件实例还没被创建
beforeRouteUpdate 在当前路由改变，但是该组件被复用时调用，可以访问组件实例 this
beforeRouteLeave 导航离开该组件的对应路由时调用，可以访问组件实例 this

### 完整的导航解析流程
- 导航被触发
- 在失活的组件里调用 beforeRouteLeave
- 调用全局的 beforeEach
- 在重用的组件里调用 beforeRouteUpdate
- 在路由配置里调用 beforeEnter
- 解析异步路由组件
- 在被激活的组件里调用 beforeRouteEnter
- 调用全局的 beforeResolve
- 导航被确认
- 调用全局的 afterEach
- 触发 DOM 更新
- 调用 beforeRouteEnter 中传给 next 的回调函数，创建好的组件实例回座位回调函数的参数传入


## 路由元信息
定义路由的时候可以配置 meta 字段。

routes 配置中的每个路由对象为路由记录，路由记录是可以嵌套的，因此，当一个路由匹配成功后，它可能匹配多个路由记录。

一个路由匹配到的所有路由记录会暴露为 `$route.matched` 数组。


## 过渡动画
### 单个路由的过渡
可以在各个组件内使用 `<transition>` 并设置不同的 name。

### 基于路由的动态过渡
基于当前路由与目标路由的变化关系，动态设置过渡效果：
```javascript
// watch $route 决定使用哪种过渡
watch: {
  '$route' (to, from) {
    const toDepth = to.path.split('/').length
    const fromDepth = from.path.split('/').length
    this.transitionName = toDepth < fromDepth ? 'slide-right' : 'slide-left'
  }
}
```


## 数据获取
### 导航完成之后获取
先完成导航，然后在接下来的组件生命周期钩子中获取数据。

### 在导航完成之前获取数据
在导航转入新的路由前获取数据。

在 beforeRouteEnter 守卫中获取数据，当数据获取成功后只调用 next 方法。


## 滚动行为
创建一个 Router 实例，并提供一个 scrollBehavior 方法。


## 路由懒加载
结合 Vue 的异步组件和 Webpack 的代码分割功能，实现路由组件的懒加载。

当我们想把某个路由下的所有组件都打包在同一个异步块(chunk)中，只需要使用命名chunk的特殊的注释语法来提供 chunk name。
```javascript
const Foo = () => import(/* webpackChunkName: "group-foo" */ './Foo.vue')
const Bar = () => import(/* webpackChunkName: "group-foo" */ './Bar.vue')
const Baz = () => import(/* webpackChunkName: "group-foo" */ './Baz.vue')
// Webpack 会将任何一个异步模块与相同的块名称组合到相同的异步块中。
```


## 导航故障
导航故障的情况：
- 用户已经位于他们正在尝试导航到的页面
- 一个导航守卫通过调用 next(false) 中断了这次导航
- 一个导航守卫抛出了一个错误，或者调用了 next(new Error())


## 错误处理
### 解决 vue-router 报 NavigationDuplicated: Avoided redundant navigation to current location 的问题
重复点击路由会在控制台报这样的错，它的提示是避免到当前位置的冗余导航。也就是重复触发了同一个路由。
```javascript
const originPush = Router.prototype.push
Router.prototype.push = function(path) {
    return originPush.call(this, path).catch(err => err)
}
```


## 参考
[1] https://v3.router.vuejs.org/zh/