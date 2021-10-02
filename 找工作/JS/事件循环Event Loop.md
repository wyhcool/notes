---
title: 事件循环
date: 2021-10-01 11:49
tags:
categories:
- 找工作
---

Event loop 是一个 JS 引擎执行模型，浏览器和 NodeJS 基于不同的技术实现了各自的 Event Loop。

微队列，microtask，也叫 jobs。一些异步任务的回调会依次进入 micro task queue，等待后续被调用，这些异步任务包括：
- process.nextTick (Node独有)
- Promise 的 then 的回调（注意，new Promise() 的函数参数是同步代码，then的回调才是异步）
- await 下方的代码和赋值代码（注意，await 后方的函数是同步代码）

宏队列，macrotask，也叫 tasks。一些异步任务的回调会依次进入 macro task queue，等待后续被调用，这些异步任务包括：
- setTimeout 的回调
- setInterval 的回调
- setImmediate 的回调
- requestAnimationFrame (浏览器独有)
- I/O
- UI rendering (浏览器独有)

JavaScript 代码执行的具体流程
- 1、执行全局 Script 同步代码，这些同步代码有一些是同步语句（new Promise()的函数参数是同步代码），有一些是异步语句（比如setTimeout等）；
- 2、异步语句的回调任务会根据上方介绍的规定，放入微任务队列或宏任务队列。
- 3、全局 Script 代码执行完毕后，调用栈 Stack 会清空；
- 4、从微队列（microtask queue）中取出位于队首的回调任务，放入调用栈 Stack 中执行，执行完后 microtask queue 长度减1；
- 5、继续取出位于微队列队首的任务，放入调用栈 Stack 中执行，以此类推，直到把微队列中的所有任务都执行完毕。注意，如果在执行微队列任务的过程中，如果又产生了新的微任务，那么会加入到微任务队列的末尾，也会在这个周期被调用执行；
- 6、微任务队列中的所有任务都执行完毕，此时微任务队列为空队列，调用栈 Stack 也为空；
- 7、取出宏队列（macrotask queue）中位于队首的任务，放入 Stack 中执行；
- 8、继续观察微队列是否有任务，如果有，则全部执行微队列任务；
- 9、重复7-8步骤；
- 10、宏队列执行完毕后，调用栈 Stack 为空；
- 11、重复第4-8个步骤；

通俗说：微队列是高优先级，每执行一串微队列，则执行一个宏队列任务，然后又去执行微队列，重复重复再重复下去。

题目1：
```javascript
console.log(1);

setTimeout(() => {
  console.log(2);
  Promise.resolve().then(() => {
    console.log(3)
  });
});

new Promise((resolve, reject) => {
  console.log(4)
  resolve(5)
}).then((data) => {
  console.log(data);
})

setTimeout(() => {
  console.log(6);
})

console.log(7);

// 打印结果顺序：
// 1
// 4
// 7
// 5
// 2
// 3
// 6
```

题目2：
```javascript
new Promise((resolve, reject) => {
  console.log(1)
  resolve(2)
}).then((data) => {
  // 1号回调
  console.log(data);
  return 3
}).then((data) => {
  // 2号回调
  console.log(data);
})

new Promise((resolve, reject) => {
  console.log(5)
  resolve(6)
}).then((data) => {
  // 3号回调
  console.log(data);
  return 7;
}).then((data) => {
  // 4号回调
  console.log(data);
})

// 打印结果顺序：
// 1
// 5
// 2
// 6
// 3
// 7
```

题目3：
```javascript
console.log('script start');

async function async1() {
    await async2();
    console.log('async1 end');
};

async function async2() {
    console.log('async2 end');
};

async1()

setTimeout(() => {
    console.log('setTimeout')
}, 0)

new Promise((resolve, reject) => {
    console.log('promise start');
    resolve()
})
.then(() => console.log('promise end'))

console.log('script end')

// 打印结果顺序：
// script start
// async2 end
// promise start
// script end
// async1 end
// promise end
// setTimeout
```

题目4：
```javascript
let x = 5;
let y;
function ret() {
    x += 1;
    console.log('x是', x);
    console.log('y是', y);
    return x;
}
async function a() {
    y = await ret();
    console.log(y);
}
async function b() {
    y = await ret();
    console.log(y);
}

a()
b()

// 打印结果顺序：
// x是6
// y是undefined
// x是7
// y是undefined
// 6
// 7
```

题目5:
```javascript
var i;
for (i = 0; i < 20; i++) {
  new Promise(resolve => {
    console.log(i)
  })
}

// 如果 new Promise() 函数参数是异步任务，就会打印 20 个 20，实际上打印的是 0 到 19，反证通过。
```