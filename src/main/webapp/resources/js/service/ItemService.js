'use strict';

App.factory('ItemService', ['$http', '$q', function($http, $q){

	var urlBase='http://gae-spring-angular.appspot.com';
	
	var url = window.location.href;
	var arr = url.split("/");
	urlBase = arr[0] + "//" + arr[2];
	
	return {
		
			fetchAllItems: function(category) {
					return $http.get(urlBase + '/item/'+category)
							.then(
									function(response){
										return response.data;
									}, 
									function(errResponse){
										console.error('Error while fetching Items');
										return $q.reject(errResponse);
									}
							);
			},
		    
			fetchSpecificItem: function(category,id) {
				return $http.get(urlBase + '/item/'+category+'/'+id)
						.then(
								function(response){
									return response.data;
								}, 
								function(errResponse){
									console.error('Error while fetching specific Item');
									return $q.reject(errResponse);
								}
						);
			}
	};

}]);
