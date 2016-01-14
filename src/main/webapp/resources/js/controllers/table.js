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

	/* jshint validthis: true */
	var u = this;

	u.list = 1;
	u.add = 0;
	u.edit = 0;
	u.user = {};
	u.users = [];
	u.allUsers = [];

	// Pagination Config
	u.totalItems = 10;
	u.totalItemsBackend = 10;
	u.currentPage = 1;
	u.itemsPerPage = 2;
	u.itemsPerPage = 2;
	u.pages = [];

	accountService.list(u.totalItemsBackend, "").then(
	// success
	function(response) {
		u.allUser = response.data;
		u.totalItems = response.resultCount;
		u.cursor = response.cursor;

		u.preparePages(u.allUser, u.itemsPerPage, u.currentPage);
		u.users = u.getPage(u.currentPage);
	},
	// fail
	function(response) {
		growlService.growl('Erro ao carregar usuários.', 'danger', 1000)
	});

	u.preparePages = function(itens, itemsPerPage, currentPage) {
		if (itens == null) {
			return;
		}

		var i = 1;
		var pageIndex = 0;
		var pageList = [];
		for (x in itens) {
			if (i <= itemsPerPage) {
				pageList.push(itens[x]);
				u.pages[pageIndex] = pageList;
				i++;
			} else {
				i = 1;
				pageList = [];
				pageIndex++;

				pageList.push(itens[x]);
				u.pages[pageIndex] = pageList;
				
				i++;
			}
		}
	};

	u.getPage = function(currentPage) {
		return u.pages[currentPage - 1];
	};

	u.pageChanged = function() {
		u.users = u.getPage(u.currentPage);
	};
	u.register = function() {
		accountService.save(u.user).then(
		// success
		function(response) {
			u.list = 1;
			u.add = 0;
			u.edit = 0;
			u.user = {};

			accountService.list(u.totalItemsBackend, "").then(
			// success
			function(response) {
				u.cursor = response.cursor;
				u.allUser = response.data;
				u.totalItems = response.resultCount;
				u.preparePages(u.allUser, u.itemsPerPage, u.currentPage);
				u.users = u.getPage(u.currentPage);
			},
			// fail
			function(response) {
				growlService.growl('Erro ao carregar usuários.', 'danger', 1000)
			});

			if (u.user.objectId == null) {
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

	u.initEdit = function(userId) {
		u.list = 0;
		u.add = 0;
		u.edit = 1;

		u.user = accountService.getById(userId);
	};

	u.refresh = function() {
		accountService.list(u.totalItemsBackend, "").then(
		// success
		function(response) {
			u.cursor = response.cursor;
			u.totalItems = 10;
			u.totalItemsBackend = 10;
			u.currentPage = 1;
			u.itemsPerPage = 2;
			u.itemsPerPage = 2;
			u.pages = [];

			u.allUser = response.data;
			u.totalItems = response.resultCount;
			u.preparePages(u.allUser, u.itemsPerPage, u.currentPage);
			u.users = u.getPage(u.currentPage);
		},
		// fail
		function(response) {
			growlService.growl('Erro ao carregar usuários.', 'danger', 1000)
		});

	};

	u.back = function() {
		u.list = 1;
		u.add = 0;
		u.edit = 0;
	};

	u.removeUser = function(userId) {
		u.user = accountService.removeUser(userId).then(
		// success
		function(response) {
			growlService.growl('Usuário deletado com sucesso.', 'success', 1000)

			accountService.list(u.totalItemsBackend, "").then(
			// success
			function(response) {

				u.totalItems = 10;
				u.totalItemsBackend = 10;
				u.currentPage = 1;
				u.itemsPerPage = 2;
				u.itemsPerPage = 2;
				u.pages = [];

				u.allUser = response.data;
				u.totalItems = response.resultCount;
				u.users = u.preparePages(u.allUser, u.itemsPerPage, u.currentPage);
				u.cursor = response.cursor;
			},
			// fail
			function(response) {
				growlService.growl('Erro ao carregar usuários.', 'danger', 1000)
			});

		},
		// fail
		function(response) {
			growlService.growl('Erro ao deletar usuário.', 'danger', 1000)
		});
	};

});