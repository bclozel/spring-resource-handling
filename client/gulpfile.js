
var path = require('path'),
    cssMinify = require('gulp-minify-css'),
    imagemin = require('gulp-imagemin'),
    pngcrush = require('imagemin-pngcrush'),
    less = require('gulp-less'),
    gulp = require('gulp'),
    Builder = require('systemjs-builder');

var paths = {
    baseUrl: 'file:' + process.cwd() + '/src/',
    bowerLibs: ['src/lib/**', '!src/lib/*/test/*'],
    css: {
        files: ['src/css/*.css'],
        root: 'src/css'
    },
    less: ['src/less/*'],
    assets: ["src/cache.manifest"],
    images: ["src/img/*"],
    destination: './dist'
};

// Optimize application CSS files and copy to "dist" folder
gulp.task('optimize-and-copy-css', function() {

    return gulp.src(paths.css.files)
        .pipe(cssMinify({root : paths.css.root, noRebase: true}))
        .pipe(gulp.dest(paths.destination + '/css'));
});

// Optimize application JavaScript files and copy to "dist" folder
gulp.task('optimize-and-copy-js', function(cb) {
    var builder = new Builder({
        baseURL: paths.baseUrl,
        map: {
            jquery: paths.baseUrl + 'lib/jquery/dist/jquery.min'
        }
    });
    builder.build('app/app', paths.destination + '/app/app.js', { minify: true, sourceMaps: true })
        .then(function() {
            cb();
        })
        .catch(function(err) {
            cb(err);
        });
});

// Optimize bower-managed JavaScript dependencies and copy to "dist" folder
gulp.task('copy-bower-lib', function() {

    return gulp.src(paths.bowerLibs)
        .pipe(gulp.dest(paths.destination + '/lib'));
});

gulp.task('copy-images', function() {
    return gulp.src(paths.images)
        .pipe(imagemin({
            progressive: true,
            svgoPlugins: [{removeViewBox: false}],
            use: [pngcrush()]
        }))
        .pipe(gulp.dest(paths.destination + '/img'))
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

gulp.task('build', ['optimize-and-copy-css', 'optimize-and-copy-js', 'copy-bower-lib',
    'copy-images', 'less', 'copy-assets'], function(){});