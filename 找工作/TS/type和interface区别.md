---
title: type 和 interface 区别
date: 2022-09-04 09:29:00
tags:
categories:
- 找工作
---

## 相似的地方
### 对象自变量式的结构定义
```typescript
// 都定义了一个有 a 属性的对象结构。
interface Foo {
    a: string
}
type Foo {
    a: string
}
```

### 函数类型
函数类型其实由两个部分构成，参数类型和返回值类型。
```typescript
// 表示同一类型的函数，
interface Foo {
    (a: string): string
}
type Foo = (a: string) => string
```

### 混合类型
由于 JavaScript 具有动态和灵活的性质，有时可能会遇到一个对象，该对象可以作为上述某些类型的组合使用，就是一个既具有函数特性又具有对象特性的类型，它具有一些属性。如下：
```typescript
interface Counter {
    (start: number): string;
    interval: number;
    reset(): void;
}
// 用 type 实现
type Counter = {
    (start: number): string;
    interval: number;
    reset(): void;
}
```

### 关于 | 和 & 操作符的行为完全相同
`&` 和 `|` 操作符都会创建一个新的类型，而且是相关类型父子类型链上的类型。它们区别是他们的产物是在父子类型链上的不同角色。如上所示，第一段代码中，对 Foo 和 Bar 使用了 `&` 操作符，创建的新的类型 Baz，结果是 Baz 是 Foo 和 Bar 的子类型，就是说，Bar 类型的变量何以赋值给Foo 和 Bar 类型的变量。在第二段代码中，对 Foo 和 Bar 使用了 | 操作符，创建的新的类型 Baz，结果是 Baz 是 Foo 和 Bar 的父类型，即，Foo 和 Bar 类型的变量可以赋值给 Bar。

```typescript
type Foo = {
    a: string
}
type Bar = {
    b: number
}
type Baz = Foo & Bar
// 用 interface 实现
interface Foo {
    a: string
}
interface Bar {
    b: number
}
type Baz = Foo & Bar
```

### 可选属性
```typescript
// 都表示a可有可无。
interface Foo {
    a?: string
}
type Foo = {
    a?: string
}
```

### 索引类型
```typescript
// 都表示索引为 string 的属性的类型都为 number，索引为 number 的属性的类型都为 string。
interface Foo {
    [x: string]: number
    [x: number]: string
}
// 
type Foo = {
    [x: string]: number
    [x: number]: string
}
```

### 用于类 implements
```typescript
interface ClockInterface {
    currentTime: Date;
}

class Clock implements ClockInterface {
    currentTime: Date = new Date();
    constructor(h: number, m: number) { }
}
```
在 TypeScript 中，implements 操作只是为 class 提供一种类型约束，没有其他作用，所以，主要是符合 class 结构的类型都是可以被 implements 的。所以除了 interface 之外，type 也可以进行 implements。但因为 JavaScript 中引入 class 是照着 OOP 来做的，所以在需要 implements时，使用 interface 有更强的语意性。

## 不同的地方
### type 类型别称
类型别称就是为已经存在的类型创建另一个名字，代表完全相同的意义：
```typescript
type ObjectAlias = object
```
虽然说这个特性是 type 独有的，但当原类型不是原始类型时，即原类型不是 number、string、boolean、object、symbol、null、undefined、void、never、unknown、any 时，interface 可以使用以下方式实现类似的功能(interface只能定义对象数据结构类型)：
```typescript
interface Foo {
  a: string
}
interface FooAlias extends Foo {}
```

### interface 扩展接口（extends interface）
```typescript
interface Foo {
  a: string
}
interface Bar extends Foo {
  b: number
}
```
这里使用 & 可以实现类似的效果，如下：
```typescript
interface Foo {
  a: string
}
type Bar = Foo & {
  b: number
}
```

### interface 扩展类（extends class）
```typescript
class Control {
    private state: any;
}

interface SelectableControl extends Control {
    select(): void;
}
```
在类声明时，其实也同时声明了一个描述该类结构和类型的接口，所以这里的扩展操作与上面相似。

## 使用场景
几乎接口的所有特性都可以通过类型别名来实现，但建议优先选择接口，接口满足不了再使用类型别名。

### type 的一般使用场景
- 一般定义基本或联合类型
- 一般定义元组类型
- 一般定义函数类型
- 定义映射类型

### interface 的使用场景
- 需要interface 重名会自动合并属性扩展的
- 定义对象数据结构(不使用type时)


## 参考
[1] https://zhuanlan.zhihu.com/p/92906055