materialAdmin.controller('userProfileCtrl',
		function(growlService, accountService, $scope) {
	
			p = this;
			p.user = {};
			accountService.getCurrent().
				then(
					function(response){
						p.user = response.data.data;
					},
					function(error){
						growlService.growl(error.data.errorMessage, 'danger');
					}
				)
			;			
			// Edit
			p.editSummary = 0;
			p.editInfo = 0;
			p.editContact = 0;

			p.submit = function(item, message) {
				accountService.save(p.user).then(
						function(response){
							
							if (item === 'profileSummary') {
								p.editSummary = 0;
							}

							if (item === 'profileInfo') {
								p.editInfo = 0;
							}

							if (item === 'profileContact') {
								p.editContact = 0;
							}

							growlService.growl(message
									+ ' has updated Successfully!', 'success');
							p.user.password = null;
							p.user.passwordAgain = null;
							
						},
						function(error){
							growlService.growl(error.data.errorMessage, 'danger');
							p.user.password = null;
							p.user.passwordAgain = null;
						});
			}
			
			p.importImage = function(){
			    var input=document.getElementById('inputFile');
			    input.click();	
			};
			
})

.directive('imgImport',['accountService', 'growlService', function(accountService, growlService) {
  return function(scope, elm, attrs) {
    elm.bind('change', function( evt ) {
      scope.$apply(function() {
    	  
        scope.images = evt.target.files;
        var reader = new FileReader();
        
        reader.addEventListener("load", function () {
        	 scope.$apply(function() {
        		 scope.pctrl.user.profileImage = reader.result;
        	 });
        	 
        	 accountService.save(scope.pctrl.user).then(
        			 function(response){
        				 growlService.growl("Profile image has updated Successfully!", 'success');
        			 },
        			 function(error){
        				 growlService.growl(error.data.errorMessage, 'danger');
        			 });

          }, false);
        
        reader.readAsDataURL(scope.images[0]);
      });
    });
  };
}]);