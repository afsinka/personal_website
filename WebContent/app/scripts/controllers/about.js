'use strict';

/**
 * @ngdoc function
 * @name webContentApp.controller:AboutCtrl
 * @description # AboutCtrl Controller of the webContentApp
 */
angular.module('webContentApp').controller('AboutCtrl', [ '$scope', '$sce', function($scope, $sce) {

	var popup = '';
	popup += ' pronounced as af-shin';
	popup += ' ';
	$scope.htmlPopover = $sce.trustAsHtml(popup);

} ]);
