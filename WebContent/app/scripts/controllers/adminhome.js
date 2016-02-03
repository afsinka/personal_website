'use strict';

angular.module('webContentApp').controller('AdminHomeCtrl', [ '$scope', '$http', '$location', function($scope, $http, $location) {

	$http({
		url : 'rest/admin',
		method : "GET"
	}).then(function(response) {
		$scope.messages = response.data;
	}, function(response) {

	});

} ]);