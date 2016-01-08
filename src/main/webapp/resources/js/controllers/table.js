materialAdmin.controller('tableCtrl', function($filter, $sce, ngTableParams, tableService) {
	var data = tableService.data;

	// Basic Example
	this.tableBasic = new ngTableParams({
		page : 1, // show first page
		count : 10
	// count per page
	}, {
		total : data.length, // length of data
		getData : function($defer, params) {
			$defer.resolve(data.slice((params.page() - 1) * params.count(), params.page() * params.count()));
		}
	})

	// Sorting
	this.tableSorting = new ngTableParams({
		page : 1, // show first page
		count : 10, // count per page
		sorting : {
			name : 'asc' // initial sorting
		}
	}, {
		total : data.length, // length of data
		getData : function($defer, params) {
			// use build-in angular filter
			var orderedData = params.sorting() ? $filter('orderBy')(data, params.orderBy()) : data;

			$defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
		}
	})

	// Filtering
	this.tableFilter = new ngTableParams({
		page : 1, // show first page
		count : 10
	}, {
		total : data.length, // length of data
		getData : function($defer, params) {
			// use build-in angular filter
			var orderedData = params.filter() ? $filter('filter')(data, params.filter()) : data;

			this.id = orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count());
			this.name = orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count());
			this.email = orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count());
			this.username = orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count());
			this.contact = orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count());

			params.total(orderedData.length); // set total for
			// recalc
			// pagination
			$defer.resolve(this.id, this.name, this.email, this.username, this.contact);
		}
	})

	// Editable
	this.tableEdit = new ngTableParams({
		page : 1, // show first page
		count : 10
	// count per page
	}, {
		total : data.length, // length of data
		getData : function($defer, params) {
			$defer.resolve(data.slice((params.page() - 1) * params.count(), params.page() * params.count()));
		}
	});
}).controller('userCtrl', function($filter, $sce, ngTableParams, tableService, $scope, accountService, $http, growlService) {

	this.list = 1;
	this.add = 0;
	this.edit = 0;
	$scope.user = {};
	$scope.users = accountService.getAll();

	var data = tableService.data;

	// Basic Example
	this.tableBasic = new ngTableParams({
		page : 1, // show first page
		count : 10
	// count per page
	}, {
		total : data.length, // length of data
		getData : function($defer, params) {
			$defer.resolve(data.slice((params.page() - 1) * params.count(), params.page() * params.count()));
		}
	})

	// Sorting
	this.tableSorting = new ngTableParams({
		page : 1, // show first page
		count : 10, // count per page
		sorting : {
			name : 'asc' // initial sorting
		}
	}, {
		total : data.length, // length of data
		getData : function($defer, params) {
			// use build-in angular filter
			var orderedData = params.sorting() ? $filter('orderBy')(data, params.orderBy()) : data;

			$defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
		}
	})

	// Filtering
	this.tableFilter = new ngTableParams({
		page : 1, // show first page
		count : 10
	}, {
		total : data.length, // length of data
		getData : function($defer, params) {
			// use build-in angular filter
			var orderedData = params.filter() ? $filter('filter')(data, params.filter()) : data;

			this.id = orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count());
			this.name = orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count());
			this.email = orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count());
			this.username = orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count());
			this.contact = orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count());

			params.total(orderedData.length); // set total for
			// recalc
			// pagination
			$defer.resolve(this.id, this.name, this.email, this.username, this.contact);
		}
	})

	// Editable
	this.tableEdit = new ngTableParams({
		page : 1, // show first page
		count : 10
	// count per page
	}, {
		total : data.length, // length of data
		getData : function($defer, params) {
			$defer.resolve(data.slice((params.page() - 1) * params.count(), params.page() * params.count()));
		}
	});

	$scope.register = function() {
		accountService.save($scope.user).then(
		// success
		function(response) {
			$scope.uCtrl.list = 1;
			$scope.uCtrl.add = 0;
			$scope.uCtrl.edit = 0;

			$scope.users = accountService.getAll();

			if ($scope.user.objectId == null) {
				growlService.growl('Usuário criado com sucesso.', 'success', 1000)
			} else {
				growlService.growl('Usuário atualizado com sucesso.', 'success', 1000)
			}
		},
		// fail
		function(response) {
			growlService.growl(error.data, 'danger');
		});
	};

	$scope.initEdit = function(userId) {
		$scope.uCtrl.list = 0;
		$scope.uCtrl.add = 0;
		$scope.uCtrl.edit = 1;

		$scope.user = accountService.getById(userId);
	};

	$scope.removeUser = function(userId) {
		$scope.user = accountService.removeUser(userId).then(
		// success
		function(response) {
			$scope.users = accountService.getAll();
			growlService.growl('Usuário deletado com sucesso.', 'success', 1000)
		},
		// fail
		function(response) {
			growlService.growl('Erro ao deletar usuário.', 'danger', 1000)
		});
	};

})
