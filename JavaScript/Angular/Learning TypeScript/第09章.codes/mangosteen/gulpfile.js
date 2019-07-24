'use strict';

//通过参数指定环境
//gulp --build
//gulp --dev
const argv = require("yargs").argv; //argv 对象用来保存命令行参数，传递参数时，参数名以 -- 开头，中间使用 = 或 空格，然后接上值。
const gulp = require("gulp");
const browserify = require("browserify");
const source = require("vinyl-source-stream"); //把 browserify 的输出文件是配成 gulp 能够解析的格式，这种格式叫做 vinyl
//vinyl-source-stream 用于将一个 stream 封装成一个 vinyl 对象，
//即创建一个 vinyl 对象，给它指定一个文件名，并将其 contents设置为该 stream
//需要注意的是，由于要封装的流本身只是一个流，并不是一个文件，所以这里指定的文件名是由用户随意指定的，可以说是假的文件名，
//但是这个假的文件名也可以被下游的输出流利用，例如使用该 vinyl 对象的文件名和路径将文件写到实际的文件系统中
const tsify = require("tsify"); //browserify 插件, 访问 typescript 编译器, 类似 gulp-typescript
const sourcemaps = require("gulp-sourcemaps"); //sourcemap 记录压缩前后的位置信息记录，当产生错误时直接定位到未压缩前的位置
const buffer = require("vinyl-buffer");
//vinyl-buffer 读取一个 vinyl 对象流，并将流中的 vinyl 对象的内容
//即contents属性，该工具主要是针对 contents 为 stream 的对象，对于 contents 为 buffer 类型的，则原样输出
//全部读取并封装到一个 buffer 中，返回一个相同的 vinyl 对象，但是将其 contents 换成封装好的 buffer
const watchify = require("watchify"); //browserify 插件, 使用 watchify 加速 browserify, 编译一个持续监视文件的改动, 并且只重新打包必要的文件
const browserSync = require("browser-sync"); //开启本地服务
const reload = browserSync.reload; //重载
const sass = require("gulp-sass");
const gulpif = require("gulp-if");
const htmlmin = require("gulp-htmlmin");
const uglify = require("gulp-uglify");
const del = require("del");
const zip = require("gulp-zip");
const moment = require("moment"); //日期处理类库
const watch = require("gulp-watch"); //在gulp4解决'只自动刷新一次问题'需要用到 gulp-watch 包

//配置项目
const rootBuildPath = "./dist";
const rootSourcePath = "./source";
const cssBuildPath = rootBuildPath + "/css";
const cssSource = rootSourcePath + "/scss/**/*.scss";
const jsSource = rootSourcePath + "/**/*.ts";
const jsBundleName = "bundle.js";
const jsBuildPath = rootBuildPath + "/js";
const jsEntryPoint = rootSourcePath + "/app/main.ts";
const htmlSource = rootSourcePath + "/app/*.html";
const htmlBuildPath = rootBuildPath + "/";
const htmlEntryPoint = "index.html";
const templatesSource = rootSourcePath + "/app/templates/**/*.hbs";
const templatesBuildPath = rootBuildPath + "/templates";

function logError(err) {
    console.log(err.message);
}

function shouldMinify() {
    return argv.build;
}

const browserifyOptions = {
    debug: !shouldMinify(),
    entries: jsEntryPoint, //入口点
    plugin: [tsify]  //tsify 将自动从 tsconfig.json 读取选项
};

if (argv.dev) {
    browserifyOptions.cache = {};
    browserifyOptions.packageCache = {};
    browserifyOptions.plugin.push(watchify)
}

const browserifyInstance = browserify(browserifyOptions);

gulp.task("js", function() {
    return browserifyInstance
        .bundle()
        .on("error", logError)
        .pipe(source(jsBundleName))
        .pipe(buffer())
        .pipe(sourcemaps.init({loadMaps: true}))
        .pipe(gulpif(shouldMinify(), uglify().on("error", logError)))  //压缩混淆
        .pipe(sourcemaps.write("./"))
        .pipe(gulp.dest(jsBuildPath));
});

gulp.task("css", function() {
    return gulp.src(cssSource)
        .pipe(sourcemaps.init())
        .pipe(sass({outputStyle: shouldMinify() ? "compressed" : "nested"}))
        .pipe(sourcemaps.write("."))
        .pipe(gulp.dest(cssBuildPath));
});

const htmlOptions = {
    removeComments: true, //清除HTML注释
    collapseWhitespace: true, //压缩HTML
    collapseBooleanAttributes: true, //省略布尔属性的值 <input checked="true"/> ==> <input />
    removeEmptyAttributes: true, //删除所有空格作属性值 <input id="" /> ==> <input />
    removeScriptTypeAttributes: true, //删除<script>的type="text/javascript"
    removeStyleLinkTypeAttributes: true, //删除<style>和<link>的type="text/css"
    minifyJS: true, //压缩页面JS
    minifyCSS: true //压缩页面CSS
};

gulp.task("html", function() {
    return gulp.src(htmlSource)
        .pipe(gulpif(shouldMinify(), htmlmin(htmlOptions)))
        .pipe(gulp.dest(htmlBuildPath));
});

gulp.task("templates", function() {
    return gulp.src(templatesSource)
    .pipe(gulpif(shouldMinify(), htmlmin(htmlOptions)))
    .pipe(gulp.dest(templatesBuildPath));
})

gulp.task("serve", function() {
    browserSync.init({
        server: {
            baseDir: rootBuildPath,
            index: htmlEntryPoint
        },
        port: 8050
    });

    watch(htmlSource, gulp.series(["html", function() { browserSync.reload(); }]));  //监听 html 文件
    watch(templatesSource, gulp.series(["templates", function() { browserSync.reload(); }]));  //监听 html 文件
    watch(cssSource, gulp.series(["css", function() { browserSync.reload(); }]));    //监听 scss 文件
    watch(jsSource, gulp.series(["js", function() { browserSync.reload(); }]));      //监听 ts 文件
});


//清理 dist 目录
gulp.task("clean", function() {
    return del(rootBuildPath);
});

//文件 zip 包
gulp.task('zip', function () {
    const timeStamp = moment().format("YYYY-MM-DD_HH-mm-ss_");
    return gulp.src([rootBuildPath + "/**/*.*", "!" + rootBuildPath + '/**/*.map', "!" + rootBuildPath + "/data/*"]) //排除 sourcemaps
        .pipe(zip("dist_" + timeStamp + ".zip"))
        .pipe(gulp.dest("./"));
});

//复制 mock 数据
gulp.task("mock", function() {
    return gulp.src(rootSourcePath + "/data/*")
        .pipe(gulp.dest(rootBuildPath + "/data/"));
})

gulp.task("default", gulp.series([
    "clean",
    gulp.parallel(["html", "templates", "css", "js"]),
    gulpif(!shouldMinify(), "mock"),
    shouldMinify() ? "zip" : "serve"
]));

/*
https://codeday.me/bug/20190706/1393560.html
https://blog.csdn.net/maomaolaoshi/article/details/78445058
*/