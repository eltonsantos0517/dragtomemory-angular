materialAdmin.controller('cardsCtrl', function($filter, $sce, ngTableParams, tableService, $scope, cardsService, $http, growlService) {

	/* jshint validthis: true */
	var u = this;

	u.list = 1;
	u.add = 0;
	u.edit = 0;
	u.user = {};
	u.users = [];
	u.search = '';
	u.filter = '';
	u.order = "";
	u.showPagination = true;

	// Pagination Config
	u.totalItems = 500;
	u.limitBackendDefault = 500;
	u.limitBackend = u.limitBackendDefault;
	u.currentPage = 1;
	u.itemsPerPage = 3;
	u.pages = [];
	u.allItens = [];
	u.numPages = 5;
	u.maxSize = 5;

	cardsService.list(u.limitBackend, "", u.order).then(
	// success
	function(response) {
		u.totalItems = response.totalCount;
		u.cursor = response.cursor;

		u.allItens = response.data;
		u.preparePages(u.allItens, u.itemsPerPage, u.currentPage);
		u.users = u.getPage(u.currentPage);
	},
	// fail
	function(response) {
		growlService.growl('Erro ao carregar cards.', 'danger', 1000)
	});

	u.preparePages = function(itens, itemsPerPage, currentPage) {
		var i = 1;
		u.pages = [];
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
		var pageRet = u.pages[currentPage - 1];

		if (!pageRet || pageRet.length === 0) {
			cardsService.list(u.limitBackend, u.cursor, u.order).then(
			// success
			function(response) {
				u.cursor = response.cursor;
				u.totalItems = response.totalCount;

				Array.prototype.push.apply(u.allItens, response.data);
				u.preparePages(u.allItens, u.itemsPerPage, u.currentPage);
				u.users = u.getPage(u.currentPage);
			},
			// fail
			function(response) {
				growlService.growl('Erro ao carregar cards.', 'danger', 1000)
			});
		}
		return pageRet;
	};

	u.pageChanged = function() {
		u.users = u.getPage(u.currentPage);
	};
	u.save = function() {
		cardsService.save(u.user).then(
		// success
		function(response) {
			u.list = 1;
			u.add = 0;
			u.edit = 0;

			if (!u.user.objectId) {
				u.totalItems = u.totalItems + 1;
				u.user.objectId = response.data.objectId;

				var newItens = [ u.user ];

				Array.prototype.push.apply(u.allItens, newItens);
				u.preparePages(u.allItens, u.itemsPerPage, u.currentPage);
				u.users = u.getPage(u.currentPage);
				u.user = {};

				growlService.growl('Card criado com sucesso.', 'success', 1000);
			} else {

				for (var i = u.allItens.length - 1; i >= 0; i--) {
					if (u.allItens[i].objectId === u.user.objectId) {
						u.allItens[i] = u.user;
						break;
					}
				}

				u.preparePages(u.allItens, u.itemsPerPage, u.currentPage);
				u.users = u.getPage(u.currentPage);
				u.user = {};

				growlService.growl('Card atualizado com sucesso.', 'success', 1000);
			}
		},
		// fail
		function(response) {
			growlService.growl(response.data.errorMessage, 'danger');
		});
	};

	u.initAdd = function() {
		u.list = 0;
		u.add = 1;
		u.edit = 0;

		u.user = {};
	};

	u.initEdit = function(userId) {
		u.list = 0;
		u.add = 0;
		u.edit = 1;

		u.user = cardsService.getById(userId);
	};

	u.refresh = function() {
		u.limitBackend = u.allItens.length;
		cardsService.list(u.limitBackend, "", u.order).then(
		// success
		function(response) {
			u.cursor = response.cursor;
			u.totalItems = response.totalCount;
			u.limitBackend = u.limitBackendDefault;

			u.allItens = response.data;
			u.preparePages(u.allItens, u.itemsPerPage, u.currentPage);
			u.users = u.getPage(u.currentPage);
		},
		// fail
		function(response) {
			u.limitBackend = u.limitBackendDefault;
			growlService.growl('Erro ao carregar card.', 'danger', 1000)
		});

	};

	u.back = function() {
		u.list = 1;
		u.add = 0;
		u.edit = 0;
	};

	u.removeUser = function(userId) {
		// confirm
		swal({
			title : "Are you sure?",
			text : "You will not be able to recover this user!",
			type : "warning",
			showCancelButton : true,
			confirmButtonColor : "#DD6B55",
			confirmButtonText : "Yes, delete it!",
			cancelButtonText : "No, cancel!",
			closeOnConfirm : false,
			closeOnCancel : false
		}, function(isConfirm) {
			if (isConfirm) {
				u.user = cardsService.removeUser(userId).then(
				// success
				function(response) {
					// u.currentPage = 1;

					for (var i = u.allItens.length - 1; i >= 0; i--) {
						if (u.allItens[i].objectId === userId) {
							u.allItens.splice(i, 1);
							u.totalItems = u.totalItems - 1;
							break;
						}
					}

					u.preparePages(u.allItens, u.itemsPerPage, u.currentPage);

					var pageRet = u.pages[u.currentPage - 1];
					if ((pageRet && pageRet.length > 0) || u.allItens.length === 0) {
						u.users = u.getPage(u.currentPage);
					}

					swal("Deleted!", "The user has been deleted.", "success");
				},
				// fail
				function(response) {
					swal("ERROR", "error");
				});
			} else {
				swal("Cancelled", "The user is safe :)", "error");
			}
		});
	};
	
	//FOR FILTER

	u.changeOrder = function(order) {
		u.order = order;
		u.refresh();
	};
	
	u.emptySearch = function(){
		u.filter = '';
		u.search = '';
	};
	
	u.changeModel = function(){
		u.search = {};
		//Adicionar no objeto search sobre quais campos da entidade será aplicado o filtro
		u.search.firstName = u.filter;
		u.search.lastName = u.filter;
		u.search.email = u.filter;
	}
	
	u.getInteratorList = function(){
		
		if(u.allItens.length > 0){
			if(u.search === ''){
				u.users = u.getPage(u.currentPage);
				return u.users;
			}else{
				return u.allItens;
			}
		}
	}
	
});