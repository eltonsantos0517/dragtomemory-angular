angular.module('gettext').run(['gettextCatalog', function (gettextCatalog) {
/* jshint -W100 */
    gettextCatalog.setStrings('pt_BR', {"Hello {{name}}!":"Oi {{name}}!","Hello!":"Oieee"});
/* jshint +W100 */
}]);