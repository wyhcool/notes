---
title: 解决 el-tabs 的 el-tab-pane 切换元素直接宽高消失问题
date: 2023-07-02 16:58:00
tags:
categories:
- ElementUI
---

## 原因分析
在页面进行加载时，隐藏的图表找不到对应的div大小，所以默认给了一个大小。所以要做的就是在页面加载时，就对图表进行初始化。


## 解决方案
利用 v-if 属性,当切换至对应的 tab 时，设置其 v-if 的值为 true 即可，同时设置默认显示的 tab。
```html
<el-tabs type="card" v-model="tabItem">
  <el-tab-pane name="heart">
    <span slot="label"><icon name="heart" scale="2"></icon>心率</span>
    <baseline ref="heart" :chartData="{}" v-if="'heart' === tabItem"></baseline>
  </el-tab-pane>
  <el-tab-pane name="breath">
    <span slot="label"><icon name="breath" scale="2"></icon>呼吸</span>
    <baseline ref="breath" :chartData="{}" v-if="'breath' === tabItem"></baseline>
  </el-tab-pane>
  <el-tab-pane label="体动" name="move">
    <span slot="label"><icon name="move" scale="2"></icon>体动</span>
    <baseline ref="move" :chartData="{}" v-if="'move' === tabItem"></baseline>
  </el-tab-pane>
</el-tabs>
```

这里默认 tab 为 heart tab，当切换时，同一时刻只有一个 v-if 为 true，当将其设置为 true 时，Vue 会重新在页面渲染组件，即完成了组件渲染的步骤。


## 参考
[1] https://www.cnblogs.com/robinunix/p/10881882.html