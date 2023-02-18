---
title: Vue模式与环境变量
date: 2023-02-18 11:41:00
tags:
categories:
- 小程序
---

### 模式与环境变量
<https://cli.vuejs.org/zh/guide/mode-and-env.html>

默认情况下，一个 Vue CLI 项目有三个模式：
- development 模式用于 vue-cli-service serve
- test 模式用于 vue-cli-service test:unit
- production 模式用于 vue-cli-service build 和 vue-cli-service test:e2e

通过传递 `--mode` 选项参数为命令行覆写默认的模式。

只有 `NODE_ENV`，`BASE_URL` 和以 `VUE_APP_` 开头的变量将通过 webpack.DefinePlugin 静态地嵌入到客户端侧的代码中。

### vue cli 解析 env 文件
<https://ask.dcloud.net.cn/article/39910>

vue-cli-service 使用 dotenv 解析 `.env` 文件之后，使用 `@vue/cli-service/lib/util/resolveClientEnv.js`
```javascript
const prefixRE = /^VUE_APP_/

module.exports = function resolveClientEnv (options, raw) {
  const env = {}
  Object.keys(process.env).forEach(key => {
    if (prefixRE.test(key) || key === 'NODE_ENV') {
      env[key] = process.env[key]
    }
  })
  env.BASE_URL = options.publicPath

  if (raw) {
    return env
  }

  for (const key in env) {
    env[key] = JSON.stringify(env[key])
  }
  return {
    'process.env': env
  }
}
```
过滤了一部分key，不是 `APP_VUE_` 开头的全局变量就会被过滤掉，此外还保留了 `NODE_ENV`

通常来说，我们都是在项目的根目录下面执行 vue-cli-service，所以默认的环境变量文件 `.env` 也是在根目录下面。

### uniapp-cli 命令
`@dcloudio/vue-cli-plugin-uni/bin/uniapp-cli.js`

本质还是使用 vue-cli-service 命令：
```javascript
const path = require('path')

const Service = require('@vue/cli-service')

const {
  initCustomScript
} = require('@dcloudio/uni-cli-shared/lib/package')
const uniI18n = require('@dcloudio/uni-cli-i18n')

module.exports = function custom (argv) {
  const script = argv._[1]
  if (!script) {
    console.error(uniI18n.__('pluginUni.pleaseConfigScriptName'))
    process.exit(0)
  }

  const scriptOptions = initCustomScript(script, path.resolve(process.cwd(), 'package.json'))
  if (scriptOptions && scriptOptions.title) {
    // console.log('>' + scriptOptions.title)
  }

  // @vue/cli-service/lib/Service.js
  const service = new Service(process.env.VUE_CLI_CONTEXT || process.cwd())

  const command = (
    process.env.NODE_ENV === 'development' &&
      process.env.UNI_PLATFORM === 'h5'
  ) ? 'uni-serve'
    : 'uni-build'

  service.run(command, {
    watch: process.env.NODE_ENV === 'development',
    minimize: process.env.UNI_MINIMIZE === 'true',
    clean: false,
    subpackage: argv.subpackage,
    plugin: argv.plugin,
    sourcemap: argv.sourcemap
  }).catch(err => {
    console.error(err)
    process.exit(1)
  })
}
```