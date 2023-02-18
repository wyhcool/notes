---
title: 使用Shadow DOM
date: 2023-02-18 11:28:00
tags:
categories:
- 小程序
---


## 使用 shadow DOM
Web components 的一个重要属性是封装，可以将标记结构、样式和行为隐藏起来，并与页面上的其他代码相隔离，保证不同的部分不会混在一起，可使代码更加干净、整洁。其中，Shadow DOM 接口是关键所在，它可以将一个隐藏的、独立的 DOM 附加到一个元素上。

打开浏览器（chrome）-> 控制台 -> 右上角设置标志 Settings ->  打开 Preferences 面板 -> 找到 Elements -> 选择 show user agent shadow DOM

Shadow DOM 允许将隐藏的 DOM 树附加到常规的 DOM 树中。它以 shadow root 节点为起始根节点，在这个根节点的下方，可以是任意元素，和普通的 DOM 元素一样。

Shadow DOM 特有的术语：
- Shadow host：一个常规 DOM 节点，Shadow DOM 会被附加到这个节点上。
- Shadow tree：Shadow DOM 内部的 DOM 树。
- Shadow root: Shadow tree 的根节点。
- Shadow boundary：Shadow DOM 结束的地方，也是常规 DOM 开始的地方。

可以使用同样的方式来操作 Shadow DOM，就和操作常规 DOM 一样，例如添加子节点、设置属性，以及为节点添加自己的样式（例如通过 element.style 属性），或者为整个 Shadow DOM 添加样式（例如在 `<style>` 元素内添加样式）。不同的是，Shadow DOM 内部的元素始终不会影响到它外部的元素（除了 :focus-within），这为封装提供了便利。

注意，不管从哪个方面来看，Shadow DOM 都不是一个新事物，在过去的很长一段时间里，浏览器用它来封装一些元素的内部结构。以一个有着默认播放控制按钮的 `<video>` 元素为例。你所能看到的只是一个 `<video>` 标签，实际上，在它的 Shadow DOM 中，包含了一系列的按钮和其他控制器。Shadow DOM 标准允许你为你自己的元素（custom element）维护一组 Shadow DOM。

可以使用 Element.attachShadow() 方法来将一个 shadow root 附加到任何一个元素上。它接受一个配置对象作为参数，该对象有一个 mode 属性，值可以是 open 或者 closed：
```javascript
let shadow = elementRef.attachShadow({mode: 'open'});
let shadow = elementRef.attachShadow({mode: 'closed'});
```

open 表示可以通过页面内的 JavaScript 方法来获取 Shadow DOM，例如使用 Element.shadowRoot 属性：
```javascript
let myShadowDom = myCustomElem.shadowRoot;
```

如果你将一个 Shadow root 附加到一个 Custom element 上，并且将 mode 设置为 closed，那么就不可以从外部获取 Shadow DOM 了，myCustomElem.shadowRoot 将会返回 null。浏览器中的某些内置元素就是如此，例如 `<video>`，包含了不可访问的 Shadow DOM。

<https://developer.mozilla.org/zh-CN/docs/Web/Web_Components/Using_shadow_DOM>