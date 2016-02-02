'use strict';

angular.module('webContentApp').controller('ContactCtrl', [ '$scope', '$http', 
                                                            function($scope, $http) {
	
	$http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";
    
    $scope.sendPost = function() {
    	
    	if ($scope.message === undefined || ($scope.message != null
    			&& $scope.message.trim().toString().length == 0)) {
    		$scope.result = "message should not be empty!";
    		return;
    	}
    	
        $http({
            url : 'rest/sendmessage',
            method : "POST",
            data : {
                'message' : $scope.message
            }
        }).then(function(response) {
        	//console.log("SUCCESS");
        	//console.log(response.data);
        	$scope.result = response.data.result;
        }, function(response) {
        	//console.log("FAIL");
        	//console.log(response.data);
        	$scope.result = "opps! something went wrong, sorry!";
        });
        
        $('#submitMsg').prop('disabled', true);
 
    };
    twttr.widgets.load();

}]);



