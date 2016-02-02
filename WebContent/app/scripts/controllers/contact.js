'use strict';

angular.module('webContentApp').controller('ContactCtrl', [ '$scope', '$http', 
                                                            function($scope, $http) {
 
	twttr.widgets.load();
	
	$http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";
    
    $scope.sendPost = function() {
        $http({
            url : 'rest/sendmessage',
            method : "POST",
            data : {
                'message' : $scope.message
            }
        }).then(function(response) {
        	//console.log("SUCCESS");
            //console.log(response.data);
            $scope.result = response.data;
        }, function(response) {
        	//console.log("FAIL");
            //console.log(response.data);
            $scope.result = response.data;
        });
 
    };

}]);



