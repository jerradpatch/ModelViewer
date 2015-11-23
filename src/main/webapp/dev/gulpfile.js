// Include gulp
var gulp = require('gulp');

var browserify = require('browserify');
var source = require('vinyl-source-stream');
// Include Our Plugins
var jshint = require('gulp-jshint');
var sass = require('gulp-sass');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var rename = require('gulp-rename');

// Lint Task
gulp.task('lint', function() {
    return gulp.src('js/*.js')
        .pipe(jshint())
        .pipe(jshint.reporter('default'));
});

// Compile Our Sass
gulp.task('sass', function() {
    return gulp.src('scss/*.scss')
        .pipe(sass())
        .pipe(gulp.dest('css'));
});

// Concatenate & Minify JS
gulp.task('scriptsDev', function() {
    return gulp.src('./../static-files/modules/compiled.js')
        .pipe(concat('compiled.js'))
        .pipe(uglify())
        .pipe(gulp.dest('./../static-files/modules/'));
});

gulp.task('browserify', function() {
    return browserify('./app.js')
        .bundle()
    //Pass desired output filename to vinyl-source-stream
        .pipe(source('compiled.js'))
    // Start piping stream to tasks!
        .pipe(gulp.dest('./../static-files/modules/'));

});


// Watch Files For Changes

gulp.task('watchDev', function() {
    gulp.watch('js/*.js', ['lint', 'scriptsDev']);
    gulp.watch('scss/*.scss', ['sass']);
});

// Default Task
gulp.task('dev', ['lint', 'sass', 'scripts', 'watchDev']);
gulp.task('min', ['browserify', 'scriptsDev']);
