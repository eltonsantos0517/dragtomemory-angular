materialAdmin.controller('cardsCtrl', function($filter, $sce, ngTableParams, tableService, $scope, cardsService, $http, growlService, $stateParams, CardsConstants) {

	/* jshint validthis: true */
	var c = this;

	$scope.CardsConstants = CardsConstants;
	
	c.today = new Date();
	
	c.list = 1;
	c.add = 0;
	c.edit = 0;
	c.card = {};
	c.cards = [];
	c.search = '';
	c.filter = '';
	c.order = "";
	c.showPagination = true;

	// Pagination Config
	c.totalItems = 500;
	c.limitBackendDefault = 500;
	c.limitBackend = c.limitBackendDefault;
	c.currentPage = 1;
	c.itemsPerPage = 3;
	c.pages = [];
	c.allItens = [];
	c.numPages = 5;
	c.maxSize = 5;
	
	c.filterBackend = $stateParams.filter;
	
	c.showMore = [];	

	cardsService.list(c.limitBackend, "", c.order, c.filterBackend).then(
	// success
	function(response) {
		c.totalItems = response.totalCount;
		c.cursor = response.cursor;

		if(response.data != null){
			c.allItens = response.data;
		}
		c.preparePages(c.allItens, c.itemsPerPage, c.currentPage);
		c.cards = c.getPage(c.currentPage);
	},
	// fail
	function(response) {
		growlService.growl('Erro ao carregar cards.', 'danger', 1000)
	});

	c.preparePages = function(itens, itemsPerPage, currentPage) {
		var i = 1;
		c.pages = [];
		var pageIndex = 0;
		var pageList = [];

		for (x in itens) {
			if (i <= itemsPerPage) {
				pageList.push(itens[x]);
				c.pages[pageIndex] = pageList;
				i++;
			} else {
				i = 1;
				pageList = [];
				pageIndex++;

				pageList.push(itens[x]);
				c.pages[pageIndex] = pageList;

				i++;
			}
		}
	};

	c.getPage = function(currentPage) {
		var pageRet = c.pages[currentPage - 1];

		if (!pageRet || pageRet.length === 0) {
			cardsService.list(c.limitBackend, c.cursor, c.order, c.filterBackend).then(
			// success
			function(response) {
				c.cursor = response.cursor;
				c.totalItems = response.totalCount;
				
				Array.prototype.push.apply(c.allItens, response.data);
				if(c.allItens.lenght > 1){
					c.preparePages(c.allItens, c.itemsPerPage, c.currentPage);
					c.cards = c.getPage(c.currentPage);
				}
			},
			// fail
			function(response) {
				growlService.growl('Erro ao carregar cards.', 'danger', 1000)
			});
		}
		return pageRet;
	};

	c.pageChanged = function() {
		c.cards = c.getPage(c.currentPage);
	};
	
	c.save = function() {
		
		//if c.edit == 1 mostrar dialog perguntando se quer voltar para estagio 1 ou não
		if(c.edit === 1){
			swal({
				title : "Deseja voltar para o primeiro estágio?",
				text : "Caso não volte o aprendizado poderá não ser completo",
				type : "warning",
				showCancelButton : true,
				confirmButtonColor : "#DD6B55",
				confirmButtonText : "Sim, volte!",
				cancelButtonText : "Não, não volte!",
				closeOnConfirm : false,
				closeOnCancel : false
			},function(isConfirm){
				if(isConfirm){
					swal("Confirmado!", "Card irá para o primeiro estágio", "success");
					c.saveCard(true);
				}else{
					swal("Não confirmado!", "Card ficara no seu estágio atual", "error");
					c.saveCard(false);
				}
			});
		}else{
			c.saveCard(false);
		}
	};
	
	c.saveCard = function(isChangeStage){
		c.card.changeStage = isChangeStage;
		cardsService.save(c.card).then(
				// success
				function(response) {
					c.list = 1;
					c.add = 0;
					c.edit = 0;

					if (!c.card.objectId) {
						c.totalItems = c.totalItems + 1;
						c.card = response.data;

						var newItens = [ c.card ];

						Array.prototype.push.apply(c.allItens, newItens);
						c.preparePages(c.allItens, c.itemsPerPage, c.currentPage);
						c.cards = c.getPage(c.currentPage);
						c.card = {};

						growlService.growl('Card criado com sucesso, ficará visivel na aba de todos os cards.', 'success', 1000);
					} else {

						for (var i = c.allItens.length - 1; i >= 0; i--) {
							if (c.allItens[i].objectId === c.card.objectId) {
								c.allItens[i] = c.card;
								break;
							}
						}

						c.preparePages(c.allItens, c.itemsPerPage, c.currentPage);
						c.cards = c.getPage(c.currentPage);
						c.card = {};

						growlService.growl('Card atualizado com sucesso.', 'success', 1000);
					}
				},
				// fail
				function(response) {
					growlService.growl(response.data.errorMessage, 'danger');
				});
	}

	c.initAdd = function() {
		c.list = 0;
		c.add = 1;
		c.edit = 0;

		c.card = {};
	};
	
	c.done = function(cardId){
		cardsService.done(cardId).then(
				function(response){
					c.refresh();
					growlService.growl('Card relembrado com sucesso', 'success', 1000)
				},
				function(error){
					growlService.growl(error.data.errorMessage, 'danger', 1000);
				}
		);		
	}

	c.initEdit = function(cardId) {
		c.list = 0;
		c.add = 0;
		c.edit = 1;

		c.card = cardsService.getById(cardId);
	};

	c.refresh = function() {
		c.limitBackend = c.allItens.length;
		cardsService.list(c.limitBackend, "", c.order, c.filterBackend).then(
		// success
		function(response) {
			c.cursor = response.cursor;
			c.totalItems = response.totalCount;
			c.limitBackend = c.limitBackendDefault;

			c.allItens = response.data;
			c.preparePages(c.allItens, c.itemsPerPage, c.currentPage);
			c.cards = c.getPage(c.currentPage);
		},
		// fail
		function(response) {
			c.limitBackend = c.limitBackendDefault;
			growlService.growl('Erro ao carregar card.', 'danger', 1000)
		});

	};

	c.back = function() {
		c.list = 1;
		c.add = 0;
		c.edit = 0;
	};

	c.removeCard = function(cardId) {
		// confirm
		swal({
			title : "Are you sure?",
			text : "You will not be able to recover this card!",
			type : "warning",
			showCancelButton : true,
			confirmButtonColor : "#DD6B55",
			confirmButtonText : "Yes, delete it!",
			cancelButtonText : "No, cancel!",
			closeOnConfirm : false,
			closeOnCancel : false
		}, function(isConfirm) {
			if (isConfirm) {
				c.card = cardsService.removeCard(cardId).then(
				// success
				function(response) {
					// c.currentPage = 1;

					for (var i = c.allItens.length - 1; i >= 0; i--) {
						if (c.allItens[i].objectId === cardId) {
							c.allItens.splice(i, 1);
							c.totalItems = c.totalItems - 1;
							break;
						}
					}

					c.preparePages(c.allItens, c.itemsPerPage, c.currentPage);

					var pageRet = c.pages[c.currentPage - 1];
					if ((pageRet && pageRet.length > 0) || c.allItens.length === 0) {
						c.cards = c.getPage(c.currentPage);
					}

					swal("Deleted!", "The card has been deleted.", "success");
				},
				// fail
				function(response) {
					swal("ERROR", "error");
				});
			} else {
				swal("Cancelled", "The card is safe :)", "error");
			}
		});
	};
	
	//FOR FILTER

	c.changeOrder = function(order) {
		c.order = order;
		c.refresh();
	};
	
	c.emptySearch = function(){
		c.filter = '';
		c.search = '';
	};
	
	c.changeModel = function(){
		c.search = {};
		//Adicionar no objeto search sobre quais campos da entidade será aplicado o filtro
		c.search.title = c.filter;
		c.search.text = c.filter;
	}
	
	c.getInteratorList = function(){
		if(!c.allItens || c.allItens == null){
			return c.cards;
		}
		
		if(c.allItens.length > 0){
			if(c.search === ''){
				c.cards = c.getPage(c.currentPage);
				return c.cards;
			}else{
				return c.allItens;
			}
		}
	}
	
	c.viewAll = function(index){
		
		if(c.showMore[index] && c.showMore[index] != null){
			if(c.showMore[index].numLimit < 9999){
				c.showMore[index].numLimit = 9999;
				c.showMore[index].expand = "Show Less";
			}else{
				c.showMore[index].numLimit = 50;
				c.showMore[index].expand = "View All";
			}
		}else{
			c.showMore[index] = {};
			c.showMore[index].numLimit = 9999;
			c.showMore[index].expand = "Show Less";
		}
	}
	
	c.getDiffInHours = function(date){
		return  moment.duration(moment(new Date(date)).diff(moment(c.today))).asHours();
	}
	
	c.executeJob = function(){
		cardsService.executeJob().then(
				// success
				function(response) {
					growlService.growl('Job processado com sucesso', 'success', 1000);
				},
				// fail
				function(response) {
					growlService.growl('Erro ao executar job', 'danger', 1000);
				});
		
	}
	
})
.constant('CardsConstants',{
	"LAST_STAGE": 5
});