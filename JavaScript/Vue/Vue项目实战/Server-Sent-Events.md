---
title: Server-Sent Events 教程
date: 2023-10-23 20:00:00
tags:
categories:
- Vue
---


服务器向浏览器推送信息，除了 WebSocket，还有一种方法：Server-Sent Events（以下简称 SSE）

## SSE 的本质
严格地说，HTTP 协议无法做到服务器主动推送信息。但是，有一种变通方法，就是服务器向客户端声明，接下来要发送的是流信息（streaming）。

也就是说，发送的不是一次性的数据包，而是一个数据流，会连续不断地发送过来。这时，客户端不会关闭连接，会一直等着服务器发过来的新的数据流，视频播放就是这样的例子。本质上，这种通信就是以流信息的方式，完成一次用时很长的下载。

SSE 就是利用这种机制，使用流信息向浏览器推送信息。它基于 HTTP 协议，目前除了 IE/Edge，其他浏览器都支持。


## SSE 的特点
SSE 与 WebSocket 作用相似，都是建立浏览器与服务器之间的通信渠道，然后服务器向浏览器推送信息。

总体来说，WebSocket 更强大和灵活。因为它是全双工通道，可以双向通信；SSE 是单向通道，只能服务器向浏览器发送，因为流信息本质上就是下载。如果浏览器向服务器发送信息，就变成了另一次 HTTP 请求。

SSE 也有自己的优点：
- SSE 使用 HTTP 协议，现有的服务器软件都支持。WebSocket 是一个独立协议。
- SSE 属于轻量级，使用简单；WebSocket 协议相对复杂。
- SSE 默认支持断线重连，WebSocket 需要自己实现。
- SSE 一般只用来传送文本，二进制数据需要编码后传送，WebSocket 默认支持传送二进制数据。
- SSE 支持自定义发送的消息类型。


## 后端处理
后端使用 Express 来搭建并发送数据：
```js

const express = require('express')
const app = express()
const port = 3000
//允许跨域
app.all('*', function (req, res, next) {
    res.setHeader("Access-Control-Allow-Origin", "*");
    next();
});

app.get('/sse', (req, res) => {
    const str = 'hello word!'
    // 设置 SSE 相关的响应头
    res.setHeader('Content-Type', 'text/event-stream;charset=utf-8');
    res.setHeader('Cache-Control', 'no-cache');
    res.setHeader('Connection', 'keep-alive');
    let index = 0
    const timer = setInterval(() => {
        if (index < str.length) {
            res.write("data: " + JSON.stringify({ content: str[index] }));
            index++
        } else {
            // 当所有数据都发送完毕时，结束响应
            clearInterval(timer); // 停止定时器
            res.end();
        }

    }, 100);
});

app.listen(port, () => {
    console.log(`Example app listening on port ${port}`)
})
```

sse 传输，我们需要在响应头中将 Content-Type 和 Connection 都设置为对应的字段，以此来保证，我们的响应能够被前端正确的处理。我们通过定时器，每次读取一个字符，来模拟每次响应的结果，读取完所有字符后，我们就将响应停止。

注意我们发送的数据格式要以 `data:` 开头，因为这是SSE规范的一部分。
- 标识行类型：以 data: 开头的行告诉客户端这是一个数据行，而不是其他类型的事件，比如注释或者消息标识。这样客户端可以正确地解析和处理接收到的数据。
- 易于解析：SSE 是一个文本协议，因此易于解析。以 data: 开头的行让客户端能够轻松地区分和提取事件数据部分，因为它们遵循明确的格式。
- 浏览器支持：大多数现代浏览器支持 SSE 协议，可以正确地处理以 data: 开头的行。这使得在客户端上使用 SSE 变得更加容易，因为浏览器会自动解析 SSE 响应中的数据。
- 规范要求：SSE 规范要求以 data: 开头的行用于传输数据，这是根据规范的定义来执行的。

## 前端处理
传统的请求方式主要依赖于使用Ajax来向后端发起请求，这种方式有一个明显的特点：一次请求只能接收一次响应数据，接收到单次响应结果后，即请求结束。然而，在某些场景下，我们需要持续地接收来自后端的数据更新，这时就需要摒弃传统的Ajax方式，而选择一些更适合实现实时数据更新的方法。

最终关注到了两个主要的API：EventSource和Fetch，它们分别具有不同的优势和用途。

| 特性 | EventSource | fetch API |
| :-: |:-|:-|
|兼容性 | 广泛支持，包括Internet Explorer 8及更高版本 | 在较新的浏览器中得到支持，不完全支持Internet Explorer |
| 数据格式 | 只支持服务器发送的文本数据，自动转换为文本 | 可以获取包括文本、JSON、Blob等在内的各种数据格式 |
| 错误处理 | 自动尝试重新连接，可以监听'error'事件来处理错误 | 没有内置的重试机制，需要手动处理错误并可能需要进行重试 |
| 流式处理 | 支持简单处理服务器发送的流式数据 | 不直接支持流式处理，但可以使用Response对象的body属性获取流式接口 |
| CORS问题 | 受同源策略限制，除非服务器配置了适当的CORS头，否则无法跨源加载 | 不受同源策略限制，可以跨源请求数据，但需要服务器配置适当的CORS头 |
| 灵活性 | 只能发送GET请求，拼接字符串传参 | 可以发起任意类型请求。传参灵活 |

使用 fetch 处理：
```js
import { useEffect, useState } from "react";
import "./App.scss";

function App() {
  const [chatText, setChatText] = useState("");

  const getRes = async () => {
    try {
      const res = await fetch("http://localhost:3000/sse", {
        method: "get",
      });
      const reader = res.body?.getReader();
      let text = "";
      while (reader) {
        const { value, done } = await reader.read();
        const chars = new TextDecoder().decode(value);
        if (done) {
          break;
        }
        const dataArray = chars.trim().split("\n\n");
        const jsonObjects = dataArray.map((data) => {
          const jsonString = data.substring("data: ".length);
          return JSON.parse(jsonString);
        });
        jsonObjects.forEach((item) => {
          text += item.content;
        });
        setChatText(text);
      }
    } catch (error) {
      console.log("error", error);
    }
  };

  useEffect(() => {
    getRes();
  }, []);

  return <div>{chatText}</div>;
}
```

getReader 方法是用于处理响应体（response body）的一种方式，它返回一个可用于异步读取响应数据的 ReadableStreamDefaultReader 对象。这个方法通常用于处理大型响应或流式数据，以便在数据逐步到达时逐步处理它们，而不是一次性将整个响应数据加载到内存中。

通过 read 方法，读取 stream 的返回值，我们可以从返回值中解构出value和done这个两个值，但是value值是Uint8Array，我们需要将其转化成UTF-8的字符。done值主要是来区分是否读取完毕，读取中，done会返回false，所有内容都读取完成后，就会返回true，我们可以done的值，来判断响应是否结束。


## 参考
[1] https://www.ruanyifeng.com/blog/2017/05/server-sent_events.html