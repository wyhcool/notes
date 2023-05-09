---
title: DOM 事件
date: 2023-05-09 22:06:00
tags:
categories:
- JavaScript
---


## 0级 DOM 事件
0级 DOM 分为2个：
- 一是在标签内写 onclick 事件
- 二是在JS写 onclick=function（）{} 函数

```html
<input type="button" onclick="alert(0);" />
<script>
  var btn = document.getElementsByClassName('button');
  btn.onclick = function(){
    alert(0);
  }
</script>
```

DOM0 级处理事件就是将一个函数赋值给一个事件处理属性，缺点在于一个处理程序无法同时绑定多个处理函数。


## 2级 DOM 事件
2级DOM 监听方法，原生有两个方法用来添加和移除事件处理程序：addEventListener()和removeEventListener()。IE下的DOM2事件通过 attachEvent 绑定和 detachEvent 进行移除事件。

注意： IE9及之后的版本都能兼容 addEventListener。

addEvenetListener()、removeEventListener() 有三个参数：
- 第一个参数 event 是事件名（如click, IE是 onclick）；
- 第二个参数 function 是事件处理程序函数；
- 第三个参数 useCapture 如果是 true 则表示在捕获阶段调用，为 false 表示在冒泡阶段调用。

addEventListener(‘onclick’, handle): 可以为元素添加多个事件处理程序，触发时会按照添加顺序依次调用。

removeEventListener(‘onclick’, handle): 不能移除匿名添加的函数。

```html
<div id="box">点我</div>
<script>
    var box = document.getElementById('box');
    box.addEventListener('click', fun1,false);
    box.addEventListener('click', fun2,false);
    function fun1() {
        console.log('方法1');
    }
    function fun2() {
        console.log('方法2');
    }
    // 删除事件
    box.removeEventListener('click', fun1, false)
</script>
```

DOM0 与 DOM2 区别：
- 如果定义了两个 DOM0 级事件，DOM0 级事件会覆盖
- DOM2 不会覆盖，会依次执行
- DOM0 和 DOM2 可以共存，不互相覆盖，但是 DOM0 之间依然会覆盖


## 3级 DOM 事件
DOM3 级事件在 DOM2 级事件的基础上添加了更多的事件类型，全部类型如下：
- UI事件，当用户与页面上的元素交互时触发，load、scroll
- 焦点事件，当元素获得或失去焦点时触发，blur、focus
- 鼠标事件，当用户通过鼠标在页面执行操作时触发，dbclick、mouseup
- 滚轮事件，当使用鼠标滚轮或类似设备时触发，mousewheel
- 文本事件，当在文档中输入文本时触发，textInput
- 键盘事件，当用户通过键盘在页面上执行操作时触发，keydown、keypress
- 合成事件，当为IME（输入法编辑器）输入字符时触发，compositionstart
- 变动事件，当底层DOM结构发生变化时触发，DOMsubtreeModified


## DOM 事件模型
DOM 事件模型分为捕获和冒泡。一个事件发生后，会在子元素和父元素之间传播（propagation）。这种传播分成三个阶段。
- 捕获阶段：事件从 window 对象自上而下向目标节点传播的阶段；
- 目标阶段：真正的目标节点正在处理事件的阶段；
- 冒泡阶段：事件从目标节点自下而上向 window 对象传播的阶段。

捕获不可以取消，但是冒泡可以取消，通过 e.stopPropagation() 取消。

## 事件代理(事件委托)
由于事件会在冒泡阶段向上传播到父节点，因此可以把子节点的监听函数定义在父节点上，由父节点的监听函数统一处理多个子元素的事件。这种方法叫做事件的代理（delegation）。

可以减少内存消耗，提高性能。假设有一个列表，列表之中有大量的列表项，我们需要在点击每个列表项的时候响应一个事件
如果给每个列表项都绑定一个函数，那对于内存消耗是非常大的，效率上需要消耗很多性能。借助事件代理，我们只需要给父容器绑定方法即可，这样不管点击的是哪一个后代元素，都会根据冒泡传播的传递机制，把容器的 click 行为触发，然后把对应的方法执行，根据事件源，我们可以知道点击的是谁，从而完成不同的事。

可以动态绑定事件。在很多时候，我们需要通过用户操作动态的增删列表项元素，如果一开始给每个子元素绑定事件，那么在列表发生变化时，就需要重新给新增的元素绑定事件，给即将删去的元素解绑事件，如果用事件代理就会省去很多这样麻烦。

### 场景一 给100个按钮添加点击事件
监听这个100个按钮的祖先，等冒泡的时候判断target是不是这100个按钮中的一个
```js
div1.addEventListener("click", (e) => {
  const t = e.target;
  if (t.tagName.toLowerCase() === "button") {
    console.log("button 被点击了");
  }
});
```

### 场景二 要监听目前不存在的元素点击事件
监听祖先，等点击的时候看看是不是监听的元素即可。
```js
setTimeout(() => {
  const button = document.createElement("button");
  button.textContent = "click 1";
  div1.appendChild(button);
}, 1000);
div1.addEventListener("click", (e) => {
  const t = e.target;
  if (t.tagName.toLowerCase() === "button") {
    console.log("button被click");
  }
});
```

### 封装事件委托
只要实行一个函数就可以实现事件委托，要求：写出这样一个函数on('click','#testDiv','li',fn)，当用户点击#testDiv里面的li元素时，调用fn函数：
```js
setTimeout(() => {
  const button = document.createElement("button");
  button.textContent = "click 1";
  div1.appendChild(button);
}, 1000);

on("click", "#div1", "button", () => {
  //'#div'是选择器不是元素
  console.log("button 被点击啦");
});
function on(eventType, element, selector, fn) {
  if (!(element instanceof Element)) {
    element = document.querySelector(element);
  }
  element.addEventListener(eventType, (e) => {
    const t = e.target; //被点击的元素
    if (t.matches(selector)) {
      //matches用来判断一个元素是否匹配一个选择器,selector是不是一个选择器
      fn(e);
    }
  });
}
```


## Event 对象常见应用
### event.preventDefault()
如果调用这个方法，默认事件行为将不再触发。什么是默认事件呢？例如表单一点击提交按钮(submit)跳转页面、a标签默认页面跳转或是锚点定位等。
```html
<!-- 方法一 -->
<a href="javascript:;">链接</a>

<!-- 方法二 -->
<a id="test" href="http://www.cnblogs.com">链接</a>
<script>
var test = document.getElementById('test');
test.onclick = function(e){
    e = e || window.event;
    return false;
}
</script>

<!-- 方法三 -->
<a id="test" href="http://www.cnblogs.com">链接</a>
<script>
var test = document.getElementById('test');
test.onclick = function(e){
    e = e || window.event;
    e.preventDefault();
}
</script>
```

### event.stopPropagation() & event.stopImmediatePropagation()
event.stopPropagation() 方法阻止事件冒泡到父元素，阻止任何父事件处理程序被执行。事件冒泡阶段是指事件从目标节点自下而上向window对象传播的阶段。
```html
<div id="outer" style="width:200px;height:200px;background:green;">
    <div id="inner" style="width:100px;height:100px;background:blue;"></div>
</div>
<script>
var outer = document.getElementById('outer');
outer.onclick = function(ev) {
    console.log('outer');
    ev.stopPropagation();
};
var inner = document.getElementById('inner');
inner.onclick = function(ev) {
    console.log('inner');
    ev.stopPropagation();
};
</script>
```
stopImmediatePropagation 既能阻止事件向父元素冒泡，也能阻止元素同事件类型的其它监听器被触发。而 stopPropagation 只能实现前者的效果：
```html
<div id="outer" style="width:200px;height:200px;background:green;">
    <div id="inner" style="width:100px;height:100px;background:blue;"></div>
</div>
<script>
var outer = document.getElementById('outer');
outer.onclick = function(ev) {
    console.log('outer');
    ev.stopPropagation();
};
var inner = document.getElementById('inner');
inner.addEventListener('click', event => {
  console.log('btn click 1');
  event.stopImmediatePropagation()
});
inner.addEventListener('click', event => {
  console.log('btn click 2');
});
// 只打印 btn click 1
```

### event.target & event.currentTarget
event.target 指向引起触发事件的元素，而 event.currentTarget 则是事件绑定的元素，只有被点击的那个目标元素的 event.target 才会等于 event.currentTarget。

也就是说，event.currentTarget 始终是监听事件者，而 event.target 是事件的真正发出者。
```html
<div id="a" style="width:200px;height:200px;background:green;">
  <div id="b" style="width:150px;height:150px;background:blue;">
    <div id="c" style="width:100px;height:100px;background:gray;">
        <div id="d" style="width:50px;height:50px;background:orange;"></div>
    </div>
  </div>
</div>
<script>
  document.getElementById('a').addEventListener('click', function(e) {
    console.log(
      'target:' + e.target.id + '&currentTarget:' + e.currentTarget.id
    )
  })
  document.getElementById('b').addEventListener('click', function(e) {
    console.log(
      'target:' + e.target.id + '&currentTarget:' + e.currentTarget.id
    )
  })
  document.getElementById('c').addEventListener('click', function(e) {
    console.log(
      'target:' + e.target.id + '&currentTarget:' + e.currentTarget.id
    )
  })
  document.getElementById('d').addEventListener('click', function(e) {
    console.log(
      'target:' + e.target.id + '&currentTarget:' + e.currentTarget.id
    )
  })
</script>
```


## 参考
[1] https://blog.csdn.net/Ultraman_and_monster/article/details/104358762

[2] https://blog.csdn.net/weixin_55953988/article/details/122615820

[3] https://blog.csdn.net/Mark1614/article/details/120131475