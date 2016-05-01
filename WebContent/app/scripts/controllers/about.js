'use strict';

/**
 * @ngdoc function
 * @name webContentApp.controller:AboutCtrl
 * @description # AboutCtrl Controller of the webContentApp
 */
angular.module('webContentApp').controller('AboutCtrl', [ '$scope', '$sce', function($scope, $sce) {

	var popup = '<ul style="color: #000 !important;">';
	popup += ' <li>formerly Af&#x15F;in</li>';
	popup += ' <li>pronounced as <i>Afsheen</i></li>';
	popup += ' <li>an old word from the Seljuk Empire</li>';
	popup += ' <li>means armor</li>';
	popup += ' <li>a common name for boys and girls in Turkey and Azerbaijan</li>';
	popup += ' </ul>'
	$scope.htmlPopover = $sce.trustAsHtml(popup);

} ]);
