const gulp = require('gulp'),
      using = require('gulp-using'),
      yandexDisk = '/Users/d/Yandex.Disk.localized/Projects/',
      project = 'Crisppic';

gulp.task('sync-to', function() {
  return gulp.src('_documents/**')
    .pipe(using())
    .pipe(gulp.dest(yandexDisk + project));
});
gulp.task('sync-from', function() {
  return gulp.src(yandexDisk + project)
    .pipe(using())
    .pipe(gulp.dest('_documents'));
});
