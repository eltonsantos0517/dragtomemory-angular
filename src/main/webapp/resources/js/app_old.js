'use strict';

var App = angular.module('myApp', [ 'ngRoute', 'angular-jwt' ]);

// App.config(['$routeProvider','$locationProvider','$httpProvider', function
// ($routeProvider, $locationProvider, $httpProvider,jwtInterceptorProvider) {
App.config(function myAppConfig($routeProvider, $locationProvider, jwtInterceptorProvider, $httpProvider) {

	// jwtInterceptorProvider.authHeader = 'X-AUTH-TOKEN';
	// jwtInterceptorProvider.authPrefix = '';

	jwtInterceptorProvider.tokenGetter = function() {
		return localStorage.getItem('JWT');
	}
	$httpProvider.interceptors.push('jwtInterceptor');

	$routeProvider.when('/', {
		templateUrl : 'items/computers',
		controller : "ItemListController as itemListCtrl",
		resolve : {
			async : [ 'ItemService', function(ItemService) {
				return ItemService.fetchAllItems('computers');
			} ]
		}
	}).when('/items/computers', {
		templateUrl : 'items/computers',
		controller : "ItemListController as itemListCtrl",
		resolve : {
			async : [ 'ItemService', function(ItemService) {
				return ItemService.fetchAllItems('computers');
			} ]
		}
	}).when('/items/phones', {
		templateUrl : 'items/phones',
		controller : "ItemListController as itemListCtrl",
		resolve : {
			async : [ 'ItemService', function(ItemService) {
				return ItemService.fetchAllItems('phones');
			} ]
		}
	}).when('/items/printers', {
		templateUrl : 'items/printers',
		controller : "ItemListController as itemListCtrl",
		resolve : {
			async : [ 'ItemService', function(ItemService) {
				return ItemService.fetchAllItems('printers');
			} ]
		}
	}).when('/items/computerdetails/:id', {
		templateUrl : 'items/computerdetails',
		controller : "ItemDetailsController as itemDetailsCtrl",
		resolve : {
			async : [ 'ItemService', '$route', function(ItemService, $route) {
				return ItemService.fetchSpecificItem('computers', $route.current.params.id);
			} ]
		}
	}).when('/items/phonedetails/:id', {
		templateUrl : 'items/phonedetails',
		controller : "ItemDetailsController as itemDetailsCtrl",
		resolve : {
			async : [ 'ItemService', '$route', function(ItemService, $route) {
				return ItemService.fetchSpecificItem('phones', $route.current.params.id);
			} ]
		}
	}).when('/items/printerdetails/:id', {
		templateUrl : 'items/printerdetails',
		controller : "ItemDetailsController as itemDetailsCtrl",
		resolve : {
			async : [ 'ItemService', '$route', function(ItemService, $route) {
				return ItemService.fetchSpecificItem('printers', $route.current.params.id);
			} ]
		}
	}).otherwise({
		redirectTo : '/'
	});

	// use the HTML5 History API
	$locationProvider.html5Mode(true);
}).run(function($rootScope, $state, store, jwtHelper) {
	$rootScope.$on('$stateChangeStart', function(e, to) {
		if (to.data && to.data.requiresLogin) {
			if (!store.get('jwt') || jwtHelper.isTokenExpired(store.get('jwt'))) {
				e.preventDefault();
				$state.go('login');
			}
		}
	});
}).controller('AppCtrl', function AppCtrl($scope, $location) {
	$scope.$on('$routeChangeSuccess', function(e, nextRoute) {
		if (nextRoute.$$route && angular.isDefined(nextRoute.$$route.pageTitle)) {
			$scope.pageTitle = nextRoute.$$route.pageTitle + ' | ngEurope Sample';
		}
	});
});
