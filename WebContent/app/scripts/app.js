'use strict';

/**
 * @ngdoc overview
 * @name webContentApp
 * @description # webContentApp
 * 
 * Main module of the application.
 */
angular.module(
		'webContentApp',
		[ 'ngAnimate', 'ngCookies', 'ngResource', 'ngRoute', 'ngSanitize',
				'ngTouch' ]).config(function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl : 'views/main.html',
		controller : 'MainCtrl',
		controllerAs : 'main'
	}).when('/about', {
		templateUrl : 'views/about.html',
		controller : 'AboutCtrl',
		controllerAs : 'about'
	}).when('/contact', {
		templateUrl : 'views/contact.html',
		controller : 'ContactCtrl',
		controllerAs : 'contact'
	}).otherwise({
        templateUrl: '404.html'
    });

	$(".nav li").on("click", function() {
		$(".nav li").removeClass("active");
		$(this).addClass("active");
	});

	$(".lamp").on("click", function(e) {
		e.preventDefault();
		if ($("body").attr("class").indexOf("enlight") > -1) {
			$("body, input, textarea").removeClass("enlight");
		} else {
			$("body, input, textarea").addClass("enlight");
		}
	});

	function menuSizer(win) {
		if (win.width() < 700) {
			$('.navbar').css('display', 'block');
			$('.demo-appheader-logo').css('display', 'none');
		} else {
			$('.navbar').css('display', 'none');
			$('.demo-appheader-logo').css('display', 'block');
		}
	}

	$(window).on('resize', function() {
		var win = $(this);
		menuSizer(win);

	});
	$(document).ready(function() {
		var win = $(window);
		menuSizer(win);
	});

});


