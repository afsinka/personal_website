'use strict';

angular.module('webContentApp').controller('ContactCtrl', [ '$scope', '$http', 
                                                            function($scope, $http) {
	
	//recaptcha keys
	//TODO change in production
	$scope.datakey = "6LfVJhYTAAAAAAQoMKwMDnu_xFqzN7rjn7PqyJwP";
	$scope.secretkey = "6LfVJhYTAAAAAMMD-EWNyUGdQdaFaLiG1m5Qal3Y";
	
	$http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";
    
    $scope.sendPost = function() {
    	
    	if ($scope.message === undefined || ($scope.message != null
    			&& $scope.message.trim().toString().length == 0)) {
    		$scope.result = "message should not be empty!";
    		return;
    	}
    	
    	var g_recaptcha_response = document.getElementById("g-recaptcha-response").value;
    	
        $http({
            url : 'rest/sendmessage',
            method : "POST",
            data : JSON.stringify({
            		message:$scope.message,
            		name:$scope.name,
            		g_recaptcha_response:g_recaptcha_response,
            		secretkey:$scope.secretkey})
        }).then(function(response) {
        	//console.log("SUCCESS");
        	//console.log(response.data);
        	$scope.result = response.data.result;
        	$('#submitMsg').prop('disabled', true);
        }, function(response) {
        	//console.log("FAIL");
        	//console.log(response);
        	if (response.status == 401) {
        		$scope.result = response.data.result;
        	} else {
        		$scope.result = "opps! something went wrong, sorry!";
        	}
        });       
 
    };
    
    if (typeof twttr === 'undefined') {
    	//TODO
    } else {
    	//twitter load
    	twttr.widgets.load();
    }
    
    //google recaptcha load
	$.getScript("https://www.google.com/recaptcha/api.js", function(){
			
	});
	
}]);



