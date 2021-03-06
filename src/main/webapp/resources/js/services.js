materialAdmin

// =========================================================================
// Header Messages and Notifications list Data
// =========================================================================

.service('accountService', [ 'Restangular', 'LoginRestangular', function(Restangular, LoginRestangular) {

	this.list = function(limit, cursor, order) {
		return Restangular.one("user").get({
			'limit' : limit,
			'cursor' : cursor,
			'order' : order
		});
	}

	this.getById = function(userId) {
		return Restangular.one('user', userId).get().$object;
	}
	
	this.getCurrent = function() {
		return LoginRestangular.one('users/current').get();
	}

	this.forgotPassword = function(email) {
		return Restangular.all("forgotPassword").post(email);
	}

	this.recoveryPassword = function(token, newPassword, newPasswordAgain) {
		var data = {}
		data.token = token;
		data.newPassword = newPassword;
		data.newPasswordAgain = newPasswordAgain;
		return Restangular.all("recoveryPassword").post(data)
	}

	this.save = function(user) {
		if (user.objectId != null) {
			return Restangular.one('user').customPUT(user);
		} else {
			return Restangular.all("user").post(user);
		}
	}

	this.removeUser = function(userId) {
		return Restangular.one("user", userId).remove();
	}

	this.login = function(user) {
		return LoginRestangular.all("login").post(user);
	}

	this.register = function(user) {
		return LoginRestangular.all('register').post(user);
	}

	this.facebookAuthenticate = function(user) {
		return LoginRestangular.all('facebookAuthenticate').post(user);
	}
} ]).directive('hasPermission', function(permissions) {
	return {
		link : function(scope, element, attrs) {
			if (!_.isString(attrs.hasPermission)) {
				throw 'hasPermission value must be a string'
			}
			var value = attrs.hasPermission.trim();
			var notPermissionFlag = value[0] === '!';
			if (notPermissionFlag) {
				value = value.slice(1).trim();
			}

			function toggleVisibilityBasedOnPermission() {
				var hasPermission = permissions.hasPermission(value);
				if (hasPermission && !notPermissionFlag || !hasPermission && notPermissionFlag) {
					element.show();
				} else {
					element.hide();
				}
			}

			toggleVisibilityBasedOnPermission();
			scope.$on('permissionsChanged', toggleVisibilityBasedOnPermission);
		}
	};
})
.service('pushService', ['Restangular', function(Restangular) {
	
	this.sendMessage = function(pushTO){
		return Restangular.all('sendMessage').post(pushTO);
	}

}])

// =========================================================================
// Header Messages and Notifications list Data
// =========================================================================

.service('messageService', [ '$resource', function($resource) {
	this.getMessage = function(img, user, text) {
		var gmList = $resource("data/messages-notifications.json");

		return gmList.get({
			img : img,
			user : user,
			text : text
		});
	}
} ])

// =========================================================================
// Best Selling Widget Data (Home Page)
// =========================================================================

.service('bestsellingService', [ '$resource', function($resource) {
	this.getBestselling = function(img, name, range) {
		var gbList = $resource("data/best-selling.json");

		return gbList.get({
			img : img,
			name : name,
			range : range,
		});
	}
} ])

// =========================================================================
// Todo List Widget Data
// =========================================================================

.service('todoService', [ '$resource', function($resource) {
	this.getTodo = function(todo) {
		var todoList = $resource("data/todo.json");

		return todoList.get({
			todo : todo
		});
	}
} ])

// =========================================================================
// Recent Items Widget Data
// =========================================================================

.service('recentitemService', [ '$resource', function($resource) {
	this.getRecentitem = function(id, name, price) {
		var recentitemList = $resource("data/recent-items.json");

		return recentitemList.get({
			id : id,
			name : name,
			price : price
		})
	}
} ])

// =========================================================================
// Recent Posts Widget Data
// =========================================================================

.service('recentpostService', [ '$resource', function($resource) {
	this.getRecentpost = function(img, user, text) {
		var recentpostList = $resource("data/messages-notifications.json");

		return recentpostList.get({
			img : img,
			user : user,
			text : text
		})
	}
} ])

// =========================================================================
// Data Table
// =========================================================================

.service('tableService', [ function() {
	this.data = [ {
		"id" : 10238,
		"name" : "Marc Barnes",
		"email" : "marc.barnes54@example.com",
		"username" : "MarcBarnes",
		"contact" : "(382)-122-5003"
	}, {
		"id" : 10243,
		"name" : "Glen Curtis",
		"email" : "glen.curtis11@example.com",
		"username" : "GlenCurtis",
		"contact" : "(477)-981-4948"
	}, {
		"id" : 10248,
		"name" : "Beverly Gonzalez",
		"email" : "beverly.gonzalez54@example.com",
		"username" : "BeverlyGonzalez",
		"contact" : "(832)-255-5161"
	}, {
		"id" : 10253,
		"name" : "Yvonne Chavez",
		"email" : "yvonne.chavez@example.com",
		"username" : "YvonneChavez",
		"contact" : "(477)-446-3715"
	}, {
		"id" : 10234,
		"name" : "Melinda Mitchelle",
		"email" : "melinda@example.com",
		"username" : "MelindaMitchelle",
		"contact" : "(813)-716-4996"

	}, {
		"id" : 10239,
		"name" : "Shannon Bradley",
		"email" : "shannon.bradley42@example.com",
		"username" : "ShannonBradley",
		"contact" : "(774)-291-9928"
	}, {
		"id" : 10244,
		"name" : "Virgil Kim",
		"email" : "virgil.kim81@example.com",
		"username" : "VirgilKim",
		"contact" : "(219)-181-7898"
	}, {
		"id" : 10249,
		"name" : "Letitia Robertson",
		"email" : "letitia.rober@example.com",
		"username" : "Letitia Robertson",
		"contact" : "(647)-209-4589"
	}, {
		"id" : 10237,
		"name" : "Claude King",
		"email" : "claude.king22@example.com",
		"username" : "ClaudeKing",
		"contact" : "(657)-988-8701"
	}, {
		"id" : 10242,
		"name" : "Roland Craig",
		"email" : "roland.craig47@example.com",
		"username" : "RolandCraig",
		"contact" : "(932)-935-9471"
	}, {
		"id" : 10247,
		"name" : "Colleen Parker",
		"email" : "colleen.parker38@example.com",
		"username" : "ColleenParker",
		"contact" : "(857)-459-2792"
	}, {
		"id" : 10252,
		"name" : "Leah Jensen",
		"email" : "leah.jensen27@example.com",
		"username" : "LeahJensen",
		"contact" : "(861)-275-4686"
	}, {
		"id" : 10236,
		"name" : "Harold Martinez",
		"email" : "martinez67@example.com",
		"username" : "HaroldMartinez",
		"contact" : "(836)-634-9133"
	}, {
		"id" : 10241,
		"name" : "Keith Lowe",
		"email" : "keith.lowe96@example.com",
		"username" : "KeithLowe",
		"contact" : "(778)-787-3100"
	}, {
		"id" : 10246,
		"name" : "Charles Walker",
		"email" : "charles.walker90@example.com",
		"username" : "CharlesWalker",
		"contact" : "(486)-440-4716"
	}, {
		"id" : 10251,
		"name" : "Lillie Curtis",
		"email" : "lillie.curtis12@example.com",
		"username" : "LillieCurtis",
		"contact" : "(342)-510-2258"
	}, {
		"id" : 10235,
		"name" : "Genesis Reynolds",
		"email" : "genesis@example.com",
		"username" : "GenesisReynolds",
		"contact" : "(339)-375-1858"
	}, {
		"id" : 10240,
		"name" : "Oscar Palmer",
		"email" : "oscar.palmer24@example.com",
		"username" : "OscarPalmer",
		"contact" : "(544)-270-9912"
	}, {
		"id" : 10245,
		"name" : "Lena Bishop",
		"email" : "Lena Bishop",
		"username" : "LenaBishop",
		"contact" : "(177)-521-1556"
	}, {
		"id" : 10250,
		"name" : "Kent Nguyen",
		"email" : "kent.nguyen34@example.com",
		"username" : "KentNguyen",
		"contact" : "(506)-533-6801"
	} ];
} ])

// =========================================================================
// Malihu Scroll - Custom Scroll bars
// =========================================================================
.service('scrollService', function() {
	var ss = {};
	ss.malihuScroll = function scrollBar(selector, theme, mousewheelaxis) {
		// $(selector).mCustomScrollbar({
		// theme : theme,
		// scrollInertia : 100,
		// axis : 'yx',
		// mouseWheel : {
		// enable : true,
		// axis : mousewheelaxis,
		// preventDefault : true
		// }
		// });
	}

	return ss;
})

// ==============================================
// BOOTSTRAP GROWL
// ==============================================

.service(
		'growlService',
		function() {
			var gs = {};
			gs.growl = function(message, type, timer) {
				$.growl({
					message : message
				}, {
					type : type,
					allow_dismiss : false,
					label : 'Cancel',
					className : 'btn-xs btn-inverse',
					placement : {
						from : 'top',
						align : 'right'
					},
					delay : 2500,
					animate : {
						enter : 'animated bounceIn',
						exit : 'animated bounceOut'
					},
					offset : {
						x : 20,
						y : 85
					},
					spacing : 10,
					z_index : 1031,
					delay : 2500,
					timer : timer,
					url_target : '_blank',
					mouse_over : false,
					icon_type : 'class',
					template : '<div data-growl="container" class="alert" role="alert">'
							+ '<button type="button" class="close" data-growl="dismiss">'
							+ '<span aria-hidden="true">&times;</span>' + '<span class="sr-only">Close</span>' + '</button>'
							+ '<span data-growl="icon"></span>' + '<span data-growl="title"></span>'
							+ '<span data-growl="message"></span>' + '<a href="#" data-growl="url"></a>' + '</div>'
				});
			}

			return gs;

}).service('cardsService', [ 'Restangular', function(Restangular, LoginRestangular) {
	
	this.list = function(limit, cursor, order, filter) {
		
		if(cursor == null){
			cursor = "";
		}
		
		return Restangular.one("card").get({
			'limit' : limit,
			'cursor' : cursor,
			'order' : order,
			'filter' : filter
		});
	}
	
	this.save = function(card) {
		if (card.objectId != null) {
			return Restangular.copy(card).put();
		} else {
			return Restangular.all("card").post(card);
		}
	}
	
	this.getById = function(cardId) {
		return Restangular.one('card', cardId).get().$object;
	}
	
	this.removeUser = function(cardId) {
		return Restangular.one("card", cardId).remove();
	}
	
	this.done = function(cardId){
		return Restangular.all("card/done").customPUT(cardId);
	}
	
	this.executeJob = function(){
		Restangular.all("job").customPUT();
	}
}])


