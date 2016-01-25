materialAdmin
		.config(
				function($stateProvider, $urlRouterProvider, jwtInterceptorProvider, $httpProvider, $locationProvider,
						RestangularProvider, FacebookProvider) {

					// JWT Config
					jwtInterceptorProvider.tokenGetter = function(store) {
						return store.get('jwt');
					}
					$httpProvider.interceptors.push('jwtInterceptor');

					// RestAngular Config
					RestangularProvider.setBaseUrl('api/1/');

					RestangularProvider.setDefaultHeaders({
						'X-API-Token' : '91387c5d1bb74b1f84198f3611972b53'
					});

					FacebookProvider.init('174666509555605');

					// Default Route
					$urlRouterProvider.otherwise("login");

					$stateProvider

							.state('404', {
								url : '/404',
								templateUrl : '404.html',
								data : {
									requiresLogin : false
								}
							})
							.state('unauthorized', {
								url : '/unauthorized',
								templateUrl : 'unauthorized.html',
								data : {
									requiresLogin : false
								}
							})
							.state('login', {
								url : '^/login',
								data : {
									requiresLogin : false
								}
							})
							.state('recovery-password', {
								url : '/recovery-password/{token}',
								data : {
									requiresLogin : false
								}
							})
							.state('console', {
								url : '/console',
								templateUrl : 'views/common.html',
								data : {
									requiresLogin : true
								}
							})

							// ------------------------------
							// HOME
							// ------------------------------

							.state(
									'home',
									{
										url : '/console/home',
										templateUrl : 'views/home.html',
										data : {
											requiresLogin : true
										},
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-level',
																	files : [
																			'vendors/bower_components/fullcalendar/dist/fullcalendar.min.css', ]
																},
																{
																	name : 'vendors',
																	insertBefore : '#app-level-js',
																	files : [
																			'vendors/sparklines/jquery.sparkline.min.js',
																			'vendors/bower_components/jquery.easy-pie-chart/dist/jquery.easypiechart.min.js',
																			'vendors/bower_components/simpleWeather/jquery.simpleWeather.min.js' ]
																} ])
											}
										}
									})

							// ------------------------------
							// HEADERS
							// ------------------------------
							.state('headers', {
								url : '/headers',
								templateUrl : 'views/common-2.html',
								data : {
									requiresLogin : true
								}
							})

							.state('headers.textual-menu', {
								url : '/textual-menu',
								templateUrl : 'views/textual-menu.html',
								data : {
									requiresLogin : true
								}
							})

							.state('headers.image-logo', {
								url : '/image-logo',
								templateUrl : 'views/image-logo.html',
								data : {
									requiresLogin : true
								}
							})

							.state('headers.mainmenu-on-top', {
								url : '/mainmenu-on-top',
								templateUrl : 'views/mainmenu-on-top.html',
								data : {
									requiresLogin : true
								}
							})

							// ------------------------------
							// TYPOGRAPHY
							// ------------------------------

							.state('typography', {
								url : '/typography',
								templateUrl : 'views/typography.html',
								data : {
									requiresLogin : true
								}
							})

							// ------------------------------
							// WIDGETS
							// ------------------------------

							.state('widgets', {
								url : '/console',
								templateUrl : 'views/common.html',
								data : {
									requiresLogin : true
								}
							})

							.state(
									'widgets.widgets',
									{
										url : '/widgets',
										templateUrl : 'views/widgets.html',
										data : {
											requiresLogin : true
										},
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-level',
																	files : [
																			'vendors/bower_components/mediaelement/build/mediaelementplayer.css', ]
																},
																{
																	name : 'vendors',
																	files : [
																			'vendors/bower_components/mediaelement/build/mediaelement-and-player.js',
																			'vendors/bower_components/autosize/dist/autosize.min.js' ]
																} ])
											}
										}
									})

							.state('widgets.widget-templates', {
								url : '/widget-templates',
								templateUrl : 'views/widget-templates.html',
								data : {
									requiresLogin : true
								}
							})

							// ------------------------------
							// TABLES
							// ------------------------------

							.state('tables', {
								url : '/console',
								templateUrl : 'views/common.html',
								data : {
									requiresLogin : true
								}
							})

							.state('tables.tables', {
								url : '/tables',
								templateUrl : 'views/tables.html',
								data : {
									requiresLogin : true
								}
							})

							.state('tables.data-table', {
								url : '/data-table',
								templateUrl : 'views/data-table.html',
								data : {
									requiresLogin : true
								}
							})

							// ------------------------------
							// FORMS
							// ------------------------------
							.state('form', {
								url : '/console',
								templateUrl : 'views/common.html',
								data : {
									requiresLogin : true
								}
							})

							.state('form.basic-form-elements', {
								url : '/basic-form-elements',
								templateUrl : 'views/form-elements.html',
								data : {
									requiresLogin : true
								},
								resolve : {
									loadPlugin : function($ocLazyLoad) {
										return $ocLazyLoad.load([ {
											name : 'vendors',
											files : [ 'vendors/bower_components/autosize/dist/autosize.min.js' ]
										} ])
									}
								}
							})

							.state(
									'form.form-components',
									{
										url : '/form-components',
										templateUrl : 'views/form-components.html',
										data : {
											requiresLogin : true
										},
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-level',
																	files : [
																			'vendors/bower_components/nouislider/jquery.nouislider.css',
																			'vendors/farbtastic/farbtastic.css',
																			'vendors/bower_components/summernote/dist/summernote.css',
																			'vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css',
																			'vendors/bower_components/chosen/chosen.min.css' ]
																},
																{
																	name : 'vendors',
																	files : [
																			'vendors/input-mask/input-mask.min.js',
																			'vendors/bower_components/nouislider/jquery.nouislider.min.js',
																			'vendors/bower_components/moment/min/moment.min.js',
																			'vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js',
																			'vendors/bower_components/summernote/dist/summernote.min.js',
																			'vendors/fileinput/fileinput.min.js',
																			'vendors/bower_components/chosen/chosen.jquery.js',
																			'vendors/bower_components/angular-chosen-localytics/chosen.js',
																			'vendors/bower_components/angular-farbtastic/angular-farbtastic.js' ]
																} ])
											}
										}
									})

							.state('form.form-examples', {
								url : '/form-examples',
								templateUrl : 'views/form-examples.html',
								data : {
									requiresLogin : true
								}
							})

							.state('form.form-validations', {
								url : '/form-validations',
								templateUrl : 'views/form-validations.html',
								data : {
									requiresLogin : true
								}
							})

							// ------------------------------
							// USER INTERFACE
							// ------------------------------

							.state('console.user-list', {
								url : '/user-list',
								templateUrl : 'views/user-list.html',
								data : {
									requiresLogin : true,
									permission : 'USER'
								}
							})
							.state('console.cards-list', {
								url : '/cards-list',
								templateUrl : 'views/cards-list.html',
								data : {
									requiresLogin : true,
								}
							})
							.state('user-interface', {
								url : '/console',
								templateUrl : 'views/common.html',
								data : {
									requiresLogin : true
								}
							})

							.state('user-interface.ui-bootstrap', {
								url : '/ui-bootstrap',
								templateUrl : 'views/ui-bootstrap.html',
								data : {
									requiresLogin : true
								}
							})

							.state('user-interface.colors', {
								url : '/colors',
								templateUrl : 'views/colors.html',
								data : {
									requiresLogin : true
								}
							})

							.state('user-interface.animations', {
								url : '/animations',
								templateUrl : 'views/animations.html',
								data : {
									requiresLogin : true
								}
							})

							.state('user-interface.box-shadow', {
								url : '/box-shadow',
								templateUrl : 'views/box-shadow.html',
								data : {
									requiresLogin : true
								}
							})

							.state('user-interface.buttons', {
								url : '/buttons',
								templateUrl : 'views/buttons.html',
								data : {
									requiresLogin : true
								}
							})

							.state('user-interface.icons', {
								url : '/icons',
								templateUrl : 'views/icons.html',
								data : {
									requiresLogin : true
								}
							})

							.state('user-interface.alerts', {
								url : '/alerts',
								templateUrl : 'views/alerts.html',
								data : {
									requiresLogin : true
								}
							})

							.state('user-interface.preloaders', {
								url : '/preloaders',
								templateUrl : 'views/preloaders.html',
								data : {
									requiresLogin : true
								}
							})

							.state('user-interface.notifications-dialogs', {
								url : '/notifications-dialogs',
								templateUrl : 'views/notification-dialog.html',
								data : {
									requiresLogin : true
								}
							})

							.state(
									'user-interface.media',
									{
										url : '/media',
										templateUrl : 'views/media.html',
										data : {
											requiresLogin : true
										},
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-level',
																	files : [
																			'vendors/bower_components/mediaelement/build/mediaelementplayer.css',
																			'vendors/bower_components/lightgallery/light-gallery/css/lightGallery.css' ]
																},
																{
																	name : 'vendors',
																	files : [
																			'vendors/bower_components/mediaelement/build/mediaelement-and-player.js',
																			'vendors/bower_components/lightgallery/light-gallery/js/lightGallery.min.js' ]
																} ])
											}
										}
									})

							.state('user-interface.other-components', {
								url : '/other-components',
								templateUrl : 'views/other-components.html',
								data : {
									requiresLogin : true
								}
							})

							// ------------------------------
							// CHARTS
							// ------------------------------

							.state('charts', {
								url : '/console',
								templateUrl : 'views/common.html',
								data : {
									requiresLogin : true
								}
							})

							.state('charts.flot-charts', {
								url : '/flot-charts',
								templateUrl : 'views/flot-charts.html',
								data : {
									requiresLogin : true
								}
							})

							.state(
									'charts.other-charts',
									{
										url : '/other-charts',
										templateUrl : 'views/other-charts.html',
										data : {
											requiresLogin : true
										},
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([ {
															name : 'vendors',
															files : [
																	'vendors/sparklines/jquery.sparkline.min.js',
																	'vendors/bower_components/jquery.easy-pie-chart/dist/jquery.easypiechart.min.js', ]
														} ])
											}
										}
									})

							// ------------------------------
							// CALENDAR
							// ------------------------------

							.state(
									'calendar',
									{
										url : '/calendar',
										templateUrl : 'views/calendar.html',
										data : {
											requiresLogin : true
										},
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-level',
																	files : [
																			'vendors/bower_components/fullcalendar/dist/fullcalendar.min.css', ]
																},
																{
																	name : 'vendors',
																	files : [
																			'vendors/bower_components/moment/min/moment.min.js',
																			'vendors/bower_components/fullcalendar/dist/fullcalendar.min.js' ]
																} ])
											}
										}
									})

							// ------------------------------
							// PHOTO GALLERY
							// ------------------------------

							.state(
									'photo-gallery',
									{
										url : '/console',
										templateUrl : 'views/common.html',
										data : {
											requiresLogin : true
										},
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-level',
																	files : [ 'vendors/bower_components/lightgallery/light-gallery/css/lightGallery.css' ]
																},
																{
																	name : 'vendors',
																	files : [ 'vendors/bower_components/lightgallery/light-gallery/js/lightGallery.min.js' ]
																} ])
											}
										}
									})

							// Default

							.state('photo-gallery.photos', {
								url : '/photos',
								templateUrl : 'views/photos.html',
								data : {
									requiresLogin : true
								}
							})

							// Timeline

							.state('photo-gallery.timeline', {
								url : '/timeline',
								templateUrl : 'views/photo-timeline.html',
								data : {
									requiresLogin : true
								}
							})

							// ------------------------------
							// GENERIC CLASSES
							// ------------------------------

							.state('generic-classes', {
								url : '/generic-classes',
								templateUrl : 'views/generic-classes.html',
								data : {
									requiresLogin : true
								}
							})

							// ------------------------------
							// PAGES
							// ------------------------------

							.state('pages', {
								url : '/console',
								templateUrl : 'views/common.html',
								data : {
									requiresLogin : true
								}
							})

							// Profile

							.state('pages.profile', {
								url : '/profile',
								templateUrl : 'views/profile.html',
								data : {
									requiresLogin : true
								}
							})

							.state('pages.profile.profile-about', {
								url : '/profile-about',
								templateUrl : 'views/profile-about.html',
								data : {
									requiresLogin : true
								}
							})

							.state(
									'pages.profile.profile-timeline',
									{
										url : '/profile-timeline',
										templateUrl : 'views/profile-timeline.html',
										data : {
											requiresLogin : true
										},
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-level',
																	files : [ 'vendors/bower_components/lightgallery/light-gallery/css/lightGallery.css' ]
																},
																{
																	name : 'vendors',
																	files : [ 'vendors/bower_components/lightgallery/light-gallery/js/lightGallery.min.js' ]
																} ])
											}
										}
									})

							.state(
									'pages.profile.profile-photos',
									{
										url : '/profile-photos',
										templateUrl : 'views/profile-photos.html',
										data : {
											requiresLogin : true
										},
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'css',
																	insertBefore : '#app-level',
																	files : [ 'vendors/bower_components/lightgallery/light-gallery/css/lightGallery.css' ]
																},
																{
																	name : 'vendors',
																	files : [ 'vendors/bower_components/lightgallery/light-gallery/js/lightGallery.min.js' ]
																} ])
											}
										}
									})

							.state('pages.profile.profile-connections', {
								url : '/profile-connections',
								templateUrl : 'views/profile-connections.html',
								data : {
									requiresLogin : true
								}
							})

							// -------------------------------

							.state('pages.listview', {
								url : '/listview',
								templateUrl : 'views/list-view.html',
								data : {
									requiresLogin : true
								}
							})

							.state('pages.messages', {
								url : '/messages',
								templateUrl : 'views/messages.html',
								data : {
									requiresLogin : true
								}
							})

							.state('pages.pricing-table', {
								url : '/pricing-table',
								templateUrl : 'views/pricing-table.html',
								data : {
									requiresLogin : true
								}
							})

							.state('pages.contacts', {
								url : '/contacts',
								templateUrl : 'views/contacts.html',
								data : {
									requiresLogin : true
								}
							})

							.state('pages.invoice', {
								url : '/invoice',
								templateUrl : 'views/invoice.html',
								data : {
									requiresLogin : true
								}
							})

							.state(
									'pages.wall',
									{
										url : '/wall',
										templateUrl : 'views/wall.html',
										data : {
											requiresLogin : true
										},
										resolve : {
											loadPlugin : function($ocLazyLoad) {
												return $ocLazyLoad
														.load([
																{
																	name : 'vendors',
																	insertBefore : '#app-level',
																	files : [
																			'vendors/bower_components/autosize/dist/autosize.min.js',
																			'vendors/bower_components/lightgallery/light-gallery/css/lightGallery.css' ]
																},
																{
																	name : 'vendors',
																	files : [
																			'vendors/bower_components/mediaelement/build/mediaelement-and-player.js',
																			'vendors/bower_components/lightgallery/light-gallery/js/lightGallery.min.js' ]
																} ])
											}
										}
									})

							// ------------------------------
							// BREADCRUMB DEMO
							// ------------------------------
							.state('breadcrumb-demo', {
								url : '/breadcrumb-demo',
								templateUrl : 'views/breadcrumb-demo.html',
								data : {
									requiresLogin : true
								}
							})
					// use the HTML5 History API
					$locationProvider.html5Mode(true);
				}).run(function($rootScope, $state, store, jwtHelper, $window, gettextCatalog, permissions) {

			var lang = $window.navigator.language || $window.navigator.userLanguage;
			gettextCatalog.setCurrentLanguage(lang.replace("-", "_"));

			var token = store.get('jwt');
			if (token && jwtHelper) {
				permissions.setPermissions(jwtHelper.decodeToken(token).role);
			}

			$rootScope.$on('$stateChangeStart', function(e, to) {

				if (to.data) {

					var permission = to.data.permission;
					if (permission && _.isString(permission) && !permissions.hasPermission(permission)) {
						$window.location.href = '/unauthorized';
						return;
					}

					if (to.data.requiresLogin) {
						if (!store.get('jwt') || jwtHelper.isTokenExpired(store.get('jwt'))) {
							e.preventDefault();

							if (to.name === "login") {
								return; // no need to redirect
							}

							// $state.go('login');
							// $window.location.href = '/login';
							window.location.href = '/login';
						} else if (to.name === "login") {
							$window.location.href = '/#/console/home';
						}
					} else if (to.name === "login") {
						// Não requer login mas esta indo para a tela de
						// login

						if (store.get('jwt') && !jwtHelper.isTokenExpired(store.get('jwt'))) {
							e.preventDefault();

							$window.location.href = '/#/console/home';
						} else {
							return;
						}
					}
				}
			});
		});
