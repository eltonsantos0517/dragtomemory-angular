materialAdmin.factory('LoginRestangular', function(Restangular) {
	return Restangular.withConfig(function(RestangularConfigurer) {
		RestangularConfigurer.setBaseUrl('api/');
		RestangularConfigurer.setDefaultHeaders({
			'X-API-Token' : '91387c5d1bb74b1f84198f3611972b53'
		});
		RestangularConfigurer.setFullResponse(true);
	});
}).factory('permissions', function($rootScope) {
	var permissionList;
	return {
		setPermissions : function(permissions) {
			permissionList = permissions;
			$rootScope.$broadcast('permissionsChanged');
		},
		hasPermission : function(permission) {
			permission = permission.trim();
			return _.some(permissionList, function(item) {
				if (_.isString(item)) {
					return item.trim() === permission
				}
			});
		}
	};
})