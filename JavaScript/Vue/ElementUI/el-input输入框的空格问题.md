---
title: el-input输入框的空格问题
date: 2023-04-30 21:00:00
tags:
categories:
- ElementUI
---

## 需求
在表单中，输入的内容要去除两端空格
- 1.允许输入空格
- 2.输入空格后表单非空验证不通过


## 解决办法
解决方式1：使用 v-model 的指令修饰符 .trim (缺点：不能输入空格，不满足需求)
解决方法2：使用 blur 方法在失去焦点时进行 trim（）
```javascript
@blur="formData.helpUrl=$event.target.value.trim()"
```


## 参考
[1] https://blog.csdn.net/qq_41893334/article/details/113308866