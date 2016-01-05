materialAdmin
		.controller(
				'tableCtrl',
				function($filter, $sce, ngTableParams, tableService) {
					var data = tableService.data;

					// Basic Example
					this.tableBasic = new ngTableParams({
						page : 1, // show first page
						count : 10
					// count per page
					}, {
						total : data.length, // length of data
						getData : function($defer, params) {
							$defer.resolve(data.slice((params.page() - 1)
									* params.count(), params.page()
									* params.count()));
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
							var orderedData = params.sorting() ? $filter(
									'orderBy')(data, params.orderBy()) : data;

							$defer.resolve(orderedData.slice(
									(params.page() - 1) * params.count(),
									params.page() * params.count()));
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
							var orderedData = params.filter() ? $filter(
									'filter')(data, params.filter()) : data;

							this.id = orderedData.slice((params.page() - 1)
									* params.count(), params.page()
									* params.count());
							this.name = orderedData.slice((params.page() - 1)
									* params.count(), params.page()
									* params.count());
							this.email = orderedData.slice((params.page() - 1)
									* params.count(), params.page()
									* params.count());
							this.username = orderedData.slice(
									(params.page() - 1) * params.count(),
									params.page() * params.count());
							this.contact = orderedData.slice(
									(params.page() - 1) * params.count(),
									params.page() * params.count());

							params.total(orderedData.length); // set total for
																// recalc
							// pagination
							$defer.resolve(this.id, this.name, this.email,
									this.username, this.contact);
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
							$defer.resolve(data.slice((params.page() - 1)
									* params.count(), params.page()
									* params.count()));
						}
					});
				})
		.controller(
				'userCtrl',
				function($filter, $sce, ngTableParams, tableService, $scope,
						crudService, $http, growlService) {

					this.list = 1;
					this.add = 0;
					this.edit = 0;
					$scope.user = {};

					$scope.register = function() {
						$http.post('/api/1/user', {
							username : $scope.user.username,
							password : $scope.user.password,
							confirmPassword : $scope.user.confirmPassword
						}).success(
								function(result, status, headers) {
									$scope.uCtrl.list = 1;
									$scope.uCtrl.add = 0;
									$scope.uCtrl.edit = 0;

									$scope.projects = crudService.getAll();

									growlService.growl('Usuário cadastrado com sucesso.', 'success')
								});
					};

					$scope.projects = crudService.getAll();

					var data = tableService.data;

					// Basic Example
					this.tableBasic = new ngTableParams({
						page : 1, // show first page
						count : 10
					// count per page
					}, {
						total : data.length, // length of data
						getData : function($defer, params) {
							$defer.resolve(data.slice((params.page() - 1)
									* params.count(), params.page()
									* params.count()));
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
							var orderedData = params.sorting() ? $filter(
									'orderBy')(data, params.orderBy()) : data;

							$defer.resolve(orderedData.slice(
									(params.page() - 1) * params.count(),
									params.page() * params.count()));
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
							var orderedData = params.filter() ? $filter(
									'filter')(data, params.filter()) : data;

							this.id = orderedData.slice((params.page() - 1)
									* params.count(), params.page()
									* params.count());
							this.name = orderedData.slice((params.page() - 1)
									* params.count(), params.page()
									* params.count());
							this.email = orderedData.slice((params.page() - 1)
									* params.count(), params.page()
									* params.count());
							this.username = orderedData.slice(
									(params.page() - 1) * params.count(),
									params.page() * params.count());
							this.contact = orderedData.slice(
									(params.page() - 1) * params.count(),
									params.page() * params.count());

							params.total(orderedData.length); // set total for
																// recalc
							// pagination
							$defer.resolve(this.id, this.name, this.email,
									this.username, this.contact);
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
							$defer.resolve(data.slice((params.page() - 1)
									* params.count(), params.page()
									* params.count()));
						}
					});
				})
