var gulp       = require('gulp'),
    del        = require('del'),
    ts         = require('gulp-typescript'),
    sourcemaps = require('gulp-sourcemaps'),
    tsProject  = ts.createProject('tsconfig.json'),
    browserify = require('browserify'),
    source     = require('vinyl-source-stream');

gulp.task('build-typescript', function () {
    return tsProject.src()
        // 注意顺序
        .pipe(sourcemaps.init())
        .pipe(tsProject())
        .pipe(sourcemaps.write())
        .pipe(gulp.dest('dist'));
});

//解决js依赖，将其打包
gulp.task('bundle', function() {
    //通过browserify管理依赖
    return browserify({
        //入口点,app.jsx
        entries : ["./dist/main.js"]
    })
    //转换为gulp能识别的流
    .bundle()
    //合并输出为app.js
    .pipe(source("bundle.js"))
    //输出到当前文件夹中
    .pipe(gulp.dest("./dist"));
});

gulp.task('clean', function () {
    return del([
      //使用一个通配模式来匹配文件夹中的所有东西
      'dist/**/*',
      //不希望删掉这个文件，所以取反这个匹配模式
      //'!dist/deploy.json'
    ]);
});

gulp.task('watch-typescript', function() {
    gulp.watch('src/**/*.ts', gulp.series('clean', 'build-typescript', 'bundle'));
});



gulp.task('default', gulp.series('clean', 'build-typescript', 'bundle'));

