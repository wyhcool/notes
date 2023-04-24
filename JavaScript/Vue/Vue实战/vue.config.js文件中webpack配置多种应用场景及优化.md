---
title: vue.config.js 文件中webpack配置，webpack 多种应用场景配置, 优化及多页面应用开发
date: 2023-04-24 21:28:00
tags:
categories:
- Vue
---

在 vue-cli3 以下版本中，关于 webpack 的一些配置都在 config 目录文件中，在 vue-cli3 以上版本中，没有了 config 目录，如果想对 webpack 相关内容进行配置，需要自己在根目录下(与 `package.json` 同级)创建一个 `vue.config.js` 文件，这个文件一旦存在，那么它会被 `@vue/cli-service` 自动加载。

## vue.config.js 中常用的配置
### 导出模块
使用 commonjs 语法导出模块：
```javascript
module.exports = {}
```

### publicPath 部署应用包的 BASE_URL
部署应用包的 BASE_URL，默认 `/`, 可以设置为相对路径 `./`,这样打出来的包，可以部署到任意路径上。
```javascript
let developmentPath='./';//开发环境-npm run serve时引用文件路径
let productionPath='./';//生产环境-npm run build打包后引用文件路径
module.exports = {
    publicPath: process.env.NODE_ENV === 'production' ? productionPath: developmentPath,
}
```

### outputDir 输出文件目录
输出文件目录（打包后生成的目录，默认dist）
```javascript
module.exports = {
    outputDir: path.join(__dirname, 'dist'), //build之后静态文件输出路径
}
```

### assetsDir 打包后生成的静态资源目录
打包后生成的静态资源目录，默认 '' ，也就是我们打包后的css，js等存放的位置
```javascript
module.exports = {
 assetsDir: 'static',
}
```

### lintOnSave
是否在保存的时候检查
```javascript
module.exports = {
    lintOnSave: process.env.NODE_ENV !== 'production', // eslint-loader 
}
```

### productionSourceMap 生产环境的 source map
生产环境的 source map，可以将其设置为 false 以加速生产环境构建，默认值是 true
```javascript
module.exports = {
    productionSourceMap: false,
}
```

### devServer
可通过 devServer.proxy 解决前后端跨域问题（反向代理）
```javascript
module.exports = {
  // 反向代理
  devServer: {
    index: '/index.html',   // 默认打开文件
    open: true,             // 自动打开浏览器
    host: 'localhost',      // 默认打开域名
    port: 8080,             // 默认打开端口号
    https: false,           // 开启关闭https请求
    hotOnly: false,         // 热更新
    proxy: {
      // 配置跨域
      '/api': {
        target: 'http://dev.test.cn:8082/', // 代理地址，会代替 axios 中设置的 baseURL
        ws: true, // proxy websockets
        changeOrigin: true,// 如果接口跨域，需要进行这个参数配置
        pathRewrite: { // pathRewrite 方法重写 url
          '^/api': '/',
        },
      },
    },
  },
}
```
扩展： hot 和 hotOnly 的区别是在某些模块不支持热更新的情况下，前者会自动刷新页面，后者不会刷新页面，而是在控制台输出热更新失败

### chainWebpack webpack配置
```javascript
module.exports = {
  chainWebpack: (config) => {
    config.plugins.delete('preload')
    config.plugins.delete('prefetch')
 
    config.module
      .rule('svg')
      .exclude.add(resolve('src/assets/icons'))
      .end()
 
    config.module
      .rule('icons')
      .test(/\.svg$/)
      .include.add(resolve('src/assets/icons'))
      .end()
      .use('svg-sprite-loader')
      .loader('svg-sprite-loader')
      .options({
        symbolId: 'icon-[name]',
      })
      .end()
 
    const imagesRule = config.module.rule('images')
    imagesRule.uses.clear() //清除原本的 images loader 配置
    imagesRule
      .test(/\.(jpg|gif|png|svg)$/)
      .exclude.add(path.join(__dirname, '../node_modules')) //不对node_modules里的图片转base64
      .end()
      .use('url-loader')
      .loader('url-loader')
      .options({ name: 'img/[name].[hash:8].[ext]', limit: 6000000 })
 
    config.optimization.splitChunks({
      cacheGroups: {
        vendors: {
          name: 'chunk-vendors',
          minChunks: pageNum,
          test: /node_modules/,
          priority: -10,
          chunks: 'initial',
        },
        elementUI: {
          name: 'chunk-elementUI', // split elementUI into a single package
          priority: 20, // the weight needs to be larger than libs and app or it will be packaged into libs or app
          test: /[\\/]node_modules[\\/]_?element-ui(.*)/, // in order to adapt to cnpm
        },
        commons: {
          name: 'chunk-commons',
          test: resolve('src/components'), // can customize your rules
          minChunks: 3, //  minimum common number
          priority: 5,
          reuseExistingChunk: true,
        },
      },
    })
  },
}
```
扩展：
preload：用于标记页面加载后即将用到的资源，浏览器将在主体渲染前加载 preload 标记文件。Vue CLI 应用会为所有初始化渲染需要的文件自动生成 preload 提示；

prefetch: 用于标记浏览器在页面加载完成后，利用空闲时间预加载的内容。Vue CLI 应用默认为所有作为 async chunk 生成的 JavaScript 文件 (通过动态 import() 按需 code splitting 的产物) 自动生成 prefetch 提示。

### configureWebpack webpack配置
```javascript
const UglifyJsPlugin = require('uglifyjs-webpack-plugin')
const CompressionWebpackPlugin = require('compression-webpack-plugin')
 
const productionGzipExtensions = ['js', 'css']
const Version = 'V6.1'
const Timestamp = new Date().getTime()
 
module.exports = {
    // webpack配置
    configureWebpack: (config) => {
          // 为生产环境修改配置
        if (process.env.NODE_ENV === 'production') {
            config.plugins.push(
                new UglifyJsPlugin({
                    uglifyOptions: {
                        compress: {
                            drop_debugger: true,//生产环境自动删除debugger
                            drop_console: true, //生产环境自动删除console
                        },
                        warnings: false,
                    },
                    sourceMap: false,   //关掉sourcemap 会生成对于调试的完整的.map文件，但同时也会减慢打包速度
                    parallel: true, //使用多进程并行运行来提高构建速度。默认并发运行数：os.cpus().length - 1。
                }),
                new CompressionWebpackPlugin({
                    filename: '[path].gz[query]',
                    algorithm: 'gzip',
                    test: new RegExp('\\.(' + productionGzipExtensions.join('|') + ')$'),
                    threshold: 10240,
                    minRatio: 0.8,
                })
            )
        }
        // 在这里配置后，减少了压缩的包内容，需要在public/index.html通过cdn方式再引入,注意对应的版本
        config.externals = { 
            vue: 'Vue',
            'vue-router': 'VueRouter',
            vuex: 'Vuex',
            axios: 'axios',
            jquery: '$',
            moment: 'moment',
            'mint-ui': 'MINT'
        },
         // 别名配置
        Object.assign(config, {
            // 开发生产共同配置
            resolve: {
                alias: {
                '@': path.resolve(__dirname, './src'),
                '@c': path.resolve(__dirname, './src/components'),
                '@p': path.resolve(__dirname, './src/pages')
                }
            }
        }),
        config.output.filename = `[name].${Version}.${Timestamp}.js`  //打包生成的文件
        config.output.chunkFilename = `[name].${Version}.${Timestamp}.js`
    },
 }
```
扩展:
uglifyjs-webpack-plugin 插件用来缩小（压缩优化）js文件，至少需要 Node v6.9.0 和 Webpack v4.0.0 版本。

compression-webpack-plugin 插件用来实现 Gzip 压缩。

chunkFilename 和 webpack.optimize.CommonsChunkPlugin 插件的作用差不多，都是用来将公共模块提取出来，但是用法不一样
```javascript
module.exports = {
    entry:{
        main:__dirname + '/app/main.js',
        index:__dirname + '/app/index.js'
    },
    output:{
        path:__dirname + '/public', //通过 HtmlWebpackPlugin 插件生成的 html 文件存放在这个目录下面
        filename:'/js/[name].js', //编译生成的 js 文件存放到根目录下面的 js 目录下面,如果 js 目录不存在则自动创建
        /*
        * chunkFilename 用来打包 require.ensure 方法中引入的模块,如果该方法中没有引入任何模块则不会生成任何chunk块文件
        * 比如在main.js文件中,require.ensure([],function(require){alert(11);}),这样不会打包块文件
        * 只有这样才会打包生成块文件require.ensure([],function(require){alert(11);require('./greeter')})
        * 或者这样require.ensure(['./greeter'],function(require){alert(11);})
        * chunk的hash值只有在require.ensure中引入的模块发生变化,hash值才会改变
        * 注意:对于不是在ensure方法中引入的模块,此属性不会生效,只能用CommonsChunkPlugin插件来提取
        * */
        chunkFilename:'js/[chunkhash:8].chunk.js'
    },
}
```
configureWebpack 和 chainWebpack 区别

在这里 configureWebpack 和 chainWebpack 的作用相同，唯一的区别就是他们修改 webpack 配置的方式不同：

chainWebpack 通过链式编程的形式，来修改默认的 webpack 配置

configureWebpack 通过操作对象的形式，来修改默认的 webpack 配置

如果对一个 loader 或 plugin 修改的配置如果是一项的话推荐 chainWebpack、如果是多项的话用 configureWebpack 直接覆写。

### css相关配置
这里配置了全局sass 需要安装的依赖 sass-loader less-loader
```javascript
module.exports = {
    css: {
        loaderOptions: {
            scss: {
                additionalData: `@import "@/assets/css/reset.scss";@import "@/assets/css/globle.scss";`  //注意配置的键名
            },
            postcss: {
                plugins: [
                    //remUnit这个配置项的数值是多少呢？？？ 通常我们是根据设计图来定这个值，原因很简单，便于开发。
                    //假如设计图给的宽度是750，我们通常就会把remUnit设置为75，这样我们写样式时，可以直接按照设计图标注的宽高来1:1还原开发。
                    require('postcss-px2rem')({
                        remUnit: 37.5
                    })
                ]
            }
        }
    },
}
```
由于 sass-loader 版本不同，loaderOptions 中的 additionalData 的键名也不同

sass-loader loader v8-， 这个选项名是 "data"，
sass-loader loader v8中， 这个选项名是 "prependData"，
sass-loader loader v10+， 这个选项名是 "additionalData"，

### pages
vue-cli3 中的 webpack 与 vue 多页面应用开发。

相关参数:
- entry：page 的入口
- template：模板来源
- filename：在 dist/index.html 的输出
- title：template 中的 title 标签
- chunks：在这个页面中包含的块

```javascript
module.exports = {
   pages:{
       main: {
           // page 的入口
           entry: "src/pages/main/main.js",
           // 模板来源
           template: "public/index.html",
           // 在 dist/index.html 的输出
           filename: "main.html",
           // 当使用 title 选项时，
           // template 中的 title 标签需要是 <title><%= htmlWebpackPlugin.options.title %></title>
           title: "Index Page",
           // 在这个页面中包含的块，默认情况下会包含
           // 提取出来的通用 chunk 和 vendor chunk。
           chunks: ["chunk-vendors", "chunk-common", "main"]
       },
       hh: {
           // page 的入口
           entry: "src/pages/login/main.js",
           // 模板来源
           template: "public/index.html",
           // 在 dist/index.html 的输出
           filename: "login.html",
           // 当使用 title 选项时，
           // template 中的 title 标签需要是 <title><%= htmlWebpackPlugin.options.title %></title>
           title: "Index Page",
           // 在这个页面中包含的块，默认情况下会包含
           // 提取出来的通用 chunk 和 vendor chunk。
           chunks: ["chunk-vendors", "chunk-common", "hh"]
       },
       // 当使用只有入口的字符串格式时，
       // 模板会被推导为 `public/subpage.html`
       // 并且如果找不到的话，就回退到 `public/index.html`。
       // 输出文件名会被推导为 `subpage.html`。
       subpage: "src/subpage/main.js"
   },
}
```
封装入口逻辑：
```javascript
const glob = require('glob') // 引入glob模块,用于扫描全部src/pages/**/main.js（返回的是一个数组）
// 打包多入口文件基本配置
function getPagesInfo() {
    let pages = {}
    glob.sync('src/pages/**/main.js').forEach((entry, i) => {
        let name = entry.slice(10, -8)
        pages[name] = {
            entry: entry,
            template: 'public.index.html',
            filename: name + '.html',
            title: '',
            chunks: ["chunk-vendors", "chunk-common", name]
        }
    })
    return pages
}
module.exports = {
    pages: getPagesInfo(),
    publicPath: './kk',
    assetsDir: 'static',
};
```


## 优化
### 优化打包 chunk-vendors.js
当运行项目并且打包的时候，会发现 chunk-vendors.js 这个文件非常大，那是因为 webpack 将所有的依赖全都压缩到了这个文件里面，这时我们可以将其拆分，将所有的依赖都打包成单独的 js。
```javascript
// 利用splitChunks将每个依赖包单独打包，在生产环境下配置，代码如下
module.exports = {
    configureWebpack: (config) => {
    if (process.env.NODE_ENV === 'production') {
      // 为生产环境修改配置...
      config.mode = 'production'
      // 将每个依赖包打包成单独的js文件
      let optimization = {
        runtimeChunk: 'single',
        splitChunks: {
          chunks: 'all',
          maxInitialRequests: Infinity,
          minSize: 20000, // 依赖包超过20000bit将被单独打包
          cacheGroups: {
            vendor: {
              test: /[\\/]node_modules[\\/]/,
              name (module) {
                // get the name. E.g. node_modules/packageName/not/this/part.js
                // or node_modules/packageName
                const packageName = module.context.match(/[\\/]node_modules[\\/](.*?)([\\/]|$)/)[1]
                // npm package names are URL-safe, but some servers don't like @ symbols
                return `npm.${packageName.replace('@', '')}`
              }
            }
          }
        }
      }
      Object.assign(config, {
        optimization
      })
    } 
  }
}
```

### 打包时去除打印信息
首先下载相关插件 `uglifyjs-webpack-plugin`
```shell
$ npm i -D uglifyjs-webpack-plugin
```

在 vue.config.js 文件中引入，并在 configureWebpack 的 optimization 中添加如下代码
```javascript
const UglifyPlugin = require('uglifyjs-webpack-plugin')
module.exports = {
    
 configureWebpack: (config) => {
    if (process.env.NODE_ENV === 'production') {
      // 为生产环境修改配置...
      config.mode = 'production'
      // 将每个依赖包打包成单独的js文件
      let optimization = {
 
        /*以下代码适用于 uglifyjs-webpack-plugin 2.1.1 及以前的版本*/
        minimizer: [new UglifyPlugin({
          uglifyOptions: {
            compress: {
              warnings: false,
              drop_console: true, // console
              drop_debugger: false,
              pure_funcs: ['console.log'] // 移除console
            }
          }
        })]
        
      }
      Object.assign(config, {
        optimization
      })
    }
   
  }
}
```

新版 uglifyjs-webpack-plugin 需写成以下方式
```javascript
minimizer: [new UglifyPlugin({
    uglifyOptions: {
        warnings: false,
        compress: {
            drop_console: false, // console
            drop_debugger: false,
            pure_funcs: ['console.log'] // 移除console
        }
    }
})]
```

### 开启gizp压缩
gizp压缩是一种http请求优化方式，通过减少文件体积来提高加载速度。html、js、css文件甚至json数据都可以用它压缩，可以减小60%以上的体积。webpack在打包时可以借助 `compression-webpack-plugin` 实现gzip压缩。

下载 compression-webpack-plugin
```shell
$ npm i -D compression-webpack-plugin
```

vue.config.js配置
```javascript
const CompressionPlugin = require("compression-webpack-plugin");
module.exports = {
  configureWebpack: (config) => {
    if (process.env.NODE_ENV === 'production') {
      // 为生产环境修改配置...
      config.mode = 'production';
 
      if(openGzip){
        config.plugins = [
          ...config.plugins,
          new CompressionPlugin({
            test:/\.js$|\.html$|.\css/, //匹配文件名
            threshold: 10240,//对超过10k的数据压缩
            deleteOriginalAssets: false //不删除源文件
          })
        ]
      }
 
    } else {
      // 为开发环境修改配置...
      config.mode = 'development';
    }
    
  }
}
```

### 图片压缩 
下载 image-webpack-loader
```shell
$ npm install --save-dev image-webpack-loader
```

在vue.config.js中修改相关配置，4M的图片使用默认设置压缩成1.4M，自定义的设置可以更小
```javascript
module.exports = {
  // ...
  // 默认设置
  const defaultOptions = {
      bypassOnDebug: true
  }
 //  自定义设置
  const customOptions = {
      mozjpeg: {
        progressive: true,
        quality: 50
      },
      optipng: {
        enabled: true,
      },
      pngquant: {
        quality: [0.5, 0.65],
        speed: 4
      },
      gifsicle: {
        interlaced: false,
      },
      // 不支持WEBP就不要写这一项
      webp: {
        quality: 75
      }
  }
  chainWebpack: config => {
 
    config.module.rule('images') 
        .test(/\.(gif|png|jpe?g|svg)$/i)
        .use('image-webpack-loader')
        .loader('image-webpack-loader')
        .options(customOptions)
        .end() 
  }
}
```

### 移动端 px2rem 响应样式
安装
```shell
$ npm i -S lib-flexible postcss-px2rem
```

在项目入口中 main.js 中引入 lib-flexible
```javascript
import 'lib-flexible' 
```

**注意事项: 由于 flexible 会动态给页面header中添加 `<meta name='viewport' >` 标签，所以务必请把目录 public/index.html 中的这个标签删除！！**

配置 postcss-px2rem
项目中vue.config.js中进行如下的配置
```javascript
module.exports = { 
    css: {
        loaderOptions: { 
            css: {}, 
            postcss: { 
                plugins: [ require('postcss-px2rem')({ remUnit: 37.5 }) 
                ] 
            } 
        } 
    } 
}
```


## 多页面开发与配置
### 搭建项目
通过 vue-cli3 脚手架进行创建
```shell
$ vue create prject-name
```
搭建文件结构，新建 pages 存放每个页面相关内容，页面文件内放入 main.js 与 router.js

配置路由
```javascript
import Vue from 'vue'
import VueRouter from 'vue-router'
Vue.use(VueRouter)

// const originalPush = VueRouter.prototype.push
// VueRouter.prototype.push = function push(location) {
//   return originalPush.call(this, location).catch((err) => err)
// }

const routes = [
    {
        path: '/login',
        name: 'login',
        component: () => import('./views/index.vue'),
        meta: {
            title: '这里是动态title'
        },
        children: [
            /*  
            单页面应用的写法     
            {
                path: 'reset',
                name: 'reset',
                component: () => import('./reset.vue')
            
            },
            */
            //  ***多页面的写法
            {
                path: 'editJobs',
                components: {
                    'editJobs': () => import('./views/reset.vue'),
                },
            },],
 
    },
    // {
    //     path: '/',
    //     redirect: '/'
    // }
 
]
export default new VueRouter({
    mode: 'hash',
    base: process.env.BASE_URL,
    routes
})
```

配置 vue.config.js
```javascript
// 打包多入口文件基本配置
function getPagesInfo() {
    let pages = {}
    const glob = require('glob') // 引入glob模块,用于扫描全部 src/pages/**/main.js（返回的是一个数组）
    glob.sync('src/pages/**/main.js').forEach((entry, i) => {
        let name = entry.slice(10, -8)
        pages[name] = {
            entry: entry,
            template: 'public/index.html',
            filename: name + '.html',
            title: '',
            chunks: ["chunk-vendors", "chunk-common", name]
        }
    })
    return pages
}
module.exports = {
    pages: getPagesInfo(),
    publicPath: './kk',
    assetsDir: 'static',
};
```

### 注意事项
- 必须为hash的
- path 路径为 /
- 或者访问路径上加上path
例如:
```
http://localhost:8080/login.html#/
http://localhost:8080/login.html#/login/
```

## vue.config.js 完整代码
```javascript
// 打包多入口文件基本配置
let developmentPath = './';//开发环境-npm run serve时引用文件路径
let productionPath = './';//生产环境-npm run build打包后引用文件路径

const UglifyJsPlugin = require('uglifyjs-webpack-plugin')//生产环境取消打印
const CompressionWebpackPlugin = require('compression-webpack-plugin')//gzip压缩

const productionGzipExtensions = ['js', 'css']
const Version = 'V6.1'
const Timestamp = new Date().getTime()
function getPagesInfo() {
    let pages = {}
    const glob = require('glob') // 引入glob模块,用于扫描全部src/pages/**/main.js（返回的是一个数组）
    glob.sync('src/pages/**/main.js').forEach((entry, i) => {
        let name = entry.slice(10, -8)
        pages[name] = {
            entry: entry,
            template: 'public/index.html',
            filename: name + '.html',
            title: '',
            chunks: ["chunk-vendors", "chunk-common", name]
        }
    })
    return pages
}
// 打包相关
module.exports = {
    pages: getPagesInfo(),//多页面应用配置
    publicPath: process.env.NODE_ENV === 'production' ? productionPath : developmentPath, // 基本路径-引用文件的路 __dirname + '/server/dist', //build之后静态文件输出路径
    assetsDir: 'static',//静态资源大包位置
    outputDir: __dirname + '/server/dist', //build之后静态文件输出路径
    lintOnSave: process.env.NODE_ENV !== 'production',// 打包的时候eslint-loader检查 
    productionSourceMap: false,//source map 检查
    // 启动服务器
    devServer: {
        index: '/login.html',   //默认打开文件
        open: true,             //自动打开浏览器
        host: 'localhost',      //默认打开域名
        port: 8080,             //默认打开端口号
        https: false,           //开启关闭https请求
        hotOnly: false,         //热更
        // 反向代理
        proxy: {
            // 配置跨域
            '/api': {
                target: 'http://dev.aabb.cn:8082/', //代理地址，这里设置的地址会代替axios中设置的baseURL
                ws: true,    proxy websockets
                changeOrigin: true,// 如果接口跨域，需要进行这个参数配置
                pathRewrite: {                //pathRewrite方法重写url
                    '^/api': '/',
                },
            },
        },
    },
    // webpack配置  链式
    chainWebpack: (config) => {
        // 1、取消预加载增加加载速度
        config.plugins.delete('preload')
        config.plugins.delete('prefetch')
 
        // 2、vue中使用SVG图标，并且想批量导入，然后需要使用的时候直接添加就可以
        config.module
            .rule('svg')
            .exclude.add(resolve('src/assets/icons'))
            .end()
        config.module
            .rule('icons')
            .test(/\.svg$/)
            .include.add(resolve('src/assets/icons'))
            .end()
            .use('svg-sprite-loader')
            .loader('svg-sprite-loader')
            .options({
                symbolId: 'icon-[name]',
            })
            .end()
 
        // 3、图片处理
        const imagesRule = config.module.rule('images')
        imagesRule.uses.clear() //清除原本的images loader配置
        imagesRule
            .test(/\.(jpg|gif|png|svg)$/)
            .exclude.add(path.join(__dirname, '../node_modules')) //不对node_modules里的图片转base64
            .end()
            .use('url-loader')
            .loader('url-loader')
            .options({ name: 'img/[name].[hash:8].[ext]', limit: 6000000 })
 
        config.optimization.splitChunks({
            cacheGroups: {
 
                vendors: {
                    name: 'chunk-vendors',
                    minChunks: pageNum,
                    test: /node_modules/,
                    priority: -10,
                    chunks: 'initial',
                },
 
                elementUI: {
                    name: 'chunk-elementUI', // split elementUI into a single package
                    priority: 20, // the weight needs to be larger than libs and app or it will be packaged into libs or app
                    test: /[\\/]node_modules[\\/]_?element-ui(.*)/, // in order to adapt to cnpm
                },
 
                commons: {
                    name: 'chunk-commons',
                    test: resolve('src/components'), // can customize your rules
                    minChunks: 3, //  minimum common number
                    priority: 5,
                    reuseExistingChunk: true,
                },
            },
        })
    },
    // webpack配置
    configureWebpack: (config) => {
          // 为生产环境修改配置
        if (process.env.NODE_ENV === 'production') {
      
            config.plugins.push(
                  // 1、取消打印
                new UglifyJsPlugin({
                    uglifyOptions: {
                        compress: {
                            drop_debugger: true,//生产环境自动删除debugger
                            drop_console: true, //生产环境自动删除console
                        },
                        warnings: false,
                    },
                    sourceMap: false,   //关掉sourcemap 会生成对于调试的完整的.map文件，但同时也会减慢打包速度
                    parallel: true, //使用多进程并行运行来提高构建速度。默认并发运行数：os.cpus().length - 1。
                }),
 
                // 2、gzip压缩
                new CompressionWebpackPlugin({
                    filename: '[path].gz[query]',
                    algorithm: 'gzip',
                    test: new RegExp('\\.(' + productionGzipExtensions.join('|') + ')$'),
                    threshold: 10240,
                    minRatio: 0.8,
                })
            )
        }
 
        // 在这里配置后，减少了压缩的包内容，需要在public/index.html通过cdn方式再引入,注意对应的版本
        config.externals = { 
            vue: 'Vue',
            'vue-router': 'VueRouter',
            vuex: 'Vuex',
            axios: 'axios',
            jquery: '$',
            moment: 'moment',
            'mint-ui': 'MINT'
        },
 
 
         // 别名配置
        Object.assign(config, {
            // 开发生产共同配置
            resolve: {
                alias: {
                '@': path.resolve(__dirname, './src'),
                '@c': path.resolve(__dirname, './src/components'),
                '@p': path.resolve(__dirname, './src/pages')
                }
            }
        }),
 
        config.output.filename = `[name].${Version}.${Timestamp}.js`  //打包生成的文件
        config.output.chunkFilename = `[name].${Version}.${Timestamp}.js`
    },
    // css相关
    css: {
        loaderOptions: {
            // 配置全局sass
            scss: {
                additionalData: `@import "@/assets/css/reset.scss";@import "@/assets/css/globle.scss";`  //注意配置的键名
            },
            // lib-flexible
            postcss: {
                plugins: [
                    //remUnit这个配置项的数值是多少呢？？？ 通常我们是根据设计图来定这个值，原因很简单，便于开发。
                    //假如设计图给的宽度是750，我们通常就会把remUnit设置为75，这样我们写样式时，可以直接按照设计图标注的宽高来1:1还原开发。
                    require('postcss-px2rem')({
                        remUnit: 37.5
                    })
                ]
            }
        }
    },
    parallel: require('os').cpus().length > 1, // 是否为 Babel 或 TypeScript 使用 thread-loader。该选项在系统的 CPU 有多于一个内核时自动启用，仅作用于生产构建。
    pwa: {}, // PWA 插件相关配置 
    pluginOptions: {},  // 第三方插件配置
};
```


## 参考
[1] https://blog.csdn.net/gcyaozuodashen/article/details/128217703