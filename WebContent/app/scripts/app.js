'use strict';

/**
 * @ngdoc overview
 * @name webContentApp
 * @description # webContentApp
 * 
 * Main module of the application.
 */
angular.module('webContentApp', [ 'ngAnimate', 'ngCookies', 'ngResource', 'ngRoute', 'ngSanitize', 'ngTouch', 'ui.bootstrap' ]).config(function($routeProvider) {
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
	}).when('/admin', {
		templateUrl : 'views/admin.html',
		controller : 'AdminCtrl',
		controllerAs : 'admin'
	}).when('/adminhome', {
		templateUrl : 'views/adminhome.html',
		controller : 'AdminHomeCtrl',
		controllerAs : 'adminhome'
	}).otherwise({
		templateUrl : '404.html'
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
		findCurrentPageName();
	});

	function findCurrentPageName() {
		// initial navigation bar highlight
		$(".nav li").removeClass("active");
		var path = window.location.hash;
		
		for (var i = 0; i < $(".nav li").size(); i++) {
			var li = $(".nav li")[i];
			var a = li.getElementsByTagName("a")
			var href = a[0].getAttribute("href")
			if (path == href) {
				$(li).addClass("active");
			}
		}
	}

});

angular.module('webContentApp').run(run);
run.$inject = [ '$rootScope', '$location', '$cookieStore', '$http' ];
function run($rootScope, $location, $cookieStore, $http) {

	$rootScope.$on('$locationChangeStart', function(event, next, current) {

		$rootScope.globals = $cookieStore.get('globals') || {};
		if ($rootScope.globals.currentUser) {
			$http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata;
		}

		var restrictedPage = $.inArray($location.path(), [ '', '/', '/about', '/contact', '/admin' ]) === -1;
		var loggedIn = $rootScope.globals.currentUser;
		if (restrictedPage && !loggedIn) {
			$location.path('/opps');
		}
	});
}
