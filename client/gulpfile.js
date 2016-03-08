
var path = require('path'),
    cssMinify = require('gulp-minify-css'),
    less = require('gulp-less'),
    gulp = require('gulp'),
    Builder = require('systemjs-builder');

var paths = {
    baseUrl: 'file:' + process.cwd() + '/src/',
    config: ['src/config.js'],
    jspmLibs: ['src/lib/**', '!src/lib/*/test/*'],
    css: {
        files: ['src/css/*.css']
    },
    less: ['src/less/*'],
    assets: ["src/cache.manifest"],
    images: ["src/img/*"],
    destination: './dist'
};

// Optimize application CSS files and copy to "dist" folder
gulp.task('optimize-and-copy-css', function() {
    return gulp.src(paths.css.files)
        .pipe(cssMinify())
        .pipe(gulp.dest(paths.destination + '/css'));
});

// Optimize application JavaScript files and copy to "dist" folder
gulp.task('optimize-and-copy-js', function(cb) {
    var builder = new Builder();
    builder.loadConfig('./src/config.js')
        .then(function() {
            builder.config({ baseURL: paths.baseUrl });
            builder.build('app/app', paths.destination + '/app/app.js', { minify: true, sourceMaps: true });
            cb();
        })
        .catch(function(err) {
            cb(err);
        });
});

// Copy jspm-managed JavaScript dependencies to "dist" folder
gulp.task('copy-lib', function() {
    return gulp.src(paths.jspmLibs)
        .pipe(gulp.dest(paths.destination + '/lib'));
});

gulp.task('copy-config', function() {
    return gulp.src(paths.config)
        .pipe(gulp.dest(paths.destination));
});

gulp.task('copy-images', function() {
    return gulp.src(paths.images)
        .pipe(gulp.dest(paths.destination + '/img'));
});

gulp.task('copy-assets', function() {
    return gulp.src(paths.assets)
        .pipe(gulp.dest(paths.destination))
});

gulp.task('less', function () {
    return gulp.src(paths.less)
        .pipe(less())
        .pipe(cssMinify({noRebase: true}))
        .pipe(gulp.dest(paths.destination + '/css'));
});

gulp.task('build', ['optimize-and-copy-css', 'optimize-and-copy-js', 'copy-lib',
    'copy-config', 'copy-images', 'less', 'copy-assets'], function(){});