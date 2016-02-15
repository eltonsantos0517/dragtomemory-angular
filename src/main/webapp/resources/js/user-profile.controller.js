materialAdmin.controller('userProfileCtrl',
		function(growlService, accountService) {
	
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
									+ ' has updated Successfully!', 'inverse');
							
						},
						function(error){
							growlService.growl(error.data.errorMessage, 'danger');
						});
				
				
			}
})