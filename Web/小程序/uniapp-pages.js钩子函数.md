---
title: uniapp pages.js 钩子函数
date: 2023-02-18 11:44:00
tags:
categories:
- 小程序
---

### pages.js VS. pages.json
<https://ask.dcloud.net.cn/article/37140>
<https://www.npmjs.com/package/uni-pages-hot-modules>

`@dcloudio/uni-cli-shared/lib/pages.js`
```javascript
function getPagesJson () {
  return processPagesJson(getJson('pages.json', true))
}

const pagesJsonJsFileName = 'pages.js'

function processPagesJson (pagesJson, loader = {
  addDependency: function () {}
}) {
  // 读取 pages.js 
  const pagesJsonJsPath = path.resolve(process.env.UNI_INPUT_DIR, pagesJsonJsFileName)
  // 存在则刷新缓存
  if (fs.existsSync(pagesJsonJsPath)) {
    delete require.cache[pagesJsonJsPath]
    const pagesJsonJsFn = require(pagesJsonJsPath)
    if (typeof pagesJsonJsFn === 'function') {
      // 函数返回值 合并入 pages.json 配置
      pagesJson = pagesJsonJsFn(pagesJson, loader)
      if (!pagesJson) {
        console.error(`${pagesJsonJsFileName}  ${uniI18n.__('cliShared.requireReturnJsonObject')}`)
      }
    } else {
      console.error(`${pagesJsonJsFileName} ${uniI18n.__('cliShared.requireExportFunction')}`)
    }
  }
  // 将 subpackages 转换成 subPackages
  if (pagesJson.subpackages && !pagesJson.subPackages) {
    pagesJson.subPackages = pagesJson.subpackages
    delete pagesJson.subpackages
  } 
  // ...
  // pages
  filterPages(pagesJson.pages)
  // subPackages
  if (Array.isArray(pagesJson.subPackages) && pagesJson.subPackages.length) {
    pagesJson.subPackages.forEach(subPackage => {
      filterPages(subPackage.pages, subPackage.root)
    })
  }
  // ...
  return pagesJson
}
```
uni-app 自带一个钩子文件 pages.js，在项目 src 目录下建立 pages.js（与 pages.json 同级）即可生效（pages.json 仍然需要存在，作为初始值，建议存放一些和路由无关的配置）。

pages.js 要求 CommonJS 规范，直接通过 module.exports 输出一个钩子函数。
