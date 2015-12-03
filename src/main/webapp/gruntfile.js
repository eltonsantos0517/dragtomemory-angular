module.exports = function(grunt) {

    // Project configuration.
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        less: {
            development: {
                options: {
                    paths: ["resources/css"]
                },
                files: {
                    "resources/css/app.css": "less/app.less",
                },
                cleancss: true
            }
        },
        csssplit: {
            your_target: {
                src: ['resources/css/app.css'],
                dest: 'resources/css/app.min.css',
                options: {
                    maxSelectors: 4095,
                    suffix: '.'
                }
            },
        },
        ngtemplates: {
          materialAdmin: {
            src: ['template/**.html', 'template/**/**.html'],
            dest: 'resources/js/templates.js',
            options: {
              htmlmin: {
                    collapseWhitespace: true,
                    collapseBooleanAttributes: true
              }
            } 
          }
        },
        watch: {
            styles: {
                files: ['resources/less/**/*.less'], // which files to watch
                tasks: ['less', 'csssplit'],
                options: {
                    nospawn: true
                }
            }
        }
    });

    // Load the plugin that provides the "less" task.
    grunt.loadNpmTasks('grunt-contrib-less');
    grunt.loadNpmTasks('grunt-csssplit');
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-angular-templates');

    // Default task(s).
    grunt.registerTask('default', ['less']);

};
