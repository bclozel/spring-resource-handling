
var gulpFilter = require('gulp-filter'),
    cram = require('gulp-cram'),
    uglify = require('gulp-uglify'),
    bowerSrc = require('gulp-bower-src'),
    cssMinify = require('gulp-minify-css'),
    sourcemaps = require('gulp-sourcemaps'),
    imagemin = require('gulp-imagemin'),
    pngcrush = require('imagemin-pngcrush'),
    less = require('gulp-less'),
    gulp = require('gulp');

var paths = {
    run: 'src/run.js',
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
gulp.task('optimize-and-copy-js', function() {

    var options = {
        includes: ['curl/loader/legacy'],
        appRoot: "./src"
    };
    return cram(paths.run, options).into('run.js')
        .pipe(sourcemaps.init())
            .pipe(uglify())
        .pipe(sourcemaps.write("./"))
        .pipe(gulp.dest(paths.destination));
});

// Optimize bower-managed JavaScript dependencies and copy to "dist" folder
gulp.task('optimize-and-copy-lib', function() {

    var filter = gulpFilter(["**/*.js", "!**/*.min.js"]);
    return bowerSrc()
        .pipe(filter)
        .pipe(uglify())
        .pipe(filter.restore())
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

gulp.task('build', ['optimize-and-copy-css', 'optimize-and-copy-js', 'optimize-and-copy-lib',
    'copy-images', 'less', 'copy-assets'], function(){});