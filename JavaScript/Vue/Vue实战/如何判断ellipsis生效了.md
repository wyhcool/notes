---
title: 如何判断 ellipsis 生效了
date: 2023-07-02 15:09:00
tags:
categories:
- Vue
---

## 概念
要让文本超过长度后，截取显示并附上省略号，可以通过如下的 css 代码实现：
```css
.ellipis {
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
    width:100px;
}
```

有时候，我们需要根据文本是否省略做一些别的事情，例如显示一个展开按钮。现在是 CSS 控制文本省略了，如何检测到呢？

直接通过文本元素的 clientWidth 和 scrollWidth 来检测。

clientWidth 是元素的可视宽度。scrollWidth 是元素的滚动宽度，包括了滑动条边框和被隐藏的内容宽度。因此可以直接判断 clientWidth 和 scrollWidth 的大小，来确定是否文本被省略了。

代码如下：
```js
/**
 * 文字是否被省略
 **/
function isElipsis(id) {
    var clientWidth = document.getElementById(id).clientWidth;
    var scrollWidth = document.getElementById(id).scrollWidth;

    return (clientWidth < scrollWidth); // 说明文字被省略了
}
```

## 参考
[1] https://blog.csdn.net/GAMEloft9/article/details/98757613